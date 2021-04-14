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

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutManager;
    int[] imvwarr = {R.drawable.pressure, R.drawable.temp, R.drawable.visbility, R.drawable.weathertype, R.drawable.winddir};
    String[] itemList = {"Pressure", "Temperature", "Visibility", "Weather Type", "Wind Direction"};
    String[] valueList = new String[5];
    EditText et3;
    Button bt3;

    class APICallerThreadClass2 implements Runnable {
        String city;
        public APICallerThreadClass2(String cityInp) {
            city = cityInp;
        }
        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            /*Request req = new Request.Builder()
                    .url("https://weatherapi-com.p.rapidapi.com/current.json?q=Mumbai")
                    .get()
                    .addHeader("x-rapidapi-key", "<YOUR RAPID-API KEY HERE>")
                    .addHeader("x-rapidapi-host", "weatherapi-com.p.rapidapi.com")
                    .build();*/
            Request req1 = new Request.Builder()
                    .url(URLMaker(city))
                    .get()
                    .addHeader("key", "<YOUR RAPID-API KEY HERE>")
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
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.rv2);
        Context context = this;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        programAdapter = new ProgramAdapter(this, itemList, valueList, imvwarr);
        recyclerView.setAdapter(programAdapter);
        bt3 = (Button)findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et3 = (EditText)findViewById(R.id.et3);
                String city = (et3.getText()).toString();
                APICallerThreadClass2 api = new APICallerThreadClass2(city);
                new Thread(api).start();
                programAdapter = new ProgramAdapter(context, itemList, valueList, imvwarr);
                recyclerView.setAdapter(programAdapter);
                et3.clearFocus();
            }
        });
        Button bt6 = (Button)findViewById(R.id.button6);
        bt6.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent i = new Intent(context, ChooseActivity.class);
                startActivity(i);
            }
        });

    }
    public String URLMaker(String city)
    {
        city = city.trim();
        city = city.replace(",", "%2C");
        city = city.replace("!","%21");
        city = city.replace(":","%3A");
        city = city.replace(" ", "%20");
        return "https://api.weatherapi.com/v1/current.json?q="+city;
    }
}
