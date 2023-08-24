package jnu.chains;

import com.jnu.jnucross.chains.bcos.BCOSUtils;
import com.jnu.jnucross.chains.cita.CITAUtils;
import com.jnu.jnucross.chains.ethereum.EthereumUtils;
import com.jnu.jnucross.chains.xuperchain.XuperChainUtils;
import org.junit.Test;

/**
 * @author SDKany
 * @ClassName UtilsTest
 * @Date 2023/8/24 15:54
 * @Version V1.0
 * @Description
 */
public class UtilsTest {
    @Test
    public void CreatPrivateKeyTest(){
        System.out.println("CITAUtils");
        String citaPrivateKey = CITAUtils.creatPrivateKey();
        System.out.println("---PrivateKey = " + citaPrivateKey);
        System.out.println("---Address = " + CITAUtils.getAddressAndPublicKey(citaPrivateKey));

        System.out.println("XuperChainUtils");
        String xuperPrivate = XuperChainUtils.creatPrivateKey();
        System.out.println("---PrivateKey = " + xuperPrivate);
        System.out.println("---Address = " + XuperChainUtils.getAddressAndPublicKey(xuperPrivate));

        System.out.println("EthereumUtils");
        String EthereumPrivate = EthereumUtils.creatPrivateKey();
        System.out.println("---PrivateKey = " + EthereumPrivate);
        System.out.println("---Address = " + EthereumUtils.getAddressAndPublicKey(EthereumPrivate));

        System.out.println("BCOSUtils");
        String bcosPrivate = BCOSUtils.creatPrivateKey();
        System.out.println("---PrivateKey = " + bcosPrivate);
        System.out.println("---Address = " + BCOSUtils.getAddressAndPublicKey(bcosPrivate));

    }
}
