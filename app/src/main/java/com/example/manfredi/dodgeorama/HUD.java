package com.example.manfredi.dodgeorama;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static com.example.manfredi.dodgeorama.GameView.STAGE_HEIGHT;
import static com.example.manfredi.dodgeorama.GameView.STAGE_WIDTH;

/**
 * Created by Manfredi on 10/02/2017.
 */

public class HUD {
    private final int HUD_TEXT_HEIGHT = 45;
    private final int GAME_OVER_HD_TEXT_HEIGHT = 80;
    private final float HALF_SCREEN = 0.5f;
    private final int DOUBLE = 2;
    private final int TEXT_X_POSITION = 10;


    public void drawHUD(Context context, Paint paint, Canvas canvas, Player player) {
        int textHeight = HUD_TEXT_HEIGHT;
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(textHeight);
        canvas.drawText(context.getString(R.string.speed) + player.getSpeed(), TEXT_X_POSITION, textHeight, paint);
        canvas.drawText(context.getString(R.string.shield) + player.getShieldLevel(), STAGE_WIDTH*HALF_SCREEN, textHeight, paint);

    }

    public void drawGameOverHUD(Context context, Paint paint, Canvas canvas) {
        int textHeight = GAME_OVER_HD_TEXT_HEIGHT;
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textHeight);
        canvas.drawText(context.getString(R.string.game_over), STAGE_WIDTH*HALF_SCREEN, textHeight*DOUBLE, paint);
        canvas.drawText(context.getString(R.string.restart), STAGE_WIDTH*HALF_SCREEN, STAGE_HEIGHT-(textHeight*DOUBLE),paint);
    }
}
