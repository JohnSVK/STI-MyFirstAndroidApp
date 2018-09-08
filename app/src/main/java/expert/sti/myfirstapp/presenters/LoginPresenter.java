package expert.sti.myfirstapp.presenters;

import expert.sti.myfirstapp.ui.activities.LoginActivity;

public class LoginPresenter implements ILoginPresenter{

    private LoginActivity mView;

    public LoginPresenter(LoginActivity view) {
        this.mView = view;
    }

    public void attemptLogin(String email, String password) {
        
        if (email.isEmpty()) {
            mView.unsuccessfulLogin(LoginActivity.EMAIL_REQUIRED_FIELD);
        } else if (!email.contains("@")) {
            mView.unsuccessfulLogin(LoginActivity.INVALID_EMAIL);
        } else if (password.isEmpty()) {
            mView.unsuccessfulLogin(LoginActivity.PASSWORD_REQUIRED_FIELD);
        } else if (password.length() < 5) {
            mView.unsuccessfulLogin(LoginActivity.PASSWORD_TOO_SHORT);
        } else {
            mView.successfulLogin();
        }
    }
}
