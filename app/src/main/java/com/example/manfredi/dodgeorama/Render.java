package com.example.manfredi.dodgeorama;

import android.graphics.Color;
import android.graphics.Paint;

import static com.example.manfredi.dodgeorama.GameView.STAGE_WIDTH;
import static java.security.AccessController.getContext;

/**
 * Created by Manfredi on 10/02/2017.
 */

public class Render {

    private void drawHUD() {
        int textHeight = 45;
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(textHeight);
        mCanvas.drawText(getContext().getString(R.string.speed) + mPlayer.getSpeed(), 10, textHeight, mPaint);
        mCanvas.drawText(getContext().getString(R.string.shield) + mPlayer.getShieldLevel(), STAGE_WIDTH*0.5f, textHeight, mPaint);

    }
}
