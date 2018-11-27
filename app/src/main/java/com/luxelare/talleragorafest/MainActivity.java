package com.luxelare.talleragorafest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.zetterstrom.com.forecast.ForecastClient;
import android.zetterstrom.com.forecast.ForecastConfiguration;
import android.zetterstrom.com.forecast.models.Forecast;
import android.zetterstrom.com.forecast.models.Language;
import android.zetterstrom.com.forecast.models.Unit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    public final String DarkSkyApiKey = "9c13f3900008ece1e0cb579cd329eace";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ForecastConfiguration configuration =
                new ForecastConfiguration.Builder(DarkSkyApiKey)
                        .setDefaultLanguage(Language.SPANISH)
                        .setDefaultUnit(Unit.SI)
                        .setCacheDirectory(getCacheDir())
                        .build();

        ForecastClient.create(configuration);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void ObtenerClima() {
        ForecastClient.getInstance()
                .getForecast(lat, lng, new Callback<Forecast>() {
                    @Override
                    public void onResponse(Call<Forecast> forecastCall, Response<Forecast> response) {
                        if (response.isSuccessful()) {
                            Forecast forecast = response.body();
                            lisaclima   = Utils.pronosticoPorHoras(forecast,getApplicationContext());
                            AdaptadorClima adaptadorClima = new AdaptadorClima(getApplicationContext(), lisaclima);
                            lv.setAdapter(adaptadorClima);
                        }
                    }
                    @Override
                    public void onFailure(Call<Forecast> forecastCall, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error de forecast: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
