package com.example.raffledrawapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ImageView ivcover;
    EditText raffleName;
    EditText raffleDescription;
    Spinner raffleType;
    String raffletypetext;
    String pictureAddress;
    EditText noOfTickets;
    EditText ticketPrice;
    Button savechanges, uploadcover;
    TextView textViewDate;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    //String rafflename = "", raffledescription = "", raffletype = "", numoftickets = "", ticketprice = "", startdate = "";

    private static final int PERMISSION_REQUEST = 0;
    private static final int UPLOAD_IMAGE = 1;
    private Object Menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }

        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        raffleName = (EditText)findViewById(R.id.txt_rafflename);
        raffleDescription = (EditText)findViewById(R.id.txt_raffledescription);
        raffleType  = (Spinner)findViewById(R.id.spn_raffletype);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Regular Raffle");
        arrayList.add("Margin Raffle");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raffleType.setAdapter(arrayAdapter);
        raffleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //long spinnerval = spinner.getItemIdAtPosition(position);
                raffletypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        noOfTickets = (EditText)findViewById(R.id.txt_numberoftickets);
        ticketPrice = (EditText)findViewById(R.id.txt_ticketprice);

        Calendar calendar = Calendar.getInstance();
        final String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        textViewDate = (TextView)findViewById(R.id.tviewstartdate);
        textViewDate.setText("Raffle Start Date " + currentDate);
        openHelper = new RaffleDatabaseHelper(this);

        savechanges = (Button)findViewById(R.id.btn_save);
        ivcover = (ImageView)findViewById(R.id.ivcover);
        //final BitmapDrawable drawable = (BitmapDrawable)ivcover.getDrawable();
        //final Bitmap bitmap = drawable.getBitmap();
        uploadcover = (Button) findViewById(R.id.btnuploadcover);
        uploadcover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, UPLOAD_IMAGE);
            }
        });



        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rafflename = raffleName.getText().toString();
                final String raffledescription = raffleDescription.getText().toString();
                final String raffletype = raffletypetext;
                final String numoftickets = noOfTickets.getText().toString();
                final String ticketprice = ticketPrice.getText().toString();
                final String startdate = currentDate;
                final byte[] imageBytes = getImageBytes(BitmapFactory.decodeFile(pictureAddress));
                if (TextUtils.isEmpty(raffleName.getText().toString()) && TextUtils.isEmpty(raffleDescription.getText().toString()) &&
                        TextUtils.isEmpty(noOfTickets.getText().toString()) && TextUtils.isEmpty(ticketPrice.getText().toString()) && imageBytes == null) {
                    Toast.makeText(MainActivity.this, "Please enter the required data", Toast.LENGTH_LONG).show();
                }else{
                    db = openHelper.getWritableDatabase();
                    insertRaffleData(rafflename, raffledescription, raffletype, numoftickets, ticketprice, startdate, imageBytes);
                    Toast.makeText(getApplicationContext(), "RAFFLE CREATED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                }

            }
        });
   }

   public void insertRaffleData(String rafflename, String raffledescription, String raffletype, String numoftickets, String ticketprice, String startdate, byte[] imagebytes){
       ContentValues contentValues = new ContentValues();
       contentValues.put(RaffleDatabaseHelper.RAFFLE_NAME, rafflename);
       contentValues.put(RaffleDatabaseHelper.RAFFLE_DESCRIPTION, raffledescription);
       contentValues.put(RaffleDatabaseHelper.RAFFLE_TYPE, raffletype);
       contentValues.put(RaffleDatabaseHelper.NUMBER_OF_TICKETS, numoftickets);
       contentValues.put(RaffleDatabaseHelper.TICKET_PRICE, ticketprice);
       contentValues.put(RaffleDatabaseHelper.START_DATE, startdate);
       contentValues.put(RaffleDatabaseHelper.RAFFLE_COVER, imagebytes);
       long id = db.insert(RaffleDatabaseHelper.TABLE_RAFFLE, null, contentValues);
   }

   public static byte[] getImageBytes(Bitmap bitmap){
       ByteArrayOutputStream stream = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
       return stream.toByteArray();
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

    public void gotorafflelist(View view) {
        Intent intent = new Intent(this, RaffleManagerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case UPLOAD_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri imageselected = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageselected, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    pictureAddress = cursor.getString(columnIndex);
                    cursor.close();
                    ivcover.setImageBitmap(BitmapFactory.decodeFile(pictureAddress));
                }
        }

    }
}
