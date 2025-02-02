package cse.mobile.dwenoeim_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditActivity extends AppCompatActivity {

    private RecyclerView imageRecyclerView;
    private ImageAdapter adapter;
    private List<String> imageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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
        });

        Button cancelButton = findViewById(R.id.editCancelButton);
        cancelButton.setOnClickListener(v -> finish());
    }

    // Retrofit API 호출
    private void fetchImagesFromApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dwenoeim.store/") // 기본 URL 설정
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getImages().enqueue(new Callback<List<ImageResponse>>() {
            @Override
            public void onResponse(Call<List<ImageResponse>> call, Response<List<ImageResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (ImageResponse imageResponse : response.body()) {
                        imageUrls.add(imageResponse.getImageUrl());
                    }
                    adapter.notifyDataSetChanged(); // 데이터 변경 알림
                } else {
                    Log.e("EditActivity", "API 응답 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ImageResponse>> call, Throwable t) {
                Log.e("EditActivity", "API 호출 실패", t);
            }
        });
    }

    // Retrofit API 인터페이스 정의
    public interface ApiService {
        @retrofit2.http.GET("test")
        Call<List<ImageResponse>> getImages();
    }

    // API 응답 데이터 모델
    public static class ImageResponse {
        @SerializedName("imageUrl") // JSON의 키와 매핑
        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }
    }
}
