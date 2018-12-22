package vn.edu.uit.quanlychitieunhom.Views;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;
import vn.edu.uit.quanlychitieunhom.R;

public class SplashScreen extends AppCompatActivity {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withLogo(R.mipmap.ic_launcher)
                .withHeaderText("")
                .withFooterText("Copyright 2018")
                .withBeforeLogoText("TEAM DEVELOPER 3 ")
                .withAfterLogoText("Quản lý chi tiêu nhóm");


        config.getHeaderTextView().setTextColor(R.color.colorPrimary);
        config.getFooterTextView().setTextColor(R.color.colorPrimary);
        config.getBeforeLogoTextView().setTextColor(R.color.colorPrimary);
        config.getAfterLogoTextView().setTextColor(R.color.colorPrimary);

        View view = config.create();
        setContentView(view);
    }
}
