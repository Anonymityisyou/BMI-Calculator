package com.nmsh.bmicalculater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    TextView tvWelcome;
    Spinner spinner1,spinner2;
    EditText etWeight;
    Button btnCalculate,btnViewHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvWelcome=findViewById(R.id.tvWelcome);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);
        etWeight=findViewById(R.id.etWeight);
        btnCalculate=findViewById(R.id.btnCalculate);
        btnViewHistory=findViewById(R.id.btnViewHistory);
        final ArrayList<String> a1 = new ArrayList<>();
        a1.add("1");
        a1.add("2");
        a1.add("3");
        a1.add("4");
        a1.add("5");
        a1.add("6");
        a1.add("7");
        ArrayAdapter adp1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,a1);

        spinner1.setAdapter(adp1);
        final ArrayList<String> a2 = new ArrayList<>();
        a2.add("1");
        a2.add("2");
        a2.add("3");
        a2.add("4");
        a2.add("5");
        a2.add("6");
        a2.add("7");
        a2.add("8");
        a2.add("9");
        a2.add("10");
        a2.add("11");
        ArrayAdapter adp2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,a1);

        spinner2.setAdapter(adp2);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=spinner1.getSelectedItemPosition();
                int b=spinner1.getSelectedItemPosition();
                int h=12*Integer.parseInt(a1.get(a))+Integer.parseInt(a2.get(b));
                int w= Integer.parseInt(etWeight.getText().toString());
                float bmi=w/(h*h);
                Intent c= new Intent(HomeActivity.this,ResultActivity.class);
                startActivity(c);



            }
        });
    }
}
