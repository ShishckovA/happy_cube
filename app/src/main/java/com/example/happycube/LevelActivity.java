package com.example.happycube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.happycube.levels.GameView;
import com.example.happycube.levels.Level0GameView;
import com.example.happycube.levels.Level1GameView;

import java.util.Set;

public class LevelActivity extends AppCompatActivity implements View.OnTouchListener {
    private GameView level;
    String levelName;

    DirectionButton buttonUp;
    DirectionButton buttonLeft;
    DirectionButton buttonRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        levelName = intent.getStringExtra("level");
        level = levelName.equals("1") ? new Level0GameView(this) : new Level1GameView(this);


        super.onCreate(savedInstanceState);

        startLevel();
    }

    private void startLevel() {
        setContentView(level);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View secondLayerView = LayoutInflater.from(this).inflate(R.layout.activity_level, null, false);
        addContentView(secondLayerView, lp);

        buttonUp = findViewById(R.id.buttonUp);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);

        buttonUp.setOnTouchListener(this);
        buttonLeft.setOnTouchListener(this);
        buttonRight.setOnTouchListener(this);
        TextView t = findViewById(R.id.level_name);
        t.setText("Уровень " + levelName);

    }

    public void completeLevel() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        System.out.println("=============");

        for (Thread t : threadSet) {
            System.out.printf("Running thr: %d\n", t.getId());
        }
        System.out.println("=============");
        System.out.printf("Level %s completed\n", levelName);
        SharedPreferences.Editor ed = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
        ed.putBoolean(levelName, true);
        ed.apply();

        View.OnTouchListener none = (v, event) -> false;
        buttonUp.setOnTouchListener(none);
        buttonLeft.setOnTouchListener(none);
        buttonRight.setOnTouchListener(none);

        ViewGroup.LayoutParams lp2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View secondLayerView2 = LayoutInflater.from(this).inflate(R.layout.activity_level_complete, null, false);
        addContentView(secondLayerView2, lp2);
        Button backHome = findViewById(R.id.button_back_home);
        backHome.setOnClickListener(v -> {
            level.stopRunning();
            Intent myIntent = new Intent(LevelActivity.this, LevelSelection.class);
            LevelActivity.this.startActivity(myIntent);

        });
        Button restart = findViewById(R.id.button_restart_level);
        restart.setOnClickListener(v -> {
            LevelActivity.this.level.setup();
            startLevel();
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (v == buttonUp) {
                System.out.println("up pressed");
                level.jump();
            }
            if (v == buttonLeft) {
                level.setDirection(3);
                System.out.println("left pressed");

            }
            if (v == buttonRight) {
                System.out.println("right pressed");

                level.setDirection(4);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP && v != buttonUp) {
            level.setDirection(0);
            System.out.println("unpressed");

            return v.performClick();
        }
        return true;
    }

}