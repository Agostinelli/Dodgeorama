package com.example.manfredi.dodgeorama;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Manfredi on 08/02/2017.
 */

public class Enemy extends Entity {

    private Bitmap mBitmap;
    private Rect mBoundingBox;
    private int mBaseSpeed;
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 20;

    public Enemy(Context context) {
        super();

        Bitmap temp = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.ship2);
        double targetHeight = 72; // TODO: move value out of code
        double scalingRatio = targetHeight/temp.getHeight();
        int newHeight = (int) (temp.getHeight() * scalingRatio);
        int newWidth = (int) (temp.getWidth() * scalingRatio);
        mBitmap = Bitmap.createScaledBitmap(temp, newWidth, newHeight, true);
        temp.recycle();
        respawn();
    }
    @Override
    public int getWidth() {return mBitmap.getWidth();}

    @Override
    public int getHeight() {return mBitmap.getHeight();}

    public Bitmap getBitmap() {
        return mBitmap;
    }



    public static int clamp(int val, final int min, final int max) {
        if (val < min) {
            val = min;
        }
        else if (val > max) {
            val = max;
        }
        return val;
    }

    public void respawn() {
        Random r = new Random();
        mBaseSpeed = r.nextInt(MAX_SPEED);
        int maxOffset = (int) (((double)GameView.STAGE_WIDTH) * 0.25);
        mX = GameView.STAGE_WIDTH + r.nextInt(maxOffset);
        mY = r.nextInt(GameView.STAGE_HEIGHT);
        mBoundingBox = new Rect(mX, mY, mX+mBitmap.getWidth(), mY+mBitmap.getHeight());

    }

    public void update() {
        super.update();
        mX -= mBaseSpeed;
    }

    public Rect getBoundingBox() {
        mBoundingBox.set(mX, mY, mX+mBitmap.getWidth(), mY+mBitmap.getHeight());
        return mBoundingBox;
    }

    public void onCollision() {
        respawn();
    }
}
