package com.example.emery.speechsynthesis;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

/**
 * @author Administrator
 * @time 2017/11/10 14:57
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */

public class VoicePlayerUtils implements MediaPlayer.OnCompletionListener {

    private volatile static VoicePlayerUtils instance;
    private MediaPlayer myMediaPlayer;

    private VoicePlayerUtils(Context context) {
        myMediaPlayer = new MediaPlayer();
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前音乐音量
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获取最大声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); //
        // 设置为最大声音，可通过SeekBar更改音量大小
        myMediaPlayer.setOnCompletionListener(this);
    }

    public static VoicePlayerUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (VoicePlayerUtils.class) {
                if (instance == null) {
                    instance = new VoicePlayerUtils(context);
                }
            }
        }
        return instance;
    }


    public void playMP3(Vector<AssetFileDescriptor> inputStreamVector) throws IOException {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(absolutePath,"temp.mp3");
        if(file.exists()){
            file.delete();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath());
        for (AssetFileDescriptor fileDescriptor : inputStreamVector) {
            try {
                FileInputStream inputStream = fileDescriptor.createInputStream();
                byte[] buffer = new byte[1024 * 1024];
                int len = -1;
                while ((len = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        myMediaPlayer.reset();
        myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        myMediaPlayer.setDataSource(file.getAbsolutePath());
        myMediaPlayer.prepare();
        myMediaPlayer.start();
    }

    public void playMP3(AssetFileDescriptor fileDescriptor) {
        try {
            MediaPlayer myMediaPlayer = new MediaPlayer();
            myMediaPlayer.reset();
            myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            myMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor
                    .getStartOffset(), fileDescriptor.getLength());
            myMediaPlayer.prepare();
            myMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
