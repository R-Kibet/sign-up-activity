package com.example.clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.clone.databinding.ActivitySignBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignActivity extends AppCompatActivity {

    //fetch all design activity use view binding//
    ActivitySignBinding binding;

    private FirebaseAuth mAuth;    //use to form sign up operation//
    FirebaseDatabase database;
    ProgressDialog progressDialog; //process to be followed in steps//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_sign); removed

        //initialize binding
        binding = ActivitySignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // we can now access all txt fields from sign up activity


        // Initialize private fields
        mAuth = FirebaseAuth.getInstance();
        database  = FirebaseDatabase.getInstance();


        //initializing progress dialog
        progressDialog = new ProgressDialog(SignActivity.this);
        progressDialog.setTitle("Creating ur account");
        progressDialog.setMessage("Account is being created"); //message is visible when e show process dialog

        //Button listener using binding
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the txt fields are empty
                //&& operator used to combine all
                if(!binding.txtUserName.getText().toString().isEmpty() && !binding.txtEmail.getText().toString().isEmpty() && !binding.txtPassword.getText().toString().isEmpty()){

                    //show progress dialog when click on sig up button
                    progressDialog.show();

                    //Create and pass username email and password
                    //creating an account
                    mAuth.createUserWithEmailAndPassword(binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    //dont show the progress bar
                                    progressDialog.dismiss();

                                    if (task.isSuccessful()){

                                        Toast.makeText(SignActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                    }

                                    //when error occurs
                                    else{
                                        Toast.makeText(SignActivity.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });



                }else {
                    Toast.makeText(SignActivity.this, "Enter Details", Toast.LENGTH_SHORT).show();
                }
            }
        });





        //hide action bar//
        getSupportActionBar().hide();
    }
}