package com.example.myfriends.weatherapp;

import android.support.v7.app.AppCompatActivity;
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

    EditText city;
    TextView result;
    Button myButton;

    //http://api.openweathermap.org/data/2.5/weather?q=jalandhar,In&appid=4a6ff1dfe08244f3033146721483f26e

    String baseURL="http://api.openweathermap.org/data/2.5/weather?q=";
    String API="&appid=4a6ff1dfe08244f3033146721483f26e";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myButton=(Button)findViewById(R.id.button);
        city=(EditText)findViewById(R.id.editText);
        result=(TextView)findViewById(R.id.textView1);



        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //to check that if you enter right city or not
                if (city.getText().toString() == null)
                {
                    Toast.makeText(MainActivity.this, "You Entered Wrong city!!!",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    String myUrl = baseURL + city.getText().toString().trim() + API;      //this is the our url of weather app for every city
                    //Log.i("URL","URL:"+myUrl);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    //Log.i("Json","JsonRequest:"+response);
                                    // myTextView.setText(response.toString());
                                    try {

                                        String info = response.getString("weather");
                                        Log.i("INFO", "INFO" + info);

                                        JSONArray arr = new JSONArray(info);

                                        for (int i = 0; i < arr.length(); i++) {
                                            JSONObject parObj = arr.getJSONObject(i);
                                            String myWeather = parObj.getString("main");
                                            result.setText(myWeather);

                                            Log.i("ID", "ID:" + parObj.getString("id"));
                                            Log.i("MAIN", "MAIN:" + parObj.getString("main"));       //


                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //Log.i("ERROR","Something went wrong:"+error);
                                    // myTextView.setText("Something went wrong"+error);
                                }
                            }
                    );
                    MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);

                }
            }
        });
    }
}
