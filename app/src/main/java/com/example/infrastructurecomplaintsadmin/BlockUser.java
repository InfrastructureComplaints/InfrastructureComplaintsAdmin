package com.example.infrastructurecomplaintsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BlockUser extends AppCompatActivity {

    FirebaseFirestore db;
    EditText text_block_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_user);
        text_block_user = (EditText) findViewById(R.id.text_block_username);
        db = FirebaseFirestore.getInstance();
    }

    public void block(View view) {
        final String username = text_block_user.getText().toString();
        db.collection("users").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        Toast.makeText(BlockUser.this, "User Found", Toast.LENGTH_SHORT).show();
                        db.collection("users").document(username).update("Blocked", "True").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(BlockUser.this, "User Blocked", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(BlockUser.this, "User Don't Exist", Toast.LENGTH_SHORT).show();
                        text_block_user.setText("");
                    }
                } else {
                    Toast.makeText(BlockUser.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void unBlock(View view) {
        final String username = text_block_user.getText().toString();
        db.collection("users").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        Toast.makeText(BlockUser.this, "User Found", Toast.LENGTH_SHORT).show();
                        db.collection("users").document(username).update("Blocked", "False").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(BlockUser.this, "User Unblocked", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(BlockUser.this, "User Don't Exist", Toast.LENGTH_SHORT).show();
                        text_block_user.setText("");
                    }
                } else {
                    Toast.makeText(BlockUser.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
