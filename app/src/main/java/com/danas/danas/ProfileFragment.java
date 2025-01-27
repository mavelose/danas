package com.danas.danas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private Dialog dialog;
    private ImageButton profilePic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePic = view.findViewById(R.id.btn_edit_profile); // Match with your layout's ImageView ID

        // Fetch the profile picture from database
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        byte[] imageBytes = dbHelper.getProfilePicture();

        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            profilePic.setImageBitmap(bitmap);
        } else {
            profilePic.setImageResource(R.drawable.placeholder_dpicon); // Default image if no image is available
        }

        ImageButton btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });

        ImageButton editButton = view.findViewById(R.id.btn_edit);
        editButton.setOnClickListener(v -> {
            // Replace ProfileFragment with ProfileEditFragment
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, new ProfileEditFragment());
            transaction.addToBackStack(null); // Add to back stack for back navigation
            transaction.commit();
        });

        return view;
    }

    private void showLogoutDialog() {
        dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog_logout);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnConfirm = dialog.findViewById(R.id.btn_confirm);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog();
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(requireActivity(), "Logout Successful!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }, 3000); // 3-second delay before starting MainActivity
            }
        });

        dialog.show();
    }

    private void showLoadingDialog() {
        Dialog loadingDialog = new Dialog(requireActivity());
        loadingDialog.setContentView(R.layout.dialog_loading); // Your custom loading layout
        Objects.requireNonNull(loadingDialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }
}