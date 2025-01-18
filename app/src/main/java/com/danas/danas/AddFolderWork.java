package com.danas.danas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddFolderWork extends AppCompatActivity {
    private String selectedColor = "#FFFFFF"; // Default color
    private EditText folderNameEditText;
    private LinearLayout colorPalette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder_work);

        folderNameEditText = findViewById(R.id.edit_folder_name_work);
        colorPalette = findViewById(R.id.color_palette);

        // Array of color button IDs
        int[] colorButtons = {R.id.color_red, R.id.color_orange, R.id.color_yellow,
                R.id.color_green, R.id.color_blue, R.id.color_purple};

        // Set click listeners for each color button
        for (int id : colorButtons) {
            ImageView colorButton = findViewById(id);
            colorButton.setOnClickListener(v -> {
                selectedColor = (String) v.getTag(); // Get color from tag
                highlightSelectedColor((ImageView) v); // Highlight selected color
            });
        }

        // Submit logic
        findViewById(R.id.btn_submit_folder_work).setOnClickListener(v -> {
            String folderName = folderNameEditText.getText().toString();
            if (!folderName.isEmpty()) {
                Intent intent = new Intent();
                intent.putExtra("folderName", folderName);
                intent.putExtra("folderColor", selectedColor);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Please enter a folder name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Highlight the selected color
    private void highlightSelectedColor(ImageView selected) {
        for (int id : new int[]{R.id.color_red, R.id.color_orange, R.id.color_yellow,
                R.id.color_green, R.id.color_blue, R.id.color_purple}) {
            ImageView colorButton = findViewById(id);
            colorButton.setAlpha(0.5f); // Dim all buttons
        }
        selected.setAlpha(1.0f); // Highlight selected button
    }
}