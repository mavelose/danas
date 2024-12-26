package com.danas.danas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);

        // Find the "GET STARTED" button
        Button startBtn = findViewById(R.id.startBtn);

        // Set up a click listener for the button
        startBtn.setOnClickListener(view -> {
            // Navigate to MainActivity
            Intent intent = new Intent(IntroActivity.this, CreateStudentIDActivity.class);
            startActivity(intent);
        });

        // Reference the TextView
        TextView textHeading = findViewById(R.id.text_heading);

        // Define the text
        String text = "Embrace the challenge and turn it into your strength.";

        // Create a SpannableString
        SpannableString spannableString = new SpannableString(text);

        // Apply color to "challenge"
        int startChallenge = text.indexOf("challenge");
        int endChallenge = startChallenge + "challenge".length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0004")),
                startChallenge, endChallenge, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Apply color to "strength"
        int startStrength = text.indexOf("strength");
        int endStrength = startStrength + "strength".length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#01C307")),
                startStrength, endStrength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the styled text to the TextView
        textHeading.setText(spannableString);

        // Handle insets for edge-to-edge design
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}