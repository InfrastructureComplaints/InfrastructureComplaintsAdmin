package com.example.infrastructurecomplaintsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateDepartment extends AppCompatActivity {

    private EditText text_name,text_email,text_contact,text_password;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_department);
        text_name = (EditText) findViewById(R.id.text_name_cd);
        text_email = (EditText) findViewById(R.id.text_email_cd);
        text_contact = (EditText) findViewById(R.id.text_contact_cd);
        text_password = (EditText) findViewById(R.id.text_password_cd);

        db = FirebaseFirestore.getInstance();
    }

    public void createDepartment(View view) {
        String email = text_email.getText().toString();
        String password = text_password.getText().toString();
        String contact = text_contact.getText().toString();
        String name = text_name.getText().toString();
        if(email.equals("") || name.equals("") || contact.equals("") || password.equals("")) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else {
            Map<String,Object> data = new HashMap<>();
            data.put("Name",name);
            data.put("Contact",contact);
            data.put("Password",password);
            db.collection("departments").document(email).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(CreateDepartment.this, "New Department added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),ListDepartment.class);
                        finish();
                    }
                }
            });
        }
    }
}
