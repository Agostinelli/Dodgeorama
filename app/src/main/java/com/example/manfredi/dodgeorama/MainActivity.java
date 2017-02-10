package com.example.manfredi.dodgeorama;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(this);

        final ImageView mImageView;
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageResource(R.drawable.background);

        SharedPreferences prefs = getSharedPreferences(GameView.PREF, Context.MODE_PRIVATE);
        int longestDistance = prefs.getInt(GameView.LONGEST_DISTANCE, 0);



        final TextView highScore = (TextView) findViewById(R.id.highscoreText);
        highScore.setText(getString(R.string.bestScore) + longestDistance);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
        finish();
    }
}
