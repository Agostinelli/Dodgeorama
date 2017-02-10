package com.example.manfredi.dodgeorama;

/**
 * Created by Manfredi on 10/02/2017.
 */

public class Star extends Entity {
    private int mColor;
    private int mDistance;
    private int mSize;

    public int getSize() {
        setSize();
        return mSize;
    }

    public void setSize() {
        switch (this.mDistance) {
            case 0:
                this.mSize = 4;
                this.mSpeed = 10;
                break;
            case 1:
                this.mSize = 3;
                this.mSpeed = 20;
                break;
            case 2:
                this.mSize = 2;
                this.mSpeed = 30;
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

}
