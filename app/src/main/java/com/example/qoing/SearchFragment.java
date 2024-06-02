package com.example.qoing;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CryptoAdapter cryptoAdapter;
    private List<CryptoItem> cryptoList = new ArrayList<>();
    private List<CryptoItem> filteredCryptoList = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cryptoAdapter = new CryptoAdapter(getActivity(), filteredCryptoList);
        recyclerView.setAdapter(cryptoAdapter);

        setupSearchView();
        fetchCryptoData();

        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCoins(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCoins(newText);
                return true;
            }
        });
    }

    private void filterCoins(String query) {
        filteredCryptoList.clear();
        for (CryptoItem coin : cryptoList) {
            if (coin.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredCryptoList.add(coin);
            }
        }
        cryptoAdapter.notifyDataSetChanged();
    }

    private void fetchCryptoData() {
        showLoading(true);
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
                    filterCoins(searchView.getQuery().toString());
                }
            }

            @Override
            public void onFailure(Call<CryptoService.CryptoResponse> call, Throwable t) {
                showLoading(false);
            }
        });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }
}
