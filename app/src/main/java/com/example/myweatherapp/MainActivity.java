package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button buttton;
    EditText city;
    TextView result;


    String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=636cfbd1b91758c6cfa1462c314002c2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttton = (Button)findViewById(R.id.button);
        city = (EditText) findViewById(R.id.getcity);
        result = (TextView) findViewById(R.id.result);


        buttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.getText()==null){
                    Toast.makeText(getApplicationContext(),"Please enter city name",Toast.LENGTH_LONG);
                }
                String myURL=baseURL + city.getText().toString() + API;
                //Log.i("URl","URl"+ myURL);

                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("JSON","JSON"+ response);
                                try {
                                    String weth = response.getString("weather");
                                    Log.i("INFO","INFO"+ weth);

                                    JSONArray ar = new JSONArray(weth);
                                    for (int i=0;i<ar.length();i++){
                                        JSONObject parobj = ar.getJSONObject(i);

                                        String myweather = parobj.getString("main");
                                        result.setText(myweather);
                                        Log.i("ID","ID" + parobj.getString("id"));
                                        Log.i("MAIN","MAIN" + parobj.getString("main"));
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }

                                //                               try {
                                //                                   String coor = response.getString("coord");
                                //                                 Log.i("COOR","Coor"+ coor);
                                //                                   JSONObject co = new JSONObject(coor);
//
//                                    String lon =co.getString("lon");
//                                  String lat =co.getString("lat");

//                                    Log.i("LON","LON" + lon);
//                                    Log.i("LAT","LAT" + lat);

//                                }catch (JSONException e){
//                                    e.printStackTrace();
//                               }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("ERROR","Something went wrong" + error);
                            }
                        }


                );
                MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

            }
        });




    }
}
