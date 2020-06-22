package com.example.raffledrawapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RaffleListAdapter extends RecyclerView.Adapter<RaffleListAdapter.RaffleViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList raffle_id, raffle_name, raffle_description, raffle_type, numberoftickets, ticketprice, startdate, rafflecover;
    //int position;

    RaffleListAdapter(Activity activity, Context context, ArrayList raffle_id, ArrayList raffle_name,
                      ArrayList raffle_description, ArrayList raffle_type,
                      ArrayList numberoftickets, ArrayList ticketprice, ArrayList startdate, ArrayList rafflecover){
        this.activity = activity;
        this.context = context;
        this.raffle_id = raffle_id;
        this.raffle_name = raffle_name;
        this.raffle_description = raffle_description;
        this.raffle_type = raffle_type;
        this.numberoftickets = numberoftickets;
        this.ticketprice = ticketprice;
        this.startdate = startdate;
        this.rafflecover = rafflecover;
    }



    @NonNull
    @Override
    public RaffleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rafflelist_item, parent, false);
        return new RaffleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RaffleViewHolder holder, final int position) {
        //this.position = position;
        holder.raffle_id_txt.setText(String.valueOf(raffle_id.get(position)));
        holder.raffle_name_txt.setText(String.valueOf(raffle_name.get(position)));
        holder.raffle_description_txt.setText(String.valueOf(raffle_description.get(position)));
        holder.raffle_type_txt.setText(String.valueOf(raffle_type.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditRaffleActivity.class);
                intent.putExtra("id", String.valueOf(raffle_id.get(position)));
                intent.putExtra("raffle_name", String.valueOf(raffle_name.get(position)));
                intent.putExtra("raffle_description", String.valueOf(raffle_description.get(position)));
                intent.putExtra("raffle_type", String.valueOf(raffle_type.get(position)));
                intent.putExtra("numberoftickets", String.valueOf(numberoftickets.get(position)));
                intent.putExtra("ticketprice", String.valueOf(ticketprice.get(position)));
                intent.putExtra("startdate", String.valueOf(startdate.get(position)));
                intent.putExtra("rafflecover", (byte[]) rafflecover.get(position));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return raffle_id.size();
    }

    public class RaffleViewHolder extends RecyclerView.ViewHolder {

        TextView raffle_id_txt, raffle_name_txt, raffle_description_txt, raffle_type_txt;
        LinearLayout mainLayout;

        public RaffleViewHolder(@NonNull View itemView) {
            super(itemView);
            raffle_id_txt = itemView.findViewById(R.id.raffle_id_txt);
            raffle_name_txt = itemView.findViewById(R.id.raffle_name_txt);
            raffle_description_txt = itemView.findViewById(R.id.raffle_description_txt);
            raffle_type_txt = itemView.findViewById(R.id.raffle_type_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
