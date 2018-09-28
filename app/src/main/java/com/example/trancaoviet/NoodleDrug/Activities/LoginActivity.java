package com.example.trancaoviet.NoodleDrug.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.trancaoviet.NoodleDrug.CallBack.IsSiginCallBack;
import com.example.trancaoviet.NoodleDrug.CallBack.LoginVerifyCallBack;
import com.example.trancaoviet.NoodleDrug.DataIO.Provider;
import com.example.trancaoviet.NoodleDrug.Model.User;
import com.example.trancaoviet.NoodleDrug.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_LOGIN_WITH_GOOGLE = 0;
    private Button btnLogin, btnRegister, btnSkip;
    private EditText edtUsername, edtPassword;

    private Dialog dialogNoti;
    private TextView Noti, btnDismissDialog;

    //login with ic_google
    private GoogleSignInClient mGoogleSignInClient;

    //login with facebook
    private CallbackManager callbackManager;
    private Provider provider = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mapControl();
        addEventForControl();

        provider = Provider.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if(account != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class) );
//        }
//
//        Profile profile = Profile.getCurrentProfile();
//        if(profile != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class) );
//        }

        ImageView btnLoginWithGoogle =   findViewById(R.id.btn_login_with_google);
        btnLoginWithGoogle.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
        final LoginButton btnLoginWithFacebook = new LoginButton(this);
        ImageView btnLoginFacebook = findViewById(R.id.btn_login_with_facebook);
        btnLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLoginWithFacebook.callOnClick();
            }
        });

        btnLoginWithFacebook.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        btnLoginWithFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String UserName = object.getString("email");
                                    final User user = new User();
                                    user.setName(UserName);
                                    user.setPassword("");

                                    UserName = UserName.substring(0,UserName.indexOf("."));

                                    provider.checkUserIsRegister( new IsSiginCallBack() {
                                        @Override
                                        public void isSigin() {
                                            provider.setUser(user);
                                            provider.setLogin(true);
                                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        }

                                        @Override
                                        public void notSigin() {
                                            provider.setUser(user);
                                            provider.setLogin(true);
                                            provider.registerNewUser(user);
                                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        }
                                    }, UserName);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_with_google:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, REQUEST_LOGIN_WITH_GOOGLE);
                break;
        }
    }

    private void mapControl() {

        btnLogin = findViewById(R.id.btnLogin);
        btnSkip = findViewById(R.id.btn_skip_login);

        edtUsername = findViewById(R.id.edt_user_name);
        edtPassword = findViewById(R.id.edt_password);

        dialogNoti = new Dialog(this);
        dialogNoti.setContentView(R.layout.dialog_login_noty);
        btnDismissDialog = dialogNoti.findViewById(R.id.btnDismissDialog);
        Noti = dialogNoti.findViewById(R.id.txtMessage);

    }

    private void addEventForControl() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = edtUsername.getText().toString();
                String Password = edtPassword.getText().toString();
                final User user = new User();
                user.setName(UserName);
                user.setPassword(Password);

                if(UserName.isEmpty()) {
                    Noti.setText("user name is empty");
                    dialogNoti.show();
                    return;
                }
                if(Password.isEmpty()) {
                    Noti.setText("password is empty");
                    dialogNoti.show();
                    return;
                }

                provider.verifyAccount(user,  new LoginVerifyCallBack() {

                    @Override
                    public void isSucess() {
                        provider.setUser(user);
                        provider.setLogin(true);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                    @Override
                    public void isFailed() {
                        Noti.setText("user name or password is not valid");
                        dialogNoti.show();
                    }
                });
            }
        });

        btnDismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNoti.dismiss();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_LOGIN_WITH_GOOGLE:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String UserName = account.getEmail();
            final User user = new User();
            user.setName(UserName);
            user.setPassword("");
            user.setAvatarURL(account.getPhotoUrl().toString() );

            UserName = UserName.substring(0,UserName.indexOf("."));

            provider.checkUserIsRegister( new IsSiginCallBack() {
                @Override
                public void isSigin() {
                    provider.setUser(user);
                    provider.setLogin(true);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

                @Override
                public void notSigin() {
                    provider.setUser(user);
                    provider.setLogin(true);
                    provider.registerNewUser(user);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }, UserName);

        } catch (ApiException e) {
        }
    }
}

