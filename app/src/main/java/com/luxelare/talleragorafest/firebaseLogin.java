package com.luxelare.talleragorafest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class firebaseLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int SIGN_IN_GOOGLE_CODE = 1;

    private Button btnCrearCuenta, btnIniciarSesion,btnCerrarSesion;
    private EditText edtMail,edtPassword;
    private SignInButton signInButton;
    private FirebaseAuth firebaseAuth;
    private  FirebaseAuth.AuthStateListener authStateListener;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_login);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        edtMail  = findViewById(R.id.etMail);
        edtPassword = findViewById(R.id.etpassword);
        signInButton = findViewById(R.id.btnSignInGoogle);
        initialize();

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signIn(edtMail.getText().toString(),edtPassword.getText().toString());
                signOutGoogle();

            }
        });

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(edtMail.getText().toString(),edtPassword.getText().toString());
            }
        });
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_GOOGLE_CODE);
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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    private void createAccount(String mail, String pass){
        firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(firebaseLogin.this, "CREATED ACC", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(firebaseLogin.this, "FAILED TO CREATE ACC", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void signIn(String mail, String pass){
        firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(firebaseLogin.this, "AUTH SUCCESS", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(firebaseLogin.this, "AUTH FAIL", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private void signOut(){
        firebaseAuth.signOut();

    }

    private void signOutGoogle(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Toast.makeText(firebaseLogin.this, "CIAO", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void signInGoogleFirebase( GoogleSignInResult googleSignInResult){
        if (googleSignInResult.isSuccess()){
            AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInResult.getSignInAccount().getIdToken(),null);
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(firebaseLogin.this, "GOOGLE AUTH SUCCESS", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(firebaseLogin.this,MainActivity.class);
                        startActivity(intent);

                    }else{Toast.makeText(firebaseLogin.this, "GOOGLE AUTH FAIL", Toast.LENGTH_SHORT).show(); }
                }
            });
        }
        else{

            Toast.makeText(this, "valio pija?"+googleSignInResult.getStatus(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_GOOGLE_CODE){
            //Toast.makeText(this, "data:"+data.getExtras(), Toast.LENGTH_SHORT).show();
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            int statusCode = googleSignInResult.getStatus().getStatusCode();
            Toast.makeText(this, "statusCode"+statusCode ,Toast.LENGTH_SHORT).show();
            signInGoogleFirebase(googleSignInResult);
            }
        }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "error"+connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}



