package com.example.reckon.ui.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reckon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyIngredient extends Fragment {


    public ModifyIngredient() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_ingredient, container, false);
    }

}
