package com.example.infrastructurecomplaintsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class Department extends AppCompatActivity {

    private TextView text_name,text_email,text_contact;
    FirebaseFirestore db;
    private String docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        text_name = (TextView) findViewById(R.id.text_name_d);
        text_email = (TextView) findViewById(R.id.text_email_d);
        text_contact = (TextView) findViewById(R.id.text_contact_d);

        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();

        db.collection("departments").whereEqualTo("Name",intent.getStringExtra("Name")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                    text_name.setText((String) doc.get("Name"));
                    docId = doc.getId();
                    text_email.setText(docId);
                    text_contact.setText((String) doc.get("Contact"));

                }
                else {
                    Toast.makeText(Department.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteDepartment(View view) {
        db.collection("departments").document(docId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(Department.this, "Department Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Department.this,ListDepartment.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
