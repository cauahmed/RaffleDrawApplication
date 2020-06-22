package com.example.raffledrawapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TicketListActivity extends AppCompatActivity {
    RecyclerView ticketlist;
    Button draw;
    RaffleDatabaseHelper rDH;
    SQLiteDatabase db;
    ArrayList<String> raffle_id, ticket_number, customer_id, customer_name;
    String raffleid, rafflename;
    TicketListAdapter ticketListAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);

        ticketlist = (RecyclerView) findViewById(R.id.ticketlist);
        rDH = new RaffleDatabaseHelper(TicketListActivity.this);
        raffle_id = new ArrayList<>();
        ticket_number = new ArrayList<>();
        customer_id = new ArrayList<>();
        customer_name = new ArrayList<>();

        getandSetIntentContent();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Ticket List for " + rafflename);
        }

        storeDataInArray();
        ticketListAdapter = new TicketListAdapter(TicketListActivity.this, TicketListActivity.this, raffle_id, ticket_number, customer_id, customer_name);
        ticketlist.setAdapter(ticketListAdapter);
        ticketlist.setLayoutManager(new LinearLayoutManager(TicketListActivity.this));
        draw = (Button) findViewById(R.id.btn_draw);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DisplayWinnerActivity.class);
                String random = ticket_number.get(new Random().nextInt(ticket_number.size()));
                intent.putExtra("randomwinner", random);
                startActivity(intent);
            }
        });

    }

    void getandSetIntentContent(){
        if(getIntent().hasExtra("id")){
            raffleid = getIntent().getStringExtra("id");
            rafflename = getIntent().getStringExtra("raffle_name");

        }else{
            Toast.makeText(this, "No Data is present", Toast.LENGTH_LONG).show();
        }
    }

    void storeDataInArray(){
        Cursor cursor = rDH.readTicketData(raffleid);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Ticket data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                raffle_id.add(cursor.getString(0));
                ticket_number.add(cursor.getString(1));
                customer_id.add(cursor.getString(2));
                customer_name.add(cursor.getString(3));
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

}
