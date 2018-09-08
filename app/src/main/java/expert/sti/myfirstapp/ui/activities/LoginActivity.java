package expert.sti.myfirstapp.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import expert.sti.myfirstapp.R;
import expert.sti.myfirstapp.presenters.ILoginPresenter;
import expert.sti.myfirstapp.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();
    public static final int EMAIL_REQUIRED_FIELD = 0;
    public static final int INVALID_EMAIL = 1;
    public static final int PASSWORD_REQUIRED_FIELD = 2;
    public static final int PASSWORD_TOO_SHORT = 3;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private ProgressBar mProgressBar;
    private LinearLayout mLoginForm;
    private Button mLoginBtn;

    private ILoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter(this);

        mEmailEditText = (EditText) findViewById(R.id.login_email);
        mPasswordEditText = (EditText) findViewById(R.id.login_password);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mLoginForm = (LinearLayout) findViewById(R.id.login_form);
        mLoginBtn = (Button) findViewById(R.id.login_btn);

        mEmailEditText.setText("jozko.mrkvicka@gmail.com");
        mPasswordEditText.setText("heslo");
    }

    public void login(View view) {

        showProgressBar();

        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        mPresenter.attemptLogin(email, password);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mLoginForm.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mLoginForm.setVisibility(View.VISIBLE);
    }

    public void unsuccessfulLogin(int errorCode) {
        hideProgressBar();
        clearEditTextError();

        switch (errorCode) {
            case EMAIL_REQUIRED_FIELD:
                mEmailEditText.setError(getString(R.string.error_required_field));
                mEmailEditText.requestFocus();
                break;
            case INVALID_EMAIL:
                mEmailEditText.setError(getString(R.string.error_invalid_email));
                mEmailEditText.requestFocus();
                break;
            case PASSWORD_REQUIRED_FIELD:
                mPasswordEditText.setError(getString(R.string.error_required_field));
                mPasswordEditText.requestFocus();
                break;
            case PASSWORD_TOO_SHORT:
                mPasswordEditText.setError(getString(R.string.error_short_password));
                mPasswordEditText.requestFocus();
                break;
            default:
                Log.e(TAG, "Wrong errorCode for unsuccessfulLogin");
        }
    }

    public void successfulLogin() {
        clearEditTextError();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }, 2000);
    }

    private void clearEditTextError() {
        mEmailEditText.setError(null);
        mPasswordEditText.setError(null);
    }
}
