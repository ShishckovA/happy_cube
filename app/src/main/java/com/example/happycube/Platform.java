package com.example.happycube;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Platform {
    final double x;
    final double y;
    final double width;
    final double height;
    final Paint paint;
    public Platform(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        paint = new Paint();
    }

    public void draw(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY) {

        canvas.drawRect(
                (float) ((x - this.width / 2 - cameraViewX) * width),
                (float) ((y - this.height / 2 - cameraViewY) * height),
                (float) ((x + this.width / 2 - cameraViewX) * width),
                (float) ((y + this.height / 2 - cameraViewY) * height), paint);

    }
}
