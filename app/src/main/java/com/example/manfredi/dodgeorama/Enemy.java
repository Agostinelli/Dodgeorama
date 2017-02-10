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


    private Rect mBoundingBox;
    private int mBaseSpeed;
    private int mShipType;
    private final double ACCELERATION = 0.25;
    private final int SHIP_TYPE_1 = 0;
    private final int SHIP_TYPE_2 = 1;
    private final int SHIP_TYPE_3 = 2;



    public Enemy(Context context, int shipType) {
        super();

        this.mShipType = shipType;

        Bitmap temp = BitmapFactory.decodeResource(
                context.getResources(), getShipAsset());
        double targetHeight = context.getResources().getInteger(R.integer.target_height);
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

    private int getShipAsset() {
        int asset = 0;

        switch (mShipType) {
            case SHIP_TYPE_1:
                asset = R.drawable.ship2;
                break;
            case SHIP_TYPE_2:
                asset = R.drawable.ship3;
                break;
            case SHIP_TYPE_3:
                asset = R.drawable.ship4;
                break;
        }
        return asset;
    }

    public void respawn() {
        Random r = new Random();
        mBaseSpeed = r.nextInt(MAX_SPEED);
        int maxOffset = (int) (((double)GameView.STAGE_WIDTH) * ACCELERATION);
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
