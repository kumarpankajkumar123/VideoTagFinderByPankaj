package com.example.TagFinder;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.TagFinder.ModalOfApi.ForeCastData;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherData extends AppCompatActivity {

    EditText location;
    MaterialButton searchbtn;
    TextView currentTemp,maxTemp,minTemp,condition,date,hourTemp1,hourTemp2,hourTemp3,hourTemp4;
    ImageView image,image1,image2,image3,image4;
    String localTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather_data);

        location = (EditText) findViewById(R.id.location);
        searchbtn = findViewById(R.id.searchbtn);
        currentTemp = findViewById(R.id.currentTemp);
        maxTemp = findViewById(R.id.maxTemp);
        minTemp = findViewById(R.id.minTemp);
        condition = findViewById(R.id.condition);
        date = findViewById(R.id.date);
        hourTemp1 = findViewById(R.id.hourTemp1);
        hourTemp2 = findViewById(R.id.hourTemp2);
        hourTemp3 = findViewById(R.id.hourTemp3);
        hourTemp4 = findViewById(R.id.hourTemp4);
        image = findViewById(R.id.image);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getValue = location.getText().toString();
                getTemp(getValue);
                location.setText("");
            }
        });

    }

    public void getTemp(String getValue) {

        if (getValue.equals("") || getValue.isEmpty()) {
            Log.e("kuch nhi h", ":");
            Toast.makeText(this, "enter location", Toast.LENGTH_SHORT).show();
        } else {
            Apiresponse apiresponse = RetrofitDataClass.getTemp().create(Apiresponse.class);
            Call<ForeCastData> res = apiresponse.getForest("weatherapi-com.p.rapidapi.com","32f8bcbd6emsh6241cd5139dc1c8p157f48jsn22b4cef94b0e",getValue);
            Log.e("the request", ":=" + res.request());

            res.enqueue(new Callback<ForeCastData>() {
                @Override
                public void onResponse(Call<ForeCastData> call, Response<ForeCastData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.e("the code", ":=" + response.code());
                        ForeCastData foreCastData = response.body();
                        Log.e("the city", ":=" + foreCastData.getLocation().getName());

                        currentTemp.setText(foreCastData.getCurrent().getTemp_c().toString());
                        condition.setText(foreCastData.getCurrent().getCondition().getText());
                        maxTemp.setText(foreCastData.getForecast().getForecastday().get(0).getDay().getMaxtemp_c());
                        minTemp.setText(foreCastData.getForecast().getForecastday().get(0).getDay().getMintemp_c());

                        Glide.with(WeatherData.this)
                                .load("https:"+foreCastData.getCurrent().getCondition().getIcon()).fitCenter().into(image);
                        Log.e("the image url",";="+foreCastData.getForecast().getForecastday().get(0).getDay().getCondition().getIcon());
                        date.setText(foreCastData.getLocation().getLocaltime());
                        localTime = foreCastData.getLocation().getLocaltime();
                        hourTemp1.setText(foreCastData.getForecast().getForecastday().get(0).getHour().get(15).getTemp_c());
                        hourTemp2.setText(foreCastData.getForecast().getForecastday().get(0).getHour().get(16).getTemp_c());
                        hourTemp3.setText(foreCastData.getForecast().getForecastday().get(0).getHour().get(17).getTemp_c());
                        hourTemp4.setText(foreCastData.getForecast().getForecastday().get(0).getHour().get(18).getTemp_c());

                        Glide.with(WeatherData.this)
                                .load("https:"+foreCastData.getForecast().getForecastday().get(0).getHour().get(15).getCondition().getIcon()).fitCenter().into(image1);
                        Glide.with(WeatherData.this)
                                .load("https:"+foreCastData.getForecast().getForecastday().get(0).getHour().get(16).getCondition().getIcon()).fitCenter().into(image2);
                        Glide.with(WeatherData.this)
                                .load("https:"+foreCastData.getForecast().getForecastday().get(0).getHour().get(17).getCondition().getIcon()).fitCenter().into(image3);
                        Glide.with(WeatherData.this)
                                .load("https:"+foreCastData.getForecast().getForecastday().get(0).getHour().get(18).getCondition().getIcon()).fitCenter().into(image4);
                    } else {
                       Log.e("response error",":"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ForeCastData> call, Throwable t) {

                }
            });

        }
    }
}