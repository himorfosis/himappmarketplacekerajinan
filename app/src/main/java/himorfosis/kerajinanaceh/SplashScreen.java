package himorfosis.kerajinanaceh;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    //get data login dari sharedpref
    String getDataEmail;
    String getDataPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // cek dan get data dari SharedPrefManager

                getDataEmail = SharedPrefManager.getLogin("loginEMAIL", "key", getApplicationContext());
                getDataPass = SharedPrefManager.getLogin("loginPASS", "key", getApplicationContext());

                Log.e("getData", "" +getDataEmail);
                Log.e("getData", "" +getDataPass);

                if (getDataEmail == null || getDataEmail.equals("") && getDataPass == null || getDataPass.equals("")) {

                    Log.e("data", "null");

                    Intent intent = new Intent(SplashScreen.this, Utama.class);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(SplashScreen.this, Aktif.class);
                    startActivity(intent);

                }

            }
        }, 2000L);
    }
}
