package com.enfotrix.anonymoushope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login,btn_signUp;
    private EditText edt_phoneNumber,edt_password;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    private Utils Utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_phoneNumber=findViewById(R.id.edt_phoneNumber);
        edt_password=findViewById(R.id.edt_password);
        btn_login=findViewById(R.id.btn_login);
        btn_signUp=findViewById(R.id.btn_signUp);
        Utils=new Utils(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validate() {
        if (edt_phoneNumber.getText().toString().isEmpty()){
            edt_phoneNumber.setError("Username Field is empty");
        }

        else if(edt_password.getText().toString().isEmpty()){
            edt_password.setError("Password field is empty");
        }
        else{
            auth(edt_phoneNumber.getText().toString(),edt_password.getText().toString());
        }
    }

    private void auth(String str_phoneNo, String str_pass) {
        db.collection("Users").whereEqualTo("Phone Number",str_phoneNo).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (!task.getResult().isEmpty()){
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            String passwordFromDB=documentSnapshot.getString("password");
                            if (passwordFromDB.equals(str_pass)){
                                //Utils.putToken(str_phoneNo);
                                Intent intent=new Intent(LoginActivity.this, MainPageActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                edt_password.setError("Incorrect Password");
                            }
                        }
                    }
                    else{
                        edt_phoneNumber.setError("Incorrect Username");
                    }
                }
            }
        });
    }
}