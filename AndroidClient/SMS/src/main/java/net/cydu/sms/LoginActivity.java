package net.cydu.sms;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import net.cydu.sms.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello",
            "bar@example.com:world"
    };

    /**
     * The default email to populate the email field with.
     */
    public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

    /*
    private Weibo mWeibo;
    private static final String CONSUMER_KEY = "742998287";// 替换为开发者的appkey，例如"1646212860";
    private static final String REDIRECT_URL = "http://blog.cydu.net/app";

    public static Oauth2AccessToken accessToken;
    public static final String TAG = "sinasdk";
    SsoHandler mSsoHandler;
    */

    // Values for email and password at the time of the login attempt.
    private String mNumber;
    private String mText;

    // UI references.
    private EditText mNumberView;
    private EditText mTextView;
    private Button authBtn, apiBtn, ssoBtn, cancelBtn;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Set up the login form.
        mNumber = getIntent().getStringExtra("10086");
        mNumberView = (EditText) findViewById(R.id.phone);
        mNumberView.setText(mNumber);

        mTextView = (EditText) findViewById(R.id.text);
        mTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.send_button || id == EditorInfo.IME_NULL) {
                    attemptSend();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.send_button ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSend();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    public void attemptSend() {

        // Reset errors.
        mNumberView.setError(null);
        mTextView.setError(null);

        // Store values at the time of the login attempt.
        mNumber = mNumberView.getText().toString();
        mText = mTextView.getText().toString();

        SMSReceiver.sendMsg(mNumber,System.currentTimeMillis()/1000,mText);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /*
    class AuthDialogListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            LoginActivity.accessToken = new Oauth2AccessToken(token, expires_in);
            if (LoginActivity.accessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                        .format(new java.util.Date(LoginActivity.accessToken
                                .getExpiresTime()));
                mTextView.setText("认证成功: \r\n access_token: " + token + "\r\n"
                        + "expires_in: " + expires_in + "\r\n有效期：" + date);
                try {
                    Class sso = Class
                            .forName("com.weibo.sdk.android.api.WeiboAPI");// 如果支持weiboapi的话，显示api功能演示入口按钮
                    //apiBtn.setVisibility(View.VISIBLE);
                } catch (ClassNotFoundException e) {
                    // e.printStackTrace();
                    Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");

                }
                //cancelBtn.setVisibility(View.VISIBLE);
                AccessTokenKeeper.keepAccessToken(LoginActivity.this, accessToken);
                Toast.makeText(LoginActivity.this, "认证成功", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onError(WeiboDialogError e) {
            Toast.makeText(getApplicationContext(),
                    "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "Auth cancel",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(getApplicationContext(),
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    */
}
