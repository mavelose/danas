package com.danas.danas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class SchoolFragment extends Fragment {
    MainActivity appMainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View schoolFragmentView = inflater.inflate(R.layout.fragment_school, container, false);
        ImageButton imgBtnAddFolder = schoolFragmentView.findViewById(R.id.btn_add_folder_school);
        appMainActivity=(MainActivity)getActivity();
        imgBtnAddFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFolderActivity = new Intent(getActivity(), AddFolderSchool.class);
                startActivity(addFolderActivity);
            }
        });

        return schoolFragmentView;
    }
}