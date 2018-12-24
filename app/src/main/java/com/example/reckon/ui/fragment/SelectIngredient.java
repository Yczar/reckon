package com.example.reckon.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.reckon.R;
import com.example.reckon.adapter.IngredientsAdapter;
import com.example.reckon.ui.ModifyIngredientDialogFragment;
import com.example.reckon.utils.OnIngredientItemSelected;
import com.example.reckon.utils.PrefManager;
import com.example.reckon.utils.ToolbarTitleListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectIngredient extends Fragment implements OnIngredientItemSelected {
    RecyclerView mRecyclerView;
    IngredientsAdapter mIngredientsAdapter;
    List<String> listOfIngredient = new ArrayList<>();
    private PrefManager manager;

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
         manager = new PrefManager(getContext());

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
        mIngredientsAdapter = new IngredientsAdapter(this, manager.getIngredientsValuesFromSP());

        mRecyclerView.setHasFixedSize(true);

        //Added the LayoutManager -*Fave
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mIngredientsAdapter);

        final FragmentManager fragmentManager = getFragmentManager();

        Button doneBtn = view.findViewById(R.id.selected_ingrdient_done_btn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyIngredientDialogFragment dialogFragment = new ModifyIngredientDialogFragment();
                dialogFragment.show(fragmentManager, "fragmentTag");
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(@NotNull String ingredient) {

        //List of Ingredient to be passed to the ModifyIngredient fragment -*Fave
        listOfIngredient.add(ingredient);
    }

    @Override
    public void onItemDeSelected(@NotNull String ingredient) {
        listOfIngredient.remove(ingredient);
    }
}
