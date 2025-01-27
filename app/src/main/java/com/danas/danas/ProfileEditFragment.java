package com.danas.danas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProfileEditFragment extends Fragment {

    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImage;
    ImageButton profilePic;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        if (data != null && data.getData() != null) {
                            selectedImage = data.getData();

                            try {
                                // Convert Uri to byte array
                                byte[] imageBytes = getBytesFromUri(selectedImage);

                                // Save to database
                                DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
                                boolean success = dbHelper.saveProfilePicture(imageBytes);

                                if (success) {
                                    Toast.makeText(getContext(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                                }

                                // Display the image in profilePic ImageButton
                                profilePic.setImageURI(selectedImage);

                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Error processing image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private byte[] getBytesFromUri(Uri uri) throws IOException {
        InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }

        return byteArrayOutputStream.toByteArray();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile, container, false);

        Button saveButton = view.findViewById(R.id.btn_save);
        ImageButton backButton = view.findViewById(R.id.btn_back);
        profilePic = view.findViewById(R.id.btn_edit_profile);

        LoadingDialog loadingDialog = new LoadingDialog(requireActivity()); // Pass the activity instance here

        saveButton.setOnClickListener(v -> {
            // Start loading dialog
            loadingDialog.startLoadingDialog();

            // Delay for 3 seconds, then dismiss the dialog and navigate to ProfileFragment
            new Handler().postDelayed(() -> {
                loadingDialog.dismissDialog();

                // Navigate back to ProfileFragment
                requireActivity().getSupportFragmentManager().popBackStack();
            }, 3000); // 3-second delay
        });

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        byte[] imageBytes = dbHelper.getProfilePicture();

        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            profilePic.setImageBitmap(bitmap);
        }

        backButton.setOnClickListener(v -> {
            // Navigate back to ProfileFragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        profilePic.setOnClickListener(v -> {
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512)
                    .createIntent(intent -> {
                        imagePickLauncher.launch(intent);
                        return null;
                    });

        });

        return view;
    }

    // LoadingDialog class
    class LoadingDialog {
        private final Activity activity;
        private AlertDialog dialog;

        LoadingDialog(Activity myActivity) {
            activity = myActivity;
        }

        void startLoadingDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            LayoutInflater inflater = activity.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.dialog_loading, null));
            builder.setCancelable(false); // Disable canceling the dialog manually

            dialog = builder.create();
            dialog.show();
        }

        void dismissDialog() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}