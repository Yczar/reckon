package com.example.reckon.ui.fragment;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.reckon.R;
import com.example.reckon.adapter.IngredientsAdapter;
import com.example.reckon.utils.OnIngredientItemSelected;
import com.example.reckon.utils.PrefManager;
import com.example.reckon.utils.ToolbarTitleListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectIngredient extends Fragment implements OnIngredientItemSelected{
    private RecyclerView mRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;
    private Map<String, Double> listOfIngredient = new HashMap<>();
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

        setHasOptionsMenu(true);

        ToolbarTitleListener toolbarTitleListener = (ToolbarTitleListener)getActivity();
        toolbarTitleListener.updateTitle(null, manager.getSelectedLiveStockToSP());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout
                .fragment_select_ingredient, container, false);


        manager = new PrefManager(view.getContext());
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mIngredientsAdapter = new IngredientsAdapter(this,
                manager.getIngredientsValuesFromSP());

        mRecyclerView.setHasFixedSize(true);

        //Added the LayoutManager -*Fave
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mIngredientsAdapter);

        return view;
    }

    @Override
    public void onItemSelected(@NotNull String ingredient, double value) {

        //List of Ingredient to be passed to the ModifyIngredient fragment -*Fave
        listOfIngredient.put(ingredient, value);
    }

    @Override
    public void onItemDeSelected(@NotNull String ingredient) {
        listOfIngredient.remove(ingredient);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void doneEditing(){
        manager.writeSelectedValuesToPrefs(listOfIngredient);
        Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_selectIngredient_to_modifyIngredients);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_select_ingredient, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.menu_action_done:
                doneEditing();
            default: return super.onOptionsItemSelected(item);
        }
    }
}
