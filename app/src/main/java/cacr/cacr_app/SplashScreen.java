package cacr.cacr_app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    SSP s;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (s.getusername(getApplicationContext()).equals("admin")) {
            i = new Intent(SplashScreen.this, Home.class);

        } else {
            i = new Intent(SplashScreen.this, MainActivity.class);
            finish();
        }


        startActivity(i);


    }
}

