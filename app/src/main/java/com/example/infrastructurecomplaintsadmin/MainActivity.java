package com.example.infrastructurecomplaintsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText text_password,text_username;
    private String choice;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_username = (EditText)findViewById(R.id.text_username);
        text_password = (EditText)findViewById(R.id.text_password);
        choice = "";
        db = FirebaseFirestore.getInstance();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radioButton_admin :
                if(checked) {
                    choice = "Admin";
                }
                break;
            case R.id.radioButton_dept :
                if(checked) {
                    choice = "Department";
                }
                break;

        }
        Toast.makeText(this, choice, Toast.LENGTH_SHORT).show();
    }

    public void login(View view) {
        if(choice.equals("")) {
            Toast.makeText(this, "Please choose login type", Toast.LENGTH_SHORT).show();
        }
        else {
            final String username = text_username.getText().toString();
            final String password = text_password.getText().toString();
            if(choice.equals("Admin")) {
                //For Admin

                CollectionReference admin = db.collection("admin");
                admin.document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if(doc.exists()) {
                                //User Found
                                if(doc.get("Password").equals(password)) {
                                    //Password Matched
                                    Toast.makeText(MainActivity.this, "User found and password matched", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, ListComlaintsAdmin.class);
                                    intent.putExtra("Username",username);
                                    intent.putExtra("Choice","Admin");
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    //Wrong Password
                                    Toast.makeText(MainActivity.this, "Enter Correct Password", Toast.LENGTH_SHORT).show();
                                    text_password.setText("");
                                }
                            }
                            else {
                                //User not found
                                Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                                text_username.setText("");
                                text_password.setText("");
                            }
                        }
                        else {
                            //Query Not completed
                            Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                //For Department
                CollectionReference departments = db.collection("departments");
                departments.document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if(doc.exists()) {
                                //User Found
                                if(doc.get("Password").equals(password)) {
                                    //Password Matched
                                    Toast.makeText(MainActivity.this, "User found and password matched", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this,ListComplaintsDep.class);
                                    intent.putExtra("Username",username);
                                    intent.putExtra("Choice","Department");
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    //Wrong Password
                                    Toast.makeText(MainActivity.this, "Enter Correct Password", Toast.LENGTH_SHORT).show();
                                    text_password.setText("");
                                }
                            }
                            else {
                                //User not found
                                Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                                text_username.setText("");
                                text_password.setText("");
                            }
                        }
                        else {
                            //Query Not completed
                            Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}
