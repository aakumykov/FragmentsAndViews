package ru.aakumykov.dvachreader.re_captcha;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import ru.aakumykov.dvachreader.Config;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.aakumykov.fragmentsandviews.R;

public class ReCaptchaTest extends AppCompatActivity {

    @BindView(R.id.button) Button button;
    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.errorView) TextView errorView;

    public static final String TAG = "ReCaptchaTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.re_captcha_test_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    void reCaptcha() {
        SafetyNet.getClient(this).verifyWithRecaptcha(Config.RECAPTCHA_PUBLIC_KEY)
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        if (!response.getTokenResult().isEmpty()) {
                            textView.setText(response.getTokenResult());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            String errorMsg = "Ошибка: " +
                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode());
                            errorView.setText(errorMsg);
                        } else {
                            String errorMsg = "Неизвестная ошибка: " + e.getMessage();
                            errorView.setText(errorMsg);
                        }
                    }
                });
    }
}
