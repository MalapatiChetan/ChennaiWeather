package com.example.chennaiweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView t1_temp,t2_city,t3_description,t4_date,temp_min,temp_max,windspeed,lon,lat,pressure,humidity;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Open Weather For Chennai");

        t1_temp = findViewById(R.id.textView);
        t2_city = findViewById(R.id.textView3);
        t3_description = findViewById(R.id.textView4);
        t4_date = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        temp_min = findViewById(R.id.textView10);
        temp_max= findViewById(R.id.temp_max);
        windspeed= findViewById(R.id.windspeed);
        lon= findViewById(R.id.lon);
        lat= findViewById(R.id.lat);
        pressure=findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find_weather();
                button.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void find_weather() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=chennai,india&appid=aa8a534892f5f3842662bf6e4ddba68f  ";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);

                    JSONObject main_object1 = response.getJSONObject("wind");
                    JSONObject main_object2 = response.getJSONObject("coord");

                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");
                    String temp_mn = main_object.getString("temp_min");
                    String temp_mx = main_object.getString("temp_max");
                    String windspd = main_object1.getString("speed");
                    String ln = main_object2.getString("lon");
                    String lt = main_object2.getString("lat");
                    String pre = main_object.getString("pressure");
                    String hum = main_object.getString("humidity");

                    temp_min.setText(temp_mn);
                    temp_max.setText(temp_mx);
                    t2_city.setText(city);
                    t3_description.setText(description);
                    windspeed.setText(windspd);
                    lon.setText(ln);
                    lat.setText(lt);
                    pressure.setText(pre);
                    humidity.setText(hum);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("EEEE-MM-dd");
                    String formatted_date = simpleDateFormat.format(calendar.getTime());
                    t4_date.setText(formatted_date);

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 32)/1.8000;
                    centi = Math.round(centi);
                    int i= (int)centi;
                    t1_temp.setText(String.valueOf(i)+"\u2109");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "refreshed", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
