package cse.mobile.dwenoeim_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

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

        // API 호출하여 문자열 응답 받기
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
                .build(); // GsonConverterFactory 제거

        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getImages().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String result = response.body().string();  // 응답 문자열 읽기
                        Log.d("EditActivity", "받은 응답: " + result);

                        // 테스트로 응답 문자열을 RecyclerView에 추가
                        imageUrls.add(result);
                        adapter.notifyDataSetChanged();  // 데이터 변경 알림
                    } catch (IOException e) {
                        Log.e("EditActivity", "응답 처리 중 오류", e);
                    }
                } else {
                    Log.e("EditActivity", "API 응답 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("EditActivity", "API 호출 실패", t);
            }
        });
    }

    // Retrofit API 인터페이스 정의
    public interface ApiService {
        @GET("test")
        Call<ResponseBody> getImages();
    }
}
