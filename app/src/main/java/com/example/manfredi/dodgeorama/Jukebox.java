package com.example.manfredi.dodgeorama;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Manfredi on 10/02/2017.
 */

public class Jukebox {

    public static int START = 0;
    private SoundPool mSoundPoll;
    private final int MAX_STREAMS = 5;
    public static int BUMP;
    private final float MAX_VOLUME = 1.0f;
    private final int MAX_PRIORITY = 1;
    private final int NO_LOOP = 0;
    private final float NORMAL_PLAYBACK = 1.0f;


    public Jukebox(Context context) {
        mSoundPoll = createSoundPool();
        loadSounds(context);
    }

    public void destroy() {
        mSoundPoll.release();
        mSoundPoll = null;
    }

    public void play(final int soundID) {
        float leftVolume = MAX_VOLUME;
        float rightVoulme = MAX_VOLUME;
        int priority = MAX_PRIORITY;
        int loop = NO_LOOP;
        float rate = NORMAL_PLAYBACK;
        if(soundID > 0) {
            mSoundPoll.play(soundID, leftVolume, rightVoulme, priority, loop, rate);
        }
    }

    private void loadSounds(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd(context.getString(R.string.bumpSound));
            BUMP = mSoundPoll.load(descriptor, 0);

            descriptor = assetManager.openFd(context.getString(R.string.startSound));
            START = mSoundPoll.load(descriptor, 0);
        }
        catch (IOException e) {
            Log.e(GameView.TAG, context.getString(R.string.sounderror) + e.toString());
        }
    }

    private SoundPool createSoundPool() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return createNewSoundPool();
        }

        return createOldSoundPool();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool createNewSoundPool() {
        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        return new SoundPool.Builder()
                .setAudioAttributes(attr)
                .setMaxStreams(MAX_STREAMS)
                .build();
    }

    @SuppressWarnings("deprecation")
    private SoundPool createOldSoundPool() {
        return new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }
}
