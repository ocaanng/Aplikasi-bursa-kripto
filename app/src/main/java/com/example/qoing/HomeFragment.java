package com.example.qoing;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CryptoAdapter cryptoAdapter;
    private List<CryptoItem> cryptoList;
    private ProgressBar progressBar;
    private ImageView imageViewError, iv_search, iv_profile;
    private TextView textViewError;
    private Button buttonRetry;

    private LinearLayout trendingSection;
    private ImageView cryptoIcon1, cryptoIcon2, cryptoIcon3;
    private TextView cryptoName1, cryptoName2, cryptoName3;
    private TextView cryptoSymbol1, cryptoSymbol2, cryptoSymbol3;
    private TextView cryptoChange1, cryptoChange2, cryptoChange3;
    private static final String ARG_USERNAME = "username";
    private String username;

    public void onItemClick(CryptoItem cryptoItem) {
        // Buat intent
        Intent intent = new Intent(getActivity(), CryptoDetailActivity.class);
        // Sisipkan data CryptoItem ke intent
        intent.putExtra("cryptoItem", cryptoItem);
        // Mulai aktivitas CryptoDetailActivity
        startActivity(intent);
    }
    public static HomeFragment newInstance(String username) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        imageViewError = view.findViewById(R.id.image_view_error);
        textViewError = view.findViewById(R.id.text_view_error);
        buttonRetry = view.findViewById(R.id.button_retry);

        // Initialize trending section views
        trendingSection = view.findViewById(R.id.trend_contain);
        cryptoIcon1 = view.findViewById(R.id.crypto_icon1);
        cryptoIcon2 = view.findViewById(R.id.crypto_icon2);
        cryptoIcon3 = view.findViewById(R.id.crypto_icon3);

        cryptoName1 = view.findViewById(R.id.crypto_name1);
        cryptoName2 = view.findViewById(R.id.crypto_name2);
        cryptoName3 = view.findViewById(R.id.crypto_name3);

        cryptoSymbol1 = view.findViewById(R.id.crypto_symbol1);
        cryptoSymbol2 = view.findViewById(R.id.crypto_symbol2);
        cryptoSymbol3 = view.findViewById(R.id.crypto_symbol3);

        cryptoChange1 = view.findViewById(R.id.crypto_change1);
        cryptoChange2 = view.findViewById(R.id.crypto_change2);
        cryptoChange3 = view.findViewById(R.id.crypto_change3);

        // Set predefined data for the trending section
        setPredefinedTrendingData();

        // Initialize RecyclerView, CryptoAdapter, and cryptoList
        cryptoList = new ArrayList<>();
        cryptoAdapter = new CryptoAdapter(getActivity(), cryptoList);

        iv_search = view.findViewById(R.id.IV_Search);
        iv_search.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, searchFragment)
                    .addToBackStack(null)
                    .commit();
        });

        iv_profile = view.findViewById(R.id.IV_Profile);
        iv_profile.setOnClickListener(v -> {
            ProfileFragment profileFragment = ProfileFragment.newInstance(username);
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, profileFragment)
                    .addToBackStack(null)
                    .commit();
        });



        // Set layout manager for RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Set adapter to RecyclerView
        recyclerView.setAdapter(cryptoAdapter);

        // Call API and get the list of cryptocurrencies
        fetchCryptoData();

        // Retry button click listener
        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCryptoData();
            }
        });

        return view;
    }

    private void fetchCryptoData() {
        showLoading(true);

        // Memanggil API dan mendapatkan daftar kripto
        CryptoService.CryptoAPI cryptoAPI = CryptoService.getCryptoAPI();
        Call<CryptoService.CryptoResponse> call = cryptoAPI.getCryptoTickers();

        call.enqueue(new Callback<CryptoService.CryptoResponse>() {
            @Override
            public void onResponse(Call<CryptoService.CryptoResponse> call, Response<CryptoService.CryptoResponse> response) {
                showLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    CryptoService.CryptoResponse cryptoResponse = response.body();
                    cryptoList.clear();
                    cryptoList.addAll(cryptoResponse.getData());
                    cryptoAdapter.notifyDataSetChanged();
                } else {
                    showError(true);
                    Log.e("Crypto", "Failed to fetch data");
                }
            }

            @Override
            public void onFailure(Call<CryptoService.CryptoResponse> call, Throwable t) {
                showLoading(false);
                showError(true);
                Log.e("Crypto", "Error: " + t.getMessage());
            }
        });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        trendingSection.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        imageViewError.setVisibility(View.GONE);
        textViewError.setVisibility(View.GONE);
        buttonRetry.setVisibility(View.GONE);
    }

    private void showError(boolean isError) {
        imageViewError.setVisibility(isError ? View.VISIBLE : View.GONE);
        textViewError.setVisibility(isError ? View.VISIBLE : View.GONE);
        buttonRetry.setVisibility(isError ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isError ? View.GONE : View.VISIBLE);
        trendingSection.setVisibility(isError ? View.GONE : View.VISIBLE);
    }

    private void setPredefinedTrendingData() {
        // First trending item
        cryptoIcon1.setImageResource(R.drawable.not); // Replace with your actual icon resource
        cryptoName1.setText("Notcoin");
        cryptoSymbol1.setText("NOT");
        cryptoChange1.setText("+102.11%");

        // Second trending item
        cryptoIcon2.setImageResource(R.drawable.btc); // Replace with your actual icon resource
        cryptoName2.setText("Bitcoin");
        cryptoSymbol2.setText("BTC");
        cryptoChange2.setText("+40.12%");

        // Third trending item
        cryptoIcon3.setImageResource(R.drawable.wif); // Replace with your actual icon resource
        cryptoName3.setText("Dogwifhat");
        cryptoSymbol3.setText("WIF");
        cryptoChange3.setText("+23.44%");
    }
}
