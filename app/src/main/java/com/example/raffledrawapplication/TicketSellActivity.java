package com.example.raffledrawapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TicketSellActivity extends AppCompatActivity {
    EditText customer_id, customer_name, number_of_tickets;
    Button processpurchase;
    String raffleid, rafflename, raffledescription, raffletype, numberoftickets, ticketprice, startdate;
    String customerid, customername, numberoftickets2, remainingticketsstr;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_ticket);

        //Calendar calendar = Calendar.getInstance();
        //final String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        customer_id = (EditText) findViewById(R.id.txtcustomerid);
        customer_name = (EditText) findViewById(R.id.txtcustomername);
        number_of_tickets = (EditText) findViewById(R.id.txt_tickets);
        TextView textViewDate = (TextView)findViewById(R.id.tviewstartdate2);

        getandSetIntentContent();

        textViewDate.setText("Raffle Purchase Date " + startdate);
        openHelper = new RaffleDatabaseHelper(this);

        processpurchase = (Button) findViewById(R.id.btn_process);


        processpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerid = customer_id.getText().toString();
                customername = customer_name.getText().toString();
                numberoftickets2 = number_of_tickets.getText().toString();
                db=openHelper.getWritableDatabase();
                insertTicketData(Integer.parseInt(raffleid), customerid, customername, numberoftickets2);

                //go_to_list(view);
            }
        });
    }

    void getandSetIntentContent(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("raffle_name") &&
                getIntent().hasExtra("raffle_description") && getIntent().hasExtra("raffle_type")){
            raffleid = getIntent().getStringExtra("id");
            rafflename = getIntent().getStringExtra("raffle_name");
            raffledescription = getIntent().getStringExtra("raffle_description");
            raffletype = getIntent().getStringExtra("raffle_type");
            numberoftickets = getIntent().getStringExtra("numberoftickets");
            ticketprice = getIntent().getStringExtra("ticketprice");
            startdate = getIntent().getStringExtra("startdate");

        }else{
            Toast.makeText(this, "No Data is present", Toast.LENGTH_LONG).show();
        }
    }

    public void insertTicketData(int raffleid, String customerid, String customername, String numoftickets) {
        int nooftickets = Integer.parseInt(numoftickets);
        int remaianingtickets = Integer.parseInt(numberoftickets) - nooftickets;

        if (remaianingtickets <= -1) {
            Toast.makeText(this, "Not enough tickets available", Toast.LENGTH_SHORT).show();
            return;
        } else {

            for (int i = 0; i < nooftickets; i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(RaffleDatabaseHelper.RAFFLE_ID, raffleid);

                String ticketnumber = "TK" + raffletype.charAt(0) + raffleid + String.format("%02d", Integer.valueOf(i));

                contentValues.put(RaffleDatabaseHelper.TICKET_NUMBER, ticketnumber);

                contentValues.put(RaffleDatabaseHelper.CUSTOMER_ID, customerid);
                contentValues.put(RaffleDatabaseHelper.CUSTOMER_NAME, customername);


                long id = db.insert(RaffleDatabaseHelper.TABLE_TICKET, null, contentValues);
            }

            remainingticketsstr = Integer.toString(remaianingtickets);
            numberoftickets = remainingticketsstr;
            Toast.makeText(getApplicationContext(), "Purchase processed successfully", Toast.LENGTH_LONG).show();
            changeTickets(Integer.toString(raffleid), remainingticketsstr);

        }
    }

    public void changeTickets(String raffleid, String numberoftickets){
        //db = openHelper.getWritableDatabase();
        ContentValues changedata = new ContentValues();
        changedata.put(RaffleDatabaseHelper.NUMBER_OF_TICKETS, numberoftickets);

        long result = db.update(RaffleDatabaseHelper.TABLE_RAFFLE, changedata, "raffleid=?", new String[]{raffleid});
        if(result == -1){
            Toast.makeText(getApplicationContext(), "Failed to Update Tickets", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Tickets Successfully Updated", Toast.LENGTH_SHORT).show();
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

}
