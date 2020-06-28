package com.example.saajan.navigation_implementation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<String>    imagename = new ArrayList<>();
    ArrayList<Integer>    images    = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> imagename, ArrayList<Integer> images, Context mContext) {
        this.imagename = imagename;
        this.images = images;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap().load(images.get(position)).into(holder.image);
        holder.Imagename.setText(imagename.get(position));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(position)
                {
                    case 0:
                                 Intent intent=new Intent(mContext,Temperature.class);
                                 mContext.startActivity(intent);
                                 break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return imagename.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    CircleImageView image;
    TextView Imagename;
    RelativeLayout parent;


        public ViewHolder(View itemView)
        {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            Imagename=itemView.findViewById(R.id.imagename);
            parent=itemView.findViewById(R.id.parent_layout);
        }
    }
}
