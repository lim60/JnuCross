package com.jnu.jnucross.adapter;

public class ChainType {
    static final int bcos_chain = 0;
    static final int cita_chain = 1;
    static final  int ethereum_chain = 2;
    static final  int farbic_chain = 3;
    static final  int xuperChain = 4;

    public static int getChainType(int val){
        switch (val){
            case (bcos_chain):
                return bcos_chain;
            case (cita_chain):
                return cita_chain;
            case (ethereum_chain):
                return ethereum_chain;
            case (farbic_chain):
                return farbic_chain;
            case (xuperChain):
                return xuperChain;
            default:
                return -1;
        }
    }
}
