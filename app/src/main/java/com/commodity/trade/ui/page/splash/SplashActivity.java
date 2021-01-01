package com.commodity.trade.ui.page.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.commodity.trade.MainActivity;
import com.commodity.trade.R;
import com.commodity.trade.ui.page.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * @author Aaron
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.sp_jump_btn)
    Button mSpJumpBtn;
    private final CountDownTimer timer = new CountDownTimer(3400, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            String tips = "跳过(" + millisUntilFinished / 1000 + "s)";
            mSpJumpBtn.setText(tips);
        }

        @Override
        public void onFinish() {
            String tips = "跳过(" + 0 + "s)";
            mSpJumpBtn.setText(tips);
            navigateToLoginActivityOrMainActivity();
        }
    };

    private void navigateToLoginActivityOrMainActivity() {
        timer.cancel();
        // 是否已经登录
        boolean flag = BmobUser.isLogin();
        if (!flag) {
            goToLoginActivity();
        }
        else {
            goToMainActivity();
        }
    }

    private void goToLoginActivity() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    private void goToMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        // 测试用 TODO
        if (BmobUser.isLogin()) {
            BmobUser.logOut();

        }
        Log.e("FWY", String.valueOf(BmobUser.isLogin()));
        timer.start();
    }

    @OnClick(R.id.sp_jump_btn)
    public void onViewClicked() {
        navigateToLoginActivityOrMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}