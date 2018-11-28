package com.luxelare.talleragorafest.ForecastModels;

import java.util.ArrayList;

public class forecast {
    private  headerForecast header;
    private ArrayList<elementForecast> elementosForecast;


    public forecast() {
    }

    public forecast(headerForecast header, ArrayList<elementForecast> elementosForecast) {
        this.header = header;
        this.elementosForecast = elementosForecast;
    }

    public headerForecast getHeader() {
        return header;
    }

    public void setHeader(headerForecast header) {
        this.header = header;
    }

    public ArrayList<elementForecast> getElementosForecast() {
        return elementosForecast;
    }

    public void setElementosForecast(ArrayList<elementForecast> elementosForecast) {
        this.elementosForecast = elementosForecast;
    }
}
