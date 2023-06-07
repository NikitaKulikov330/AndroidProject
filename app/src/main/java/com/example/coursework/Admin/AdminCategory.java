package com.example.coursework.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.coursework.R;



public class AdminCategory extends AppCompatActivity {
    private ImageView lipstick;
    private ImageView highlighter;
    private ImageView nailpolish;
    private ImageView mascara;
    private ImageView sculpturing;
    private ImageView concealer;
    private ImageView powder;
    private ImageView parfum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        init();

        lipstick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminHome.class);
                intent.putExtra("category","lipsticks");
                startActivity(intent);
            }
        });
        highlighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminHome.class);
                intent.putExtra("category","highlighter");
                startActivity(intent);
            }
        });
        nailpolish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminHome.class);
                intent.putExtra("category","nailpolish");
                startActivity(intent);
            }
        });
        mascara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminHome.class);
                intent.putExtra("category","mascara");
                startActivity(intent);
            }
        });
        sculpturing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminHome.class);
                intent.putExtra("category","sculpturing");
                startActivity(intent);
            }
        });
        concealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminHome.class);
                intent.putExtra("category","concealer");
                startActivity(intent);
            }
        });
        powder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminHome.class);
                intent.putExtra("category","powder");
                startActivity(intent);
            }
        });
        parfum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminHome.class);
                intent.putExtra("category","parfum");
                startActivity(intent);
            }
        });
    }

    private void init(){
        lipstick = findViewById(R.id.lipsticks);
        highlighter = findViewById(R.id.highlighters);
        nailpolish = findViewById(R.id.nailpolish);
        mascara = findViewById(R.id.mascara);
        sculpturing = findViewById(R.id.sculpturing);
        concealer = findViewById(R.id.concealer);
        powder = findViewById(R.id.powder);
        parfum = findViewById(R.id.parfums);
    }
}
