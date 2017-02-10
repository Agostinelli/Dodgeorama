package com.example.manfredi.dodgeorama;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Manfredi on 10/02/2017.
 */

public class Star extends Entity {
    private int mColor;
    private int mDistance;
    private int mSize;
    private final int BIG_SIZE_STAR = 4;
    private final int MEDIUM_SIZE_STAR = 3;
    private final int SMALL_SIZE_STAR = 2;
    private final int FAST_SPEED = 30;
    private final int MEDIUM_SPEED = 20;
    private final int SLOW_SPEED = 10;
    private final int SMALL_DISTANCE = 0;
    private final int MEDIUM_DISTANCE = 1;
    private final int BIG_DISTANCE = 2;

    public int getSize() {
        setSize();
        return mSize;
    }

    public void setSize() {
        switch (this.mDistance) {
            case SMALL_DISTANCE:
                this.mSize = BIG_SIZE_STAR;
                this.mSpeed = SLOW_SPEED;
                break;
            case MEDIUM_DISTANCE:
                this.mSize = MEDIUM_SIZE_STAR;
                this.mSpeed = MEDIUM_SPEED;
                break;
            case BIG_DISTANCE:
                this.mSize = SMALL_SIZE_STAR;
                this.mSpeed = FAST_SPEED;
                break;
        }
    }

    public Star(int mX, int mY, int mColor, int mDistance) {
        super(mX, mY);
        this.mColor = mColor;
        this.mDistance = mDistance;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public int getDistance() {
        return mDistance;
    }

    public void setDistance(int mDistance) {
        this.mDistance = mDistance;
    }

    public void render(Canvas canvas, Paint paint) {
        paint.setColor(this.getColor());
        canvas.drawCircle(this.getX(), this.getY(), this.getSize(), paint);
    }

}
