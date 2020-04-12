package com.example.infrastructurecomplaintsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ApproveComplaint extends AppCompatActivity {

    private String priority = "";

    private FirebaseFirestore db;
    private ArrayList<String> depts;

    private Spinner spinner;
    private String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_complaint);
        Intent intent = getIntent();
        docId = intent.getStringExtra("ComplaintId");

        spinner = findViewById(R.id.spinner);
        depts = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        db.collection("departments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        depts.add((String) documentSnapshot.get("Name"));
                    }

                    //Retrieved Departments List
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ApproveComplaint.this,android.R.layout.simple_spinner_item,depts);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

                }
            }
        });

    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_high :
                if(checked) {
                    priority = "High";
                }
                break;
            case R.id.radio_medium :
                if(checked) {
                    priority = "Medium";
                }
                break;

            case R.id.radio_low :
                priority = "Low";
                break;
        }

    }

    public void approveComplaint(View view) {
        final String dept = (String) spinner.getSelectedItem();
        db.collection("complaints").document(docId).update("Priority",priority).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ApproveComplaint.this, "Priority Assigned", Toast.LENGTH_SHORT).show();
                    db.collection("complaints").document(docId).update("Type",dept).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                db.collection("complaints").document(docId).update("Status","Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(ApproveComplaint.this, "Approved", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ApproveComplaint.this,ListComlaintsAdmin.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }
}
