package com.example.qoing;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CryptoResponse {
    @SerializedName("data")
    private List<CryptoItem> data;

    public List<CryptoItem> getData() {
        return data;
    }
}
