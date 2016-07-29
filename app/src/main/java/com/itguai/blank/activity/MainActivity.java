package com.itguai.blank.activity;

import android.os.Handler;
import android.util.Log;
import com.itguai.blank.R;
import com.itguai.blank.base.BaseActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    Handler hanadler= new Handler();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("log","onPause*");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("log","onResume*");
    }

    private void doleaks() {
        hanadler.postDelayed (new Runnable() {
            @Override
            public void run() {
                Log.d("log","-------------------------554157545--------------**++***");
                for (int i =  0 ;i<90000;i++){
                    leak();
                }
                doleaks();
            }
        },10);

    }

    private ArrayList<Cat> cats = new ArrayList<>();

    class Cat {

    }

    class Box {
        Cat hiddenCat;
    }

    static class Docker {
        static Box container;
    }

    private void leak() {

        // ...
        Box box = new Box();
        Cat schrodingerCat = new Cat();
        box.hiddenCat = schrodingerCat;
        Docker.container = box;
        cats.add(schrodingerCat);


    }
}
