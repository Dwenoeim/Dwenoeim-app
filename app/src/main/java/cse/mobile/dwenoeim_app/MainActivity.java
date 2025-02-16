package cse.mobile.dwenoeim_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private LoginUtil loginUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUtil = new LoginUtil(this);

        LinearLayout kakaoLogin = findViewById(R.id.kakaoogin);
        kakaoLogin.setOnClickListener(v -> loginUtil.loginGoogle(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginUtil.handleGoogleActivityResult(requestCode, resultCode, data, MainActivity.this);
    }
}
