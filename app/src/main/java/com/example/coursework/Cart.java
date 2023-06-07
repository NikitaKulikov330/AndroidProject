package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import java.util.List;

public class Cart extends AppCompatActivity implements CategoryAdapterCart.ItemClickListener {
    RecyclerView recyclerView;
    Button order;

    public static  List<String> namee = new ArrayList<>();
    public static List<String> pricee = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar_cart);
        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recycler_view_cart);



        CategoryAdapterCart categoryAdapter = new CategoryAdapterCart(namee, pricee, this);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        order = findViewById(R.id.make_order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this,Order.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }

}