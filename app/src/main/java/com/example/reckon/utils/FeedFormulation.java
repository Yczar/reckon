package com.example.reckon.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedFormulation {

    /**returns the recommended size of the ingredients selected by the user based on the feedSize
     * */
    public static Map<String, Double> getSelectedIngredientsSize(Map<String, Object> selectedIngredients,
                                                                 Map<String, Double> unselectedIngredients,
                                                                 Double feedSize){
        Map<String, Double> doubleMap = new HashMap<>();
        Double unselectedTotal = 0.0;
        for(Map.Entry<String, Double> entry : unselectedIngredients.entrySet()){
            Double value = Double.valueOf(entry.getValue().toString());
            unselectedTotal += value;
        }
        //convert value to KG
        unselectedTotal = unselectedTotal/1000;

        for(Map.Entry<String, Object> entry : selectedIngredients.entrySet()){
            String key = entry.getKey();
            //ingredients size in db is in gram, so dividing by 1000 converts to kg
            Double value = Double.valueOf(entry.getValue().toString()) / 1000;
            //calculate extra sum
            Double extraSum = value/(1 - unselectedTotal) * unselectedTotal;

            doubleMap.put(key, (value + extraSum) * feedSize);
        }
        return doubleMap;
    }

    public static Map<String, Double> getCalculatedIngredients(Map<String, Object> selectedIngredients,
                                                               Map<String, Object> selectedIngredientsDCP,
                                                               Double feedSize, Double minDCP){
        /**Step one: separate ingredients into energy and protein source*/
        Map<String, Double> calculatedIngredients = new HashMap<>();
        //DCP list
        Map<String, Double> proteinSourceList = new HashMap<>();
        Map<String, Double> energySourceList = new HashMap<>();
        //size ratio list
        Map<String, Double> proteinList = new HashMap<>();
        Map<String, Double> energyList = new HashMap<>();

        for (Map.Entry<String, Object> entry : selectedIngredients.entrySet()){
            String key = entry.getKey();
            //ingredients size ratio
            Double sizeValue = Double.valueOf(entry.getValue().toString()) / 1000;
            //get the ingredients DCP
            Double value = Double.valueOf(selectedIngredientsDCP.get(key).toString());

            if (value > 20){
                proteinSourceList.put(key, value);
                proteinList.put(key, sizeValue);
            }else{
                energySourceList.put(key, value);
                energyList.put(key, sizeValue);
            }
        }
        /**Step two: sum all the ingredients value in selectedSubIngredients together*/
        /*Double subIngredientsSum = 0.0;
        Double newFeedSize;
        for (Map.Entry<String, Object> entry : selectedSubIngredients.entrySet()){
            String key = entry.getKey();
            Double value = Double.valueOf(entry.getValue().toString()) / 1000;
            subIngredientsSum += value;
            //add sub selected ingredients size to the calculatedIngredients map
            //calculatedIngredients.put(key, value*feedSize);
        }
        //total feed size to be used in pearson calculation
        newFeedSize = feedSize - (feedSize * subIngredientsSum);*/

        /**Step three: find the average of the dcp value in energySource and proteinSource*/
        Double energyAverage = getAverage(energySourceList);
        Double proteinAverage = getAverage(proteinSourceList);
        Double total = Math.abs(energyAverage - minDCP) + Math.abs(proteinAverage - minDCP);

        /**Step four: do pearson calculations*/
        Double proteinSource = Math.abs(energyAverage - minDCP)/total * feedSize;
        Double energySource = Math.abs(proteinAverage - minDCP)/total * feedSize;

        /**Step five: get the size of the ingredients chosen*/
        Double ratioSumEnergy = getSum(energyList);
        Double ratioSumProtein = getSum(proteinList);
        for (Map.Entry<String, Double> entry : energyList.entrySet()){
            String key = entry.getKey();
            Double value = entry.getValue();
            calculatedIngredients.put(key, value * energySource / ratioSumEnergy);
        }
        for (Map.Entry<String, Double> entry : proteinList.entrySet()){
            String key = entry.getKey();
            Double value = entry.getValue();
            calculatedIngredients.put(key, value * proteinSource / ratioSumProtein);
        }

        return calculatedIngredients;
    }

    private static Double getSum(Map<String, Double> sourceList) {
        Double sum = 0.0;
        for (Map.Entry<String, Double> entry : sourceList.entrySet()){
            Double value = entry.getValue();
            sum += value;
        }
        return sum;
    }

    private static Double getAverage(Map<String, Double> source) {
        Double sum = 0.0;
        for (Map.Entry<String, Double> entry : source.entrySet()){
            Double value = entry.getValue();
            sum += value;
        }
        return sum/source.size();
    }

    public static List<Double> getTotalDCPandPrice(Map<String, Double> ingSize,
                                                   Map<String, Double> ingDCP,
                                                   Map<String, Double> ingPrice){
        List<Double> dcpAndPrice = new ArrayList<>();
        Double totalPrice = 0.0;
        Double totalDCP = 0.0;
        Object[] ingredients = ingSize.keySet().toArray();
        for(int i=0; i<ingSize.size(); i++){
            Double size = ingSize.get(ingredients[i]);
            Double dcp = ingDCP.get(ingredients[i]);
            Double price = ingPrice.get(ingredients[i]);

            totalPrice += size*price;
            totalDCP += size*dcp;
        }
        Double feedSize = getSum(ingSize);
        dcpAndPrice.add(totalDCP/feedSize);
        dcpAndPrice.add(totalPrice);
        dcpAndPrice.add(feedSize);
        return dcpAndPrice;
    }
}
