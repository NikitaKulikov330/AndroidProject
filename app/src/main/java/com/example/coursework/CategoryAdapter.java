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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    int catg[];
    String nm[];
    private ItemClickListener itemClickListener;


    public CategoryAdapter(String nm[], int catg[] , ItemClickListener itemClickListener){
        this.nm = nm;
        this.catg = catg;
        this.itemClickListener = itemClickListener;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_structure,parent,false);
        return new MyViewHolder(itemView1 , itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nm1.setText(nm[position]);
        System.out.println(nm[position]);
        holder.imge.setImageResource(catg[position]);

    }

    @Override
    public int getItemCount() {
        return nm.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nm1;
        ImageView imge;
        ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView , ItemClickListener itemClickListener) {
            super(itemView);
            nm1 = itemView.findViewById(R.id.t1);
            imge = itemView.findViewById(R.id.t2);
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

    public static interface ItemClickListner {
        public  void onClick(View view , int position ,boolean isLongClick);
    }
}
