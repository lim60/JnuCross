package com.webank.wecross.config;

import com.moandjiezana.toml.Toml;
import com.webank.wecross.account.AccountManager;
import com.webank.wecross.exception.WeCrossException;
import com.webank.wecross.host.WeCrossHost;
import com.webank.wecross.mapper.*;
import com.webank.wecross.network.rpc.RPCService;
import com.webank.wecross.network.rpc.URIHandlerDispatcher;
import com.webank.wecross.network.rpc.authentication.AuthFilter;
import com.webank.wecross.network.rpc.netty.RPCBootstrap;
import com.webank.wecross.network.rpc.netty.RPCConfig;
import com.webank.wecross.network.rpc.web.WebService;
import javax.annotation.Resource;

import com.webank.wecross.routine.xa.XATransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;

@Configuration
public class RPCServiceConfig {
    @Resource RPCConfig rpcConfig;

    @Resource AuthFilter authFilter;

    @Resource
    AccountManager accountManager;

    @Resource WebService webService;

    @Resource
    Toml toml;

    @Resource
    TransactionTemplate transactionTemplate;

    @Resource
    XATransactionManager xaTransactionManager;
    @Resource
    WeCrossHost weCrossHost;

    //mapper
    @Resource
    CrossTransactionMapper crossTransactionMapper;
    @Resource
    TransactionMapper transactionMapper;
    @Resource
    TChainAccountsMapper tChainAccountsMapper;
    @Resource
    TUniversalAccountsMapper tUniversalAccountsMapper;
    @Resource
    ChainMapper chainMapper;
    @Resource
    SmartContractMapper smartContractMapper;
    @Resource
    GatewayMapper gatewayMapper;
    @Resource
    ChainNodeMapper chainNodeMapper;
    @Resource
    AddressingIpAndStateMapper addressingIpAndStateMapper;
    //mapper



    private static final Logger logger = LoggerFactory.getLogger(RPCServiceConfig.class);

    @Bean
    public RPCService newRPCService() throws WeCrossException {
        RPCBootstrap rpcBootstrap = new RPCBootstrap();
        rpcBootstrap.setConfig(rpcConfig);
        rpcBootstrap.setAccountManager(accountManager);
        rpcBootstrap.setAuthFilter(authFilter);

        URIHandlerDispatcher uriHandlerDispatcher = new URIHandlerDispatcher();
        uriHandlerDispatcher.setWebService(webService);
        uriHandlerDispatcher.setXaTransactionManager(xaTransactionManager);
        uriHandlerDispatcher.setTransactionTemplate(transactionTemplate);
        uriHandlerDispatcher.setCrossTransactionMapper(crossTransactionMapper);
        uriHandlerDispatcher.setTransactionMapper(transactionMapper);
        uriHandlerDispatcher.setTChainAccountsMapper(tChainAccountsMapper);
        uriHandlerDispatcher.setTUniversalAccountsMapper(tUniversalAccountsMapper);
        uriHandlerDispatcher.setChainMapper(chainMapper);
        uriHandlerDispatcher.setSmartContractMapper(smartContractMapper);
        uriHandlerDispatcher.setGatewayMapper(gatewayMapper);
        uriHandlerDispatcher.setChainNodeMapper(chainNodeMapper);
        rpcBootstrap.setUriHandlerDispatcher(uriHandlerDispatcher);

        RPCService rpcService = new RPCService();
        rpcService.setRpcBootstrap(rpcBootstrap);
        return rpcService;
    }
}
