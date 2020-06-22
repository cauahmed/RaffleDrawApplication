package com.example.raffledrawapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditRaffleActivity extends AppCompatActivity {
   ImageView raffle_cover;
   EditText raffle_name, raffle_description;
   Button save_changes, delete_raffle, sell_tickets, list_tickets;
   String raffleid, rafflename, raffledescription;
   String raffleid2, rafflename2, raffledescription2, raffletype, numberoftickets, ticketprice, startdate;
   byte[] rafflecover;
   SQLiteOpenHelper openHelper;
   SQLiteDatabase db;

   @Override
    protected  void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.edit_raffle);

       openHelper = new RaffleDatabaseHelper(EditRaffleActivity.this);
       raffle_cover = findViewById(R.id.iv_rafflecover);
       raffle_name = findViewById(R.id.txt_rafflename2);
       raffle_description = findViewById(R.id.txt_description2);
       save_changes = findViewById(R.id.btnsavechanges);
       delete_raffle = findViewById(R.id.btn_delete);
       sell_tickets = findViewById(R.id.btn_sell);
       list_tickets = findViewById(R.id.btn_listtickets);

       getandSetIntentContent();

       ActionBar ab = getSupportActionBar();
       if (ab != null) {
           ab.setTitle(rafflename);
       }


       save_changes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rafflename = raffle_name.getText().toString();
            raffledescription = raffle_description.getText().toString();
            changeData(raffleid, rafflename, raffledescription);
        }


       });

       delete_raffle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               confirmDialog();
           }
       });

       sell_tickets.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), TicketSellActivity.class);
               intent.putExtra("id", raffleid2);
               intent.putExtra("raffle_name", rafflename2);
               intent.putExtra("raffle_description", raffledescription2);
               intent.putExtra("raffle_type", raffletype);
               intent.putExtra("numberoftickets", numberoftickets);
               intent.putExtra("ticketprice", ticketprice);
               intent.putExtra("startdate", startdate);
               startActivity(intent);
           }
       });

       list_tickets.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), TicketListActivity.class);
               intent.putExtra("id", raffleid2);
               intent.putExtra("raffle_name", rafflename2);
               startActivity(intent);
           }
       });

   }

   void getandSetIntentContent(){
      if(getIntent().hasExtra("id") && getIntent().hasExtra("raffle_name") &&
              getIntent().hasExtra("raffle_description") && getIntent().hasExtra("raffle_type")){
          raffleid = getIntent().getStringExtra("id");
          rafflename = getIntent().getStringExtra("raffle_name");
          raffledescription = getIntent().getStringExtra("raffle_description");
          raffleid2 = getIntent().getStringExtra("id");
          rafflename2 = getIntent().getStringExtra("raffle_name");
          raffledescription2 = getIntent().getStringExtra("raffle_description");
          raffletype = getIntent().getStringExtra("raffle_type");
          numberoftickets = getIntent().getStringExtra("numberoftickets");
          ticketprice = getIntent().getStringExtra("ticketprice");
          startdate = getIntent().getStringExtra("startdate");
          rafflecover = getIntent().getByteArrayExtra("rafflecover");

          raffle_cover.setImageBitmap(getImage(rafflecover));
          raffle_name.setText(rafflename);
          raffle_description.setText(raffledescription);

      }else{
         Toast.makeText(this, "No Data is present", Toast.LENGTH_LONG).show();
      }
   }

   public void confirmDialog(){
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle("Delete " + rafflename + " ?");
       builder.setMessage("Confirm deletion of " + rafflename + " ?");
       builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               deleteRaffle(raffleid);
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

    public void changeData(String raffleid, String rafflename, String raffledescription){
        db = openHelper.getWritableDatabase();
        ContentValues changedata = new ContentValues();
        changedata.put(RaffleDatabaseHelper.RAFFLE_NAME, rafflename);
        changedata.put(RaffleDatabaseHelper.RAFFLE_DESCRIPTION, raffledescription);

        long result = db.update(RaffleDatabaseHelper.TABLE_RAFFLE, changedata, "raffleid=?", new String[]{raffleid});
        if(result == -1){
            Toast.makeText(getApplicationContext(), "Failed to Update Raffle", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Raffle Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteRaffle(String raffleid){
       db = openHelper.getWritableDatabase();
       long result = db.delete(RaffleDatabaseHelper.TABLE_RAFFLE, "raffleid=?", new String[]{raffleid});
        if(result == -1){
            Toast.makeText(getApplicationContext(), "Failed to Delete Raffle", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Raffle Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap getImage(byte[] image) {
       return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
