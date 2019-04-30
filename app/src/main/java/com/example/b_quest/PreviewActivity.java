package com.example.b_quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreviewActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("treasure_hunt");

    Map<String, String> lordMap = new HashMap<>();
    TreasureHunt hunt;

    Button join;
    Button cancelButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);

        join = findViewById(R.id.join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(PreviewActivity.this, MyTreasureHuntActivity.class);
                //intent.putExtra("thID", getIntent().getStringExtra("thID"));
                finish();
                //startActivity(intent);
                joinEvent();
            }
        });

        cancelButton = findViewById(R.id.cancel_button);
    }
    public void joinEvent(){
        final DocumentReference doc =  collectionReference.document(getIntent().getStringExtra("thID"));
        Query query = collectionReference.whereEqualTo("treasureHuntID", getIntent().getStringExtra("thID"));
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        hunt = document.toObject(TreasureHunt.class);
                        if(hunt.getInvMap().get("inv1").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            doc.update("invMap.inv1","");
                            doc.update("lordMap.inv1",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        }else if(hunt.getInvMap().get("inv2").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            doc.update("invMap.inv2","");
                            doc.update("lordMap.inv2",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        }else if(hunt.getInvMap().get("inv3").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            doc.update("invMap.inv3","");
                            doc.update("lordMap.inv3",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Query failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
