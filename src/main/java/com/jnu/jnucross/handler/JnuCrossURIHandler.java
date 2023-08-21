package com.jnu.jnucross.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
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
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.net.InetAddress;
import java.util.List;

/**
 *
 */
@Data
public class JnuCrossURIHandler implements URIHandler {
    private static final Logger logger = LoggerFactory.getLogger(JnuCrossURIHandler.class);

    private TransactionTemplate transactionTemplate;
    private AccountManager accountManager;
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
            case "getTransaction":
                handleGetTransaction(userContext, uri, method, content, callback);
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
                    InetAddress byName = InetAddress.getByName("192.168.50.106");
                    boolean reachable = byName.isReachable(500);

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

    private void handleGetTransaction(UserContext userContext, String uri, String method, String content, Callback callback) {

        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                return null;

            }
        });

    }

    }
