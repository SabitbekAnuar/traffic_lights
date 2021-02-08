package com.anuar.traffic_lights;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout b_1, b_2, b_3;
    private Button button;
    private boolean start_stop = false;
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_1 = findViewById(R.id.bulb_1);
        b_2 = findViewById(R.id.bulb_2);
        b_3 = findViewById(R.id.bulb_3);
        button = findViewById(R.id.button1);
        button.setText(R.string.button_start);
    }

    public void onClickStart(View view) {
        if (!start_stop) {
            button.setText(R.string.button_stop);
            start_stop = true;
            b_2.clearAnimation();
            new Thread(() -> {
                while (start_stop) {
                    counter++;
                    runOnUiThread(() -> {
                        switch (counter) {
                            case 30:
                                b_1.setBackgroundColor(getResources().getColor(R.color.red));
                                b_2.setBackgroundColor(getResources().getColor(R.color.grey));
                                b_3.setBackgroundColor(getResources().getColor(R.color.grey));
                                counter = 30;
                                break;
                            case 50:
                                b_2.setBackgroundColor(getResources().getColor(R.color.yellow));
                                b_1.setBackgroundColor(getResources().getColor(R.color.grey));
                                b_3.setBackgroundColor(getResources().getColor(R.color.grey));
                                counter = 50;
                                break;
                            case 51:
                                b_3.setBackgroundColor(getResources().getColor(R.color.green));
                                b_1.setBackgroundColor(getResources().getColor(R.color.grey));
                                b_2.setBackgroundColor(getResources().getColor(R.color.grey));
                                break;
                            case 76:
                                init();
                                counter = 27;
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            start_stop = false;
            button.setText(R.string.button_start);
            b_2.setBackgroundColor(getResources().getColor(R.color.yellow));
            b_1.setBackgroundColor(getResources().getColor(R.color.grey));
            b_3.setBackgroundColor(getResources().getColor(R.color.grey));
            init();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        start_stop = false;
        b_2.clearAnimation();
        b_3.clearAnimation();
    }

    private void init() {
        Animation anim_yellow = new AlphaAnimation(0.0f, 1.0f);
        Animation anim_green = new AlphaAnimation(0.0f, 1.0f);
        if (!start_stop) {
            anim_yellow.setDuration(1000); //You can manage the time of the blink with this parameter
            anim_yellow.setRepeatMode(Animation.RESTART);
            anim_yellow.setRepeatCount(Animation.INFINITE);
            b_2.startAnimation(anim_yellow);
        }
        else {
            anim_green.setDuration(1000);
            anim_green.setRepeatMode(Animation.RESTART);
            anim_green.setRepeatCount(2);
            b_3.startAnimation(anim_green);
        }
    }
}