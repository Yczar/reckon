package com.example.reckon.ui.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reckon.R;
import com.example.reckon.adapter.IngredientsAdapter;

import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyIngredient extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    RecyclerView mRecyclerView;
    IngredientsAdapter mIngredientsAdapter;
    Map<String, Number> ingredients;

    public ModifyIngredient() {
        // Required empty public constructor
    }

    public static ModifyIngredient newInstance(Map<String, Objects> ingredient) {
        ModifyIngredient fragment = new ModifyIngredient();
        Bundle args = new Bundle();
//        args.putMap(ARG_PARAM1, ingredient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            ingredients = getArguments().getMap(ARG_PARAM1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout
                .fragment_modify_ingredient, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mIngredientsAdapter = new IngredientsAdapter(ingredients);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mIngredientsAdapter);
        return view;
    }

}
