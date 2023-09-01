package com.jnu.jnucross.chains;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Block {
    private int chainType;
    private byte[] rawBytes;
    private BlockHeader blockHeader;
    private List<String> transactionsHashes = new LinkedList<>();

    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public void setBlockHeader(BlockHeader blockHeader) {
        this.blockHeader = blockHeader;
    }

    public List<String> getTransactionsHashes() {
        return transactionsHashes;
    }

    public void setTransactionsHashes(List<String> transactionsHashes) {
        this.transactionsHashes = transactionsHashes;
    }

    public byte[] getRawBytes() {
        return rawBytes;
    }

    public void setRawBytes(byte[] rawBytes) {
        this.rawBytes = rawBytes;
    }

    public int getChainType() {
        return chainType;
    }

    public void setChainType(int chainType) {
        this.chainType = chainType;
    }

    @Override
    public String toString() {
        return "Block{" +
                "chainType=" + chainType +
                ", rawBytes=" + Numeric.toHexString(rawBytes) +
                ", blockHeader=" + blockHeader +
                ", transactionsHashes=" + Arrays.toString(transactionsHashes.toArray()) +
                '}';
    }
}
