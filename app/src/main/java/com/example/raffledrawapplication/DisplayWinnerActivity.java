package com.example.raffledrawapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayWinnerActivity extends AppCompatActivity {

    TextView winner;
    Button notify;
    RaffleDatabaseHelper rDH;
    SQLiteDatabase db;
    String winningticket;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_winner);

        rDH = new RaffleDatabaseHelper(DisplayWinnerActivity.this);
        winner = (TextView) findViewById(R.id.tv_winningticket);
        notify = (Button) findViewById(R.id.btn_notify);
        getandSetIntentContent();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Random Winner");
        }

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DisplayWinnerActivity.this, "Customer has been notified", Toast.LENGTH_LONG).show();
                deleteRaffle(String.valueOf(winningticket.charAt(3)));
                deleteTickets(String.valueOf(winningticket.charAt(3)));
            }
        });


    }

    void getandSetIntentContent(){
        if(getIntent().hasExtra("randomwinner")){
            winningticket = getIntent().getStringExtra("randomwinner");
            winner.setText(winningticket);

        }else{
            Toast.makeText(this, "No Data is present", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteRaffle(String raffleid){
        db = rDH.getWritableDatabase();
        long result = db.delete(RaffleDatabaseHelper.TABLE_RAFFLE, "raffleid=?", new String[]{raffleid});
        if(result == -1){
            Toast.makeText(getApplicationContext(), "Failed to Close Raffle", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Raffle Closed", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTickets(String raffleid){
        db = rDH.getWritableDatabase();
        long result = db.delete(RaffleDatabaseHelper.TABLE_TICKET, "raffleid=?", new String[]{raffleid});
        if(result == -1){
            Toast.makeText(getApplicationContext(), "Failed to Close Tickets", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Tickets Closed", Toast.LENGTH_SHORT).show();
        }
    }


}
