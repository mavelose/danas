package com.danas.danas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CreatingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private static final int LOADING_DURATION = 3000; // Duration in milliseconds (3 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creating);

        // Initialize the ProgressBar
        progressBar = findViewById(R.id.progressBar);

        // Simulate a loading process
        new Handler().postDelayed(() -> {
            // Transition to MainActivity after the delay
            Intent intent = new Intent(CreatingActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Finish the current activity to remove it from the back stack
        }, LOADING_DURATION);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}