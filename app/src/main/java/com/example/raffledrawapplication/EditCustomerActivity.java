package com.example.raffledrawapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EditCustomerActivity extends AppCompatActivity {
    EditText customer_id, customer_name;
    Button savechanges;
    String raffleid, customerid, customername, ticketnumber;
    String customerid2, customername2;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_customer);


        openHelper = new RaffleDatabaseHelper(EditCustomerActivity.this);
        customer_id = (EditText) findViewById(R.id.txtcustomerid);
        customer_name = (EditText) findViewById(R.id.txtcustomername);
        savechanges = (Button) findViewById(R.id.btnsavechanges);

        getandSetIntentContent();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Ticket Number " + ticketnumber);
        }


        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerid2 = customer_id.getText().toString();
                customername2 = customer_name.getText().toString();
                changeData(ticketnumber, customerid2, customername2);
            }
        });



    }

    void getandSetIntentContent(){
        if(getIntent().hasExtra("raffleid") && getIntent().hasExtra("customerid") &&
                getIntent().hasExtra("customername") && getIntent().hasExtra("ticketnumber")){
            raffleid = getIntent().getStringExtra("raffleid");
            customerid = getIntent().getStringExtra("customerid");
            customername = getIntent().getStringExtra("customername");
            ticketnumber = getIntent().getStringExtra("ticketnumber");
            customer_id.setText(customerid);
            customer_name.setText(customername);
        }else{
            Toast.makeText(this, "No Ticket data is present", Toast.LENGTH_LONG).show();
        }
    }

    public void changeData(String ticketnumber, String customerid, String customername){
        db = openHelper.getWritableDatabase();
        ContentValues changedata = new ContentValues();
        changedata.put(RaffleDatabaseHelper.CUSTOMER_ID, customerid);
        changedata.put(RaffleDatabaseHelper.CUSTOMER_NAME, customername);

        long result = db.update(RaffleDatabaseHelper.TABLE_TICKET, changedata, "ticketnumber=?", new String[]{ticketnumber});
        if(result == -1){
            Toast.makeText(getApplicationContext(), "Failed to Update Ticket", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Ticket Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }




}

