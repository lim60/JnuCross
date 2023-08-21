package com.jnu.jnucross.adapter;

// by yanlin
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class TransactionGraph {
    private static Logger logger = LoggerFactory.getLogger(TransactionGraph.class);

    public static List<TransactionInfo> bfsSearch(TransactionInfo startNode) {
        List<TransactionInfo> result = new ArrayList<>();
        if (startNode == null) {
            return result;
        }

        Queue<TransactionInfo> queue = new LinkedList<>();
        Set<TransactionInfo> visited = new HashSet<>();

        queue.offer(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            TransactionInfo node = queue.poll();
            result.add(node);

            for (TransactionInfo neighbor : node.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return result;
    }

    public static List<String>  getPathList(List<TransactionInfo> Transactions)
    {
        List<String>PathList = new ArrayList<>();
        for (TransactionInfo transaction: Transactions){
            List<String> paths = transaction.getPaths();
            for (String path:paths){
                if (!PathList.contains(path)) {
                    PathList.add(path);
                }
            }

        }
        return PathList;
    }

    public static String[] getArgs(String method_type){
        String[] args = new String[10];

        switch(method_type){
            default:
                break;
        }

        return args;
    }
    public static void constructTransactionGraph(List<TransactionInfo> allTransactions)
    {
        for (TransactionInfo tx : allTransactions) {
            logger.info("***Transaction ID: " + tx.getPaths());
            for (TransactionInfo otherTx : allTransactions) {
                if ((otherTx.getSeq()- tx.getSeq()) <= 1 && tx.getTransactionID() != otherTx.getTransactionID()) {
                    tx.addNeighbor(otherTx);
                }
            }
        }

    }
}

