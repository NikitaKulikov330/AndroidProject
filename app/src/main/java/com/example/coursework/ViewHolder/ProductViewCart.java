package com.example.coursework.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.coursework.Interface.ItemClickListner;
import com.example.coursework.R;

public class ProductViewCart extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName,productPrice;
    public ItemClickListner listner;

    public ProductViewCart(View itemView){
        super(itemView);
        productName = itemView.findViewById(R.id.name_cart);
        productPrice = itemView.findViewById(R.id.name_cart2);

    }

    public ItemClickListner getListner() {
        return listner;
    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }


    @Override
    public void onClick(View view) {
        listner.onClick(view, getBindingAdapterPosition(), false);
    }
}

