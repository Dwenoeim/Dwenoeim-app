package cse.mobile.dwenoeim_app;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

public class LoginUtil {

    private GoogleSignInClient googleLoginClient;
    private static final int REQ_GOOGLE_LOGIN = 1001;

    @Inject
    public LoginUtil() {
        // 기본 생성자
    }

    public void loginGoogle(Activity activity) {
        String webClientId = activity.getString(R.string.google_login_web_client_id);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build();

        googleLoginClient = GoogleSignIn.getClient(activity, signInOptions);

        if (googleLoginClient != null) {
            googleLoginClient.signOut().addOnCompleteListener(task -> {
                // 로그아웃 후 로그인 화면 띄우기
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
                if (account != null && account.getId() != null && account.getEmail() != null) {
                    Log.d("GoogleLogin", "email = " + account.getEmail() + ", id = " + account.getId() + ", token = " + account.getIdToken());
                }
            } catch (ApiException e) {
                Log.d("GoogleLogin", "google login fail = " + e.getMessage());
                if (e.getStatusCode() == 12501) {
                    // 사용자가 로그인 취소
                }
            }
        }
    }
}
