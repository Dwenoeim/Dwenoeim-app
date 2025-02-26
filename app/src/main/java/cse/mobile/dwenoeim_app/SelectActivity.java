package cse.mobile.dwenoeim_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SelectActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100; // 카메라 요청 코드
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101; // 권한 요청 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        LinearLayout qrLayout = findViewById(R.id.qr_button);
        qrLayout.setOnClickListener(v -> {
            // 카메라 권한 확인 및 요청
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                // 권한이 있는 경우 카메라 앱 실행
                openCamera();
            }
        });

        LinearLayout editLayout = findViewById(R.id.edit_button);
        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> {
            finish();
        });

    }

    // 카메라 앱 실행 메서드
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "카메라 앱을 열 수 없습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 승인된 경우 카메라 실행
                openCamera();
            } else {
                Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 카메라 앱 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                // 촬영한 사진을 ImageView에 표시하는 경우
//                ImageView capturedImage = findViewById(R.id.captured_image);
//                capturedImage.setImageBitmap(photo);

                Toast.makeText(this, "사진 촬영 성공!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "사진 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "사진 촬영이 취소되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
