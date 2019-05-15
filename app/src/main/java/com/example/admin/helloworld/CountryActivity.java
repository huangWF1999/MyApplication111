package com.example.admin.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CountryActivity extends AppCompatActivity {
    private String[] weather_ids={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
    private String[] data={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
    private TextView textView=null;
    private ListView contrylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_country );
        this.textView=findViewById( R.id.textView );
        this.contrylistview=findViewById( R.id.contrylistview );

        Intent intent=getIntent();
        int cityid=intent.getIntExtra( "cid",0 );
        int pid=intent.getIntExtra( "pid",0 );
        final ArrayAdapter<String>adapter=new ArrayAdapter<>( this,android.R.layout.simple_list_item_1,data );
        contrylistview.setAdapter( adapter );
        this.contrylistview.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i( "点击了哪一个",""+position+":"+weather_ids[position]);
                Intent intent=new Intent( CountryActivity.this,WeatherActivity.class);
                intent.putExtra( "wid",weather_ids[position] );
                startActivity( intent );
            }
        } );

        String weatherUrl = "http://guolin.tech/api/china/"+pid+"/"+cityid;
        HttpUtil.sendOkHttpRequest(weatherUrl,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                final String responseText = response.body().string();
                parseJSONObject(responseText);
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                } );
            }
        });
    }
    private void parseJSONObject(String responseText){
        JSONArray jsonArray = null;
        try{jsonArray=new JSONArray( responseText );
            String[] result= new String[jsonArray.length()];
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject( i );
                this.data[i]=jsonObject.getString( "name" );
                this.weather_ids[i]=jsonObject.getString( "weather_id" );
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}

