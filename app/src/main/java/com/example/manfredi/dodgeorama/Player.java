package com.example.manfredi.dodgeorama;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by Manfredi on 05/02/2017.
 */

public class Player extends Entity {


    private boolean mIsBoosting;
    private static final int GRAVITY = 12;
    private Rect mBoundingBox;
    private int mShield;
    private final int FORWARD = 2;
    private final int BACKWARD = 4;



    public int getShieldLevel() {
        return mShield;
    }

    public void setShieldLevel(int shield) {
        this.mShield = shield;
    }



    public Player(Context context) {
        super(0, 0);
        mIsBoosting = false;
        Bitmap temp = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.ship1);
        double targetHeight = context.getResources().getInteger(R.integer.target_height);
        double scalingRatio = targetHeight/temp.getHeight();
        int newHeight = (int) (temp.getHeight() * scalingRatio);
        int newWidth = (int) (temp.getWidth() * scalingRatio);
        mBitmap = Bitmap.createScaledBitmap(temp, newWidth, newHeight, true);
        temp.recycle();
        mBoundingBox = new Rect(mX, mY, mX+mBitmap.getWidth(), mY+mBitmap.getHeight());
        mShield = context.getResources().getInteger(R.integer.shield);
    }
    @Override
    public int getWidth() {return mBitmap.getWidth();}

    @Override
    public int getHeight() {return mBitmap.getHeight();}

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
            mSpeed += FORWARD;
        }
        else {
            mSpeed -= BACKWARD;
        }
        mSpeed = clamp(mSpeed, MIN_SPEED, MAX_SPEED);

        mY -= mSpeed;
        mY += GRAVITY;
    }

    public void onCollision() {
        mShield--;
        //if (mShield < 0) {}
    }

    public Rect getBoundingBox() {
        mBoundingBox.set(mX, mY, mX+mBitmap.getWidth(), mY+mBitmap.getHeight());
        return mBoundingBox;
    }

}


















