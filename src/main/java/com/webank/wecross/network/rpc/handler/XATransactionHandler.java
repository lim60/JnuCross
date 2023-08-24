package com.webank.wecross.network.rpc.handler;

import static com.webank.wecross.exception.WeCrossException.ErrorCode.GET_UA_FAILED;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.jnucross.adapter.Adapter;
import com.webank.wecross.account.UniversalAccount;
import com.webank.wecross.account.UserContext;
import com.webank.wecross.common.NetworkQueryStatus;
import com.webank.wecross.exception.WeCrossException;
import com.webank.wecross.host.WeCrossHost;
import com.webank.wecross.network.UriDecoder;

import com.webank.wecross.restserver.RestResponse;
import com.webank.wecross.routine.xa.XAResponse;
import com.webank.wecross.routine.xa.XATransactionManager;
import com.webank.wecross.stub.ObjectMapperFactory;
import com.webank.wecross.stub.Path;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** POST/GET /xa/method/ **/

public class XATransactionHandler implements URIHandler {
    private final Logger logger = LoggerFactory.getLogger(XATransactionHandler.class);
    private final ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    private XATransactionManager xaTransactionManager;
    private WeCrossHost host;


    @Override
    public  void handle(
            UserContext userContext,
            String uri,
            String httpMethod,
            String content,
            Callback callback) {

        RestResponse<Object> restResponse = new RestResponse<Object>();

        UniversalAccount ua;
        try {
            ua = host.getAccountManager().getUniversalAccount(userContext);
            if (ua == null) {
                throw new WeCrossException(GET_UA_FAILED, "Failed to get universal account");
            }
        } catch (WeCrossException e) {
            restResponse.setErrorCode(
                    NetworkQueryStatus.UNIVERSAL_ACCOUNT_ERROR + e.getErrorCode());
            restResponse.setMessage(e.getMessage());
            callback.onResponse(restResponse);
            return;
        }
        try {
            UriDecoder uriDecoder = new UriDecoder(uri);
            String method = uriDecoder.getMethod();

            if (logger.isDebugEnabled()) {
                logger.debug("uri: {}, method: {}, request string: {}", uri, method, content);
            }

            switch (method) {
                // by yanlin
                case "startXATransaction": {
                    logger.info("now I'm in startXATransaction");


                    Adapter.startXATransaction(content,ua,xaTransactionManager,host);
                    return;
                }
                case "commitXATransaction": {
                    Adapter.commitTransaction(content,ua,xaTransactionManager);
                    return;
                }
                case "rollbackXATransaction": {
                    Adapter.rollbackTransaction(content,ua,xaTransactionManager);
                    return;
                }
                case "getXATransaction": {
                    Adapter.getXATransaction(content,ua,restResponse,xaTransactionManager,host,callback);
                    return;
                }
                case "listXATransactions": {
                    Adapter.listXATransactions(content,ua,restResponse,xaTransactionManager,host,callback);
                    return;
                }
                default: {
                    logger.warn("Unsupported method: {}", method);
                    restResponse.setErrorCode(NetworkQueryStatus.URI_PATH_ERROR);
                    restResponse.setMessage("Unsupported method: " + method);
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Error while processing xa: ", e);
            restResponse.setErrorCode(NetworkQueryStatus.INTERNAL_ERROR);
            restResponse.setMessage("Undefined error: " + e.getMessage());
        }
        callback.onResponse(restResponse);
    }

    private Set<Path> decodePathSet(Set<String> paths) throws WeCrossException {
        Set<Path> res =
                paths.parallelStream()
                        .map(
                                (s) -> {
                                    try {
                                        return Path.decode(s);
                                    } catch (Exception e) {
                                        logger.error("Decode path error: ", e);
                                        return null;
                                    }
                                })
                        .collect(Collectors.toSet());

        if (res.isEmpty() || res.size() < paths.size()) {
            throw new WeCrossException(
                    WeCrossException.ErrorCode.POST_DATA_ERROR, "Invalid path found");
        }

        return res;
    }

    public Set<Path> filterAndSortChainPaths(Set<String> paths) throws WeCrossException {
        Set<String> tempPaths = new HashSet<>();
        for (String path : paths) {
            try {
                String[] splits = path.split("\\.");
                tempPaths.add(splits[0] + "." + splits[1]);
            } catch (Exception e) {
                throw new WeCrossException(
                        WeCrossException.ErrorCode.POST_DATA_ERROR, "Invalid path found");
            }
        }

        Set<Path> sortSet = new TreeSet<>(Comparator.comparing(Path::toString));
        sortSet.addAll(decodePathSet(tempPaths));
        return sortSet;
    }

    public XATransactionManager getXaTransactionManager() {
        return xaTransactionManager;
    }

    public void setXaTransactionManager(XATransactionManager xaTransactionManager) {
        this.xaTransactionManager = xaTransactionManager;
    }

    public void setHost(WeCrossHost host) {
        this.host = host;
    }
}
