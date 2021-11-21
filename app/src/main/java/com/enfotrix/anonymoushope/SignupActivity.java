package com.enfotrix.anonymoushope;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {
    private EditText edt_fullName,edt_phoneNo,edt_pass,edt_retypePass;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edt_fullName=findViewById(R.id.edt_full_name);
        edt_phoneNo=findViewById(R.id.edt_phoneNum4Signup);
        edt_pass=findViewById(R.id.edt_pass4Signup);
        edt_retypePass=findViewById(R.id.edt_re_pass4signUp);

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
        }
        else if (edt_pass.getText().toString().length()<8){
            edt_pass.setError("Password must have 8 character long");
        }
        else{
            signup(edt_phoneNo.getText().toString().trim());
        }
    }

    private void signup(String edt_phone) {
    }
}