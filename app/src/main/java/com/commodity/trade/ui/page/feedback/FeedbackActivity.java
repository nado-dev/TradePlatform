package com.commodity.trade.ui.page.feedback;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.commodity.mylibrary.util.QQUtil;
import com.commodity.mylibrary.util.ValidateUtil;
import com.commodity.trade.R;
import com.commodity.trade.entity.feedback.Feedback;
import com.commodity.trade.util.Config;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FeedbackActivity extends AppCompatActivity {

    @BindView(R.id.custom_back)
    ImageView customBack;
    @BindView(R.id.custom_name)
    EditText customName;
    @BindView(R.id.custom_email)
    EditText customEmail;
    @BindView(R.id.custom_detail)
    EditText customDetail;
    @BindView(R.id.custom_qqgroup)
    Button customQqGroup;
    @BindView(R.id.custom_submit)
    Button customSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.custom_back, R.id.custom_qqgroup, R.id.custom_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.custom_back:
                finish();
                break;
            case R.id.custom_qqgroup:
                sharetoqqgroup();
                break;
            case R.id.custom_submit:
                updateFeedBack();
                break;
            default:
        }
    }

    private void updateFeedBack() {
        String name = customName.getText().toString().trim();
        String email = customEmail.getText().toString().trim();
        String detail = customDetail.getText().toString().trim();

        if (!ValidateUtil.isEmail(email) || "".equals(name) || "".equals(detail)) {
            shortToast("输入有误，请检查您的输入");
            return;
        }

        Feedback newFeedBack = new Feedback();
        newFeedBack.setDetail(detail);
        newFeedBack.setEmail(email);
        newFeedBack.setName(name);
        newFeedBack.save(new SaveListener <String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    shortToast("反馈成功，我们会尽快通过邮箱给您反馈");
                }
                else{
                    shortToast("反馈失败"+e.getMessage());
                }
            }
        });
    }

    private void shortToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void sharetoqqgroup() {
        boolean result = new QQUtil(this).joinQQGroup(Config.REQUEST_QQGROUP_KEY);
        String resInfo = result ? "跳转成功" : "跳转失败";
        Toast.makeText(FeedbackActivity.this, resInfo, Toast.LENGTH_SHORT).show();
    }
}