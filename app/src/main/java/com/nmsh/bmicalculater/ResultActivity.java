package com.nmsh.bmicalculater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {
    Button btnBack,btnShare,btnSave;
    TextView tvResult,tvk1,tvk2,tvk3,tvk4;
    SharedPreferences sp;
    String name,sex;
    int age;
    static String BMI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        btnBack=findViewById(R.id.btnBack);
        btnShare=findViewById(R.id.btnShare);
        btnSave=findViewById(R.id.btnSave);
        tvResult=findViewById(R.id.tvResult);
        tvk1=findViewById(R.id.tvk1);
        tvk2=findViewById(R.id.tvk2);
        tvk3=findViewById(R.id.tvk3);
        tvk4=findViewById(R.id.tvk4);
        sp=getSharedPreferences("mysp1",MODE_PRIVATE);
        name=sp.getString("name","");
        age=sp.getInt("age",18);
        sex=sp.getString("sex",null);

        Intent a =getIntent();
        double bmi=a.getDoubleExtra("BMI",20);
        String msg;
        if(bmi<18.5){
            msg="Your BMI is "+bmi+"and you are UNDERWEIGHT";
            tvResult.setText(msg);
            tvk1.setTextColor(Color.RED);
        }
        else if(bmi>=18.5 && bmi<25){
            msg="Your BMI is "+bmi+"and you are NORMAL";
            tvResult.setText(msg);
            tvk2.setTextColor(Color.RED);
        }
        else if(bmi>25 && bmi<30){
            msg="Your BMI is "+bmi+"and you are OVERWEIGHT";
            tvResult.setText(msg);
            tvk3.setTextColor(Color.RED);
        }
        else {
            msg="Your BMI is "+bmi+"and you are OBESE";
            tvResult.setText(msg);
            tvk1.setTextColor(Color.RED);
        }
        setBMI(bmi);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,HomeActivity.class));
                finish();
            }
        });
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp=getSharedPreferences("mysp1",MODE_PRIVATE);
                Date date = new Date();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String d = df.format(date);
                SharedPreferences.Editor editor = sp.edit();
                String s = d+"   BMI: "+getBMI()+"\n";
                String rec = sp.getString("Record","");
                StringBuffer op = new StringBuffer();
                op.append(rec);
                op.append(s);
                editor.putString("Record",op.toString());
                editor.commit();
                Toast.makeText(ResultActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_SEND);
                a.setType("text/plain");
                String Msg ="NAME : "+name+" GENDER : "+sex+" AGE : "+age;
                Msg+=tvResult.getText().toString() +"\n";
                a.putExtra(Intent.EXTRA_TEXT,Msg);
            }
        });

    }

    private String getBMI() {
        return BMI;
    }

    private void setBMI(Double bmi) {
        BMI=bmi.toString();
    }
}
