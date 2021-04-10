package com.example.recyclerprac;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseActivity extends AppCompatActivity {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sw1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);
        Context context = this;
        Button bt1 = (Button)findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw1 = (Switch)findViewById(R.id.switch1);
                boolean swcheck = sw1.isChecked();
                if(!swcheck)
                {
                    Intent i = new Intent(context, MainActivity1.class);
                    startActivity(i);
                }
                else if(swcheck){
                    Intent i = new Intent(context, MainActivity2.class);
                    startActivity(i);
                }
            }
        });
    }

}
