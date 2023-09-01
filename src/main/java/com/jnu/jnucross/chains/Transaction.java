package com.jnu.jnucross.chains;

import com.google.protobuf.ByteString;

import java.util.Arrays;
import java.util.Map;

public class Transaction {
    private String from;
    private String to;
    private int chainType;
    private String hash;
    private byte[] rawBytes; //
    private long blockNumber;

    // 合约调用相关
    private String contractName;
    private String methodName;
    private Map<String, Object> args;
    private int status;
    private String message;



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

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", chainType=" + chainType +
                ", hash='" + hash + '\'' +
                //", rawBytes=" + Arrays.toString(rawBytes) +
                ", blockNumber=" + blockNumber +
                ", contractName='" + contractName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", args=" + args +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
