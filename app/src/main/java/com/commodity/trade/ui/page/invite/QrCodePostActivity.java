package com.commodity.trade.ui.page.invite;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.commodity.mylibrary.util.BitmapUtil;
import com.commodity.mylibrary.util.ViewUtil;
import com.commodity.trade.R;
import com.commodity.trade.entity.demo.TestBean;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.network.request.TestRequest;
import com.commodity.trade.util.Config;
import com.commodity.trade.util.QrUtil;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class QrCodePostActivity extends AppCompatActivity{
    private static final int REQUEST_CODE_SCAN = 100;
    String[] perms = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    List <String> permissionsList = new ArrayList <>();


    @BindView(R.id.announce_detail_back)
    ImageView tvback;
    @BindView(R.id.tv_order_share)
    TextView tvQrPostUsername;
    @BindView(R.id.tv_share_name)
    TextView tvQrPostUserType;
    @BindView(R.id.iv_order_qr_code)
    ImageView ivQrPostQr;
    @BindView(R.id.cl_qr_post)
    ConstraintLayout clQrPostMain;
    @BindView(R.id.btn_qr_post_save)
    Button btnQrPostSave;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.tv_qr_post_invite_code)
    TextView tvQrPostInviteCode;
    @BindView(R.id.order_qr_post_share)
    Button btnQrPostScan;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_post);
        ButterKnife.bind(this);

        initData();
        initMyQrCode();

    }

    private void initMyQrCode() {
        Bitmap bitmap = QrUtil.createQrCodeBitmap(Config.PREFIX_INVITE_R +
                        currentUser.getObjectId().substring(0,6),
                800, 800,
                "UTF-8", "H", "1", R.color.light_blue, Color.WHITE);
        ivQrPostQr.setImageBitmap(bitmap);
    }

    private void initData() {
        currentUser = BmobUser.getCurrentUser(User.class);
        String sName = "李老八888";
        String sType = "嗯卷Seller";
        String sCode = "AAAAAA";
        tvQrPostUsername.setText(currentUser.getUsername());
        tvQrPostUserType.setText(currentUser.convertTypeToString(currentUser.getUserType()));
        tvQrPostInviteCode.setText(currentUser.getObjectId().substring(0,6));
    }

    @OnClick({R.id.announce_detail_back, R.id.btn_qr_post_save, R.id.order_qr_post_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.announce_detail_back:
                finish();
                break;
            case R.id.btn_qr_post_save:
                Bitmap bitmap = ViewUtil.getBitmapByView(clQrPostMain);
                BitmapUtil.saveBitmapToLocal(QrCodePostActivity.this, bitmap);
                break;
            case  R.id.order_qr_post_share:
                // 扫一扫
                getPermission();
                startActivityForResult(new Intent(QrCodePostActivity.this, CaptureActivity.class),
                        REQUEST_CODE_SCAN);
            default:
        }
    }


    /**
     * 申请相关权限
     */
    private void getPermission() {
        permissionsList.clear();
        //判断哪些权限未授予
        for(String permission : perms){
            if(ActivityCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                permissionsList.add(permission);
            }
        }

        //请求权限
        if(!permissionsList.isEmpty()){
            //将List转为数组
            String[] permissions = permissionsList.toArray(new String[permissionsList.size()]);
            ActivityCompat.requestPermissions(QrCodePostActivity.this, permissions, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                if (content != null && content.contains(Config.URL_DOWNLOAD+"?invite_code_person=")) {
//                    Log.e("SCAN RESULT", "个人邀请码" + content.substring(48));
//                }
//                if (content != null && content.contains(Config.URL_DOWNLOAD+"?invite_code_goods=")) {
//                    Log.e("SCAN RESULT", "商品邀请码" + content.substring(47));
//                }
                shortToast(content);
            }
        }
    }

    void shortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            shortToast("已授权");
        }
    }

}