package com.example.happycube;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LevelSelection extends AppCompatActivity {
    final int N_LEVELS = 30;
    final int N_LEVELS_AVAILABLE = 2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
        Button btnback = findViewById(R.id.button_back);
        btnback.setOnClickListener(v -> {

            Intent myIntent = new Intent(LevelSelection.this, MenuActivity.class);
            LevelSelection.this.startActivity(myIntent);
        });
        LinearLayout cont = findViewById(R.id.container3);
        SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        boolean opened = true;

        for (int i = 0; i < N_LEVELS; i += 5) {
            LinearLayout lay = new LinearLayout(this);
            lay.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    )
            );
            lay.setGravity(Gravity.CENTER);
            lay.setPadding(0, 0, 0, 50);
            for (int j = 0; j < 5; ++j) {
                TextView btnTag = new TextView(this);
                String levelName = String.valueOf(i + j + 1);
                boolean completed = pref.getBoolean(levelName, false);
                int drawableId;
                if (completed) {
                    drawableId = R.drawable.layout_bg_completed;
                } else if (opened) {
                    drawableId = R.drawable.layout_bg_opened;
                } else {
                    drawableId = R.drawable.layout_bg;
                }
                btnTag.setBackground(ContextCompat.getDrawable(
                        this, drawableId
                ));
                if (!completed) {
                    opened = false;
                }

                final boolean isAvailable = i + j + 1 <= N_LEVELS_AVAILABLE;
                btnTag.setText(levelName);
                btnTag.setTextColor(getResources().getColor(R.color.white));
                btnTag.setGravity(Gravity.CENTER);
                btnTag.setForegroundGravity(Gravity.CENTER);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                lp.setMarginEnd(j == 4 ? 0 : 50);
                btnTag.setLayoutParams(lp);
                btnTag.setTextSize(24);

                btnTag.setPadding(0, 0, 10, 0);
                lay.addView(btnTag);
                btnTag.setOnClickListener(v -> {
                    if (!isAvailable) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Новые уровни уже на подходе!", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    Intent myIntent = new Intent(LevelSelection.this, LevelActivity.class);
                    myIntent.putExtra("level", levelName);
                    LevelSelection.this.startActivity(myIntent);
                });
            }
            cont.addView(lay);
        }
    }
}