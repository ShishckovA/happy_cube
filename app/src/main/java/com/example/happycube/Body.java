package com.example.happycube;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Body {
    final double x;
    final double y;
    final double height;
    final double width;
    final Paint paint;

    public Body(double x, double y, double height, double width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        paint = new Paint();

    }
    public boolean touched(Player p) {
        return Math.abs(p.x - x) <= width / 2 + p.width / 2 && Math.abs(p.y - y) <= height / 2 + p.height;
    }

    public abstract void draw(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY);
}