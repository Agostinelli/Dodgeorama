package com.example.manfredi.dodgeorama;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Manfredi on 05/02/2017.
 */

public class GameView extends SurfaceView implements Runnable {

    private Context mContext;
    private Thread mGameThread;
    volatile boolean mIsRunning;

    private Paint mPaint;
    private Canvas mCanvas;
    private SurfaceHolder mHolder;
    private Player mPlayer;
    private boolean mGameOver;
    public static final String TAG = "DODGEORAMA";
    public static final String PREF = "com.example.manfredi.dodgeorama";
    public static final String LONGEST_DISTANCE = "longest_distance";

    private final int DELAY = 1000/60;
    public static final int STAGE_HEIGHT = 720;
    private final int STAR_COUNT = 40;
    private final int SHIELD_LEVEL = 3;
    public static final int STAGE_WIDTH = 1280;
    private ArrayList<Star> mStars;
    private ArrayList<Enemy> mEnemies;
    private static final int ENEMY_COUNT = 4;
    private int mDistanceTraveled = 0;
    private int mLongestDistanceTraveled = 0;
    private final int MAX_COLOR_COUNT = 5;
    private final int YELLOW = 0;
    private final int WHITE = 1;
    private final int CYAN = 2;
    private final int RED = 3;
    private final int GREEN = 4;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private Jukebox mJukebox;

    HUD mHud = new HUD();


    public GameView(Context context) {
        super(context);
        mContext = context;
        mIsRunning = false;
        mPaint = new Paint();
        mHolder = getHolder();
        mHolder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT);
        mPrefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mJukebox = new Jukebox(context);
        startGame();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP: // finger lifted
                mPlayer.stopBoost();
                if(mGameOver) {
                    startGame();
                }
                break;
            case MotionEvent.ACTION_DOWN: // finger pressed
                mPlayer.startBoost();
                break;
        }
        return true;
    }

    private void startGame() {
        mStars = new ArrayList<Star>();
        mEnemies = new ArrayList<Enemy>();
        Random rand = new Random();
        mPlayer = new Player(mContext);
        for(int i = 0; i < STAR_COUNT; i++) {
            int x = rand.nextInt(STAGE_WIDTH);
            int y =  rand.nextInt(STAGE_HEIGHT);
            int distance = rand.nextInt(3);
            mStars.add(new Star(x, y, randomColor(), distance));
        }
        for(int i = 0; i < ENEMY_COUNT; i++) {
            int shipType = rand.nextInt(3);
            mEnemies.add(new Enemy(mContext, shipType));
        }
        mGameOver = false;
        mPlayer.setShieldLevel(SHIELD_LEVEL);

        mDistanceTraveled = 0;
        mLongestDistanceTraveled = mPrefs.getInt(LONGEST_DISTANCE, 0);
        mJukebox.play(Jukebox.START);
    }

    private int randomColor() {
        Random rand = new Random();
        int n = rand.nextInt(MAX_COLOR_COUNT);
        int color = 0;
        switch (n) {
            case YELLOW:
                color = Color.YELLOW;
                break;
            case WHITE:
                color = Color.WHITE;
                break;
            case CYAN:
                color = Color.CYAN;
                break;
            case RED:
                color = Color.RED;
                break;
            case GREEN:
                color = Color.GREEN;
        }
        return color;
    }

    // core game loop
    @Override
    public void run() {
        while(mIsRunning) {
            // input
            update();
            render();
            checkGameOver();
            rateLimit();
        }
    }

    private void checkGameOver() {
        if(mGameOver) {
            return;
        }

        if(mPlayer.getShieldLevel() <= 0) {
            mGameOver = true;
            if(mDistanceTraveled > mLongestDistanceTraveled) {
                mEditor.putInt(LONGEST_DISTANCE, mDistanceTraveled);
                mEditor.apply();
            }
        }
    }

    private void worldWrap(Entity e) {
        final int minX = -e.getWidth();
        final int minY = -e.getHeight();
        final int maxX = STAGE_WIDTH;
        final int maxY = STAGE_HEIGHT;

        if(e.getX() < minX) {
            e.setX(maxX);
        }
        else if (e.getX() > maxX) {
            e.setX(minX);
        }
        if(e.getY() < minY) {
            e.setY(maxY);
        }
        else if (e.getY() > maxY) {
            e.setY(minY);
        }
    }


    private void rateLimit() {
        try {
            mGameThread.sleep(DELAY);
        }
        catch (InterruptedException e) {

        }
    }

    private void update() {
        mPlayer.update();
        worldWrap(mPlayer);

        for(Star star : mStars) {
            star.setSpeed(-mPlayer.getSpeed()-star.getSpeed());
            star.update();
            worldWrap(star);
        }

        for(Enemy enemy : mEnemies) {
            enemy.setSpeed(-mPlayer.getSpeed());
            enemy.update();
            worldWrap(enemy);
            if(isColliding(mPlayer, enemy)) {
                enemy.onCollision();
                mPlayer.onCollision();
                mJukebox.play(Jukebox.BUMP);
            }
        }
        if (!mGameOver) {
            mDistanceTraveled += mPlayer.getSpeed();
        }

    }

    private boolean isColliding(Player a, Enemy b) {
        return a.getBoundingBox().intersect(b.getBoundingBox());
    }

    private void render() {
        if(!mHolder.getSurface().isValid()) {
            return;
        }
        mCanvas = mHolder.lockCanvas();

        if (mCanvas == null) {
            return;
        }
        mCanvas.drawColor(Color.BLACK);

        for(Star star : mStars) {
            star.render(mCanvas, mPaint);
        }

        mPaint.setColor(Color.WHITE);

        for(Enemy enemy : mEnemies) {
            enemy.render(mCanvas, mPaint);
        }
        mPlayer.render(mCanvas, mPaint);

        if(!mGameOver) {
            mHud.drawHUD(mContext, mPaint, mCanvas, mPlayer);
        }
        else {
            mHud.drawGameOverHUD(mContext, mPaint, mCanvas);
        }

        mHolder.unlockCanvasAndPost(mCanvas);
    }

    public void pause() {
        mIsRunning = false;
        try {
            mGameThread.join();
        }
        catch (InterruptedException e) {

        }
    }

    public void resume() {
        mIsRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }


    public void destroy() {
        mJukebox.destroy();
        mJukebox = null;

        mGameThread = null;
        mPaint = null;
        mHolder = null;
        mContext = null;
        mPlayer = null;
        mPrefs = null;
        mEditor = null;
        mHud = null;

        mStars.clear();
        mEnemies.clear();
    }
}
