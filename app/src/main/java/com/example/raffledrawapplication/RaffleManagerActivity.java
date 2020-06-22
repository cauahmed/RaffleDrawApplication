package com.example.raffledrawapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RaffleManagerActivity extends AppCompatActivity {
    RecyclerView rafflelist;
    FloatingActionButton addentry, deleteall;

    RaffleDatabaseHelper rDH;
    SQLiteDatabase db;
    ArrayList<String> raffle_id, raffle_name, raffle_description, raffle_type, numberoftickets, ticketprice, startdate;
    RaffleListAdapter raffleListAdapter;
    ArrayList<byte[]> rafflecover;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raffle_manager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        rafflelist = (RecyclerView) findViewById(R.id.rafflelist);

        rDH = new RaffleDatabaseHelper(RaffleManagerActivity.this);
        raffle_id = new ArrayList<>();
        raffle_name = new ArrayList<>();
        raffle_description = new ArrayList<>();
        raffle_type = new ArrayList<>();
        numberoftickets = new ArrayList<>();
        ticketprice = new ArrayList<>();
        startdate = new ArrayList<>();
        rafflecover = new ArrayList<>();
        addentry = (FloatingActionButton) findViewById(R.id.fab_add_entry);
        addentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_creator(view);

            }
        });
        deleteall = (FloatingActionButton) findViewById(R.id.fab_delete_all);
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
                Toast.makeText(view.getContext(), "Deleted All Raffle", Toast.LENGTH_SHORT).show();


            }
        });


        storeDataInArray();
        raffleListAdapter = new RaffleListAdapter(RaffleManagerActivity.this, RaffleManagerActivity.this, raffle_id, raffle_name, raffle_description,
                raffle_type, numberoftickets, ticketprice, startdate, rafflecover);
        rafflelist.setAdapter(raffleListAdapter);
        rafflelist.setLayoutManager(new LinearLayoutManager(RaffleManagerActivity.this));
    }

    public void deleteAll(){
        db = rDH.getWritableDatabase();
        db.execSQL("DELETE FROM " + RaffleDatabaseHelper.TABLE_RAFFLE);
        Toast.makeText(this, "Deleted All Raffle", Toast.LENGTH_SHORT).show();

    }
    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Raffles?");
        builder.setMessage("Confirm deletion of raffles?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteAll();
                Intent intent = new Intent(RaffleManagerActivity.this, RaffleManagerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArray(){
        Cursor cursor = rDH.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No raffle data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                raffle_id.add(cursor.getString(0));
                raffle_name.add(cursor.getString(1));
                raffle_description.add(cursor.getString(2));
                raffle_type.add(cursor.getString(3));
                numberoftickets.add(cursor.getString(4));
                ticketprice.add(cursor.getString(5));
                startdate.add(cursor.getString(6));
                rafflecover.add(cursor.getBlob(cursor.getColumnIndex(RaffleDatabaseHelper.RAFFLE_COVER)));

            }

        }
    }


    public void go_to_home(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void go_to_creator(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void go_to_list(View view) {
        Intent intent = new Intent(this, RaffleManagerActivity.class);
        startActivity(intent);
    }

    /*public void go_to_sell_activity(View view) {
        Intent intent = new Intent(this, TicketSellActivity.class);
        startActivity(intent);
    }*/

    /*public void go_to_edit_activity(View view) {
        Intent intent = new Intent(this, EditRaffleActivity.class);
        startActivity(intent);
    }*/




}








