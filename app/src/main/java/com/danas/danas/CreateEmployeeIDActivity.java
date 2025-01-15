package com.danas.danas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateEmployeeIDActivity extends AppCompatActivity {
    private EditText companyInput, roleInput, numberOfJobsInput;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_employee_idactivity);

        // Initialize Views
        companyInput = findViewById(R.id.company_input);
        roleInput = findViewById(R.id.role_input);
        numberOfJobsInput = findViewById(R.id.jobs_input);

        // Initialize Database Helper
        dbHelper = new DatabaseHelper(this);

        // Set window insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the Terms and Regulations click listener
        TextView termsText = findViewById(R.id.terms_text);
        termsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to TermsAndConditionsActivity
                Intent intent = new Intent(CreateEmployeeIDActivity.this, TermsAndRegulationsActivity.class);
                startActivity(intent);
            }
        });

        // Set up the Save button listener
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEmployeeIDActivity.this, CreatingActivity.class);
                startActivity(intent);

                // Get input data
                String company = companyInput.getText().toString().trim();
                String role = roleInput.getText().toString().trim();
                String numberOfJobsStr = numberOfJobsInput.getText().toString().trim();

                // Validate inputs
                if (company.isEmpty() || role.isEmpty() || numberOfJobsStr.isEmpty()) {
                    Toast.makeText(CreateEmployeeIDActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int numberOfJobs;
                try {
                    numberOfJobs = Integer.parseInt(numberOfJobsStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(CreateEmployeeIDActivity.this, "Number of jobs must be a valid number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save data to database
                dbHelper.saveEmployeeData(company, role, numberOfJobs);
                Toast.makeText(CreateEmployeeIDActivity.this, "Employee data saved!", Toast.LENGTH_SHORT).show();

                // Clear inputs
                clearInputFields();
            }
        });
    }

    private void clearInputFields() {
        companyInput.setText("");
        roleInput.setText("");
        numberOfJobsInput.setText("");
    }
}