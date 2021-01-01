package com.commodity.trade.ui.page.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.commodity.trade.R;
import com.commodity.trade.entity.user.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class NormalRegisterActivity extends AppCompatActivity {

    @BindView(R.id.user_phone_number)
    EditText userPhoneNumber;
    @BindView(R.id.user_username)
    EditText userUsername;
    @BindView(R.id.user_type)
    EditText userType;
    @BindView(R.id.user_password)
    EditText userPassword;
    @BindView(R.id.user_password_confirm)
    EditText userPasswordConfirm;
    @BindView(R.id.register)
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register)
    public void onViewClicked() {
        signUp();
    }

    private void signUp() {
        final User user = new User();
        user.setUsername(userUsername.getText().toString());
        user.setPassword(userPassword.getText().toString());
        user.setMobilePhoneNumber(userPhoneNumber.getText().toString());
        user.setUserType(Integer.parseInt(userType.getText().toString()));

        user.signUp(new SaveListener <User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e != null) {
                    e.printStackTrace();
                    Toast.makeText(NormalRegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(NormalRegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}