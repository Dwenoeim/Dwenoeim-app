package cse.mobile.dwenoeim_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
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


//        // Facebook 버튼 클릭 이벤트
//        Button facebookLoginButton = findViewById(R.id.logoImageView);
//        facebookLoginButton.setOnClickListener(v ->
//                Toast.makeText(MainActivity.this, "kakao 로그인 클릭됨", Toast.LENGTH_SHORT).show()
//        );
    }
}