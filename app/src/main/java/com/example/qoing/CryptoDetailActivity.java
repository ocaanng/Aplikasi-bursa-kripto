package com.example.qoing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CryptoDetailActivity extends AppCompatActivity {

    private TextView tv_header;
    private TextView textViewTitle;
    private TextView textViewSymbol;
    private TextView textViewPrice;
    private TextView textViewMarketCap;
    private TextView textViewVolume;
    private TextView textViewCirculatingSupply;
    private TextView textViewTotalSupply;
    private TextView textViewChange24h;
    private TextView textViewChange1h;
    private TextView textViewChange7d;
    private ImageView imageViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_detail);

        // Mengambil data dari intent
        CryptoItem cryptoItem = getIntent().getParcelableExtra("CryptoItem");

        // Inisialisasi view
        tv_header = findViewById(R.id.text_header);
        imageViewLogo = findViewById(R.id.image_view_logo);
        textViewTitle = findViewById(R.id.text_view_title);
        textViewSymbol = findViewById(R.id.text_view_symbol);
        textViewPrice = findViewById(R.id.text_view_price);
        textViewMarketCap = findViewById(R.id.text_view_mcap);
        textViewVolume = findViewById(R.id.text_view_vol24);
        textViewCirculatingSupply = findViewById(R.id.text_view_cspply);
        textViewTotalSupply = findViewById(R.id.text_view_tspply);
        textViewChange24h = findViewById(R.id.text_view_pch24h);
        textViewChange1h = findViewById(R.id.text_view_pch1h);
        textViewChange7d = findViewById(R.id.text_view_pch7d);

        // Menampilkan data koin
        if (cryptoItem != null) {
            tv_header.setText(cryptoItem.getName());
            textViewTitle.setText(cryptoItem.getName());
            textViewSymbol.setText(cryptoItem.getSymbol());
            textViewPrice.setText("$" + cryptoItem.getPrice());
            textViewMarketCap.setText("$" + cryptoItem.getMarketCap());
            textViewVolume.setText("$" + cryptoItem.getVolume24h());
            textViewCirculatingSupply.setText(cryptoItem.getCirculatingSupply());
            textViewTotalSupply.setText(cryptoItem.getTotalSupply());
            textViewChange24h.setText(cryptoItem.getPercentChange24h() + "%");
            textViewChange1h.setText(cryptoItem.getPercentChange1h() + "%");
            textViewChange7d.setText(cryptoItem.getPercentChange7d() + "%");

            // Mengatur gambar logo
            int imageId = getResources().getIdentifier(cryptoItem.getSymbol().toLowerCase(), "drawable", getPackageName());
            imageViewLogo.setImageResource(imageId != 0 ? imageId : R.drawable.ic_launcher_background);
        } else {
            // Jika objek null, tampilkan pesan kesalahan atau lakukan penanganan lainnya
            Log.e("CryptoDetailActivity", "CryptoItem is null");
            // Misalnya, tampilkan pesan kesalahan
            Toast.makeText(this, "Failed to load crypto data", Toast.LENGTH_SHORT).show();
            // Kemudian selesaikan aktivitas atau lakukan tindakan lainnya
            finish();
        }

        // Tombol kembali
        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
