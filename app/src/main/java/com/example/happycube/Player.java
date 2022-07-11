package com.example.happycube;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class Player {
    double x, y;
    final double height;
    final double width;
    double speedx = 0, speedy = 0;
    final double maxSpeedX = 0.02;
    final Paint paint;
    final double eps = 1e-5;

    public Player(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        paint = new Paint();

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }


    public void accelerate(double dx, double dy) {
        speedx += dx;
        speedy += dy;
    }

    public void update(ArrayList<Platform> platforms) {

        speedx *= 0.9;
        speedy += 0.003;
        if (Math.abs(speedx) > maxSpeedX) {
            speedx = maxSpeedX * speedx / Math.abs(speedx);
        }
        for (Platform p : platforms) {
            if (p.x - p.width / 2 < x + width / 2 &&
                    x - width / 2 < p.x + p.width / 2 &&
                    y + height / 2 <= p.y - p.height / 2 &&
                    y + speedy + height / 2 > p.y - p.height / 2) {
                speedy = 0;
                y = p.y - height / 2 - p.height / 2 - eps / 2;
            }
            if (p.x - p.width / 2 < x + width / 2 &&
                    x - width / 2 < p.x + p.width / 2 &&
                    y - height / 2 >= p.y + p.height / 2 &&
                    y + speedy - height / 2 < p.y + p.height / 2) {
                speedy = 0;
                y = p.y + height / 2 + p.height / 2;
            }

            // Horizontal collision
            if (p.y - p.height / 2 < y + height / 2 &&
                    y - height / 2 < p.y + p.height / 2 &&
                    x + width / 2 <= p.x - p.width / 2 &&
                    x + speedx + width / 2 > p.x - p.width / 2) {
                speedx = 0;
                x = p.x - width / 2 - p.width / 2;
            }

            if (p.y - p.height / 2 < y + height / 2 &&
                    y - height / 2 < p.y + p.height / 2 &&
                    x - width / 2 >= p.x + p.width / 2 &&
                    x + speedx - width / 2 < p.x + p.width / 2) {
                speedx = 0;
                x = p.x + width / 2 + p.width / 2;
            }
        }
        x += speedx;
        y += speedy;

    }

    public void jump(ArrayList<Platform> platforms) {
        for (Platform p: platforms) {
            if (p.x - p.width / 2 < x + width / 2 &&
                    x - width / 2 < p.x + p.width / 2 &&
                    -eps < (p.y - p.height / 2 - y - height / 2 - eps / 2) &&
                    (p.y - p.height / 2 - y - height / 2 - eps / 2) < eps

            ) {
                speedy = -0.05;
                break;
            }
        }
    }


    public void draw(Canvas canvas, double height, double width, double cameraViewX, double cameraViewY) {
        paint.setColor(0xffff0000);
        canvas.drawRect((float) ((x - this.width / 2 - cameraViewX) * width),
                (float) ((y - this.height / 2 - cameraViewY) * height),
                (float) ((x + this.width / 2 - cameraViewX) * width),
                (float) ((y + this.height / 2 - cameraViewY) * height),
                paint);

    }
}
