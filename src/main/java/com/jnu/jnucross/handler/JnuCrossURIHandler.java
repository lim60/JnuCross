package com.jnu.jnucross.handler;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jnu.jnucross.adapter.Adapter;
import com.jnu.jnucross.constants.TransactionTypeConstant;
import com.jnu.jnucross.json.AvailableIpJson;
import com.jnu.jnucross.response.AvailableIpRsp;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jnu.jnucross.constants.AddressingStrategyConstant;
import com.jnu.jnucross.constants.TransactionStatesConstant;
import com.jnu.jnucross.constants.CrossTransactionTypeConstant;
import com.jnu.jnucross.request.GenerateTransactionReq;
import com.jnu.jnucross.response.GetCrossTransactionRSp;
import com.jnu.jnucross.response.GetAddressPageInfoRsp;
import com.jnu.jnucross.response.GetTransactionInfoRsp;
import com.jnu.jnucross.util.ResultUtil;
import com.webank.wecross.account.UniversalAccount;
import com.webank.wecross.account.AccountManager;
import com.webank.wecross.account.UserContext;
import com.webank.wecross.entity.*;
import com.webank.wecross.host.WeCrossHost;
import com.webank.wecross.mapper.*;
import com.webank.wecross.network.UriDecoder;
import com.webank.wecross.network.rpc.handler.URIHandler;
import com.webank.wecross.restserver.RestResponse;
import com.webank.wecross.routine.xa.XAResponse;
import com.webank.wecross.routine.xa.XATransactionManager;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@Data
public class JnuCrossURIHandler implements URIHandler {
    private static final Logger logger = LoggerFactory.getLogger(JnuCrossURIHandler.class);

    private XATransactionManager xaTransactionManager;
    private WeCrossHost weCrossHost;

    private TransactionTemplate transactionTemplate;
    private AccountManager accountManager;
    private CrossTransactionMapper crossTransactionMapper;
    private TransactionMapper transactionMapper;
    private TChainAccountsMapper tChainAccountsMapper;
    private TUniversalAccountsMapper tUniversalAccountsMapper;
    private ChainMapper chainMapper;
    private SmartContractMapper smartContractMapper;
    private GatewayMapper gatewayMapper;
    private ChainNodeMapper chainNodeMapper;


    @Override
    public void handle(
            UserContext userContext, String uri, String method, String content, Callback callback) {
        UriDecoder uriDecoder = new UriDecoder(uri);
        String operation = uriDecoder.getMethod();
        switch (operation) {
            //-----------------跨链转账-----------------
            //生成事务
            case "generateTransferTransaction":
                handleGenerateTransferTransaction(userContext, uri, method, content, callback);
                break;
            //查询事务
            case "getCrossTransaction":
                handleGetCrossTransaction(userContext, uri, method, content, callback);
                break;
            //查询寻址信息
            case "getAddressPageInfo":
                handleGetAddressPageInfo(userContext, uri, method, content, callback);
                break;
            //获取可用ip
            case "getAvailableIp":
                handleGetAvailableIp(userContext, uri, method, content, callback);
                break;
            //pingIp
            case "doPing":
                handleDoPing(userContext, uri, method, content, callback);
                break;
            //获取交易信息
            case "getTransactionInfo":
                handleGetTransactionInfo(userContext, uri, method, content, callback);
                break;
            //开启事务
            case "startCrossTransaction":
                handleStartCrossTransaction(userContext, uri, method, content, callback);
                break;
            //提交事务
            case "commitTransaction":
                handleCommitTransaction(userContext, uri, method, content, callback);
                break;
            //回滚事务
            case "rollbackTransaction":
                handleRollbackTransaction(userContext, uri, method, content, callback);
                break;
            //-----------------跨链转账-----------------

            //-----------------合约-----------------
            //获取合约列表
//            case "getSmartContract":
//                handleGetSmartContract(userContext, uri, method, content, callback);
//                break;
            //-----------------合约-----------------
        }

    }

    private void handleGetCrossTransaction(UserContext userContext, String uri, String method, String content, Callback callback) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    //查询全部事务
                    List<CrossTransaction> crossTransactions = crossTransactionMapper.selectList(null);
                    Set<Long> creatorIds = crossTransactions.stream().map(CrossTransaction::getCreatorId).collect(Collectors.toSet());
                    Set<String> chainIds = CollUtil.newHashSet();
                    crossTransactions.forEach(crossTransaction -> {
                        String chains = crossTransaction.getChains();
                        List<String> split = StrUtil.split(chains, ",");
                        chainIds.addAll(split);
                    });
                    List<TUniversalAccounts> tUniversalAccounts = tUniversalAccountsMapper.selectBatchIds(creatorIds);
                    Map<Long, TUniversalAccounts> userMap = tUniversalAccounts.stream().collect(Collectors.toMap(TUniversalAccounts::getId, Function.identity()));
                    List<Chain> chains = chainMapper.selectBatchIds(chainIds);
                    Map<Long, Chain> chainMap = chains.stream().collect(Collectors.toMap(Chain::getId, Function.identity()));

                    List<GetCrossTransactionRSp> result = CollUtil.newArrayList();
                    crossTransactions.forEach(crossTransaction -> {
                        GetCrossTransactionRSp resultItem = new GetCrossTransactionRSp();
                        resultItem.setTransactionId(crossTransaction.getId());
                        resultItem.setType(crossTransaction.getType());
                        resultItem.setCreator(userMap.get(crossTransaction.getCreatorId()).getUsername());
                        String chainIdStr = crossTransaction.getChains();
                        List<String> split = StrUtil.split(chainIdStr, ",");
                        List<String> chainNameList = split.stream().map(chainId -> chainMap.get(Long.valueOf(chainId)).getChainName()).collect(Collectors.toList());
                        resultItem.setCrossChainTarget(chainNameList);
                        resultItem.setStartTime(DateUtil.format(crossTransaction.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                        resultItem.setState(crossTransaction.getState());
                        result.add(resultItem);
                    });
                    callback.onResponse(ResultUtil.success(result));
                }catch (Exception e){
                    status.setRollbackOnly();
                    logger.error("事务查询失败:",e);
                    callback.onResponse("事务查询失败");
                }
                return null;
            }
        });
    }


    private void handleGenerateTransferTransaction(UserContext userContext, String uri, String method, String content, Callback callback) {

        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    if (StrUtil.isBlank(content)) {
                        throw new Exception( "handleGenerateTransferTransaction req content is null");
                    }
                    GenerateTransactionReq req = JSONUtil.toBean(content, GenerateTransactionReq.class);
                    logger.info("handleCrossChainTransfer request:{}", req);
                    UniversalAccount universalAccount = accountManager.getUniversalAccount(userContext);
                    String uaID = universalAccount.getUaID();
                    //查询用户表
                    TUniversalAccounts tUniversalAccount = tUniversalAccountsMapper.
                            selectOne(new LambdaQueryWrapper<TUniversalAccounts>().eq(TUniversalAccounts::getUaid, uaID));
                    Long uaPrimaryId = tUniversalAccount.getId();
                    String crossTransactionId = req.getCrossTransactionId();
                    List<String> split = StrUtil.split(req.getChainIds(), ",");
                    Long firstChainId = Long.parseLong(split.get(0));//第一条链id
                    Long secondChainId = Long.parseLong(split.get(1));//第二条链id
                    //先插入事务表
                    CrossTransaction crossTransaction = new CrossTransaction();
                    crossTransaction.setId(crossTransactionId);
                    crossTransaction.setChains(req.getChainIds());
                    crossTransaction.setType(CrossTransactionTypeConstant.CROSS_CHAIN_TRANSFER);
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
//                    String firstAdminAccountName = firstAdminAccounts.get(0).getAccountName();
//                    Long firstAdminAccountId = firstAdminAccounts.get(0).getId();
//                    String secondAdminAccountName = secondAdminAccounts.get(0).getAccountName();
//                    Long secondAdminAccountId = secondAdminAccounts.get(0).getId();

                    Long firstAdminAccountId = 1L;
                    Long secondAdminAccountId = 2L;
                    String firstAdminAccountName = "account1";
                    String secondAdminAccountName = "account2";


                    List<Transaction> transactionList = CollUtil.newArrayList();
                    //交易表:第一条链
                    Transaction firstTransaction = new Transaction();
                    firstTransaction.setId(req.getTransactionIds().get(0));
                    firstTransaction.setCrossTransactionId(crossTransactionId);
                    firstTransaction.setType(TransactionTypeConstant.BALANCE_TRANSFER);
                    firstTransaction.setChainId(firstChainId);
                    firstTransaction.setInitiateAccountId(req.getLaunchAccountId());
                    List<String> firstParameter = CollUtil.newArrayList(req.getLaunchAccountName(), firstAdminAccountName, req.getMoney().toString());
                    firstTransaction.setParameter(JSONUtil.toJsonStr(firstParameter));
                    firstTransaction.setExecutionOrder(0);
                    transactionList.add(firstTransaction);
                    //交易表:第二条链
                    Transaction secondTransaction = new Transaction();
                    secondTransaction.setId(req.getTransactionIds().get(1));
                    secondTransaction.setCrossTransactionId(crossTransactionId);
                    secondTransaction.setType(TransactionTypeConstant.BALANCE_TRANSFER);
                    secondTransaction.setChainId(secondChainId);
                    secondTransaction.setInitiateAccountId(secondAdminAccountId);
                    List<String> secondParameter = CollUtil.newArrayList(secondAdminAccountName, req.getParticipateAccountName(), req.getMoney().toString());
                    secondTransaction.setParameter(JSONUtil.toJsonStr(secondParameter));
                    secondTransaction.setExecutionOrder(1);
                    transactionList.add(secondTransaction);
                    for (Transaction transaction : transactionList) {
                        transactionMapper.insert(transaction);
                    }
                    callback.onResponse(ResultUtil.success());
                } catch (Exception e) {
                    status.setRollbackOnly();
                    logger.error("跨链转账交易生成异常", e);
                    callback.onResponse("跨链转账交易生成失败");
                }

                return null;
            }
        });

    }

    private void handleGetAvailableIp(UserContext userContext, String uri, String method, String content, Callback callback) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            final UriDecoder uriDecoder = new UriDecoder(uri);
            String domain;
            String contractType;
            {
                try {
                    contractType = "合约类型";
                    domain = uriDecoder.getQueryBykey("domain");
                } catch (Exception e) {
                    logger.error("获取寻址信息失败:",e);
                    callback.onResponse("获取寻址信息失败");
                }
            }
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {



                }catch (Exception e){
                    status.setRollbackOnly();
                    logger.error("获取交易列表失败:",e);
                    callback.onResponse("获取交易列表失败");
                }
                return null;
            }
        });
    }

    private void handleDoPing(UserContext userContext, String uri, String method, String content, Callback callback) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            final UriDecoder uriDecoder = new UriDecoder(uri);
            String domain;
            String contractType;
            {
                try {

                } catch (Exception e) {
                    logger.error("ip连通异常:",e);
                    callback.onResponse("ip连通异常");
                }
            }
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {




                }catch (Exception e){
                    status.setRollbackOnly();
                    logger.error("ip连通异常:",e);
                    callback.onResponse("ip连通异常");
                }
                return null;
            }
        });
    }


    private void handleGetAddressPageInfo(UserContext userContext, String uri, String method, String content, Callback callback) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            final UriDecoder uriDecoder = new UriDecoder(uri);
            String transactionId;
            {
                try {
                    transactionId = uriDecoder.getQueryBykey("transactionId");
                } catch (Exception e) {
                    logger.error("获取寻址信息失败:",e);
                    callback.onResponse("获取寻址信息失败");
                }
            }
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    if (StrUtil.isBlank(transactionId)){
                        throw new Exception("doInTransaction method transactionId null");
                    }
                    CrossTransaction crossTransaction = crossTransactionMapper.selectById(transactionId);
                    Integer crossTransactionType = crossTransaction.getType();
                    List<Transaction> transactions = transactionMapper.selectList(Wrappers.<Transaction>lambdaQuery()
                            .eq(Transaction::getTransactionIp, transactionId));
                    List<GetAddressPageInfoRsp> result = new ArrayList<>(transactions.size());
                    transactions.forEach(transaction -> {
                        GetAddressPageInfoRsp rsp = new GetAddressPageInfoRsp();
                        Long chainId = transaction.getChainId();
                        Chain chain = chainMapper.selectById(chainId);
                        String parameter = transaction.getParameter();
                        if (ObjectUtil.equal(crossTransactionType, CrossTransactionTypeConstant.CROSS_CHAIN_TRANSFER)){
                            // TODO: 2023/8/23 先写着，后面需要根据事务类型来判断

                        }
                        rsp.setContractName("balance");
                        rsp.setCallMethod("transfer");
                        rsp.setChainName(chain.getChainName());
                        rsp.setFinalIp(transaction.getTransactionIp());
                        rsp.setOrder(transaction.getExecutionOrder());
                        List<AvailableIpJson> availableIpJsons = JSONUtil.toList(parameter, AvailableIpJson.class);
                        rsp.setAvailableIps(Convert.toList(AvailableIpRsp.class,availableIpJsons));
                        result.add(rsp);
                    });
                    callback.onResponse(ResultUtil.success(result));
                }catch (Exception e){
                    status.setRollbackOnly();
                    logger.error("获取交易列表失败:",e);
                    callback.onResponse("获取交易列表失败");
                }
                return null;
            }
        });
    }

    private void handleGetTransactionInfo(UserContext userContext, String uri, String method, String content, Callback callback) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            final UriDecoder uriDecoder = new UriDecoder(uri);
            String crossTransactionId;
            {
                try {
                    crossTransactionId = uriDecoder.getQueryBykey("crossTransactionId");
                } catch (Exception e) {
                    logger.error("获取交易详情失败:",e);
                    callback.onResponse("获取交易详情失败");
                }
            }
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    if (StrUtil.isBlank(crossTransactionId)){
                        throw new Exception("handleGetTransactionInfo method transactionId null");
                    }
                    CrossTransaction crossTransaction = crossTransactionMapper.selectById(crossTransactionId);
                    String crossTransactionId = crossTransaction.getId();

                    List<Transaction> transactions = transactionMapper.selectList(Wrappers.<Transaction>lambdaQuery()
                            .eq(Transaction::getCrossTransactionId,crossTransactionId).orderBy(true,true,Transaction::getExecutionOrder));
                    Set<Long> chainIds = transactions.stream().map(Transaction::getChainId).collect(Collectors.toSet());
                    Set<Long> contractIds = transactions.stream().map(Transaction::getSmartContractId).collect(Collectors.toSet());
                    Set<Long> accountIds = transactions.stream().map(Transaction::getInitiateAccountId).collect(Collectors.toSet());
                    List<Chain> chains = chainMapper.selectBatchIds(chainIds);
                    List<SmartContract> smartContracts = smartContractMapper.selectBatchIds(contractIds);
                    List<TChainAccounts> tChainAccounts = tChainAccountsMapper.selectBatchIds(accountIds);
                    Map<Long, Chain> chainMap = chains.stream().collect(Collectors.toMap(Chain::getId, Function.identity()));
                    Map<Long, SmartContract> contractMap = smartContracts.stream().collect(Collectors.toMap(SmartContract::getId, Function.identity()));
                    Map<Long, TChainAccounts> accountsMap = tChainAccounts.stream().collect(Collectors.toMap(TChainAccounts::getId, Function.identity()));
                    List<GetTransactionInfoRsp> result = CollUtil.newArrayList();

                    transactions.forEach(transaction -> {
                        GetTransactionInfoRsp resultItem = new GetTransactionInfoRsp();
                        resultItem.setHash(transaction.getHash());
                        resultItem.setChainName(chainMap.get(transaction.getChainId()).getChainName());
                        resultItem.setBlockHeight(transaction.getChainHeight());
                        resultItem.setInitiateAccountName(accountsMap.get(transaction.getInitiateAccountId()).getAccountName());
                        resultItem.setContractName(contractMap.get(transaction.getSmartContractId()).getContractName());
                        resultItem.setFunction(transaction.getFunction());
                        resultItem.setParameter(JSONUtil.toList(transaction.getParameter(),String.class));
                        resultItem.setTransactTime(transaction.getTransactionTime());
                        resultItem.setState(transaction.getState());
                        resultItem.setTransactionId(transaction.getId());
                        resultItem.setTransactionType(transaction.getType());
                        resultItem.setChainType(chainMap.get(transaction.getChainId()).getChainType());
                        result.add(resultItem);
                    });
                    callback.onResponse(ResultUtil.success(result));
                }catch (Exception e){
                    status.setRollbackOnly();
                    logger.error("获取交易列表失败:",e);
                    callback.onResponse("获取交易列表失败");
                }
                return null;
            }
        });
    }

    private void handleStartCrossTransaction(UserContext userContext, String uri, String method, String content, Callback callback) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    UniversalAccount ua = accountManager.getUniversalAccount(userContext);
//                     TODO: 2023/8/21 调用林老师开启事务方法
                    Adapter.startXATransaction(content,ua,new RestResponse<XAResponse>(),xaTransactionManager,weCrossHost,callback);
                    callback.onResponse(ResultUtil.success());
                }catch (Exception e){
                    status.setRollbackOnly();
                    logger.error("发起事务失败:",e);
                    callback.onResponse("发起事务失败");
                }
                return null;
            }
        });
    }

    private void handleCommitTransaction(UserContext userContext, String uri, String method, String content, Callback callback) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    callback.onResponse(ResultUtil.success());
                }catch (Exception e){
                    status.setRollbackOnly();
                    logger.error("提交事务失败:",e);
                    callback.onResponse("提交事务失败");
                }
                return null;
            }
        });
    }

    private void handleRollbackTransaction(UserContext userContext, String uri, String method, String content, Callback callback) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    callback.onResponse(ResultUtil.success());
                }catch (Exception e){
                    status.setRollbackOnly();
                    logger.error("回滚事务失败:",e);
                    callback.onResponse("回滚事务失败");
                }
                return null;
            }
        });
    }
    }
