package l.com.ldk.duykhanh.whatdoyoumean.ACTIVITY.LOGIN;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import l.com.ldk.duykhanh.whatdoyoumean.R;

public class SplashScreenActivity extends AppCompatActivity {
    private static int TIME_OUT = 1500;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash_screen);
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class );
                startActivity( i );
            }
        }, TIME_OUT );
    }
}
