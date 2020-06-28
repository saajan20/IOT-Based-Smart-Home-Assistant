package com.example.saajan.navigation_implementation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ApplianceFragment extends Fragment  {

    private ImageView image1,image2;
    View root;
    private DatabaseReference ref;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.appliance_fragment,container,false);
        image1= root.findViewById(R.id.image1);
        image2 = root.findViewById(R.id.image2);
        System.out.println(image1);
        appliance_status();
        return root;
    }

    private void appliance_status() {

        ref = FirebaseDatabase.getInstance().getReference("ApplianceStatus/Appliance1");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                if(data.equals("1"))
                {
                    System.out.println(image1);
                    image1.setImageResource(R.drawable.on);
                }
                else
                {
                    image1.setImageResource(R.drawable.off);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference("ApplianceStatus/Appliance2");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                if(data.equals("1"))
                {

                    image2.setImageResource(R.drawable.on);
                }
                else
                {
                    image2.setImageResource(R.drawable.off);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
