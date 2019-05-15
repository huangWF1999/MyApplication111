package com.example.admin.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    TextView weathertextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_weather );
        this.weathertextview=findViewById( R.id.weathertextview );
        String weatherId=getIntent().getStringExtra( "wid" );
        String weatherUrl="http://guolin.tech/api/weather?cityid="+weatherId;

        HttpUtil.sendOkHttpRequest(weatherUrl,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                final String responseText = response.body().string();
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        weathertextview.setText(responseText);
                    }
                } );
            }
        });
    }

    }

