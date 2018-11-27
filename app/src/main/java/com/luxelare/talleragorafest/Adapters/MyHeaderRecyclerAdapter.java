package com.luxelare.talleragorafest.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public MyHeaderRecyclerAdapter(headerForecast header, List<elementForecast> ListaClima)
    {
        this.header = header;
        this.ListaClima = ListaClima;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
            return  new VHHeader(v);
        }
        else if(viewType == TYPE_ELEMENT)
        {
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
            TextView Fecha,Pronostico,Temperatura,Icono,ProbLluvia,VelViento,Humedad;

            VHHeader VHheader = (VHHeader)holder;
            VHheader.Fecha.setText(header.getFecha());
            VHheader.Pronostico.setText(header.getPronostico());
            VHheader.Temperatura.setText(header.getTemperatura());
            VHheader.Icono.setText(header.getIcono());
            VHheader.ProbLluvia.setText(header.getProbLluvia());
            VHheader.VelViento.setText(header.getVelViento());
            VHheader.Humedad.setText(header.getHumedad());

        }
        else if(holder instanceof VHItem)
        {
            elementForecast currentItem = getItem(position-1);
            VHItem VHitem = (VHItem)holder;
            String[] HoraYFechaConFormato= Utils.FormatearFechaYHora(currentItem.getFecha());
            VHitem.fecha.setText(HoraYFechaConFormato[1]);
            VHitem.temperatura.setText(currentItem.getTemperatura());
            VHitem.icono.setText(currentItem.getIcono());
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
          /*  this.fecha = (TextView)itemView.findViewById(R.id.txtHora);
            this.icono = (TextView)itemView.findViewById(R.id.txtFecha);
            this.temperatura = (TextView)itemView.findViewById(R.id.txtDato1);
            this.pronostico = (TextView)itemView.findViewById(R.id.txtDato2);
            this.probabLluvia = (TextView)itemView.findViewById(R.id.txtDato3);*/

            
        }
    }
}