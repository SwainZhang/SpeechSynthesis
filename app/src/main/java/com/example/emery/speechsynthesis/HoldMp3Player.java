package com.example.emery.speechsynthesis;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/1/19.
 */
public class HoldMp3Player {
    private static HoldMp3Player mInstance;
    private Context context;
    private SoundPool pool;
    private int holdsount;
    private  HashMap<String, Integer> mCharacterHashMap;


    private HoldMp3Player(Context context) {
        this.context = context;
        init();
    }


    public static HoldMp3Player getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HoldMp3Player(context.getApplicationContext());
        }
        return mInstance;
    }


    private void init() {
        mCharacterHashMap = new HashMap<>();
        pool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mCharacterHashMap.put("please",pool.load(context, R.raw.cn_please, 1));
        mCharacterHashMap.put("0",pool.load(context, R.raw.cn_0, 1));
        mCharacterHashMap.put("take",pool.load(context, R.raw.cn_take_dinner, 1));


    }


    public void play(String contentKey) {




                // pool.play(mCharacterHashMap.get(contentKey), 1.0f, 1.0f, 0, 0, 1.0f);
                int please = pool.play(mCharacterHashMap.get("please"), 1.0f, 1.0f, 0, 0, 1.0f);
                int play = pool.play(mCharacterHashMap.get("0"), 1.0f, 1.0f, 0, 0, 1.0f);
                Log.i("@take", "zero -->"+play);
                int take = pool.play(mCharacterHashMap.get("take"), 1.0f, 1.0f, 0, 0, 1.0f);
                Log.i("@take", "take -->"+take);

            }







}
