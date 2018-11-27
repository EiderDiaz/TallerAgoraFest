package com.luxelare.talleragorafest.ForecastModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eider on 16/08/2017.
 */
 public class elementForecast {


    private String fecha  ;
    private String icono   ;
    private String temperatura   ;
    private String pronostico  ;
    private String probabLluvia;

    public elementForecast() {
    }

    public elementForecast(String fecha, String icono, String temperatura, String pronostico, String probabLluvia) {
        this.fecha = fecha;
        this.icono = icono;
        this.temperatura = temperatura;
        this.pronostico = pronostico;
        this.probabLluvia = probabLluvia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getPronostico() {
        return pronostico;
    }

    public void setPronostico(String pronostico) {
        this.pronostico = pronostico;
    }

    public String getProbabLluvia() {
        return probabLluvia;
    }

    public void setProbabLluvia(String probabLluvia) {
        this.probabLluvia = probabLluvia;
    }
}