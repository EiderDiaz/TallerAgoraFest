package com.luxelare.talleragorafest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.zetterstrom.com.forecast.ForecastClient;
import android.zetterstrom.com.forecast.ForecastConfiguration;
import android.zetterstrom.com.forecast.models.Forecast;
import android.zetterstrom.com.forecast.models.Language;
import android.zetterstrom.com.forecast.models.Unit;

import com.luxelare.talleragorafest.Adapters.MyHeaderRecyclerAdapter;
import com.luxelare.talleragorafest.ForecastModels.elementForecast;
import com.luxelare.talleragorafest.ForecastModels.forecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    public final String DarkSkyApiKey = "9c13f3900008ece1e0cb579cd329eace";
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view_clima);
        ForecastConfiguration configuration =
                new ForecastConfiguration.Builder(DarkSkyApiKey)
                        .setDefaultLanguage(Language.SPANISH)
                        .setDefaultUnit(Unit.SI)
                        .setCacheDirectory(getCacheDir())
                        .build();

        ForecastClient.create(configuration);
        ObtenerClima();


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
                .getForecast(25.790466, -108.985886, new Callback<Forecast>() {
                    @Override
                    public void onResponse(Call<Forecast> forecastCall, Response<Forecast> response) {
                        if (response.isSuccessful()) {
                            Forecast forecast = response.body();
                            //Toast.makeText(MainActivity.this, "forecast"+forecast.getDaily().getSummary(), Toast.LENGTH_SHORT).show();
                            forecast forecasteado = Utils.pronosticoPorHoras(forecast);
                            MyHeaderRecyclerAdapter adapter = new MyHeaderRecyclerAdapter(forecasteado.getHeader(),forecasteado.getElementosForecast(),getApplicationContext());
                            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<Forecast> forecastCall, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error de forecast: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }



}
