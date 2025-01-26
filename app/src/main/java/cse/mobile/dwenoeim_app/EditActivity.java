package cse.mobile.dwenoeim_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private RecyclerView imageRecyclerView;
    private ImageAdapter adapter;
    private List<String> imageUrls = new ArrayList<>(); // API에서 가져올 이미지 URL 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit); // XML 레이아웃 파일 연결

        imageRecyclerView = findViewById(R.id.imageRecyclerView);
        Button saveButton = findViewById(R.id.saveButton);

        // RecyclerView 설정
        adapter = new ImageAdapter(this, imageUrls);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageRecyclerView.setAdapter(adapter);

        // API 호출하여 이미지 URL 가져오기
        fetchImagesFromApi();

        // SAVE 버튼 클릭 이벤트
        saveButton.setOnClickListener(v -> {
            Toast.makeText(this, "이미지 저장 완료!", Toast.LENGTH_SHORT).show();
            // SAVE 버튼 로직 추가 필요 (예: API로 데이터 전송)
        });
    }

    // API에서 이미지 가져오기
    private void fetchImagesFromApi() {
        // 여기에 Retrofit 또는 OkHttp를 사용하여 API 호출 로직 추가
        // 임시로 샘플 데이터 추가
        imageUrls.add("https://via.placeholder.com/150");
        imageUrls.add("https://via.placeholder.com/200");
        imageUrls.add("https://via.placeholder.com/250");
        adapter.notifyDataSetChanged(); // 데이터 변경 알림
    }
}
