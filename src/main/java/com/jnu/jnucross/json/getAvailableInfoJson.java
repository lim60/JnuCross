package com.jnu.jnucross.json;

import lombok.Data;

import java.util.List;

@Data
public class getAvailableInfoJson {
    private int id;
    private int chainId;
    private List<String> transactionType;
    private String addr;
    private double longitude;
    private double latitude;
    private List<String> connectedList;
}
