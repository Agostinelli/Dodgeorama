package com.example.manfredi.dodgeorama;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Manfredi on 05/02/2017.
 */

public class Player extends Entity {

    private Bitmap mBitmap;

    private boolean mIsBoosting;
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 20;
    private static final int GRAVITY = 12;
    private Rect mBoundingBox;

    public int getShieldLevel() {
        return mShield;
    }

    public void setShieldLevel(int shield) {
        this.mShield = shield;
    }

    private int mShield = 3; // TODO magic value


    public Player(Context context) {
        super(0, 0);
        mIsBoosting = false;
        Bitmap temp = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.ship1);
        double targetHeight = 72; //TODO hardcoded value
        double scalingRatio = targetHeight/temp.getHeight();
        int newHeight = (int) (temp.getHeight() * scalingRatio);
        int newWidth = (int) (temp.getWidth() * scalingRatio);
        mBitmap = Bitmap.createScaledBitmap(temp, newWidth, newHeight, true);
        temp.recycle();
        mBoundingBox = new Rect(mX, mY, mX+mBitmap.getWidth(), mY+mBitmap.getHeight());
        mShield = 3;
    }
    @Override
    public int getWidth() {return mBitmap.getWidth();}

    @Override
    public int getHeight() {return mBitmap.getHeight();}

    public Bitmap getBitmap() {
        return mBitmap;
    }



    public void startBoost() {
        mIsBoosting = true;
    }

    public void stopBoost() {
        mIsBoosting = false;
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

    public void update() {
        if (mIsBoosting) {
            mSpeed += 2;
        }
        else {
            mSpeed -= 4;
        }
        mSpeed = clamp(mSpeed, MIN_SPEED, MAX_SPEED);

        mY -= mSpeed;
        mY += GRAVITY;
    }

    public void onCollision() {
        mShield--;
        if (mShield < 0) {
            Log.d("Error", "Dead");
        }
    }

    public Rect getBoundingBox() {
        mBoundingBox.set(mX, mY, mX+mBitmap.getWidth(), mY+mBitmap.getHeight());
        return mBoundingBox;
    }

}


















