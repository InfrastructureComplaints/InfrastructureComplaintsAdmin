package com.example.infrastructurecomplaintsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Feedback extends AppCompatActivity {

    private String docId;
    private FirebaseFirestore db;
    private TextView feed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feed = (TextView) findViewById(R.id.feed);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        docId = intent.getStringExtra("ComplaintId");

        db.collection("feedbacks").whereEqualTo("Complaint_Id",docId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                            if(doc.exists());
                                feed.setText((String) doc.get("Feedback"));
                }
            }
        });
    }
}
