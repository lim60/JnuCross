package com.webank.wecross.config;

import com.moandjiezana.toml.Toml;
import com.webank.wecross.account.AccountManager;
import com.webank.wecross.exception.WeCrossException;
import com.webank.wecross.mapper.*;
import com.webank.wecross.network.rpc.RPCService;
import com.webank.wecross.network.rpc.URIHandlerDispatcher;
import com.webank.wecross.network.rpc.authentication.AuthFilter;
import com.webank.wecross.network.rpc.netty.RPCBootstrap;
import com.webank.wecross.network.rpc.netty.RPCConfig;
import com.webank.wecross.network.rpc.web.WebService;
import javax.annotation.Resource;

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

    @Resource AccountManager accountManager;

    @Resource WebService webService;

    @Resource
    Toml toml;

    @Resource
    TransactionTemplate transactionTemplate;
//    @Resource
//    UserTableJPA userTableJPA;
//    @Resource
//    CrossTransactionJPA crossTransactionJPA;
//    @Resource
//    TransactionJPA transactionJPA;
//    @Resource
//    TUniversalAccountsJPA universalAccountsJPA;
//    @Resource
//    TChainAccountsJPA tChainAccountsJPA;

    @Resource
    CrossTransactionMapper crossTransactionMapper;
    @Resource
    TransactionMapper transactionMapper;
    @Resource
    TChainAccountsMapper tChainAccountsMapper;
    @Resource
    TUniversalAccountsMapper tUniversalAccountsMapper;



    private static final Logger logger = LoggerFactory.getLogger(RPCServiceConfig.class);

    @Bean
    public RPCService newRPCService() throws WeCrossException {
        RPCBootstrap rpcBootstrap = new RPCBootstrap();
        rpcBootstrap.setConfig(rpcConfig);
        rpcBootstrap.setAccountManager(accountManager);
        rpcBootstrap.setAuthFilter(authFilter);

        URIHandlerDispatcher uriHandlerDispatcher = new URIHandlerDispatcher();
        uriHandlerDispatcher.setWebService(webService);
        uriHandlerDispatcher.setTransactionTemplate(transactionTemplate);
        uriHandlerDispatcher.setCrossTransactionMapper(crossTransactionMapper);
        uriHandlerDispatcher.setTransactionMapper(transactionMapper);
        uriHandlerDispatcher.setTChainAccountsMapper(tChainAccountsMapper);
        uriHandlerDispatcher.setTUniversalAccountsMapper(tUniversalAccountsMapper);
        rpcBootstrap.setUriHandlerDispatcher(uriHandlerDispatcher);

        RPCService rpcService = new RPCService();
        rpcService.setRpcBootstrap(rpcBootstrap);
        return rpcService;
    }
}
