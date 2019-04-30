package com.example.b_quest;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.sql.Ref;

public class HeroUpload1 extends AppCompatActivity {

    private Button choosePic;
    private Button uploadPic;
    private Button goBack;

    ImageView image;
    public Uri imgUri;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    private StorageTask uploadTask;

    StorageReference ref;




//
private CollectionReference collectionReference = db.collection("treasure_hunt");
    private TreasureHunt hunt;
    private TextView thName;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_uploads);

//
        thName= (TextView) findViewById(R.id.text_view_hero_upload_name);
        getTreasureHunt();



        choosePic = (Button) findViewById(R.id.choose_pic_hero);
        uploadPic = (Button) findViewById(R.id.upload_pic_hero);
        goBack = (Button) findViewById(R.id.goBackHeroUpload);
       image = (ImageView) findViewById(R.id.imageUpload44);



        /////****
        choosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileChooser();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HeroUpload1.this, Hero.class);

                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        /////****

        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(HeroUpload1.this, "Upload in progress", Toast.LENGTH_LONG).show();
                }else{
                    FileUploader();
                }
            }
        });



    }






    private void FileUploader(){


            StorageReference Ref = mStorageRef.child(getIntent().getStringExtra("thID") + getIntent().getStringExtra("questName") );

            uploadTask = Ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(HeroUpload1.this, "Picture was successfully uploaded", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(HeroUpload1.this, "Picture wasn't successfully uploaded, Please try again", Toast.LENGTH_LONG).show();
                        }
                    });

    }



    private void FileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imgUri = data.getData();
            Picasso.get().load(imgUri).into(image);
        }
    }


















    private void getTreasureHunt() {
        if (getIntent().hasExtra("thID")) {
            Query query = collectionReference.whereEqualTo("treasureHuntID", getIntent().getStringExtra("thID"));
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            hunt = document.toObject(TreasureHunt.class);
                            thName.setText(hunt.getTreasureHunt());

                        }
                    }
                }
            });

        }


    }



}
