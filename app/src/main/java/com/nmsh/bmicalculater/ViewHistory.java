package com.nmsh.bmicalculater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ViewHistory extends AppCompatActivity {
    SharedPreferences sp;
    TextView tvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        tvHistory=findViewById(R.id.tvHistory);
        sp =getSharedPreferences("mysp1",MODE_PRIVATE);
        tvHistory.setText("");
        String record = sp.getString("Record","No data found");
        if(record.length()==0){
            tvHistory.setText("No data found");
        }
        else {
            tvHistory.setText(record);
        }
    }
}
