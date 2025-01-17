package com.danas.danas;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize the DatabaseHelper
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        // Initialize the TextViews
        TextView txtName = rootView.findViewById(R.id.txt_name);
        TextView txtPosition = rootView.findViewById(R.id.txt_position);

        // Fetch data from database
        Cursor studentCursor = dbHelper.getStudentData();
        Cursor employeeCursor = dbHelper.getEmployeeData();

        // Set data for student
        if (studentCursor != null && studentCursor.moveToFirst()) {
            // Ensure column indices are valid
            int firstNameIndex = studentCursor.getColumnIndex(DatabaseHelper.COLUMN_FIRST_NAME);
            int lastNameIndex = studentCursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_NAME);
            int programIndex = studentCursor.getColumnIndex(DatabaseHelper.COLUMN_PROGRAM);
            int yearLevelIndex = studentCursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR_LEVEL);

            if (firstNameIndex != -1 && lastNameIndex != -1 && programIndex != -1 && yearLevelIndex != -1) {
                String firstName = studentCursor.getString(firstNameIndex);
                String lastName = studentCursor.getString(lastNameIndex);
                String program = studentCursor.getString(programIndex);
                int yearLevel = studentCursor.getInt(yearLevelIndex);

                // Format the student's full name
                txtName.setText(getString(R.string.student_name, firstName, lastName));

                // Format the student's year level with suffix and program
                String yearWithSuffix = getYearWithSuffix(yearLevel);
                String position = getString(R.string.student_info, yearWithSuffix, program);
                txtPosition.setText(position);
            }
        }

        // Set data for employee role (assuming only one employee record for now)
        if (employeeCursor != null && employeeCursor.moveToFirst()) {
            int roleIndex = employeeCursor.getColumnIndex(DatabaseHelper.COLUMN_ROLE);

            if (roleIndex != -1) {
                String role = employeeCursor.getString(roleIndex);
                String formattedRole = formatRole(role);  // Capitalize role text
                txtPosition.append("\n" + formattedRole);  // Appending to the same TextView
            }
        }

        // Close cursors
        if (studentCursor != null) studentCursor.close();
        if (employeeCursor != null) employeeCursor.close();

        // Set up the Edit Profile Button click listener
        Button btnEditProfile = rootView.findViewById(R.id.btn_edit_profile);
        btnEditProfile.setOnClickListener(v -> {
            // Create a new EditProfileFragment
            EditProfileFragment editProfileFragment = new EditProfileFragment();

            // Replace the current fragment with the EditProfileFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, editProfileFragment); // Ensure your container ID is correct
            transaction.addToBackStack(null); // Add to back stack to navigate back
            transaction.commit();
        });

        return rootView;
    }

    // Helper method to get the year level with the correct suffix
    private String getYearWithSuffix(int yearLevel) {
        String suffix;
        switch (yearLevel) {
            case 1:
                suffix = "st";
                break;
            case 2:
                suffix = "nd";
                break;
            case 3:
                suffix = "rd";
                break;
            default:
                suffix = "th";
                break;
        }
        return yearLevel + suffix;
    }

    // Helper method to format the role with the first letter of each word in uppercase
    private String formatRole(String role) {
        String[] words = role.split(" ");
        StringBuilder formattedRole = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                formattedRole.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        // Remove the trailing space
        return formattedRole.toString().trim();
    }
}