package com.example.qoing;

import android.os.Parcel;
import android.os.Parcelable;

public class CryptoItem implements Parcelable {
    private String name;
    private String symbol;
    private String price;
    private String marketCap;
    private String volume24h;
    private String circulatingSupply;
    private String totalSupply;
    private String percentChange24h;
    private String percentChange1h;
    private String percentChange7d;

    public CryptoItem(String name, String symbol, String price, String marketCap, String volume24h, String circulatingSupply, String totalSupply, String percentChange24h, String percentChange1h, String percentChange7d) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.marketCap = marketCap;
        this.volume24h = volume24h;
        this.circulatingSupply = circulatingSupply;
        this.totalSupply = totalSupply;
        this.percentChange24h = percentChange24h;
        this.percentChange1h = percentChange1h;
        this.percentChange7d = percentChange7d;
    }

    protected CryptoItem(Parcel in) {
        name = in.readString();
        symbol = in.readString();
        price = in.readString();
        marketCap = in.readString();
        volume24h = in.readString();
        circulatingSupply = in.readString();
        totalSupply = in.readString();
        percentChange24h = in.readString();
        percentChange1h = in.readString();
        percentChange7d = in.readString();
    }

    public static final Creator<CryptoItem> CREATOR = new Creator<CryptoItem>() {
        @Override
        public CryptoItem createFromParcel(Parcel in) {
            return new CryptoItem(in);
        }

        @Override
        public CryptoItem[] newArray(int size) {
            return new CryptoItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(symbol);
        dest.writeString(price);
        dest.writeString(marketCap);
        dest.writeString(volume24h);
        dest.writeString(circulatingSupply);
        dest.writeString(totalSupply);
        dest.writeString(percentChange24h);
        dest.writeString(percentChange1h);
        dest.writeString(percentChange7d);
    }

    // Getter dan setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(String volume24h) {
        this.volume24h = volume24h;
    }

    public String getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(String circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public String getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    public String getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(String percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public String getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(String percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public String getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(String percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

}
