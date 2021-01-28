package com.commodity.trade.ui.page.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.commodity.mylibrary.util.ValidateUtil;
import com.commodity.mylibrary.view.CaptchaView;
import com.commodity.mylibrary.view.InviteCodeView;
import com.commodity.mylibrary.weight.MyDialog;
import com.commodity.trade.MainActivity;
import com.commodity.trade.R;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.ui.page.register.RegisterActivity;
import com.commodity.trade.util.Config;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author Aaron
 */
public class  PhoneLoginActivity extends AppCompatActivity {

    public static final int STATUS_INPUTTING_PHONE_NUM = 0;
    public static final int STATUS_INPUTTING_VERIFY_CODE = 1;

    int currInputStatus = STATUS_INPUTTING_PHONE_NUM;
    boolean enableResent = false;

    private String phoneNum = "";

    private final CountDownTimer timer = new CountDownTimer(60100, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            String timeLeft = (millisUntilFinished / 1000) + "秒";
            tvCaptchaTimer.setText(timeLeft);
        }

        @Override
        public void onFinish() {
            tvCaptchaTimer.setText("重新发送");
            enableResent = true;
        }
    };

    @BindView(R.id.captcha_view)
    InviteCodeView captchaView;
    @BindView(R.id.img_tab_back)
    ImageView imgTabBack;
    @BindView(R.id.tv_login_title)
    TextView tvLoginTitle;
    @BindView(R.id.tv_show_auto_signin)
    TextView tvShowAutoSignin;
    @BindView(R.id.tv_86)
    TextView tv86;
    @BindView(R.id.et_login_phone)
    TextInputEditText etLoginPhone;
    @BindView(R.id.tv_login_next)
    TextView tvLoginNext;
    @BindView(R.id.rl_login_phone)
    RelativeLayout rlLoginPhone;
    @BindView(R.id.tv_capcha_title)
    TextView tvCapchaTitle;
    @BindView(R.id.tv_captcha_phone)
    TextView tvCaptchaPhone;
    @BindView(R.id.tv_captcha_timer)
    TextView tvCaptchaTimer;
    @BindView(R.id.rl_captcha)
    RelativeLayout rlCaptcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        ButterKnife.bind(this);
        setCaptchaViewCallBack();
    }

    private void setCaptchaViewCallBack() {
        captchaView.setOnInputListener(code -> {
            timer.cancel();
            enableResent = true;
            validateCode(code);
        });
    }

    private void validateCode(String code) {
        BmobUser.signOrLoginByMobilePhone(phoneNum, code, new LogInListener <BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    BmobUser.fetchUserInfo(new FetchUserInfoListener <BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if(e == null) {
                                shortToast("短信注册或登录成功：" + bmobUser.getUsername());
                                User currentUser = BmobUser.getCurrentUser(User.class);
                                if (currentUser.invitorCode == null || "".equals(currentUser.invitorCode)) {
                                    makeDialogToGetInvited();
                                }
                                else {
                                    startActivity(new Intent(PhoneLoginActivity.this, MainActivity.class));
                                }
                            }
                        }
                    });
                }
                else {
                    shortToast("短信注册或登录失败：" + e.getErrorCode() + "-" + e.getMessage());
                }
            }
        });
    }

    private void makeDialogToGetInvited() {
        new MyDialog(PhoneLoginActivity.this)
                .title("请完成邀请验证")
                .message("您未在本平台邀请验证，请完成邀请验证")
                .setCancelable(true)
                .cancelText("取消")
                .sureText("确定")
                .setSureOnClickListener(v -> {
                    startActivity(new Intent(PhoneLoginActivity.this, RegisterActivity.class));
                }).build().show();
    }

    @OnClick({R.id.img_tab_back, R.id.et_login_phone, R.id.tv_login_next, R.id.tv_capcha_title, R.id.tv_captcha_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_tab_back:
                goBackOption();
                break;
            case R.id.et_login_phone:
                break;
            case R.id.tv_login_next:
                doWithBtnLoginPhone();
                break;
            case R.id.tv_capcha_title:
                break;
            case R.id.tv_captcha_phone:
                break;
            case R.id.tv_captcha_timer:
                if (!enableResent) {
                    return;
                } else {
                    enableResent = false;
                    timer.start();
                    requestNetWorkToLoadCode();
                }
                break;
            default:
        }
    }

    /**
     * 请求网络
     */
    private void requestNetWorkToLoadCode() {

    }

    /**
     * 处理 下一步（发送验证码） 按钮
     */
    private void doWithBtnLoginPhone() {
        boolean isLegalInput = checkPhoneNumberInputStatus();
        if (isLegalInput) {
            // 合法
            // goNext Bmob
            BmobSMS.requestSMSCode(phoneNum, Config.BMOB_SMS_TEMPLATE, new QueryListener <Integer>() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
                        Toast.makeText(PhoneLoginActivity.this, "验证短信发送成功", Toast.LENGTH_SHORT).show();
                        timer.start();
                        changeInputStatus(STATUS_INPUTTING_VERIFY_CODE);
                        tvCapchaTitle.setText(String.format("验证码已发送至%s", phoneNum));
                    }
                    else {
                        Log.e("SMS_BMOB","发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                        Toast.makeText(PhoneLoginActivity.this, "发送验证码失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, "请输入正确格式的手机号", Toast.LENGTH_SHORT).show();
        }


//        shortToast("验证短信发送成功");
//        timer.start();
//        changeInputStatus(STATUS_INPUTTING_VERIFY_CODE);
//        tvCapchaTitle.setText(String.format("验证码已发送至%s", phoneNum));
    }

    /**
     * 切换InputStatus
     */
    private void changeInputStatus(int target) {
        if (currInputStatus == target) {
            shortToast("cannot change mode with same status, curr is" + currInputStatus);
            Log.d(PhoneLoginActivity.class.getSimpleName(), "cannot change mode with same status");
            return;
        }
        currInputStatus = target;
        shortToast("currInputStatus is " + currInputStatus);
        if (currInputStatus == STATUS_INPUTTING_PHONE_NUM) {
            rlLoginPhone.setVisibility(View.VISIBLE);
            rlCaptcha.setVisibility(View.GONE);
        }
        if (currInputStatus == STATUS_INPUTTING_VERIFY_CODE){
            rlLoginPhone.setVisibility(View.GONE);
            rlCaptcha.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 检查手机号码输入是否有误
     *
     * @return status
     */
    private boolean checkPhoneNumberInputStatus() {
        String phoneInput = etLoginPhone.getText() != null ? etLoginPhone.getText().toString().trim() : "";
        boolean ans =  ValidateUtil.isMobile(phoneInput);
        if (ans) {
            phoneNum = phoneInput;
            return true;
        }
        return false;
    }

    /**
     * 根据currInputStatus实现返回功能
     */
    private void goBackOption() {
        if (currInputStatus == STATUS_INPUTTING_PHONE_NUM) {
            finish();
        }
        if (currInputStatus == STATUS_INPUTTING_VERIFY_CODE) {
            rlCaptcha.setVisibility(View.GONE);
            rlLoginPhone.setVisibility(View.VISIBLE);
            changeInputStatus(STATUS_INPUTTING_PHONE_NUM);
        }
    }

    @Override
    public void onBackPressed() {
        goBackOption();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    void shortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}