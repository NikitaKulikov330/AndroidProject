package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.coursework.Categories.Concealer;
import com.example.coursework.Categories.Highlighter;
import com.example.coursework.Categories.Lipstick;
import com.example.coursework.Categories.Mascara;
import com.example.coursework.Categories.Nailpolish;
import com.example.coursework.Categories.Parfum;
import com.example.coursework.Categories.Powder;
import com.example.coursework.Categories.Sculpturing;

import java.lang.reflect.Array;

public class CategoryUser extends AppCompatActivity implements CategoryAdapter.ItemClickListener {
    RecyclerView recyclerView;


    int img[] = {R.drawable.parfums, R.drawable.concealer, R.drawable.lipsticks, R.drawable.powder, R.drawable.nailpolish, R.drawable.highlighters3, R.drawable.mascara, R.drawable.sculpturing};
    String ttl[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_user);

        Toolbar toolbar = findViewById(R.id.toolbar_ctg);
        toolbar.setTitle("Categories");
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recycler_category);

        ttl = getResources().getStringArray(R.array.str_array);

        CategoryAdapter categoryAdapter = new CategoryAdapter(ttl, img, this);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        Array.get(img, position);
        if (img[position] == R.drawable.parfums) {
            Intent intent = new Intent(this, Parfum.class);
            startActivity(intent);
        } else if (img[position] == R.drawable.concealer) {
            Intent intent = new Intent(this, Concealer.class);
            startActivity(intent);
        } else if (img[position] == R.drawable.lipsticks) {
            Intent intent = new Intent(this, Lipstick.class);
            startActivity(intent);
        } else if (img[position] == R.drawable.powder) {
            Intent intent = new Intent(this, Powder.class);
            startActivity(intent);
        } else if (img[position] == R.drawable.nailpolish) {
            Intent intent = new Intent(this, Nailpolish.class);
            startActivity(intent);
        } else if (img[position] == R.drawable.highlighters3) {
            Intent intent = new Intent(this, Highlighter.class);
            startActivity(intent);
        } else if (img[position] == R.drawable.mascara) {
            Intent intent = new Intent(this, Mascara.class);
            startActivity(intent);
        } else if (img[position] == R.drawable.sculpturing) {
            Intent intent = new Intent(this, Sculpturing.class);
            startActivity(intent);
        }
    }
}