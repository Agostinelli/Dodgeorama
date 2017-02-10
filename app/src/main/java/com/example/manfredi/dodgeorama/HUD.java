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

    public void drawHUD(Context context, Paint paint, Canvas canvas, Player player) {
        int textHeight = 45;
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(textHeight);
        canvas.drawText(context.getString(R.string.speed) + player.getSpeed(), 10, textHeight, paint);
        canvas.drawText(context.getString(R.string.shield) + player.getShieldLevel(), STAGE_WIDTH*0.5f, textHeight, paint);

    }

    public void drawGameOverHUD(Context context, Paint paint, Canvas canvas) {
        int textHeight = 80;
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textHeight);
        canvas.drawText(context.getString(R.string.game_over), STAGE_WIDTH*0.5f, textHeight*2, paint);
        canvas.drawText(context.getString(R.string.restart), STAGE_WIDTH*0.5f, STAGE_HEIGHT-(textHeight*2),paint);
    }
}
