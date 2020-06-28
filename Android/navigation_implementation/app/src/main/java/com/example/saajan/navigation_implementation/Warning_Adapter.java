package com.example.saajan.navigation_implementation;

import android.content.Context;
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

public class Warning_Adapter extends RecyclerView.Adapter<Warning_Adapter.ViewHolder> {

    ArrayList<String> imagename = new ArrayList<>();
    ArrayList<String>    images    = new ArrayList<>();
    private ArrayList<String> messages=new ArrayList<>();
    private ArrayList<String> timestamps=new ArrayList<>();
    private Context mContext;

    public Warning_Adapter(ArrayList<String> imagename, ArrayList<String> images,ArrayList<String> messages, ArrayList<String> timestamps, Context mContext) {
        this.imagename = imagename;
        this.images = images;
        this.mContext = mContext;
        this.messages=messages;
        this.timestamps=timestamps;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView Imagename;
        RelativeLayout parent;
        TextView message;
        TextView timestamp;

        public ViewHolder(View itemView)
        {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            Imagename=itemView.findViewById(R.id.imagename);
            parent=itemView.findViewById(R.id.parent);
            message=itemView.findViewById(R.id.message);
            timestamp=itemView.findViewById(R.id.timestamp);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.warning_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap().load(images.get(position)).into(holder.image);
        holder.Imagename.setText(imagename.get(position));
        holder.message.setText(messages.get(position));
        holder.timestamp.setText(timestamps.get(position));

    }

    @Override
    public int getItemCount() {
        return imagename.size();
    }

}
