package com.jnu.jnucross.adapter;
// by yanlin
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.*;

public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);
    private static final HikariConfig hikariConfig = new HikariConfig("conf/database.properties");
    private static final HikariDataSource HikaridataSource = new HikariDataSource(hikariConfig);

    public static Connection getConnection() throws SQLException {
        return HikaridataSource.getConnection();
    }
    public class TransactionNotFoundException extends RuntimeException {
        public TransactionNotFoundException(String message) {
            super(message);
        }
        public TransactionNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateTransaction(TransactionInfo transaction, String type) {
        Connection connection = null;
        try
        {
            connection = DatabaseUtil.getConnection();
            connection.setAutoCommit(false); // 关闭自动提交

            logger.info("Connected to the database.");
            // Check if the transaction already exists in the database
            if (transactionExists(connection, transaction.getTransactionID())) {
                // The transaction is already in the database, update it
                if (Objects.equals(type, "status")){
                    updateTransactionStatus(connection,transaction);
                }
                else if (Objects.equals(type, "success")){
                    updateTransactionSuccess(connection,transaction);
                }
                else if (Objects.equals(type, "fail")){
                    updateTransactionFail(connection,transaction);
                }
            } else {
                // The transaction is not in the database, insert transaction
                insertTransaction(connection,transaction);
                //logger.error("transaction not in the database");
            }
            // 手动提交事务
            connection.commit();
        } catch (SQLException e) {
            //logger.error("Error connecting to the database: " + e.getMessage(), e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                logger.error("Error rolling back transaction: " + rollbackEx.getMessage(), rollbackEx);
            }
            logger.error("Error connecting to the database: " + e.getMessage(), e);
        }
        finally {
            // 恢复自动提交并关闭连接
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException closeEx) {
                logger.error("Error closing connection: " + closeEx.getMessage(), closeEx);
            }
        }
    }

    private static void insertTransaction(Connection connection, TransactionInfo transaction) throws SQLException {
        String insertQuery = "INSERT INTO transaction(id, state, cross_transaction_id) " +
                "VALUES (?, ?, ?)";
        logger.info("Inserting a new row into the database.");

        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setInt(1, transaction.getTransactionID());
            insertStatement.setInt(2, transaction.getStatus());
            insertStatement.setString(3, transaction.getXaTransactionID());
            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Data inserted successfully.");
            } else {
                logger.info("Failed to insert data.");
            }
        }
    }

    // Utility method to check if a transaction already exists in the database
    private static boolean transactionExists(Connection connection, int transactionID) throws SQLException {
        String selectQuery = "SELECT COUNT(*) FROM transaction WHERE id = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, transactionID);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

    private static boolean XAtransactionExists(Connection connection, String XAtransactionID) throws SQLException {
        String selectQuery = "SELECT COUNT(*) FROM cross_transaction WHERE id = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, XAtransactionID);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

    private static void updateTransactionStatus(Connection connection, TransactionInfo transaction) throws SQLException {
        String updateQuery = "UPDATE transaction SET " +
                "state = ? " +
                "WHERE id = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, transaction.getStatus());
            //updateStatement.setObject(2, transaction.getEndTimestamp());
            updateStatement.setInt(2, transaction.getTransactionID());
            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Data updated successfully.");
            } else {
                logger.info("Failed to update data.");
            }
        }
    }

    private static void updateTransactionSuccess(Connection connection, TransactionInfo transaction) {
        String updateQuery = "UPDATE transaction SET " +
                "state = ?, transaction_time = ?, chain_height= ?, hash= ? " +
                "WHERE id = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, transaction.getStatus());
            updateStatement.setObject(2, transaction.getEndTimestamp());
            updateStatement.setInt(3, transaction.getBlockNumber());
            updateStatement.setString(4, transaction.getTxHash());
            updateStatement.setInt(5, transaction.getTransactionID());

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Data updated successfully.");
            } else {
                logger.info("Failed to update data.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateTransactionFail(Connection connection, TransactionInfo transaction) {
        String updateQuery = "UPDATE transaction SET " +
                "state = ? " +
                "WHERE id = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, transaction.getStatus());
            //updateStatement.setObject(2, transaction.getEndTimestamp());
            //updateStatement.setString(3, transaction.getResponse().getMessage());
            updateStatement.setInt(2, transaction.getTransactionID());

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Data updated successfully.");
            } else {
                logger.info("Failed to update data.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateTransactionsRollback(List<Integer>transactionIDs, Map<Integer,ZonedDateTime> rollbackTimes) {
        Connection connection = null;
        try
        {
            connection = DatabaseUtil.getConnection();
            connection.setAutoCommit(false); // 关闭自动提交
            logger.info("Connected to the database. Now rollback the transaction");

            for (Integer transactionID: transactionIDs){
                ZonedDateTime endTime = rollbackTimes.get(transactionID);
                //updateTransactionRollbackStatus(connection,transactionID, endTime);
                String updateQuery = "UPDATE transaction SET " +
                        "state = ? " +
                        "WHERE id = ?";

                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, Adapter.TransactionStatus.rollbacked.ordinal());
                    //updateStatement.setObject(2, endTime);
                    updateStatement.setInt(2, transactionID);
                    int rowsUpdated = updateStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        logger.info("Data updated successfully.");
                    } else {
                        logger.info("Failed to update data.");
                    }
                }
            }
            connection.commit();

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                logger.error("Error rolling back transaction: " + rollbackEx.getMessage(), rollbackEx);
            }
            logger.error("Error connecting to the database: " + e.getMessage(), e);
        }
        finally {
            // 恢复自动提交并关闭连接
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException closeEx) {
                logger.error("Error closing connection: " + closeEx.getMessage(), closeEx);
            }
        }
    }

    public static  List<Integer> findTransactionFromXA(String XAtransactionID){
        List<Integer> transactionIds = new ArrayList<>();
        try(Connection connection = DatabaseUtil.getConnection()) {
            String selectQuery = "SELECT id FROM transaction WHERE cross_transaction_id = ? ";

            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, XAtransactionID);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int transactionId = resultSet.getInt("id");
                        logger.info("transactionId is "+transactionId);
                        transactionIds.add(transactionId);
                    }
                    return transactionIds;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateXATransactionFinish(int status, String XATransactionID, ZonedDateTime endTime){
        Connection connection = null;
        try
        {
            connection = DatabaseUtil.getConnection();
            connection.setAutoCommit(false);
            if (XAtransactionExists(connection, XATransactionID))
            {
                String updateQuery = "UPDATE cross_transaction SET " +
                        "state = ?, end_time = ? " +
                        "WHERE id = ?";

                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, status);
                    updateStatement.setObject(2, endTime);
                    updateStatement.setString(3, XATransactionID);
                    int rowsUpdated = updateStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        logger.info("Data updated successfully.");
                        connection.commit();
                    } else {
                        logger.info("Failed to update data.");
                    }

                } catch (SQLException e) {
                    connection.rollback();
                    throw new RuntimeException(e);
                }
            }
        }
        catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                logger.error("Error rolling back transaction: " + rollbackEx.getMessage(), rollbackEx);
            }
            throw new RuntimeException(e);
        }
        finally {
            // 恢复自动提交并关闭连接
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException closeEx) {
                logger.error("Error closing connection: " + closeEx.getMessage(), closeEx);
            }
        }
    }

    public static void updateXATransaction(int status, String XATransactionID){
        Connection connection = null;
        try
        {
            connection = DatabaseUtil.getConnection();
            connection.setAutoCommit(false);
            logger.info("connecting to database for XATransaction");

            if (XAtransactionExists(connection, XATransactionID)) {

                String updateQuery = "UPDATE cross_transaction SET " +
                        "state = ? " +
                        "WHERE id = ?";

                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, status);
                    updateStatement.setString(2, XATransactionID);
                    int rowsUpdated = updateStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        logger.info("Data updated successfully.");
                        connection.commit();
                    } else {
                        logger.info("Failed to update data.");
                    }
                } catch (SQLException e) {
                    connection.rollback();
                    throw new RuntimeException(e);
                }
            }
            else {
                //insertXATransaction(status, XATransactionID);
                String insertQuery = "INSERT INTO cross_transaction(id, state) " +
                        "VALUES (?, ?)";
                logger.info("Inserting a new row into the cross_trainsaction database.");

                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setString(1, XATransactionID);
                    insertStatement.setInt(2, status);
                    int rowsAffected = insertStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        logger.info("Data inserted successfully.");
                        connection.commit();
                    } else {
                        logger.info("Failed to insert data.");
                    }
                }catch (SQLException e) {
                    connection.rollback();
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                logger.error("Error rolling back transaction: " + rollbackEx.getMessage(), rollbackEx);
            }
            throw new RuntimeException(e);
        }
        finally {
            // 恢复自动提交并关闭连接
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException closeEx) {
                logger.error("Error closing connection: " + closeEx.getMessage(), closeEx);
            }
        }
    }

}
