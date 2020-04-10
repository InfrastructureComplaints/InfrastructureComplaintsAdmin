package com.example.infrastructurecomplaintsadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class StatusUpdateDept extends AppCompatActivity {

    //Image purpose variables
    private static final int PICK_IMAGE = 1;
    private Uri mImageURI;
    private ImageView imageView;

    private String ComplaintId;

    private boolean imagePlucked;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_update_dept);

        imagePlucked = false;
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images");
        Intent intent= getIntent();
        ComplaintId = intent.getStringExtra("ComplaintId");

    }


    //Image functions
    //Get Image extension
    private String getImageExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    public void clickImage(View view) {
        openFileChooser();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageURI = data.getData();

            //Picasso.with(this).load(mImageURI).into(imageView);
            //Glide.with(this).load(mImageURI).into(imageView);
            imagePlucked = true;
            Toast.makeText(this, "imagePlucked", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(View view) {
        db.collection("complaints").document(ComplaintId).update("Status","Resolved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(StatusUpdateDept.this, "Complaint Resolved", Toast.LENGTH_SHORT).show();
                    if(imagePlucked) {
                        StorageReference imageRef = storageReference.child(ComplaintId + ".jpg");
                        imageRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    storageReference.child(ComplaintId + ".jpg").putFile(mImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(StatusUpdateDept.this, "Image Updated", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });

    }
}
