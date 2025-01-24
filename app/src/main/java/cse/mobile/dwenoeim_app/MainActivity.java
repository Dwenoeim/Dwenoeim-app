package cse.mobile.dwenoeim_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout kakaoLogin = findViewById(R.id.kakaoogin);

// 클릭 이벤트 추가
        kakaoLogin.setOnClickListener(v -> {
            // SelectActivity로 이동
            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
            startActivity(intent);
        });
    }
}