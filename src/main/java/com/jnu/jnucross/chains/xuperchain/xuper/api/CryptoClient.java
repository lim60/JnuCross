package com.jnu.jnucross.chains.xuperchain.xuper.api;

import com.jnu.jnucross.chains.xuperchain.xuper.config.Config;
import com.jnu.jnucross.chains.xuperchain.xuper.crypto.Crypto;
import com.jnu.jnucross.chains.xuperchain.xuper.crypto.gm.GmCryptoClient;
import com.jnu.jnucross.chains.xuperchain.xuper.crypto.xchain.XChainCryptoClient;

public class CryptoClient {
    /**
     * 读取配置文件，获取加密方式
     *
     * @return CryptoClient
     */
    public static Crypto getCryptoClient() {
        if (Config.CRYPTO_GM.equals(Config.getInstance().getCrypto())) {
            return new GmCryptoClient();
        }
        return new XChainCryptoClient();
    }
}
