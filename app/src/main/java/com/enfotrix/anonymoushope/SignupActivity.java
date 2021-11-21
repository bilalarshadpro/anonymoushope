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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText edt_fullName,edt_phoneNo,edt_pass,edt_retypePass;
    private Button btn_save;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edt_fullName=findViewById(R.id.edt_full_name);
        edt_phoneNo=findViewById(R.id.edt_phoneNum4Signup);
        edt_pass=findViewById(R.id.edt_pass4Signup);
        edt_retypePass=findViewById(R.id.edt_re_pass4signUp);
        btn_save=findViewById(R.id.btn_saveSignup);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        if (edt_fullName.getText().toString().isEmpty()){
            edt_fullName.setError("Enter FullName");
        }else if (edt_phoneNo.getText().toString().isEmpty()){
            edt_phoneNo.setError("Enter PhoneNumber");
        }
        else if (edt_pass.getText().toString().isEmpty()){
            edt_pass.setError("Enter Password");
        }else if (!edt_pass.getText().toString().equals(edt_retypePass.getText().toString())){
            edt_retypePass.setError("Password does not match");
        }
        else if (edt_pass.getText().toString().length()<8){
            edt_pass.setError("Password must be 8 character long");
        }
        else{
            signup(edt_phoneNo.getText().toString().trim());
        }
    }

    private void signup(String str_phone) {
        db.collection("Users").whereEqualTo("Phone Number",str_phone).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (!task.getResult().isEmpty()){
                        edt_phoneNo.setError("Phone Already exists");
                    }
                    else{
                        String fullName=edt_fullName.getText().toString().trim();
                        String phoneNumber=edt_phoneNo.getText().toString().trim();
                        String password=edt_pass.getText().toString().trim();

                        Map<String,String> userMap=new HashMap<>();

                        userMap.put("Full Name",fullName);
                        userMap.put("Phone Number",phoneNumber);
                        userMap.put("password",password);

                        db.collection("Users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}