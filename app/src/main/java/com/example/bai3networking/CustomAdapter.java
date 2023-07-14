package com.example.bai3networking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    Context context;
    ArrayList<AlbumModel>list;

    public CustomAdapter(Context context, ArrayList<AlbumModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.txtid.setText(list.get(position).id);
        holder.txttitle.setText(list.get(position).title);
        Picasso.get().load(list.get(position).thumbnailUrl).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtid,txttitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
            txtid=itemView.findViewById(R.id.txtid);
            txttitle=itemView.findViewById(R.id.txttitle);


        }
    }
}
