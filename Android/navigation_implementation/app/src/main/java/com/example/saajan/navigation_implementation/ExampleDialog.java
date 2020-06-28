package com.example.saajan.navigation_implementation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment {
private EditText editText;
    private DatabaseReference ref;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        ref= FirebaseDatabase.getInstance().getReference(MainActivity.AlertTopic);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.feedback,null);
        editText = view.findViewById(R.id.editText);
        alertDialogBuilder.setView(view)
                           .setTitle(MainActivity.AlertTopic)
                           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                            })
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                    String response = editText.getText().toString();
                                    System.out.println(response);
                                    Map<String,Object> insertvalues=new HashMap<>();
                                    insertvalues.put("Name","Saajan Kumar Jha");
                                    insertvalues.put("Message",response);
                                    insertvalues.put("TimeStamp",(new Date()).toString());
                                    String key=ref.push().getKey();
                                    ref.child(key).setValue(insertvalues);
                             }
                             });
        return alertDialogBuilder.create();
    }
}
