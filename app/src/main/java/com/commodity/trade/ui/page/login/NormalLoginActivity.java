package com.commodity.trade.ui.page.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.commodity.mylibrary.weight.MyDialog;
import com.commodity.trade.MainActivity;
import com.commodity.trade.R;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.ui.page.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.LogInListener;

public class NormalLoginActivity extends AppCompatActivity {

    @BindView(R.id.logon_username)
    EditText logonUsername;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.bt_login)
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_login2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        String phone = logonUsername.getText().toString();
        String passed = loginPassword.getText().toString();

        BmobUser.loginByAccount(phone, passed, new LogInListener <User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Log.d("LoginInfo", "登录成功：" + user.getUsername());
                    fetchUserInfo();

                } else {
                    Log.d("LoginInfo",  "登录失败：" + e.getMessage());
                }
            }
        });
    }

    private void makeDialogToGetInvited() {
        new MyDialog(this)
                .title("请完成邀请验证")
                .message("您未在本平台邀请验证，请完成邀请验证")
                .setCancelable(true)
                .cancelText("取消")
                .sureText("确定")
                .setCancelOnClickListener(v -> {
                    onBackPressed();
                })
                .setSureOnClickListener(v -> {
                    startActivity(new Intent(this, RegisterActivity.class));
                }).build().show();

    }

    private void fetchUserInfo() {
        BmobUser.fetchUserInfo(new FetchUserInfoListener <BmobUser>() {
            @Override
            public void done(BmobUser user, BmobException e) {
                if (e == null) {
                    final User myUser = BmobUser.getCurrentUser(User.class);
                    Log.e("Update To Bmob", myUser.toString() + " "+ myUser.getObjectId());
                    askForGetInvitedOrGoMainActivity();
                } else {
                    e.printStackTrace();
                    Log.e("Update To Bmob",e.getMessage());
                }
            }
        });
    }

    private void askForGetInvitedOrGoMainActivity() {
        String invitorCode = (String) BmobUser.getObjectByKey("invitorCode");
        if (invitorCode == null || "".equals(invitorCode)) {
            makeDialogToGetInvited();
        }
        else {
            startActivity(new Intent(NormalLoginActivity.this, MainActivity.class));
        }
    }
}