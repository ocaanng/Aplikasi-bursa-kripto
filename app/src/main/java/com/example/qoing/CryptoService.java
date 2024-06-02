package com.example.qoing;

import com.example.qoing.CryptoItem;
import com.google.gson.annotations.SerializedName; // Impor untuk @SerializedName
import java.util.List; // Impor untuk List
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class CryptoService {
    private static final String BASE_URL = "https://api.coinlore.net/";

    // Antar muka Retrofit
    public interface CryptoAPI {
        @GET("api/tickers/")
        Call<CryptoResponse> getCryptoTickers(); // Mengembalikan CryptoResponse, bukan List<CryptoItem>
    }

    // Kelas CryptoResponse
    public static class CryptoResponse {
        @SerializedName("data")
        private List<CryptoItem> data;

        public List<CryptoItem> getData() {
            return data;
        }
    }

    // Metode untuk mendapatkan instance Retrofit
    public static CryptoAPI getCryptoAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(CryptoAPI.class);
    }
}
