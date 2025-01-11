package com.danas.danas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CreateStudentIDActivity extends AppCompatActivity {
    private EditText firstNameInput, lastNameInput, universityInput, programInput, yearLevelInput;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_student_id);

        // Initialize Views
        firstNameInput = findViewById(R.id.firstname_input);
        lastNameInput = findViewById(R.id.lastname_input);
        universityInput = findViewById(R.id.university_input);
        programInput = findViewById(R.id.program_input);
        yearLevelInput = findViewById(R.id.yearlevel_input);

        // Initialize Database Helper
        dbHelper = new DatabaseHelper(this);

        // Find the "Next" button
        Button nextButton = findViewById(R.id.next_button);

        // Set an OnClickListener to handle the button click
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input data
                String firstName = firstNameInput.getText().toString().trim();
                String lastName = lastNameInput.getText().toString().trim();
                String university = universityInput.getText().toString().trim();
                String program = programInput.getText().toString().trim();
                String yearLevelStr = yearLevelInput.getText().toString().trim();

                // Validate inputs
                if (firstName.isEmpty() || lastName.isEmpty() || university.isEmpty() ||
                        program.isEmpty() || yearLevelStr.isEmpty()) {
                    Toast.makeText(CreateStudentIDActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int yearLevel;
                try {
                    yearLevel = Integer.parseInt(yearLevelStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(CreateStudentIDActivity.this, "Year level must be a valid number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save data to database
                dbHelper.saveStudentData(firstName, lastName, university, program, yearLevel);
                Toast.makeText(CreateStudentIDActivity.this, "Student data saved!", Toast.LENGTH_SHORT).show();

                // Clear inputs
                clearInputFields();

                // Create an intent to transition to CreateEmployeeIDActivity
                Intent intent = new Intent(CreateStudentIDActivity.this, CreateEmployeeIDActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clearInputFields() {
        firstNameInput.setText("");
        lastNameInput.setText("");
        universityInput.setText("");
        programInput.setText("");
        yearLevelInput.setText("");
    }
}