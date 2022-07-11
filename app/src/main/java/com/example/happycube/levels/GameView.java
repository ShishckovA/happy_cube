package com.example.happycube.levels;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewTreeObserver;

import androidx.core.content.ContextCompat;

import com.example.happycube.Finish;
import com.example.happycube.LevelActivity;
import com.example.happycube.Platform;
import com.example.happycube.Player;
import com.example.happycube.R;

import java.util.ArrayList;


public abstract class GameView extends SurfaceView implements Runnable {
    Player player;
    final Context context;
    final Thread thread;
    int direction = 0;
    double cameraViewX = 0, cameraViewY = 0;
    boolean goJump = false;
    final double cameraAngleX = 0.2;
    final double cameraAngleY = 0.8;
    ArrayList<Platform> platforms;
    volatile double canvasHeight = 0, canvasWidth = 0;
    final int FPS = 40;
    final SurfaceHolder holder;
    Finish finish;
    boolean running = true;
    boolean finished = false;
    final Drawable finishImage;
    final Drawable finishDoorImage;

    public GameView(Context context) {
        super(context);
        this.context = context;

        finishImage = ContextCompat.getDrawable(this.context, R.drawable.finish);
        finishDoorImage = ContextCompat.getDrawable(this.context, R.drawable.only_door);

        holder = getHolder();

        thread = new Thread(this);
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    setup();
                    thread.start();
                }
            });
        }
    }

    protected void commonSetup() {
        cameraViewX = 0;
        cameraViewY = 0;
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        running = true;
        finished = false;
        direction = 0;
        goJump = false;
    }

    abstract public void setup();

    public void update() {
        if (!finished) {
            if (goJump) {
                player.jump(platforms);
                goJump = false;
            }
            if (direction == 2) {
                player.accelerate(0, 0.002);
            }
            if (direction == 3) {
                player.accelerate(-0.001, 0);
            }
            if (direction == 4) {
                player.accelerate(0.001, 0);
            }
        }
        if (player.getX() - cameraViewX > 0.5 + cameraAngleX / 2) {
            cameraViewX = player.getX() - (0.5 + cameraAngleX / 2);
        } else if (player.getX() - cameraViewX < 0.5 - cameraAngleX / 2) {
            cameraViewX = player.getX() - (0.5 - cameraAngleX / 2);
        }
        if (player.getY() - cameraViewY > 0.5 + cameraAngleY / 2) {
            cameraViewY += 0.01;
        } else if (player.getY() - cameraViewY < 0.5 - cameraAngleY / 2) {
            cameraViewY -= 0.005;
        }
        player.update(platforms);
        if (finish.touched(player) && !finished) {
            finished = true;
            Handler mainHandler = new Handler(Looper.getMainLooper());

            LevelActivity levelActivity = (LevelActivity) this.context;
            Runnable myRunnable = levelActivity::completeLevel;
            mainHandler.post(myRunnable);
        }
    }

    public void stopRunning() {
        running = false;
    }

    void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            if (canvas == null) {
                return;
            }
            Paint paint = new Paint();
            paint.setColor(0xffffffff);
            canvas.drawPaint(paint);

            finish.draw(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);

            player.draw(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);
            finish.drawDoor(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);

            for (Platform platform : platforms) {
                platform.draw(canvas, canvasHeight, canvasWidth, cameraViewX, cameraViewY);
            }
            holder.unlockCanvasAndPost(canvas);

        }
    }

    public void jump() {
        goJump = true;
    }

    public void setDirection(int dir) {
        direction = dir;
    }

    @Override
    public void run() {
        update();
        long time = System.currentTimeMillis(), time2;
        long dt = 1000 / FPS;
        while (running) {
            update();
            draw();
            time2 = System.currentTimeMillis();
            if (time2 - time < dt) {
                try {
                    Thread.sleep(dt - (time2 - time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            time = time2;
        }

    }
}
