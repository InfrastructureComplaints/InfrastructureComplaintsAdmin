package com.example.infrastructurecomplaintsadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
