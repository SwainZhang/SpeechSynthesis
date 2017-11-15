package com.example.emery.speechsynthesis;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Administrator
 * @time 2017/11/15 16:31
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */

public class PlayMusic implements android.media.MediaPlayer.OnCompletionListener {
    private MediaPlayer player = new MediaPlayer();
    private int playIndex = -1;
    private String[] playList = null;
    private int time = 0;

    public void mediaPlayer(String[] soundUrl, int interval) {
        if (player != null)
            player.reset();
        else
            player = new MediaPlayer();
        playList = soundUrl;
        playIndex = -1;
        time = interval;
        player.setOnCompletionListener(this);
       // playNext();
    }

    private android.media.MediaPlayer.OnPreparedListener preparedListener = new android.media.MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (player != null)
                player.start();
        }
    };
    private void playNext(Context context) {
        playIndex++;
        if (!(playIndex >= 0 && playIndex < playList.length))
            return;

        String soundFileName = playList[playIndex];

        try {
            player.reset();
            //从Asset目录中读取音频
            AssetFileDescriptor afd = context.getAssets().openFd(soundFileName);
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            player.setLooping(false);
            player.prepareAsync();
            player.setOnPreparedListener(preparedListener);

        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               // playNext();
            }
        }, time);
    }


}
