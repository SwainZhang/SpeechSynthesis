package com.example.emery.speechsynthesis;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.ed_number);

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void cnplay(View view) throws IOException {
        /*new Thread(new Runnable() {
            @Override
            public void run() {

                HoldMp3Player.getInstance(MainActivity.this).play("0");
             //   HoldMp3Player.getInstance(MainActivity.this).play("take");
            }
        }).start();*/

      // pcmplay(view);
        pcmplay(view);
    }
    public void enplay(View view){
        enPlay(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pcmplay(View view) {
        try {
            String number = mEditText.getText().toString().trim();




            Vector<String> playPath = new Vector<>();
            String please = "cn_please.mp3";
            playPath.add(please);

            int length = number.length();
            for (int i = 0; i < length; i++) {
                char c = number.charAt(i);
                switch (length) {

                    case 2://22

                        if (i == 1) {
                            castNumber2Path(playPath, 10);
                        }

                        break;
                    case 3://222
                        if (i == 1) {
                            castNumber2Path(playPath, 100);
                        } else if (i == 2) {
                            if (!Objects.equals(number.charAt(1), '0')) {
                                castNumber2Path(playPath, 10);
                            } else {
                                if (!Objects.equals(number.charAt(2), '0')) {
                                    castNumber2Path(playPath, 0);
                                }
                            }

                        }

                        break;
                    case 4://2222
                        if (i == 1) {
                            castNumber2Path(playPath, 1000);
                        } else if (i == 2) {
                            if (!Objects.equals(number.charAt(1), '0')) {
                                castNumber2Path(playPath, 100);
                            } else {
                                if (!Objects.equals(number.charAt(2), '0')) {
                                    castNumber2Path(playPath, 0);
                                }
                            }
                        } else if (i == 3) {
                            if (!Objects.equals(number.charAt(2), '0')) {
                                castNumber2Path(playPath, 10);
                            } else {
                                if (!Objects.equals(number.charAt(3), '0')) {
                                    castNumber2Path(playPath, 0);
                                }
                            }
                        }

                        break;
                    default:

                        break;
                }

                if (!Objects.equals(c, '0'))
                    castNumber2Path(playPath, c);

            }

            String take = "cn_take_dinner.mp3";
            playPath.add(take);


            VoicePlayerUtils.getInstance(this).playMP3(castPath2Stream(playPath));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private Vector<AssetFileDescriptor> castPath2Stream(Vector<String> playPath) throws IOException {
        Vector<AssetFileDescriptor> descriptors=new Vector<>();
        AssetManager assetManager=getAssets();
        for (String s : playPath) {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(s);
            descriptors.add(assetFileDescriptor);
        }

        return descriptors;
    }

    private void castNumber2Path(List<String> pathList, char number) {
        try {
            String numPath = "cn_";
            StringBuilder numBuilder = new StringBuilder();
            numBuilder.append(numPath);
            numBuilder.append(number);
            numBuilder.append(".mp3");
            pathList.add(numBuilder.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void castNumber2Path(List<String> pathList, int number) {
        try {
            String numPath = "cn_";
            StringBuilder numBuilder = new StringBuilder();
            numBuilder.append(numPath);
            numBuilder.append(number);
            numBuilder.append(".mp3");
            pathList.add(numBuilder.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void enPlay(View view) {
        String prefix = "A101BFF";
        String subfix = "15DG";

        Vector<String> playPath = new Vector<>();
        String table = "en_table.mp3";
        playPath.add(table);

        for (int i = 0; i < prefix.length(); i++) {
            char charAt = prefix.charAt(i);
            if (Character.isDigit(charAt)) {

                String numberPath = "en_" + charAt + ".mp3";
                playPath.add(numberPath);

            } else {

                String numberPath = Character.toUpperCase(charAt) + ".mp3";
                playPath.add(numberPath);

            }
        }

        /*String number = "en_number" + ".mp3";
        playPath.add(number);*/

        for (int i = 0; i < subfix.length(); i++) {
            char charAt = subfix.charAt(i);
            if (Character.isDigit(charAt)) {

                String numberPath = "en_" + charAt + ".mp3";
                playPath.add(numberPath);

            } else {

                String numberPath = Character.toUpperCase(charAt) + ".mp3";
                playPath.add(numberPath);

            }
        }

        String take = "en_take_dinner" + ".mp3";
        playPath.add(take);


        try {
            VoicePlayerUtils.getInstance(this).playMP3(castPath2Stream(playPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
