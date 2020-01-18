package com.nmsh.bmicalculater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    TextView tvHeading;
    EditText etName,etAge,etPhone;
    RadioGroup rgSex;
    RadioButton rbMale,rbFemale;
    Button btnRegister;
    SharedPreferences sp;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        tvHeading=findViewById(R.id.tvHeading);
        etName=findViewById(R.id.etName);
        etAge=findViewById(R.id.etAge);
        etPhone=findViewById(R.id.etPhone);
        rgSex=findViewById(R.id.rgSex);
        rbMale=findViewById(R.id.rbMale);
        rbFemale=findViewById(R.id.rbFemale);
        btnRegister=findViewById(R.id.btnRegister);
        sp=getSharedPreferences("mysp1",MODE_PRIVATE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etName.getText().toString();
                int age= Integer.parseInt(etAge.getText().toString());
                String ph=etPhone.getText().toString();

                if(name.length()<2){
                    etName.setError("Enter valid data ");
                    etName.requestFocus();
                    Toast.makeText(LoginPage.this, "", Toast.LENGTH_SHORT).show();
                }
                if(age<18){
                    etAge.setError("Age must be greater than 18 ");
                    etAge.requestFocus();
                }
                if(ph.length()<10){
                    etPhone.setError("Enter valid data ");
                    etPhone.requestFocus();
                }
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("name",name);
                editor.putInt("age",age);
                editor.putString("phone",ph);
                editor.putString("Record","");

                rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb=rgSex.findViewById(checkedId);
                        String sex = rb.getText().toString();
                        setSex(sex);
                    }
                });
                editor.putString("sex",sex);
                editor.commit();
                startActivity(new Intent(LoginPage.this,HomeActivity.class));
                finish();
            }
        });
    }
    public void setSex(String g){
        sex = g;
    }
}
