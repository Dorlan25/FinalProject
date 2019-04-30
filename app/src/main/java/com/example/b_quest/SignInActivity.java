package com.example.b_quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.firebase.firestore.FirebaseFirestore.getInstance;

public class SignInActivity extends AppCompatActivity {

    public static final String TAG = "BQuest";
    private AutoCompleteTextView mEmail;
    private EditText mPassword;

    //FirebaseAut variable
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        Button cancelSignIn = findViewById(R.id.cancel_sign_in_button);
        cancelSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        Button signIn = findViewById(R.id.sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (email.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "You must enter email and password", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "Sign in onComplete: " + task.isSuccessful());

                    if (task.isSuccessful()) {
                        Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
                        finish();
                        startActivity(intent);
                    } else if (!task.isSuccessful()) {
                        Log.d(TAG, "Problem signing in: " + task.getException());
                        errorMessage("Wrong email or password");
                    }
                }
            });
        }
    }

//    @Override
//    public void onBackPressed(){
//        super.onBackPressed();
//        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//        finish();
//        startActivity(intent);
//    }

    public void errorMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
