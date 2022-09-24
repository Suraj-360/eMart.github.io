package com.suraj.emart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.suraj.emart.R;
import com.suraj.emart.databinding.ActivitySignUpBinding;
import com.suraj.emart.models.UserModel;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding activitySignUpBinding;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(activitySignUpBinding.getRoot());

        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.blue));

        activitySignUpBinding.alreadyHaveAccount.setOnClickListener(v ->
        {
            Intent loginActivityIntent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(loginActivityIntent);
        });

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        activitySignUpBinding.createAccountMain.setOnClickListener(v -> {
            String userName = activitySignUpBinding.userNameRegister.getText().toString();
            String email = activitySignUpBinding.emailRegister.getText().toString();
            String password = activitySignUpBinding.passwordRegister.getText().toString();
            String mobile = activitySignUpBinding.mobileRegister.getText().toString();
            String cPassword = activitySignUpBinding.confirmPasswordRegister.getText().toString();
            final String[] userId = new String[1];

            if (password.equals(cPassword)) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = Objects.requireNonNull(task.getResult().getUser()).getUid();
                            firebaseFirestore.collection("eMartUser").document(userId).set(new UserModel(email,password,mobile, userName,userId)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                        Intent dashBoardIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                        dashBoardIntent.putExtra("userId",userId);
                                        startActivity(dashBoardIntent);
                                    }
                                    else
                                    {
                                        Toast.makeText(SignUpActivity.this, "Database problem", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else
                        {
                            Toast.makeText(SignUpActivity.this, "Something went wrong.. :-(" + Objects.requireNonNull(task.getException()), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else
            {
                Toast.makeText(SignUpActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent dashBoardIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(dashBoardIntent);
        finish();
    }
}