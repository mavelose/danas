package com.danas.danas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EditProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Find the back button image view
        ImageView imgProfileBack = rootView.findViewById(R.id.img_profile_back);

        // Set onClickListener to go back to ProfileFragment
        imgProfileBack.setOnClickListener(v -> {
            // Replace EditProfileFragment with ProfileFragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, new ProfileFragment()); // Replace with ProfileFragment
            transaction.addToBackStack(null); // Optionally add to back stack so the user can navigate back
            transaction.commit();
        });

        return rootView;
    }
}