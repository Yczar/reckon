package com.example.reckon.utils;

import java.util.ArrayList;
import java.util.List;

public class FeedFormulation {
    public Integer getSizeOfSingleIngredients(Integer totalSize, Integer ingKG){
        return totalSize*ingKG;
    }
    public List<Double> getSizeOfIngredients(Integer totalSize, List<Double> ingKG){
        List<Double> integerList = new ArrayList<>();
        for (int i=0; i<ingKG.size(); i++){
            integerList.add(ingKG.get(i) * totalSize);
        }
        return integerList;
    }
    public Double getTotalDCP(Integer totalsize, List<Integer> ingKg, List<Double> dcp){
        Double totalCP = 0.0;
        for (int i=0; i<ingKg.size(); i++){
            totalCP += ingKg.get(i) * dcp.get(i) / 100;
        }
        return totalCP / totalsize * 100;
    }

    public Double getTotalProce(List<Double> ingKg, List<Double> price){
        Double totalPrice = 0.0;
        for (int i=0; i<ingKg.size(); i++){
            totalPrice += ingKg.get(i) * price.get(i);
        }
        return totalPrice;
    }
}
