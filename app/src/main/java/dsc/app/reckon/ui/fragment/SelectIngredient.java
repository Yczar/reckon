package dsc.app.reckon.ui.fragment;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reckon.R;
import com.example.reckon.adapter.IngredientsAdapter;
import dsc.app.reckon.utils.FeedFormulation;
import com.example.reckon.utils.OnIngredientItemSelected;
import com.example.reckon.utils.PrefManager;
import com.example.reckon.utils.ToolbarTitleListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
import androidx.recyclerview.widget.RecyclerView;import dsc.app.reckon.adapter.IngredientsAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectIngredient extends Fragment implements OnIngredientItemSelected{
    private RecyclerView mRecyclerView;
    private RecyclerView mSubRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;
    private IngredientsAdapter mSubIngredientsAdapter;
    private Map<String, Object> listOfIngredient = new HashMap<>();
    private Map<String, Double> unselectedIngredient = new HashMap<>();
//    private Map<String, Object> listOfSUbIngredient = new HashMap<>();
    private Map<String, Double> calculatedIngredient = new HashMap<>();
    private PrefManager manager;
    private Double feedSize;
    private TextInputEditText feedSizeEdt;
    private TextInputLayout feedSizeLayout;
    public static final String TYPE_INGREDIENTS = "type_ingredients";
    public static final String TYPE_SUB_INGREDIENTS = "type_sub_ingredients";
    Map<String, Object> dcpValuesMap;
    private TextView textView;

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

        textView = view.findViewById(R.id.tv_sub_title);
        textView.setText(new StringBuilder().append("Age range: ")
                .append(manager.getSelectedAgeRange().getAge_range()).toString());

        mRecyclerView = view.findViewById(R.id.recycler_view);

        mIngredientsAdapter = new IngredientsAdapter(this,
                manager.getLiveStockAgeRangeFromSP().getIngredients());

        mRecyclerView.setHasFixedSize(true);

        //Added the LayoutManager -*Fave
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mIngredientsAdapter);
        feedSizeEdt = view.findViewById(R.id.feed_size);
        feedSizeLayout = view.findViewById(R.id.feed_size_layout);

        //TODO modify this line
        //all checkbox will be checked by default so, listOfIngredients must contain all ingredients
        listOfIngredient = manager.getLiveStockAgeRangeFromSP().getIngredients();
        return view;
    }

    @Override
    public void onItemSelected(@NotNull String ingredient, double value) {

        //List of Ingredient to be passed to the ModifyIngredient fragment -*Fave
        listOfIngredient.put(ingredient, value);
        unselectedIngredient.remove(ingredient);
    }

    @Override
    public void onItemDeSelected(@NotNull String ingredient, double value) {
        listOfIngredient.remove(ingredient);
        unselectedIngredient.put(ingredient, value);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void doneEditing(){
        //TODO move this lines of codes to the background
        if (!feedSizeEdt.getText().toString().isEmpty()){
            //perform necessary calculations
            feedSize = Double.valueOf(feedSizeEdt.getText().toString());
            if (feedSize > 10.0) {
//        calculatedIngredient = FeedFormulation
//                .getSelectedIngredientsSize(listOfIngredient, unselectedIngredient, feedSize);
                dcpValuesMap = manager.getIngredientsDCPFromSP();
                Double minDCP = manager.getSelectedAgeRange().getMin_dcp();
                calculatedIngredient = FeedFormulation
                        .getCalculatedIngredients(listOfIngredient, dcpValuesMap, feedSize, minDCP);
                // write to sharedPreferences
                manager.writeSelectedValuesToPrefs(calculatedIngredient);
                manager.writeFeedSizeValueToPrefs(feedSize);
                Navigation.findNavController(Objects.requireNonNull(getView()))
                        .navigate(R.id.action_selectIngredient_to_modifyIngredients);
            }else{
                feedSizeLayout.setError("feed size to small");
            }
        }else{
            feedSizeLayout.setError("field cannot be empty");
        }
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
