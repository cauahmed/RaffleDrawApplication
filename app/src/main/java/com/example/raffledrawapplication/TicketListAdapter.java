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

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList raffle_id, ticket_number, customer_id, customer_name;

    TicketListAdapter(Activity activity, Context context, ArrayList raffle_id, ArrayList ticket_number,
                      ArrayList customer_id, ArrayList customer_name){
        this.activity = activity;
        this.context = context;
        this.raffle_id = raffle_id;
        this.ticket_number = ticket_number;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ticketlist_item, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, final int position) {
        holder.txt_raffleid.setText(String.valueOf(raffle_id.get(position)));
        holder.ticketnumber_txt.setText(String.valueOf(ticket_number.get(position)));
        holder.customerid_txt.setText(String.valueOf(customer_id.get(position)));
        holder.customername_txt.setText(String.valueOf(customer_name.get(position)));
        holder.mainticketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditCustomerActivity.class);
                intent.putExtra("raffleid", String.valueOf(raffle_id.get(position)));
                intent.putExtra("customerid", String.valueOf(customer_id.get(position)));
                intent.putExtra("customername", String.valueOf(customer_name.get(position)));
                intent.putExtra("ticketnumber", String.valueOf(ticket_number.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return raffle_id.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView txt_raffleid, customername_txt, ticketnumber_txt, customerid_txt;
        LinearLayout mainticketLayout;


        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_raffleid = itemView.findViewById(R.id.txt_raffleid);
            customername_txt = itemView.findViewById(R.id.customername_txt);
            ticketnumber_txt = itemView.findViewById(R.id.ticketnumber_txt);
            customerid_txt = itemView.findViewById(R.id.customerid_txt);
            mainticketLayout = itemView.findViewById(R.id.mainticketLayout);
        }
    }
}
