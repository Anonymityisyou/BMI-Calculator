package com.nmsh.bmicalculater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    TextView tvWelcome;
    static TextView tvTemp;
    Spinner spinner1,spinner2;
    EditText etWeight;
    Button btnCalculate,btnViewHistory;
    SharedPreferences sp;
    GoogleApiClient gac;
    Location loc;


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
        sp=getSharedPreferences("mysp1",MODE_PRIVATE);
        tvTemp=findViewById(R.id.tvTemp);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        gac=builder.build();

        String name=sp.getString("name","user");
        String msg="Welcome "+name;
        tvWelcome.setText(msg);

        final ArrayList a1 = new ArrayList();
        a1.add("1");
        a1.add("2");
        a1.add("3");
        a1.add("4");
        a1.add("5");
        a1.add("6");
        a1.add("7");
        ArrayAdapter adp1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,a1);

        spinner1.setAdapter(adp1);
        final ArrayList a2 = new ArrayList();
        a2.add("1");
        a2.add("2");
        a2.add("3");
        a2.add("4");
        a2.add("5");
        a2.add("6");//Connect krr taaro devide and build krr, have thai jaase
        a2.add("7");
        a2.add("8");
        a2.add("9");
        a2.add("10");
        a2.add("11");
        ArrayAdapter adp2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,a2);

        spinner2.setAdapter(adp2);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=spinner1.getSelectedItemPosition();
                int b=spinner2.getSelectedItemPosition();

                float h=(float)((30.48*Integer.parseInt((String) a1.get(a)))/100 + (2.54*Integer.parseInt((String) a2.get(b)))/100 );
                double w= Double.parseDouble(etWeight.getText().toString());
                if(etWeight.getText().toString().length()==0){
                    etWeight.setError("Required");
                    etWeight.requestFocus();
                    return;
                }
                double bmi=w/(h*h);
                Intent c= new Intent(HomeActivity.this,ResultActivity.class);
                c.putExtra("BMI",bmi);
                startActivity(c);
            }
        });

        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ViewHistory.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.m1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(R.id.aboutus==item.getItemId()){
            Intent a =new Intent(Intent.ACTION_VIEW);
            a.setData(Uri.parse("https://github.com/nimish1001"));
            startActivity(a);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(loc!=null){
            double lat=loc.getLatitude();
            double lon=loc.getLongitude();

            Geocoder g =new Geocoder(HomeActivity.this, Locale.ENGLISH);

            try{
                List<Address> addressList = g.getFromLocation(lat,lon,1);
                Address address  = addressList.get(0);
                String city = address.getLocality();
                MyATask t1 = new MyATask();
                String a1="http://api.openweathermap.org/data/2.5/weather?units=metric";
                String a2="&q="+city;
                String a3="appid=c6e315d09197cec231495138183954bd";
                String info=a1+a2+a3;
                t1.execute(info);
            }
            catch (Exception e){
                Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Can't find your location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gac!=null){
            gac.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gac!=null){
            gac.disconnect();
        }
    }
}

class MyATask extends AsyncTask<String,Void,Double>{
    double temp;

    @Override
    protected Double doInBackground(String... strings) {
        String json="",line="";
        try{
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();

            InputStreamReader isr=new InputStreamReader(con.getInputStream());
            BufferedReader br =new BufferedReader(isr);

            while((line=br.readLine())!=null){
                json+=line+"\n";
            }
            JSONObject o = new JSONObject(json); // Abe aiya bau lag thai ryu
            JSONObject p = o.getJSONObject("main");
            temp=p.getDouble("temp");
        }
        catch (Exception e){

        }
        return temp;
    }

    @Override
    protected void onPostExecute(Double aDouble) {
        super.onPostExecute(aDouble);
        HomeActivity.tvTemp.setText("Temperature: "+ temp);
    }
}
