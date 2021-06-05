# Spring
```java

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.style.TtsSpan;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    TextView _mClockText;
    TextView _mFlag;
    private static Handler _mHandler;
    ProgressBar _mPb;
    int _iPbValue =0;
    // Timer 관련
    Timer timer = new Timer();
    TimerTask _mTimerTask;
    // Random 관련
    Random rnd;
    // Data 관련
    int[] _mDataArray;
    // Receive & Rendering Flag
    boolean _mReceiveFlag = false;
    boolean[] _RenderingFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        InitializeVariable();

        _mClockText = findViewById(R.id.clock);
        _mFlag= findViewById(R.id.flag);
        _mPb = findViewById(R.id.progress1);
        rnd = new Random();

        Initializetimer();

        _mHandler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(_mReceiveFlag == true)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
                    String strTime = sdf.format(cal.getTime());
                    _mClockText.setText(strTime);
                    _mPb.setProgress(_mDataArray[100]);
                    _mFlag.setText(_mReceiveFlag+"");
                    _mReceiveFlag = false;
                }
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }
                    _mHandler.post(runnable) ;
                }
            }
        }
        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();
        timer.schedule(_mTimerTask,60,30);

    }

    private void InitializeVariable() {
        _mDataArray = new int[105];
        _RenderingFlag = new boolean[2];

        _mClockText = findViewById(R.id.clock);
        _mFlag= findViewById(R.id.flag);
        _mPb = findViewById(R.id.progress1);
        rnd = new Random();
        for(int datAarrayindex =0; datAarrayindex<_mDataArray.length;datAarrayindex++)
            _mDataArray[datAarrayindex] =0;
        for(int booleanCount =0; booleanCount<_RenderingFlag.length;booleanCount++)
            _RenderingFlag[booleanCount] = false;
    }

    private void Initializetimer() {

        StopTimer();
        _mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if(_mReceiveFlag == false)
                {
                    for(int i=0;i<_mDataArray.length;i++)
                    {
                        _mDataArray[i] = rnd.nextInt(255);
                        _mReceiveFlag = true;
                    }
                }
            }
        };
    }
    private void StopTimer(){
        if(_mTimerTask !=null){
            _mTimerTask.cancel();
            _mTimerTask = null;
        }
    }

}

```
