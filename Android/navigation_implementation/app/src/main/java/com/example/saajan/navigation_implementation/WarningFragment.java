package com.example.saajan.navigation_implementation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WarningFragment extends Fragment {

    private DatabaseReference ref;
    private ArrayList<String> imageUrl=new ArrayList<>();
    private ArrayList<String> sensorname=new ArrayList<>();
    private ArrayList<String> message=new ArrayList<>();
    private ArrayList<String> timestamp=new ArrayList<>();
    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.warning_fragment,container,false);
        final RecyclerView recyclerView= root.findViewById(R.id.re_warning);

        ref = FirebaseDatabase.getInstance().getReference("Warning");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot  snapshot:dataSnapshot.getChildren()){

                    Map<String,Object> data = (Map<String,Object>)snapshot.getValue();
                        String type = (String) data.get("Type");
                        String time = (String) data.get("Time");
                        System.out.println(type+" "+time);
                        switch (type)
                        {
                            case "Fire" :         imageUrl.add("https://banner2.cleanpng.com/20180825/tlu/kisspng-computer-icons-portable-network-graphics-gas-image-free-flame-icon-314458-download-flame-icon-31445-5b818322e8b465.8386454815352143709532.jpg");
                                                  sensorname.add("Fire Alert");
                                                  message.add("A fire has been detected by fire sensor");
                                                  timestamp.add(time);
                                                  break;

                            case "Intrusion" :    imageUrl.add("https://image.shutterstock.com/image-vector/security-camera-rec-trendy-style-600w-1528298456.jpg");
                                                  sensorname.add("Intrusion Alert");
                                                  message.add("An Intrusion has been detected by motion detector sensor");
                                                  timestamp.add(time);
                                                  break;

                            case "Gas" :          imageUrl.add("https://thumbs.dreamstime.com/b/gas-tank-icon-propane-cylinder-pressure-fuel-lpg-flat-style-isolated-white-background-74447818.jpg");
                                                  sensorname.add("Gas Leakage Alert");
                                                  message.add("A Gas Leakage has been detected by gas detector sensor");
                                                  timestamp.add(time);
                                                  break;

                            case "Air Quality":   imageUrl.add("https://image.shutterstock.com/image-vector/wind-icon-colored-symbol-premium-600w-1167269836.jpg");
                                                  sensorname.add("Air Quality Alert");
                                                  message.add("Air Quality just crossed the limit");
                                                  timestamp.add(time);
                                                  break;

                            case "Temperature":   imageUrl.add("https://thumbs.dreamstime.com/z/thermometer-vector-sun-heat-temperature-icon-thermometer-vector-icon-sun-heat-temperature-scale-summer-weather-119319936.jpg");
                                                  sensorname.add("Temperature Alert");
                                                  message.add("Temperature just crossed the limit");
                                                  timestamp.add(time);
                                                  break;

                        }

                }
                Collections.reverse(imageUrl);
                Collections.reverse(sensorname);
                Collections.reverse(message);
                Collections.reverse(timestamp);
                Warning_Adapter a= new  Warning_Adapter(sensorname,imageUrl,message,timestamp,getActivity());
                recyclerView.setAdapter(a);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imageUrl.add("https://banner2.cleanpng.com/20180825/tlu/kisspng-computer-icons-portable-network-graphics-gas-image-free-flame-icon-314458-download-flame-icon-31445-5b818322e8b465.8386454815352143709532.jpg");
        sensorname.add("Fire Alert");
        message.add("A fire has been detected by fire sensor");
        timestamp.add("Mar 1 12:25");

        imageUrl.add("https://image.shutterstock.com/image-vector/security-camera-rec-trendy-style-600w-1528298456.jpg");
        sensorname.add("Intrusion Alert");
        message.add("An Intrusion has been detected by motion detector sensor");
        timestamp.add("Mar 2 11:56");

        imageUrl.add("https://thumbs.dreamstime.com/b/gas-tank-icon-propane-cylinder-pressure-fuel-lpg-flat-style-isolated-white-background-74447818.jpg");
        sensorname.add("Gas Leakage Alert");
        message.add("A Gas Leakage has been detected by gas detector sensor");
        timestamp.add("Mar 3 05:28");

        imageUrl.add("https://thumbs.dreamstime.com/z/thermometer-vector-sun-heat-temperature-icon-thermometer-vector-icon-sun-heat-temperature-scale-summer-weather-119319936.jpg");
        sensorname.add("Temperature Alert");
        message.add("Temperature just crossed the limit");
        timestamp.add("Mar 4 15:47");

        imageUrl.add("https://image.shutterstock.com/image-vector/wind-icon-colored-symbol-premium-600w-1167269836.jpg");
        sensorname.add("Air Quality Alert");
        message.add("Air Quality just crossed the limit");
        timestamp.add("Mar 5 06:36");

        imageUrl.add("https://image.shutterstock.com/image-vector/speaker-icon-600w-432221320.jpg");
        sensorname.add("Sound Intensity Alert");
        message.add("Sound Intensity just crossed the limit");
        timestamp.add("Mar 6 20:14");

        imageUrl.add("https://image.shutterstock.com/image-vector/vector-illustration-light-bulb-rays-600w-624161009.jpg");
        sensorname.add("Light Intensity Alert");
        message.add("Light Intensity just crossed the limit");
        timestamp.add("Mar 9 09:45");

        Warning_Adapter a= new  Warning_Adapter(sensorname,imageUrl,message,timestamp,getActivity());
        recyclerView.setAdapter(a);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }
}
