package com.example.reckon;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class Trashes {
    void trash(){
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference codesRef = rootRef.collection("CodeClinic").document("Codes");
        codesRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    Map<String, java.lang.Object> map = task.getResult().getData();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        list.add(entry.getKey());
                        android.util.Log.d("TAG", entry.getKey());
                    }
                    //Do what you want to do with your list
                }
            }
        });
    }
}
