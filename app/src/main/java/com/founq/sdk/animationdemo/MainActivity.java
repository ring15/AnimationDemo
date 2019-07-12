package com.founq.sdk.animationdemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    private FloatingActionButton mFAB;
    private boolean isOpen = false;
    private FrameLayout[] mFrameLayouts = new FrameLayout[4];
    private ImageView[] mImageViews = new ImageView[4];
    private float halfWidth;
    private float halfHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFAB = findViewById(R.id.fab);
        mFrameLayout = findViewById(R.id.layout_frame);

        mImageViews[0] = new ImageView(this);
        mImageViews[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_action_chat_light));
        mImageViews[1] = new ImageView(this);
        mImageViews[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera_light));
        mImageViews[2] = new ImageView(this);
        mImageViews[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video_light));
        mImageViews[3] = new ImageView(this);
        mImageViews[3].setImageDrawable(getResources().getDrawable(R.drawable.ic_action_place_light));


        mFrameLayouts[0] = new FrameLayout(this);
        mFrameLayouts[1] = new FrameLayout(this);
        mFrameLayouts[2] = new FrameLayout(this);
        mFrameLayouts[3] = new FrameLayout(this);

        Drawable backgroundDrawable = getResources().getDrawable(R.drawable.button_sub_action_selector);
        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(60, 60, Gravity.CENTER);
        final int margin2 = getResources().getDimensionPixelSize(R.dimen.sub_action_button_content_margin);
        params.setMargins(margin2, margin2, margin2, margin2);

        for (int i = 0; i < mFrameLayouts.length; i++) {
            mFrameLayouts[i].setBackground(backgroundDrawable);
            mFrameLayouts[i].addView(mImageViews[i], params);
        }


        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double angle = 90 / (mFrameLayouts.length - 1);
                if (!isOpen) {
                    for (int i = 0; i < mFrameLayouts.length; i++) {
                        double m = (angle * i) * Math.PI * 2 / 360;
                        float x = (float) (180 * Math.sin(m));
                        float y = (float) (180 * Math.cos(m));
                        animationOpen(mFrameLayouts[i], x, y);
                    }
                    isOpen = true;
                } else {
                    for (int i = 0; i < mFrameLayouts.length; i++) {
                        double m = (angle * i) * Math.PI * 2 / 360;
                        float x = (float) (180 * Math.sin(m));
                        float y = (float) (180 * Math.cos(m));
                        animationClose(mFrameLayouts[i], x, y);
                    }
                    isOpen = false;
                }
            }
        });

        mFrameLayouts[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("testFAB", "width:" + mFAB.getMeasuredWidth() + " height:" + mFAB.getMeasuredHeight());
                Log.i("testimg", "width:" + mImageViews[0].getMeasuredWidth() + " height:" + mImageViews[0].getMeasuredHeight());
                Log.i("testframe", "width:" + mFrameLayouts[0].getMeasuredWidth() + " height:" + mFrameLayouts[0].getMeasuredHeight());
                Log.i("testFABpoint", "width:" + mFAB.getX() + " height:" + mFAB.getY());
                Log.i("testimgpoint", "width:" + mImageViews[0].getX() + " height:" + mImageViews[0].getY());
                Log.i("testframepoint", "width:" + mFrameLayouts[0].getX() + " height:" + mFrameLayouts[0].getY());
            }
        });

    }

    private void animationOpen(final FrameLayout frameLayout, float x, float y) {

        halfWidth = frameLayout.getMeasuredWidth() / 2;
        halfHeight = frameLayout.getMeasuredHeight() / 2;
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -x + halfWidth);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -y + halfHeight);
        PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 720);
        PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
        PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
        PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 1);

        ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(frameLayout, pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
        animation.setDuration(500);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100, 100, PixelFormat.RGBA_8888);
                params.gravity = Gravity.TOP | Gravity.LEFT;
                params.leftMargin = (int) (mFAB.getX() - halfWidth);
                params.topMargin = (int) (mFAB.getY() - halfHeight);
                mFrameLayout.addView(frameLayout, params);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animation.start();
    }

    private void animationClose(final View view, float x, float y) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, x);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, y);
        PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, -720);
        PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0);
        PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0);
        PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0);

        ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
        animation.setDuration(500);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFrameLayout.removeView(view);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animation.start();
    }
}
