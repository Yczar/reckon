package com.example.reckon.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reckon.R;
import com.example.reckon.adapter.IngredientsAdapter;
import com.example.reckon.utils.PrefManager;
import com.example.reckon.utils.ToolbarTitleListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectIngredient extends Fragment {
    RecyclerView mRecyclerView;
    IngredientsAdapter mIngredientsAdapter;


    public SelectIngredient() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Code to update the update the toolbar's title - *Fave
        PrefManager manager = new PrefManager(getContext());

        ToolbarTitleListener toolbarTitleListener = (ToolbarTitleListener)getActivity();
        toolbarTitleListener.updateTitle(null, manager.getSelectedLiveStockToSP());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout
                .fragment_select_ingredient, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mIngredientsAdapter = new IngredientsAdapter(new PrefManager(getContext()).getIngredientsValuesFromSP());

        mRecyclerView.setHasFixedSize(true);

        //Added the LayoutManager -*Fave
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mIngredientsAdapter);
        return view;
    }

}
