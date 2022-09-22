package com.tzt.warehouse.comm.utlis;

import lombok.Data;

@Data
public class SMKeyPair {

    //私钥
    private String priKey;
    //公钥
    private String pubKey;

    public SMKeyPair(String priKey, String pubKey) {
        this.priKey = priKey;
        this.pubKey = pubKey;
    }
}
