package cse.mobile.dwenoeim_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LoginUtil loginUtil; // Google 로그인 유틸리티 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUtil = new LoginUtil(); // Google 로그인 유틸리티 객체 생성

        LinearLayout kakaoLogin = findViewById(R.id.kakaoogin);

        // 클릭 이벤트 추가 (카카오 버튼 -> Google 로그인 실행)
        kakaoLogin.setOnClickListener(v -> loginUtil.loginGoogle(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginUtil.handleGoogleActivityResult(requestCode, resultCode, data, MainActivity.this);
    }
}
