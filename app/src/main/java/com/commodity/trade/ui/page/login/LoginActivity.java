package com.commodity.trade.ui.page.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.commodity.trade.MainActivity;
import com.commodity.trade.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Aaron
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_phone)
    TextView btnLoginPhone;
    @BindView(R.id.login_experience)
    TextView btnLoginExperience;
    @BindView(R.id.login_checkbox)
    CheckBox loginCheckbox;

    boolean checkBoxStatus = false;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setCheckBoxChangeListener();
    }

    private void setCheckBoxChangeListener() {
        loginCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> checkBoxStatus = isChecked);
    }


    @OnClick({R.id.login_phone, R.id.login_experience, R.id.button, R.id.button_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_phone:
                if (checkBoxStatus) {
                    navigateToPhoneLoginActivity();
                    finish();
                } else {
                    Toast.makeText(this, "请点击同意《用户协议》和《隐私政策》", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.login_experience:
                if (checkBoxStatus) {
                    navigateToNormalRegisterActivity();
                } else {
                    Toast.makeText(this, "请点击同意《用户协议》和《隐私政策》", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button:
                if (checkBoxStatus) {
                    startActivity(new Intent(LoginActivity.this, NormalLoginActivity.class));
                } else {
                    Toast.makeText(this, "请点击同意《用户协议》和《隐私政策》", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_register:
                if (checkBoxStatus) {
                    startActivity(new Intent(LoginActivity.this, NormalRegisterActivity.class));
                } else {
                    Toast.makeText(this, "请点击同意《用户协议》和《隐私政策》", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void navigateToPhoneLoginActivity() {
        startActivity(new Intent(LoginActivity.this, PhoneLoginActivity.class));
    }

    /**
     * 前往手机号码注册界面
     */
    private void navigateToMainActivityAsVisitor() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    /**
     * 前往账号密码注册界面
     */
    private void navigateToNormalRegisterActivity() {
        startActivity(new Intent(LoginActivity.this, NormalRegisterActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}