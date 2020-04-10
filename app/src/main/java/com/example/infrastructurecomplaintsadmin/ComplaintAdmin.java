package com.example.infrastructurecomplaintsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ComplaintAdmin extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView text_subject;
    private String description;
    private String status;
    private String user;
    private TextView text_description;
    private TextView text_user;
    private TextView text_status;
    private String docId;
    private Button app,rej,del;
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_admin);

        text_subject = (TextView)findViewById(R.id.text_subject_c);
        Intent intent = getIntent();
        subject = intent.getStringExtra("Subject");
        text_subject.setText(subject);
        text_description = (TextView)findViewById(R.id.text_description_c);
        text_user = (TextView)findViewById(R.id.text_user_c);
        text_status = (TextView)findViewById(R.id.text_status);
        app = (Button)findViewById(R.id.button_approve);
        rej = (Button)findViewById(R.id.button_reject);
        del = (Button)findViewById(R.id.button_delete);
        del.setVisibility(View.INVISIBLE);
        app.setVisibility(View.INVISIBLE);
        rej.setVisibility(View.INVISIBLE);

        //Getting databse instance
        db = FirebaseFirestore.getInstance();

        db.collection("complaints").whereEqualTo("Subject",subject).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    //Toast.makeText(Complaint.this, "Query Successfull", Toast.LENGTH_SHORT).show();

                    DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                    description = (String) doc.get("Description");
                    status = (String) doc.get("Status");
                    if(status.equals("Rejected")) {
                        del.setVisibility(View.VISIBLE);
                    }
                    else {
                        app.setVisibility(View.VISIBLE);
                        rej.setVisibility(View.VISIBLE);
                    }
                    user = (String) doc.get("User");

                    //Setting data in interface
                    text_description.setText(description);
                    text_status.setText(status);
                    text_user.setText(user);
                    docId = doc.getId();


                }
                else {
                    Toast.makeText(ComplaintAdmin.this, "Failed to fetch", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void approve(View view) {

        Toast.makeText(this, "Approved", Toast.LENGTH_SHORT).show();
    }

    public void reject(View view) {

        //Toast.makeText(this, "Rejected", Toast.LENGTH_SHORT).show();
        db.collection("complaints").document(docId).update("Status","Rejected").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ComplaintAdmin.this, "Complaint Rejected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ComplaintAdmin.this,ComplaintAdmin.class);
                    intent.putExtra("Subject",subject);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(ComplaintAdmin.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void delete(View view) {
        //Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        db.collection("complaints").document(docId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ComplaintAdmin.this, "Complaint Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(ComplaintAdmin.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
