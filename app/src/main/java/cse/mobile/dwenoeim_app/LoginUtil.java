package cse.mobile.dwenoeim_app;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUtil {

    private GoogleSignInClient googleLoginClient;
    private static final int REQ_GOOGLE_LOGIN = 1001;
    private static final String BACKEND_URL = "https://hearmeout.kr/api/"; // ✅ baseUrl 유지

    public LoginUtil(Activity activity) {
        String webClientId = activity.getString(R.string.google_login_web_client_id);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build();

        googleLoginClient = GoogleSignIn.getClient(activity, signInOptions);
    }

    public void loginGoogle(Activity activity) {
        if (googleLoginClient != null) {
            googleLoginClient.signOut().addOnCompleteListener(task -> {
                // 로그아웃 후 로그인 진행
                Intent signInIntent = googleLoginClient.getSignInIntent();
                activity.startActivityForResult(signInIntent, REQ_GOOGLE_LOGIN);
            });
        }
    }

    public void handleGoogleActivityResult(int requestCode, int resultCode, Intent data, Activity activity) {
        if (requestCode == REQ_GOOGLE_LOGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null && account.getIdToken() != null) {
                    String idToken = account.getIdToken();
                    Log.d("GoogleLogin", "ID Token: " + idToken);

                    // ✅ 백엔드로 ID 토큰 보내기
                    sendIdTokenToBackend(idToken, activity);
                }
            } catch (ApiException e) {
                Log.d("GoogleLogin", "Google login fail: " + e.getMessage());
                Toast.makeText(activity, "Google 로그인 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendIdTokenToBackend(String idToken, Activity activity) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthService authService = retrofit.create(AuthService.class);
        authService.login(new AuthCodeRequest(idToken)).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse.TokenData tokenData = response.body().getData();

                    if (tokenData != null) {
                        String accessToken = tokenData.getAccessToken();
                        String refreshToken = tokenData.getRefreshToken();
                        Log.d("BackendLogin", "Access Token: " + accessToken);
                        Log.d("BackendLogin", "Refresh Token: " + refreshToken);

                        navigateToNextActivity(activity, accessToken);
                    } else {
                        Log.e("BackendLogin", "TokenData is null!");
                        Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                        Log.e("BackendLogin", "API 응답 실패: " + response.code() + ", Error: " + errorBody);
                    } catch (Exception e) {
                        Log.e("BackendLogin", "Error parsing response body", e);
                    }
                    Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                Log.e("BackendLogin", "Login API 요청 실패", t);
                Toast.makeText(activity, "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToNextActivity(Activity activity, String accessToken) {
        Intent intent = new Intent(activity, SelectActivity.class);
        intent.putExtra("accessToken", accessToken); // ✅ 다음 화면에 액세스 토큰 전달
        activity.startActivity(intent);
        activity.finish(); // ✅ 현재 화면 종료
    }
}
