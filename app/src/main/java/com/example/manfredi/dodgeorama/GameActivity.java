package com.example.manfredi.dodgeorama;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
    private GameView mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGame = new GameView(this);
        setContentView(mGame);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGame.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGame.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGame.destroy();
    }
}
