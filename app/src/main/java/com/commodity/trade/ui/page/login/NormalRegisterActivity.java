package com.commodity.trade.ui.page.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.commodity.mylibrary.util.ValidateUtil;
import com.commodity.trade.R;
import com.commodity.trade.entity.user.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.SaveListener;

public class NormalRegisterActivity extends AppCompatActivity {

    @BindView(R.id.user_phone_number)
    EditText userPhoneNumber;
    @BindView(R.id.user_username)
    EditText userUsername;
    @BindView(R.id.user_type)
    Spinner userType;
    @BindView(R.id.user_password)
    EditText userPassword;
    @BindView(R.id.user_password_confirm)
    EditText userPasswordConfirm;
    @BindView(R.id.register)
    Button register;
    private int userTypeInt = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_login);
        ButterKnife.bind(this);
        String[] mItems = {"(点击选择)","卖家", "中间人", "买家"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);
        userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                NormalRegisterActivity.this.userTypeInt = position - 1;
                Log.e("FWY", "selected" + userTypeInt);
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });
    }

    @OnClick(R.id.register)
    public void onViewClicked() {
        signUp();
    }

    private void signUp() {
        boolean b = checkData();
        if (!b) {
            return;
        }

        final User user = new User();
        user.setUsername(userUsername.getText().toString());
        user.setPassword(userPassword.getText().toString());
        user.setMobilePhoneNumber(userPhoneNumber.getText().toString());
        user.setUserType(userTypeInt);

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

    private boolean checkData() {
        String pw1 = userPassword.getText().toString();
        String pw2 = userPasswordConfirm.getText().toString();
        String phone = userPhoneNumber.getText().toString();
        if (! pw1.equals(pw2)) {
            Toast.makeText(this, "密码两次输入不匹配", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(pw1) || TextUtils.isEmpty(pw2)) {
            Toast.makeText(this, "请正确填写密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(phone) || !ValidateUtil.isMobile(phone)) {
            Toast.makeText(this, "请正确填写电话号码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(userUsername.getText().toString())) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (userTypeInt == -1) {
            Toast.makeText(this, "请选择您的用户类型", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }
}