package com.danas.danas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WorkFragment extends Fragment {
    MainActivity appMainActivity;
    private static final int ADD_FOLDER_REQUEST_CODE = 1;
    private LinearLayout folderContainer;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View workFragmentView = inflater.inflate(R.layout.fragment_work, container, false);

        ImageButton imgBtnAddFolder = workFragmentView.findViewById(R.id.btn_add_folder_work);
        folderContainer = workFragmentView.findViewById(R.id.folder_container); // Container to hold folders
        appMainActivity = (MainActivity) getActivity();

        imgBtnAddFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddFolderWork activity for result
                Intent addFolderActivity = new Intent(getActivity(), AddFolderWork.class);
                startActivityForResult(addFolderActivity, ADD_FOLDER_REQUEST_CODE);
            }
        });

        return workFragmentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_FOLDER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // Retrieve the folder name and color from the AddFolderWork activity
            String folderName = data.getStringExtra("folderName");
            String folderColor = data.getStringExtra("folderColor");

            // Add the folder dynamically to the UI
            addFolderToUI(folderName, folderColor);
        }
    }

    private void addFolderToUI(String folderName, String folderColor) {
        // Create a TextView for the folder
        TextView folderView = new TextView(getContext());
        folderView.setText(folderName);
        folderView.setBackgroundColor(Color.parseColor(folderColor));
        folderView.setPadding(20, 20, 20, 20);
        folderView.setTextSize(16);
        folderView.setTextColor(Color.WHITE);

        // Set layout parameters for the TextView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 10, 10, 10);
        folderView.setLayoutParams(params);

        // Add the TextView to the container
        folderContainer.addView(folderView);
    }
}