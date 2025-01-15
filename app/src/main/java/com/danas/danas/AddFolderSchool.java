package com.danas.danas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddFolderSchool extends AppCompatActivity implements View.OnClickListener {

    Button btnAddFolder;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder_school);

        //btnAddFolder = findViewById(R.id.btn_submit_folder);
        btnCancel = findViewById(R.id.btn_cancel_submit_folder);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancel_submit_folder) {
            finish();
        }
    }
}