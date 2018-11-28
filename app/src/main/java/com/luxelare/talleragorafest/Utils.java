package com.luxelare.talleragorafest;

import android.content.Context;
import android.util.Log;
import android.zetterstrom.com.forecast.models.DataPoint;
import android.zetterstrom.com.forecast.models.Forecast;
import android.zetterstrom.com.forecast.models.Icon;
/*
import android.zetterstrom.com.forecast.models.DataPoint;
import android.zetterstrom.com.forecast.models.Forecast;
import android.zetterstrom.com.forecast.models.Icon;
import android.zetterstrom.com.forecast.models.PrecipitationType;

import com.example.eider.bitacoradevuelos.modelo.ClimaInfo;*/

import com.luxelare.talleragorafest.ForecastModels.elementForecast;
import com.luxelare.talleragorafest.ForecastModels.forecast;
import com.luxelare.talleragorafest.ForecastModels.headerForecast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Eider on 17/08/2017.
 */

public class Utils {

    public static forecast pronosticoPorHoras(Forecast JsonClima) {
        forecast ArraypronosticoPorHoras = new forecast(); //objeto que va al adapter
        headerForecast headerForecast = new headerForecast();// header
        ArrayList<elementForecast> elementForecastArrayList = new ArrayList<>(); //lista de elementos forecast

        try {
            if (JsonClima != null && JsonClima.getDaily() != null && JsonClima.getDaily().getDataPoints() != null) {
                int contador=0;
                ArrayList<DataPoint> dailyDataPoints = JsonClima.getDaily().getDataPoints(); //convertimos el json en datapoints para poder recorrerlo
                for (DataPoint dataPoint : dailyDataPoints) { //recorremos los datapoints
                    contador++;
                    if (contador==1){ //llenar los datos del header
                       // Log.d("pronosticoPorHoras: ","__________debuger:"+dataPoint.getWindSpeed()+"/"+dataPoint.getHumidity());
                        headerForecast.setFecha(dataPoint.getTime().toString());
                        headerForecast.setPronostico(dataPoint.getSummary());
                        headerForecast.setTemperatura(dataPoint.getTemperatureMax()+"/"+dataPoint.getTemperatureMin());
                        headerForecast.setIcono(dataPoint.getIcon().getText());
                       headerForecast.setProbLluvia(dataPoint.getPrecipProbability().toString());
                       headerForecast.setVelViento(dataPoint.getWindSpeed().toString());
                        headerForecast.setHumedad(dataPoint.getHumidity().toString());
                    }else { //llenar lista de elementos
                        elementForecast element = new elementForecast();
                        element.setFecha(dataPoint.getTime().toString());
                        element.setIcono(dataPoint.getIcon().getText());
                        element.setTemperatura(dataPoint.getTemperatureMax()+"/"+dataPoint.getTemperatureMin());
                        element.setPronostico(dataPoint.getSummary());
                        element.setProbabLluvia(dataPoint.getPrecipProbability().toString());
                        elementForecastArrayList.add(element);
                    }
                    ArraypronosticoPorHoras.setHeader(headerForecast); //vaciar el header
                    ArraypronosticoPorHoras.setElementosForecast(elementForecastArrayList); //vaciar los elementos
                } //ciclo para recorrer los datapoints
            } //sin datos vacios
        } // fin de catch

        catch (Exception ex) {
            Log.d("pronosticoPorHoras: ", ex.getMessage());

            }
        return ArraypronosticoPorHoras;
    }

    public static Double cadenaToDobleyCuatroDecimales(String n, Context context) {
        // TODO: 10/08/2017 este metodo convierte una cadena en un double con 4 decimales jeje
        String[] splitcoor = n.split("\\.");
        String entero = splitcoor[0];
        String cuatrodecimales = splitcoor[1].substring(0, 4);
        Double d = Double.valueOf(entero + "." + cuatrodecimales);
        // Toast.makeText(context, "coordenada :"+d, Toast.LENGTH_SHORT).show();
        return d;

    }

    public static String[] FormatearFechaYHora(String fechaSinFormato) {
        String[] FechaConFormato = new String[2];

        String[] fechayhora = fechaSinFormato.split(" ");
        String[] hora = fechayhora[1].split(":");
        String[] fecha = fechayhora[0].split("-");
        String diayMes = FormatearFecha(fecha[1], fecha[2]);

        FechaConFormato[0] = hora[0];
        FechaConFormato[1] = diayMes;

        return FechaConFormato;

    }

    public static String FormatearFecha(String mes, String dia) {
        String fechaConFormato = dia;
        switch (mes) {
            case "01":
                fechaConFormato += "/Ene";
                break;
            case "02":
                fechaConFormato += "/Feb";
                break;
            case "03":
                fechaConFormato += "/Mar";
                break;
            case "04":
                fechaConFormato += "/Abr";
                break;
            case "05":
                fechaConFormato += "/May";
                break;
            case "06":
                fechaConFormato += "/Jun";
                break;
            case "07":
                fechaConFormato += "/Jul";
                break;
            case "08":
                fechaConFormato += "/Ago";
                break;
            case "09":
                fechaConFormato += "/Sep";
                break;
            case "10":
                fechaConFormato += "/Oct";
                break;
            case "11":
                fechaConFormato += "/Nov";
                break;
            case "12":
                fechaConFormato += "/Dic";
                break;
        }

        return fechaConFormato;

    }
}


