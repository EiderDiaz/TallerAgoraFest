package com.luxelare.talleragorafest;

import android.content.Context;
import android.util.Log;
/*
import android.zetterstrom.com.forecast.models.DataPoint;
import android.zetterstrom.com.forecast.models.Forecast;
import android.zetterstrom.com.forecast.models.Icon;
import android.zetterstrom.com.forecast.models.PrecipitationType;

import com.example.eider.bitacoradevuelos.modelo.ClimaInfo;*/

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Eider on 17/08/2017.
 */

public class Utils {

/*
    public static String ObtenerClimaActual(Forecast forecast) {

        if (forecast != null && forecast.getCurrently() != null) {
            ArrayList<String> currentlywheather = new ArrayList<String>();
            currentlywheather.add(forecast.getCurrently().getSummary());
            currentlywheather.add(forecast.getCurrently().getPrecipProbability().toString());
            currentlywheather.add(forecast.getCurrently().getTemperature().toString());
            currentlywheather.add(forecast.getCurrently().getHumidity().toString());
            currentlywheather.add(forecast.getCurrently().getWindSpeed().toString());
            currentlywheather.add(forecast.getCurrently().getCloudCover().toString());
            String clima = Arrays.asList(currentlywheather).toString().replace("[", "");
            clima = clima.replace("]", "");
            String[] arrayClima = clima.split(",");
            String estadoMeteorologico = arrayClima[0];
            String probabilidadDePresipitacion = arrayClima[1];
            String temperatura = arrayClima[2];
            String humedad = arrayClima[3];
            String velocidadDeVientos = arrayClima[4];
            String coverturaDeNubes = arrayClima[5];
            String climaConFormato = "estadoMeteorologico: " + estadoMeteorologico +
                    "\n probabilidadDePresipitacion:" + probabilidadDePresipitacion +
                    "\n temperatura:" + temperatura +
                    "\n humedad:" + humedad +
                    "\n velocidadDeVientos:" + velocidadDeVientos +
                    "\n coverturaDeNubes:" + coverturaDeNubes;
            return climaConFormato;

        } else {
            return "forecast vacio";
        }

    }

    public static ArrayList<ClimaInfo> pronosticoPorHoras(Forecast forecast, Context context) {
        ArrayList<ClimaInfo> ArraypronosticoPorHoras = new ArrayList<>();

        try {
            if (forecast != null && forecast.getHourly() != null && forecast.getHourly().getDataPoints() != null) {
                ArrayList<DataPoint> HourlyDataPoints = forecast.getHourly().getDataPoints();
                for (DataPoint dataPoint : HourlyDataPoints) {
                    String temp = dataPoint.getTime().toString();
                    //Toast.makeText(context, "temp:"+temp, Toast.LENGTH_SHORT).show();
                    temp = temp.substring(11, 13);
                    Log.d("XXXXXXXX", "pronosticoPorHoras: " + temp);
                    String fecha = dataPoint.getTime().toString().substring(0, 16);
                    //solo toma las siguientes horas 6,9 12,15,18
                    if (temp.equals("06") || temp.equals("09") || temp.equals("12") || temp.equals("15") || temp.equals("18")) {
                        Icon icono = dataPoint.getIcon();
                        String estadoMeteorologico = dataPoint.getSummary() + "/" + icono.getText();
                        String probabilidadDePresipitacion = dataPoint.getPrecipProbability().toString();
                        String temperatura = dataPoint.getTemperature().toString();
                        String humedad = dataPoint.getHumidity().toString();
                        String velocidadDeVientos = dataPoint.getWindSpeed().toString();
                        String coverturaDeNubes = dataPoint.getCloudCover().toString();
                        ArraypronosticoPorHoras.add(new ClimaInfo(fecha, estadoMeteorologico, probabilidadDePresipitacion, temperatura, humedad, velocidadDeVientos, coverturaDeNubes));
                    } //condicional para obtener solo las temperaturas señaladas
                } //ciclo para recorrer los datapoints
            } //sin datos vacios
        } // fin de catch

        catch (Exception ex) {

        }


        return ArraypronosticoPorHoras;
    } */

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


