package com.example.ehealthhistory.database;

import android.util.Log;

import com.example.ehealthhistory.data.model.User.User;
import com.example.ehealthhistory.data.model.footballer.Footballer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FireBase {

    private FirebaseFirestore db;

    public FireBase()
    {
        db = FirebaseFirestore.getInstance();
    }

    public String getName(String username) {
        final String[] name = {""};

        db.collection("user")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                name[0] = document.getData().get("name").toString();
                                Log.d("DOCUMENTO. ","nombre: " + name[0]);

                            }
                        } else {
                        }
                    }
                });
        return name[0];
    }

    public ArrayList<String> getRolesOfUsername(String username) {

        final ArrayList<String> rolesFinales = new ArrayList<>();

        db.collection("user")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String[] roles = (String[]) document.getData().get("rol");

                                for(int i = 0; i < roles.length; i++)
                                {
                                    Log.d("Rol",roles[i]);
                                    rolesFinales.add(roles[i]);
                                }

                            }
                        } else {
                        }
                    }
                });
        return rolesFinales;
    }

    public  HashMap<String,String>  getFootballerData(String username) {

        Footballer footballer = new Footballer();
        final HashMap<String,String> hashmapValuesFootballer = new HashMap<>();

        db.collection("footballer")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                footballer.setActive(true);
                                hashmapValuesFootballer.put("active", document.getData().get("active").toString());
                                hashmapValuesFootballer.put("name", document.getData().get("name").toString());
                                hashmapValuesFootballer.put("telecom", document.getData().get("telecom").toString());
                                hashmapValuesFootballer.put("birthday", document.getData().get("birthday").toString());
                                hashmapValuesFootballer.put("gender", document.getData().get("gender").toString());
                                hashmapValuesFootballer.put("contact_address", document.getData().get("contact_address").toString());
                                hashmapValuesFootballer.put("contact_name", document.getData().get("contact_name").toString());
                                hashmapValuesFootballer.put("contact_telecom", document.getData().get("contact_telecom").toString());
                                hashmapValuesFootballer.put("comunication_prefered", document.getData().get("comunication_prefered").toString());
                                hashmapValuesFootballer.put("comunication_lenguage", document.getData().get("comunication_lenguage").toString());

                            }
                        } else {
                        }
                    }
                });
        return hashmapValuesFootballer;
    }
}
