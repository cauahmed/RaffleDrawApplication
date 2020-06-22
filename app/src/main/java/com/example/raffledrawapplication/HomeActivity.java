package com.example.raffledrawapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {
    private Object Menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        //setSupportActionBar(myToolbar);
    }



    public void gotorafflelist(View view) {
        Intent intent = new Intent(this, RaffleManagerActivity.class);
        startActivity(intent);
    }

    public void gotocreator(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
