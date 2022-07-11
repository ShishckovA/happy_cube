package com.example.happycube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    final int N_LEVELS = 2;
    Button start;
    Button resetProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        start = findViewById(R.id.menu_start_button);
        start.setOnClickListener(v -> {
            Intent myIntent = new Intent(MenuActivity.this, LevelSelection.class);
            MenuActivity.this.startActivity(myIntent);
        });
        resetProgress = findViewById(R.id.reset_progress);
        resetProgress.setOnClickListener(v -> {

            SharedPreferences.Editor ed = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
            for (int i = 1; i <= N_LEVELS; ++i) {
                String cur = String.valueOf(i);
                ed.putBoolean(cur, false);
            }
            ed.apply();

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Прогресс сброшен", Toast.LENGTH_SHORT);
            toast.show();
        });
    }
}