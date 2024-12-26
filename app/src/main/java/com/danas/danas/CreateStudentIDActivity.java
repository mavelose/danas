package com.danas.danas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CreateStudentIDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_student_id);

        // Find the "Next" button
        Button nextButton = findViewById(R.id.next_button);

        // Set an OnClickListener to handle the button click
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to transition to CreateEmployeeIDActivity
                Intent intent = new Intent(CreateStudentIDActivity.this, CreateEmployeeIDActivity.class);
                startActivity(intent);
            }
        });
    }
}