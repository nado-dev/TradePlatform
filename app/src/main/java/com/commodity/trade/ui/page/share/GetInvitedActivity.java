package com.commodity.trade.ui.page.share;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.commodity.mylibrary.view.InviteCodeView;
import com.commodity.mylibrary.weight.LoadingDialog;
import com.commodity.trade.R;
import com.commodity.trade.entity.share.ShareBean;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.network.request.share.ShareRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * @author Aaron
 */
public class GetInvitedActivity extends AppCompatActivity {
    @BindView(R.id.order_input_back)
    ImageView orderInputBack;
    @BindView(R.id.captcha_view)
    InviteCodeView captchaView;
    @BindView(R.id.textView)
    TextView textView;
    ShareRequest request = new ShareRequest();
    private User currentUser;
    private LoadingDialog loadingDialog;
    ShareBean queryInfoBean;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_invited);
        ButterKnife.bind(this);
        MutableLiveData <ShareBean> shareData = request.getShareData();
        this.currentUser = BmobUser.getCurrentUser(User.class);
        if (currentUser.getUserType() == 0) {
            // 发布者无法接受邀请
            Toast.makeText(this, "用户类型错误，无法接受邀请", Toast.LENGTH_SHORT).show();
            finish();
        }
        shareData.observe(this, shareBean -> {
            // 提交
            if (shareBean.getErrorCode() == 200) {
                loadingDialog.dismiss();
                Toast.makeText(GetInvitedActivity.this, "邀请成功", Toast.LENGTH_SHORT).show();
            }
            // 查询信息
            if (shareBean.getErrorCode() == 201) {
                queryInfoBean = shareBean;
                Log.e("shareBean", queryInfoBean.toString());
                // 2. 提交
                Intent intent = new Intent(GetInvitedActivity.this, DetailedItemActivity.class);
                intent.putExtra("shareId", queryInfoBean.getData().getShareId());
                startActivity(intent);

            }
            else {
                Toast.makeText(GetInvitedActivity.this, "邀请失败", Toast.LENGTH_SHORT).show();
                Log.e("FWY", shareBean.toString());
            }
        });
        initCodeInput();
    }

    private void initCodeInput() {
        captchaView.setOnInputListener(code -> {
            // 网络请求
            if (code.length() == 6) {
                this.code = code;
                showLoading();
                // 1. 查询节点信息
                request.findShareCodeInfo(code);
            }
        });
    }

    private void showLoading() {
        this.loadingDialog = new LoadingDialog(this, "正在登录...", R.mipmap.ic_dialog_loading);
        loadingDialog.show();
    }

    @OnClick(R.id.order_input_back)
    public void onViewClicked() {
        finish();
    }
}