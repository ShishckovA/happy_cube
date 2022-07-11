package com.example.happycube.levels;

import android.content.Context;

import com.example.happycube.Finish;
import com.example.happycube.Platform;
import com.example.happycube.Player;

import java.util.ArrayList;

public class Level0GameView extends GameView {
    public Level0GameView(Context context) {
        super(context);
    }

    @Override
    public void setup() {
        commonSetup();
        double height = 0.1;
        double width = height * canvasHeight / canvasWidth;
        double platformSizeX = 10 / canvasWidth, platformSizeY = 10 / canvasHeight;
        player = new Player((float) 0, (float) 0.5,
                width, height);

        platforms = new ArrayList<>();
        platforms.add(new Platform(0.2, 0.6, 0.1, platformSizeY));

        platforms.add(new Platform(0.4, 0.4, 0.1, platformSizeY));
        platforms.add(new Platform(0.7, 0.4, 0.1, platformSizeY));
        platforms.add(new Platform(0.85, 0.2, 0.1, platformSizeY));
        platforms.add(new Platform(1.15, 0.2, 0.1, platformSizeY));

        finish = Finish.from_bottom_part(1.15, 0.2, canvasWidth, canvasHeight, finishImage, finishDoorImage);

        platforms.add(new Platform(1. / 2, 0.9, 15, platformSizeY));
        platforms.add(new Platform(-0.3, 0, platformSizeX, 1.8));
        platforms.add(new Platform(1.3, 0, platformSizeX, 1.8));


    }
}
