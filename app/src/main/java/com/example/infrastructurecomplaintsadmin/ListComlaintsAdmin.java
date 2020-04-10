package com.example.infrastructurecomplaintsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class ListComlaintsAdmin extends AppCompatActivity {


    String email;
    private FirebaseFirestore db;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comlaints_admin);

        //Getting Intent extra infromation i.e user email
        Intent intent = getIntent();
        email = intent.getStringExtra("Username");

        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

        //Initialize Recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Array List of Complaint Objects
        final ArrayList<CmpItem> cmps = new ArrayList<>();


        //Getting data from Firebase
        db = FirebaseFirestore.getInstance();
        CollectionReference complaints = db.collection("complaints");
        complaints.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    //Toast.makeText(ListComplaints.this, "Fetching Successfull", Toast.LENGTH_SHORT).show();


                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = (String) document.get("Subject");
                        String date = (String)document.get("Date");
                        String time = (String)document.get("Time");
                        CmpItem cmp = new CmpItem(subject,date,time);
                        cmps.add(cmp);
                    }
                    //Initializing Adapter
                    Collections.sort(cmps,new cmpComparator());
                    mAdapter = new MyAdapter(ListComlaintsAdmin.this,cmps);
                    recyclerView.setAdapter(mAdapter);
                } else {
                    Toast.makeText(ListComlaintsAdmin.this, "Failed to fetch Complaints", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}
