package com.example.coursework;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapterCart extends RecyclerView.Adapter<CategoryAdapterCart.MyViewHolder> {
    List<String> catg = new ArrayList<>();
    List<String> nm = new ArrayList<>();

    private ItemClickListener itemClickListener;


    public CategoryAdapterCart( List<String> catg,List<String> nm, ItemClickListener itemClickListener){
        this.nm = nm;
        this.catg = catg;
        this.itemClickListener = itemClickListener;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_structure,parent,false);
        return new MyViewHolder(itemView1 , itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nm2.setText(nm.get(position));
        System.out.println(nm.get(position));
        holder.nm1.setText(catg.get(position));

    }

    @Override
    public int getItemCount() {
        return nm.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nm1;
        TextView nm2;
        ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView , ItemClickListener itemClickListener) {
            super(itemView);
            nm1 = itemView.findViewById(R.id.name_cart);
            nm2 = itemView.findViewById(R.id.name_cart2);
            this.itemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(getBindingAdapterPosition());
        }
    }
    public interface ItemClickListener{
        void onItemClick(int position);
    }
}
