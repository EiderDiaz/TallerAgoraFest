package com.luxelare.talleragorafest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;
import android.zetterstrom.com.forecast.ForecastClient;
import android.zetterstrom.com.forecast.ForecastConfiguration;
import android.zetterstrom.com.forecast.models.Forecast;
import android.zetterstrom.com.forecast.models.Language;
import android.zetterstrom.com.forecast.models.Unit;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luxelare.talleragorafest.Adapters.MyHeaderRecyclerAdapter;
import com.luxelare.talleragorafest.ForecastModels.elementForecast;
import com.luxelare.talleragorafest.ForecastModels.forecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public final String DarkSkyApiKey = "9c13f3900008ece1e0cb579cd329eace";
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

  //  DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
   // DatabaseReference mensaje_ref = ref.child("mensaje");

    private FirebaseAuth firebaseAuth;
    private  FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;
    @Override
    protected void onStart() {
        super.onStart();


    }

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
        ObtenerClima(25.790466,-108.985886);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivityForResult(i,1);
            }
        });
    }



    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.d("firebaselogin", "onAuthStateChanged: logeado :"+firebaseUser.getEmail());
                    Log.d("firebaselogin", "onAuthStateChanged: logeado : "+firebaseUser.getUid());
                }else { Log.d("firebaselogin", "onAuthStateChanged:- cerrado sesion"); }
            }
        };



        //initialize google account
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

             googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


    }
    public void ObtenerClima(double lat,double lng) {
        ForecastClient.getInstance()
                .getForecast(lat, lng, new Callback<Forecast>() {
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String latitud=data.getStringExtra("lat");
                String longitud=data.getStringExtra("lng");
                double lat = Utils.cadenaToDobleyCuatroDecimales(latitud,getApplicationContext());
                double lng = Utils.cadenaToDobleyCuatroDecimales(longitud,getApplicationContext());
                ObtenerClima(lat,lng);


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    //GoogleSignInRe





}
