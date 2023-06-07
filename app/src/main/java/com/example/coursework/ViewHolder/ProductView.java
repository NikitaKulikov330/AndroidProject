package com.example.coursework.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.coursework.Interface.ItemClickListner;
import com.example.coursework.R;

public class ProductView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName, productName1, productName2, productDescription, productPrice, productCart;
    public ImageView productImage;
    public ItemClickListner listner;

    public ProductView(View itemView) {
        super(itemView);

        productDescription = itemView.findViewById(R.id.product_description);
        productName = itemView.findViewById(R.id.product_name);
        productPrice = itemView.findViewById(R.id.product_price);
        productImage = itemView.findViewById(R.id.product_image);
        productCart = itemView.findViewById(R.id.product_cart);
        productName1 = itemView.findViewById(R.id.name_cart);
        productName2 = itemView.findViewById(R.id.name_cart2);
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