package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {
    TextInputLayout  Username, Password;
     Button callSignup;
    Button callDashboard;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
       callSignup = findViewById(R.id.newsignup);
        callDashboard = findViewById(R.id.dashboard);
        progressBar = findViewById(R.id.progressBar);
        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
           }
        });





    }

    private Boolean validateUsername() {
        String val = Username.getEditText().getText().toString();

        if (val.isEmpty()) {
            Username.setError("Field cannot be empty");
            return false;
        } else {
            Username.setError(null);
            Username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = Password.getEditText().getText().toString();

        if (val.isEmpty()) {
            Password.setError("Field cannot be empty");
            return false;
        } else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view){
        if (!validateUsername() | !validatePassword()){
            return;
        }
        else {

            isUser();
        }

    }

    private void isUser() {
        progressBar.setVisibility(View.VISIBLE);
        final String userEnteredUsername = Username.getEditText().getText().toString().trim();
        final String userEnteredPassword = Password.getEditText().getText().toString().trim();
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

       Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Username.setError(null);
                    Username.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {
                       Username.setError(null);
                       Username.setErrorEnabled(false);
                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(), menu.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                    } else {
                        progressBar.setVisibility(View.GONE);
                       Password.setError("Wrong Password");
                       Password.requestFocus();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Username.setError("No such User exist");
                   Username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });




    }


}
//       UserHelperClass user = new UserHelperClass();
//      reference.child(user.getPhoneNo()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        String userEnteredUsername = Username.getEditText().getText().toString().trim();
//                       String userEnteredPassword = Password.getEditText().getText().toString().trim();
//                        String userName = snapshot.child("username").getValue(String.class);
//                        String password = snapshot.child("password").getValue(String.class);

//                       if (userName.equals(userEnteredUsername) && password.equals(userEnteredPassword)) {
//                           Toast.makeText(Login.this, "all done", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Login.this,dashboard.class);
//                           startActivity(intent);
//                           finish();
//                      } else {
//                          Toast.makeText(Login.this, "UserName or Password cant match", Toast.LENGTH_SHORT).show();
//                      }
//   }
//              }else {
//                   Toast.makeText(Login.this, "User Cannot exist", Toast.LENGTH_SHORT).show();
//              }
//          }

//         @Override
//          public void onCancelled(@NonNull @NotNull DatabaseError error) {
//            Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    });