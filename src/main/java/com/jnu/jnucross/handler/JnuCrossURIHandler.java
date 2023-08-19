package com.jnu.jnucross.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jnu.jnucross.constants.AddressingStrategyConstant;
import com.jnu.jnucross.constants.TransactionStatesConstant;
import com.jnu.jnucross.constants.TransactionTypeConstant;
import com.jnu.jnucross.json.TransactionParamJson;
import com.jnu.jnucross.request.CrossChainTransferReq;
import com.jnu.jnucross.util.ResultUtil;
import com.webank.wecross.account.UniversalAccount;
import com.webank.wecross.account.AccountManager;
import com.webank.wecross.account.UserContext;
import com.webank.wecross.entity.CrossTransaction;
import com.webank.wecross.entity.TChainAccounts;
import com.webank.wecross.entity.TUniversalAccounts;
import com.webank.wecross.entity.Transaction;
import com.webank.wecross.exception.WeCrossException;
import com.webank.wecross.mapper.CrossTransactionMapper;
import com.webank.wecross.mapper.TChainAccountsMapper;
import com.webank.wecross.mapper.TUniversalAccountsMapper;
import com.webank.wecross.mapper.TransactionMapper;
import com.webank.wecross.network.UriDecoder;
import com.webank.wecross.network.rpc.handler.URIHandler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 *
 */
@Data
public class JnuCrossURIHandler implements URIHandler {
    private static final Logger logger = LoggerFactory.getLogger(JnuCrossURIHandler.class);

    private TransactionTemplate transactionTemplate;
    private AccountManager accountManager;
//    private CrossTransactionJPA crossTransactionJPA;
//    private TransactionJPA transactionJPA;
//    private TUniversalAccountsJPA tUniversalAccountsJPA;
//    private TChainAccountsJPA tChainAccountsJPA;
    private CrossTransactionMapper crossTransactionMapper;
    private TransactionMapper transactionMapper;
    private TChainAccountsMapper tChainAccountsMapper;
    private TUniversalAccountsMapper tUniversalAccountsMapper;


    @Override
    public void handle(
            UserContext userContext, String uri, String method, String content, Callback callback) {
        UriDecoder uriDecoder = new UriDecoder(uri);
        String operation = uriDecoder.getMethod();
        switch (operation) {
            //跨链转账
            case "crossChainTransfer":
                handleCrossChainTransfer(userContext, uri, method, content, callback);
                break;
            case "listChain":
//                listChain(userContext, uri, method, content, callback);
                break;
        }

    }

    private void handleCrossChainTransfer(UserContext userContext, String uri, String method, String content, Callback callback) {

        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    if (StrUtil.isBlank(content)) {
                        throw new WeCrossException(WeCrossException.ErrorCode.REQ_CONTENT_EMPTY, "req content is null");
                    }
                    CrossChainTransferReq req = JSONUtil.toBean(content, CrossChainTransferReq.class);
                    logger.info("handleCrossChainTransfer request:{}", req);
                    UniversalAccount universalAccount = accountManager.getUniversalAccount(userContext);
                    String uaID = universalAccount.getUaID();
                    //查询用户表
                    TUniversalAccounts tUniversalAccount = tUniversalAccountsMapper.
                            selectOne(new LambdaQueryWrapper<TUniversalAccounts>().eq(TUniversalAccounts::getUaid, uaID));
                    Long uaPrimaryId = tUniversalAccount.getId();
                    String transactionId = req.getTransactionId();
                    List<String> split = StrUtil.split(req.getChainIds(), ",");
                    Long firstChainId = Long.parseLong(split.get(0));//第一条链id
                    Long secondChainId = Long.parseLong(split.get(1));//第二条链id
                    //先插入事务表
                    CrossTransaction crossTransaction = new CrossTransaction();
                    crossTransaction.setId(transactionId);
                    crossTransaction.setChains(req.getChainIds());
                    crossTransaction.setType(TransactionTypeConstant.CROSS_CHAIN_TRANSFER);
                    crossTransaction.setState(TransactionStatesConstant.EXECUTING);
                    crossTransaction.setInitiateAccount(req.getLaunchAccountId());
                    crossTransaction.setAddressingStrategy(AddressingStrategyConstant.DEFAULT_Strategy);
                    crossTransaction.setCreatorId(uaPrimaryId);
                    crossTransactionMapper.insert(crossTransaction);

                    //将两条链的admin的账户查出来
                    List<TChainAccounts> firstAdminAccounts = tChainAccountsMapper.selectList(Wrappers.<TChainAccounts>lambdaQuery()
                            .eq(TChainAccounts::getChainId, firstChainId)
                            .eq(TChainAccounts::getUsername, "org1-admin"));
                    List<TChainAccounts> secondAdminAccounts = tChainAccountsMapper.selectList(Wrappers.<TChainAccounts>lambdaQuery()
                            .eq(TChainAccounts::getChainId, secondChainId)
                            .eq(TChainAccounts::getUsername, "org1-admin"));

                    // TODO: 2023/8/19 后面改进
//                    Long firstAdminAccountId = firstAdminAccounts.get(0).getId();
//                    Long secondAdminAccountId = secondLAdminAccounts.get(0).getId();
                    Long firstAdminAccountId = 1L;
                    Long secondAdminAccountId = 2L;

                    List<Transaction> transactionList = CollUtil.newArrayList();
                    //交易表:第一条链
                    Transaction firstTransaction = new Transaction();
                    firstTransaction.setCrossTransactionId(transactionId);
                    firstTransaction.setChainId(firstChainId);
                    firstTransaction.setInitiateAccountId(req.getLaunchAccountId());
                    TransactionParamJson firstJson = new TransactionParamJson();
                    firstJson.setMoney(req.getMoney());
                    firstJson.setReceiverAccountId(firstAdminAccountId);
                    firstTransaction.setParameter(JSONUtil.toJsonStr(firstJson));
                    firstTransaction.setExecutionOrder(1);
                    transactionList.add(firstTransaction);
                    //交易表:第二条链
                    Transaction secondTransaction = new Transaction();
                    secondTransaction.setCrossTransactionId(transactionId);
                    secondTransaction.setChainId(secondChainId);
                    secondTransaction.setInitiateAccountId(secondAdminAccountId);
                    TransactionParamJson secondJson = new TransactionParamJson();
                    secondJson.setMoney(req.getMoney());
                    secondJson.setReceiverAccountId(req.getParticipateAccountId());
                    secondTransaction.setParameter(JSONUtil.toJsonStr(secondJson));
                    secondTransaction.setExecutionOrder(2);
                    transactionList.add(secondTransaction);
                    for (Transaction transaction : transactionList) {
                        transactionMapper.insert(transaction);
                    }
                    //调用开启事务接口 todo

                    callback.onResponse(ResultUtil.success());
                } catch (Exception e) {
                    status.setRollbackOnly();
                    logger.error("跨链转账交易发起异常", e);
                    callback.onResponse("跨链转账交易发起失败");
                }
                return null;
            }
        });

    }




    /*
    入参
     */
//    private void handleCrossChainTransfer(UserContext userContext, String uri, String method, String content, Callback callback) {
//        Connection connection = null;
//        List<PreparedStatement> statements = null;
//        List<ResultSet> resultSets = null;
//        try {
//            if (StrUtil.isBlank(content)){
//                throw new WeCrossException(WeCrossException.ErrorCode.REQ_CONTENT_EMPTY,"req content is null");
//            }
//
//            CrossChainTransferReq req = JSONUtil.toBean(content, CrossChainTransferReq.class);
//            logger.info("handleCrossChainTransfer request:{}",req);
//            UniversalAccount universalAccount = accountManager.getUniversalAccount(userContext);
//            String uaID = universalAccount.getUaID();
//            String chainIds = req.getChainIds();//链ids
//            String launchAccountId = req.getLaunchAccountId();//发起账户id
//            Integer participateAccountId = Integer.parseInt(req.getParticipateAccountId());
//            String transactionId = req.getTransactionId();
//            //事务表生成记录，拿到事务id
//            connection = JdbcUtils.getConnection();
//            connection.setAutoCommit(true);
//            String insertField = StrUtil.join(",",
//                    CrossTransactionTable.ID,
//                    CrossTransactionTable.TYPE,
//                    CrossTransactionTable.CHAINS,
//                    CrossTransactionTable.STATE,
//                    CrossTransactionTable.INITIATE_ACCOUNT,
//                    CrossTransactionTable.ADDRESSING_STRATEGY,
//                    CrossTransactionTable.CREATOR_ID);
//            String sql1 = "INSERT INTO " +
//                    "cross_transaction("+ insertField + ")\n" +
//                    "VALUES(?,?,?,?,?,?,?,?)";
//            PreparedStatement ps1 = connection.prepareStatement(sql1);
//            ps1.setString(1, transactionId);
//            ps1.setInt(2, TransactionTypeConstant.CROSS_CHAIN_TRANSFER);
//            ps1.setString(3, chainIds);
//            ps1.setInt(4, TransactionStatesConstant.EXECUTING);
//            ps1.setString(5, launchAccountId);
//            ps1.setInt(6, addressingStrategyConstant.DEFAULT_Strategy);
//            ps1.setInt(7, Integer.parseInt(uaID));//
//            logger.info("handleCrossChainTransfer sql1:{}",ps1.toString());
//            ps1.executeUpdate();
//            //记录交易表
//            List<String> split = StrUtil.split(chainIds, ",");
//            Integer firstChainId = Integer.parseInt(split.get(1));
//            Integer secondChainId = Integer.parseInt(split.get(2));
//
//            //先把两条链的admin账户id拿出来
//            String sql2  = "SELECT id FROM t_chain_accounts WHERE chain_id = ? AND username = 'admin' LIMIT 1";
//            PreparedStatement ps2 = connection.prepareStatement(sql2);
//            ps2.setInt(1,firstChainId);
//            ResultSet sql2Result = ps2.executeQuery();
//            sql2Result.next();
//            int firstAdminId = sql2Result.getInt(1);
//            String sql3  = "SELECT id FROM t_chain_accounts WHERE chain_id = ? AND username = 'admin' LIMIT 1";
//            PreparedStatement ps3 = connection.prepareStatement(sql3);
//            ps3.setInt(1,secondChainId);
//            ResultSet sql3Result = ps3.executeQuery();
//            sql3Result.next();
//            int secondAdminId = sql3Result.getInt(1);
//
//            String insertField2 = StrUtil.join(",",
//                    TransactionTable.CROSS_TRANSACTION_ID,
//                    TransactionTable.CHAIN_ID,
//                    TransactionTable.INITIATE_ACCOUNT_ID,
//                    TransactionTable.PARAMETER,
//                    TransactionTable.EXECUTION_ORDER);
//            String sql4 = "INSERT INTO " +
//                    "transaction("+ insertField2 + ")\n" +
//                    "VALUES(?,?,?,?,?,?,?)";
//            PreparedStatement ps4 = connection.prepareStatement(sql4);
//            //第一条交易记录
//            ps4.setString(1,transactionId);
//            ps4.setInt(2,firstChainId);
//            ps4.setInt(3,Integer.parseInt(launchAccountId));
//            TransactionParamJson firstJson = new TransactionParamJson();
//            firstJson.setMoney(req.getMoney());
//            firstJson.setReceiverAccountId(firstAdminId);
//            ps4.setString(4,firstJson.toString());
//            ps4.setInt(5,1);
//            ps4.addBatch();
//            //第二条条交易记录
//            ps4.setString(1,transactionId);
//            ps4.setInt(2,secondChainId);
//            ps4.setInt(3,secondAdminId);
//            TransactionParamJson secondJson = new TransactionParamJson();
//            secondJson.setMoney(req.getMoney());
//            secondJson.setReceiverAccountId(participateAccountId);
//            ps4.setString(4,secondJson.toString());
//            ps4.setInt(5,2);
//            ps4.addBatch();
//            ps4.executeUpdate();
//
//            // TODO: 2023/8/17 调用发起事务接口
//
//        } catch (Exception e) {
//            try {
//                connection.rollback();
//            } catch (SQLException throwables) {
//                logger.error("数据库事务回滚失败");
//            }
//            logger.error("方法出错",e);
//            callback.onResponse("跨链转账交易发起失败");
//        }finally {
////            JdbcUtils.close(connection,);
//        }
    }
