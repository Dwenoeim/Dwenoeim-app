package cse.mobile.dwenoeim_app;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {
    @POST("google/token")
    @Headers("Content-Type: application/json")
    Call<AuthResponse> login(@Body AuthCodeRequest request);
}

// ✅ JSON 요청을 위한 DTO 클래스 (authCode 필드 사용)
class AuthCodeRequest {
    private String authCode;

    public AuthCodeRequest(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }
}

class AuthResponse {
    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private TokenData data;

    public TokenData getData() {
        return data;
    }

    public static class TokenData {
        @SerializedName("accessToken")
        private String accessToken;

        @SerializedName("refreshToken")
        private String refreshToken;

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}

