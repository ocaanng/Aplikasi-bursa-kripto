package com.example.qoing;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.ViewHolder> {
    private List<CryptoItem> cryptoList;
    private Context context;

    public CryptoAdapter(Context context, List<CryptoItem> cryptoList) {
        this.context = context;
        this.cryptoList = cryptoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crypto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CryptoItem cryptoItem = cryptoList.get(position);
        holder.cryptoName.setText(cryptoItem.getName());
        holder.cryptoSymbol.setText(cryptoItem.getSymbol());

        // Get the resource ID of the image using its name
        int resourceId = context.getResources().getIdentifier(cryptoItem.getSymbol().toLowerCase(), "drawable", context.getPackageName());

        // Set the image resource
        if (resourceId != 0) {
            holder.cryptoIcon.setImageResource(resourceId);
        } else {
            // Set a default image if the resource is not found
            holder.cryptoIcon.setImageResource(R.drawable.btc);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CryptoAdapter", "CryptoItem: " + cryptoItem);
                Intent intent = new Intent(context, CryptoDetailActivity.class);
                intent.putExtra("cryptoItem", cryptoItem);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cryptoName, cryptoSymbol;
        ImageView cryptoIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cryptoName = itemView.findViewById(R.id.crypto_name);
            cryptoSymbol = itemView.findViewById(R.id.crypto_symbol);
            cryptoIcon = itemView.findViewById(R.id.crypto_icon);
        }
    }
}
