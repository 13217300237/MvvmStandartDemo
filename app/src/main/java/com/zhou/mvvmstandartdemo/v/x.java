package com.zhou.mvvmstandartdemo.v;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;

public class x {

    private void test(){
        Animator a = ValueAnimator.ofFloat(0f,1f);
        a.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
    }
}
