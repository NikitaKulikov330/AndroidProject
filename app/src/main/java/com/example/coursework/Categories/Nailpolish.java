package com.example.coursework.Categories;

import static com.example.coursework.Cart.namee;
import static com.example.coursework.Cart.pricee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coursework.Admin.Products;
import com.example.coursework.Cart;
import com.example.coursework.CartAdd;
import com.example.coursework.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Nailpolish extends AppCompatActivity {
    DatabaseReference productRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nailpolish);

        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        Toolbar toolbar = findViewById(R.id.toolbar_nailpolish);
        toolbar.setTitle("Nailpolish");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_nailpolish);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nailpolish.this, Cart.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recycler_view_nailpolish);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(productRef.orderByChild("category").equalTo("nailpolish"),Products.class).build();
        FirebaseRecyclerAdapter<Products, CartAdd.ProductView> adapter = new FirebaseRecyclerAdapter<Products, CartAdd.ProductView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartAdd.ProductView holder, int position, @NonNull Products model) {
                holder.productPrice.setText("Price: " + model.getPrice() + "$");
                holder.productName.setText(model.getTitle());
                holder.productDescription.setText(model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.productImage);
                holder.productCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Nailpolish.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        pricee.add("Price: " + model.getPrice() + "$");
                        namee.add(model.getTitle());
                    }
                });
//                System.out.println(model.getImage());
//                System.out.println(model.getTitle());

            }

            @NonNull
            @Override
            public CartAdd.ProductView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_structure, parent, false);
                CartAdd.ProductView holder = new CartAdd.ProductView(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
}