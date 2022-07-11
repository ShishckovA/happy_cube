package com.example.happycube.levels;

import android.content.Context;

import com.example.happycube.Finish;
import com.example.happycube.Platform;
import com.example.happycube.Player;

import java.util.ArrayList;

public class Level1GameView extends GameView {
    public Level1GameView(Context context) {
        super(context);
    }

    @Override
    public void setup() {
        commonSetup();

        double objectSizeX = 100 / canvasWidth, objectSizeY = 100 / canvasHeight,
                platformSizeX = 10 / canvasWidth, platformSizeY = 10 / canvasHeight;
        player = new Player((float) 0, (float) 0.5,
                objectSizeX, objectSizeY);

        platforms = new ArrayList<>();
        platforms.add(new Platform(0.2, 0.6, 0.1, platformSizeY));

        for (int i = 0; i < 3; ++i) {
            platforms.add(new Platform(0.4 + i, 0.4, 0.1, platformSizeY));
            platforms.add(new Platform(0.7 + i, 0.4, 0.1, platformSizeY));
            platforms.add(new Platform(0.85 + i, 0.2, 0.1, platformSizeY));
            platforms.add(new Platform(1.1 + i, 0.2, 0.1, platformSizeY));
        }

        platforms.add(new Platform(1. / 2, 0.9, 15, platformSizeY));
        platforms.add(new Platform(-1, 0, platformSizeX, 1.8));
        platforms.add(new Platform(4, 0, platformSizeX, 1.8));
        finish = Finish.from_bottom_part(3.1, 0.2, canvasWidth, canvasHeight, finishImage, finishDoorImage);

    }
}
