package com.example.saajan.navigation_implementation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    private ArrayList<Integer> imageUrl=new ArrayList<>();
    private ArrayList<String> imagename=new ArrayList<>();
    View root ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.home_fragment,container,false);
        RecyclerView recyclerView= root.findViewById(R.id.re);


        imageUrl.add(R.drawable.noun);
        imagename.add("Temperature");

        imageUrl.add(R.drawable.humidity);
        imagename.add("Humidity");

        imageUrl.add(R.drawable.airr_quality);
        imagename.add("Air Quality");

        imageUrl.add(R.drawable.speaker);
        imagename.add("Sound Intensity");

        imageUrl.add(R.drawable.bu);
        imagename.add("Light Intensity");

        RecyclerViewAdapter a=new RecyclerViewAdapter(imagename,imageUrl,getActivity());
        recyclerView.setAdapter(a);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }

}
