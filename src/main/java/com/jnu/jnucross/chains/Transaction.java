package com.jnu.jnucross.chains;

public class Transaction {
    private String from;
    private String to;
    private int chainType;
    private String hash;
    private byte[] rawBytes; //
    private long blockNumber;

    public Transaction() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getChainType() {
        return chainType;
    }

    public void setChainType(int chainType) {
        this.chainType = chainType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public byte[] getRawBytes() {
        return rawBytes;
    }

    public void setRawBytes(byte[] rawBytes) {
        this.rawBytes = rawBytes;
    }

    public long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(long blockNumber) {
        this.blockNumber = blockNumber;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", chainType=" + chainType +
                ", hash='" + hash + '\'' +
                ", rawBytes=" + Numeric.toHexString(rawBytes) +
                ", blockNumber=" + blockNumber +
                '}';
    }
}
