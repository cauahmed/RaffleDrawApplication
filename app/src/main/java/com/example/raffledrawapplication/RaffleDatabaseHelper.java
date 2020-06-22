package com.example.raffledrawapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class RaffleDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "raffle_database.db";
    private static final int DATABASE_VERSION = 4;
    public static final String TABLE_RAFFLE = "raffle_details";
    public static final String TABLE_TICKET = "ticket_details";
    public static final String RAFFLE_ID = "raffleid";
    public static final String RAFFLE_COVER = "rafflecover";
    public static final String NUMBER_OF_TICKETS = "numberoftickets";
    public static final String RAFFLE_NAME = "rafflename";
    public static final String RAFFLE_DESCRIPTION = "raffledescription";
    public static final String RAFFLE_TYPE = "raffletype";
    public static final String TICKET_PRICE = "ticketprice";
    public static final String TICKET_NUMBER = "ticketnumber";
    public static final String START_DATE = "startdate";
    public static final String CUSTOMER_ID = "customerid";
    public static final String CUSTOMER_NAME = "customername";



    /*private static final String CREATE_TABLE_RAFFLE = "CREATE TABLE " + TABLE_RAFFLE + "(" +
            RAFFLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + RAFFLE_NAME +
            " TEXT," + RAFFLE_DESCRIPTION + " TEXT," + RAFFLE_TYPE +  " TEXT," + NUMBER_OF_TICKETS + " TEXT," + TICKET_PRICE + " TEXT," + START_DATE + " TEXT );";

    private static final String CREATE_TABLE_TICKET = "CREATE TABLE " + TABLE_TICKET + "(" + RAFFLE_ID +
            " INTEGER," + TICKET_NUMBER + " TEXT," + CUSTOMER_ID + " TEXT," + CUSTOMER_NAME + " TEXT );";*/




    public RaffleDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //Log.d("raffletable", CREATE_TABLE_RAFFLE);
        //Log.d("tickettable", CREATE_TABLE_TICKET);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +TABLE_RAFFLE+ "(raffleid INTEGER PRIMARY KEY AUTOINCREMENT, rafflename TEXT, raffledescription TEXT, raffletype TEXT, numberoftickets TEXT, ticketprice TEXT, startdate TEXT, rafflecover BLOB NOT NULL)");
        db.execSQL("CREATE TABLE " +TABLE_TICKET+ "(raffleid INTEGER, ticketnumber TEXT, customerid TEXT, customername TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_RAFFLE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TICKET + "'");
        onCreate(db);
    }

    //adding raffle and relevant ticket details to the database
    /*public void addRaffle(String rafflename, String raffledescription, String raffletype, String numberoftickets, String ticketprice, String startdate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesRaffle = new ContentValues();
        valuesRaffle.put(RAFFLE_NAME, rafflename);
        valuesRaffle.put(RAFFLE_DESCRIPTION, raffledescription);
        valuesRaffle.put(RAFFLE_TYPE, raffletype);
        valuesRaffle.put(NUMBER_OF_TICKETS, numberoftickets);
        valuesRaffle.put(TICKET_PRICE, ticketprice);
        valuesRaffle.put(START_DATE, startdate);
        long id = db.insertWithOnConflict(TABLE_RAFFLE, null, valuesRaffle, SQLiteDatabase.CONFLICT_IGNORE);

    }*/

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_RAFFLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
           cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readTicketData(String raffleid){
        String query = "SELECT * FROM " + TABLE_TICKET + " WHERE " + RAFFLE_ID + " = " + raffleid;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }






}
