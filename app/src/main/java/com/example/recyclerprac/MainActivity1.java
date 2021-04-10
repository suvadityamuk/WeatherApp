package com.example.recyclerprac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity1 extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutManager;
    int[] imvwarr = {R.drawable.pressure, R.drawable.temp, R.drawable.visbility, R.drawable.weathertype, R.drawable.winddir};
    String[] itemList = {"Pressure", "Temperature", "Visibility", "Weather Type", "Wind Direction"};
    String[] valueList = new String[5];
    EditText et1;
    EditText et2;

    class APICallerThreadClass1 implements Runnable {
        String latitude;
        String longitude;
        public APICallerThreadClass1(String lat, String longit) {
            latitude = lat;
            longitude = longit;
        }
        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            /*Request req = new Request.Builder()
                    .url("https://weatherapi-com.p.rapidapi.com/current.json?q=Mumbai")
                    .get()
                    .addHeader("x-rapidapi-key", "a047a4ca4fmshe5d8ee7cd211087p188235jsne9127bcc380b")
                    .addHeader("x-rapidapi-host", "weatherapi-com.p.rapidapi.com")
                    .build();*/
            Request req1 = new Request.Builder()
                    .url(URLMaker(latitude, longitude))
                    .get()
                    .addHeader("key", "bd2d594fc7c041a0a34191254201812")
                    .build();
            try {
                Response response = client.newCall(req1).execute();
                JSONObject jsonObject = new JSONObject(response.body().string());
                valueList[1] = String.valueOf(jsonObject.getJSONObject("current").getDouble("temp_c"))+"°C";
                valueList[4] = String.valueOf(jsonObject.getJSONObject("current").getInt("wind_degree"))+"°";
                valueList[0] = String.valueOf(jsonObject.getJSONObject("current").getDouble("pressure_mb"))+" milliBars";
                valueList[3] = jsonObject.getJSONObject("current").getJSONObject("condition").getString("text");
                valueList[2] = String.valueOf(jsonObject.getJSONObject("current").getDouble("vis_km"))+" kilometres";
            }
            catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Context context = this;
        recyclerView = findViewById(R.id.rv2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        APICallerThreadClass api = new APICallerThreadClass();
//        new Thread(api).start();
        programAdapter = new ProgramAdapter(this, itemList, valueList, imvwarr);
        recyclerView.setAdapter(programAdapter);
        Button bt1 = (Button)findViewById(R.id.bt3);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                et1 = (EditText)findViewById(R.id.et1);
                et2 = (EditText)findViewById(R.id.et3);
                String lat = (et1.getText()).toString();
                String longit =(et2.getText()).toString();
                APICallerThreadClass1 api = new APICallerThreadClass1(lat, longit);
                new Thread(api).start();
                programAdapter = new ProgramAdapter(context, itemList, valueList, imvwarr);
                recyclerView.setAdapter(programAdapter);
                et1.clearFocus();
                et2.clearFocus();
            }
        });
        Button bt5 = (Button)findViewById(R.id.button5);
        bt5.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent i = new Intent(context, ChooseActivity.class);
                startActivity(i);
            }
        });
    }
    public String URLMaker(String Lat, String Longit)
    {
        return "https://api.weatherapi.com/v1/current.json?q="+Lat+","+Longit;
    }


}