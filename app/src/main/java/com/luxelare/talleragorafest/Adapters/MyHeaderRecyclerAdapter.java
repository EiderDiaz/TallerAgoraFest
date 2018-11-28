package com.luxelare.talleragorafest.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luxelare.talleragorafest.ForecastModels.elementForecast;
import com.luxelare.talleragorafest.R;
import com.luxelare.talleragorafest.ForecastModels.headerForecast;
import com.luxelare.talleragorafest.Utils;


import java.util.List;


public class MyHeaderRecyclerAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ELEMENT = 1;

    headerForecast header;
    List<elementForecast> ListaClima;
    Context context;

    public MyHeaderRecyclerAdapter(headerForecast header, List<elementForecast> ListaClima, Context context) {
        this.header = header;
        this.ListaClima = ListaClima;
        this.context= context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
            return  new VHHeader(v);
        }
        else if(viewType == TYPE_ELEMENT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new VHItem(v);
        }
        return null;
    }

    private elementForecast getItem(int position)
    {
        return ListaClima.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VHHeader)
        {

            VHHeader VHheader = (VHHeader)holder;
            VHheader.Fecha.setText(header.getFecha());
            VHheader.Pronostico.setText(header.getPronostico());
            Toast.makeText(context, "header.getPronostico()"+header.getPronostico(), Toast.LENGTH_SHORT).show();
            Log.d("onBindViewHolder: ",header.getPronostico() );
            VHheader.Temperatura.setText(header.getTemperatura());
            //VHheader.Icono.setText();
            VHheader.ProbLluvia.setText(header.getProbLluvia());
            VHheader.VelViento.setText(header.getVelViento());
            VHheader.Humedad.setText(header.getHumedad());

            /*if (header.getIcono().equals("clear-day")){
                Drawable img = context.getResources().getDrawable( R.drawable.clearday);
                img.setBounds( 0, 0, 60, 60 );
                VHheader.Pronostico.setCompoundDrawables( img, null, null, null );
            }
            if (header.getIcono().equals("clear-night")){
                Drawable img = context.getResources().getDrawable( R.drawable.clearnight);
                img.setBounds( 0, 0, 60, 60 );
                VHheader.Pronostico.setCompoundDrawables( img, null, null, null );
            }

            if (header.getIcono().equals("rain") || header.getIcono().equals("thunderstorm") || header.getIcono().equals("tornado"))  {
                Drawable img = context.getResources().getDrawable( R.drawable.rain);
                img.setBounds( 0, 0, 60, 60 );
                VHheader.Pronostico.setCompoundDrawables( img, null, null, null );
            }
            if (header.getIcono().equals("snow") || header.getIcono().equals("sleet") || header.getIcono().equals("hail"))  {
                Drawable img = context.getResources().getDrawable( R.drawable.snow);
                img.setBounds( 0, 0, 60, 60 );
                VHheader.Pronostico.setCompoundDrawables( img, null, null, null );
            }
            if (header.getIcono().equals("wind")){
                Drawable img = context.getResources().getDrawable( R.drawable.wind);
                img.setBounds( 0, 0, 60, 60 );
                VHheader.Pronostico.setCompoundDrawables( img, null, null, null );
            }
            if (header.getIcono().equals("fog")){
                Drawable img = context.getResources().getDrawable( R.drawable.fog);
                img.setBounds( 0, 0, 60, 60 );
                VHheader.Pronostico.setCompoundDrawables( img, null, null, null );
            }
            if (header.getIcono().equals("cloudy") || header.getIcono().equals("partly-cloudy-night"))  {
                Drawable img = context.getResources().getDrawable( R.drawable.cloudy);
                img.setBounds( 0, 0, 60, 60 );
                VHheader.Pronostico.setCompoundDrawables( img, null, null, null );
            }
            if (header.getIcono().equals("partly-cloudy-day")){
                Drawable img = context.getResources().getDrawable( R.drawable.partlycloudy);
                img.setBounds( 0, 0, 60, 60 );
                VHheader.Pronostico.setCompoundDrawables( img, null, null, null );
            }
            */


        }
        else if(holder instanceof VHItem) {
            elementForecast currentItem = getItem(position-1);
            VHItem VHitem = (VHItem)holder;
           // String[] HoraYFechaConFormato= Utils.FormatearFechaYHora();
            VHitem.fecha.setText(currentItem.getFecha());
            VHitem.temperatura.setText(currentItem.getTemperatura());
//            VHitem.icono.setText(currentItem.getIcono());
            VHitem.pronostico.setText(currentItem.getPronostico());
            VHitem.probabLluvia.setText(currentItem.getProbabLluvia());

        }

    }

    //    need to override this method
    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ELEMENT;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return ListaClima.size()+1;
    }

    class VHHeader extends RecyclerView.ViewHolder{
        TextView Fecha,Pronostico,Temperatura,Icono,ProbLluvia,VelViento,Humedad;


        public VHHeader(View itemView) {
            super(itemView);
            this.Fecha = (TextView)itemView.findViewById(R.id.txtDiayFecha);
            this.Pronostico = (TextView)itemView.findViewById(R.id.txtPronostico);
            this.Temperatura = (TextView)itemView.findViewById(R.id.txtTemperatura);
            //this.Icono = (TextView)itemView.findViewById(R.id.txtHeaderDato2);
            this.ProbLluvia = (TextView)itemView.findViewById(R.id.txtProbabLluvia);
            this.VelViento = (TextView)itemView.findViewById(R.id.txtVelViento);
            this.Humedad = (TextView)itemView.findViewById(R.id.txtHumedad);


        }
    }

    class VHItem extends RecyclerView.ViewHolder{
        TextView fecha;
        TextView icono;
        TextView temperatura;
        TextView pronostico;
        TextView probabLluvia;
       

        public VHItem(View itemView) {
            super(itemView);
            this.fecha = (TextView)itemView.findViewById(R.id.txtDiayFecha);
            //this.icono = (TextView)itemView.findViewById(R.id.txt);
            this.temperatura = (TextView)itemView.findViewById(R.id.txtTemperatura);
            this.pronostico = (TextView)itemView.findViewById(R.id.txtPronostico);
            this.probabLluvia = (TextView)itemView.findViewById(R.id.txtProbabLluvia);

        }
    }
}