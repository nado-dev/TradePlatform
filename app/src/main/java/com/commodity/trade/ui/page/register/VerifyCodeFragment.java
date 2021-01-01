package com.commodity.trade.ui.page.register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.commodity.mylibrary.util.StringUtil;
import com.commodity.mylibrary.view.InviteCodeView;
import com.commodity.mylibrary.weight.LoadingDialog;
import com.commodity.trade.MainActivity;
import com.commodity.trade.R;
import com.commodity.trade.entity.message.Event;
import com.commodity.trade.entity.message_bean.InviteMessageBox;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.entity.user.UserConfig;
import com.commodity.trade.network.request.InviteRequest;
import com.commodity.trade.util.Config;
import com.yzq.zxinglibrary.android.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by AaFaa
 * on 2020/12/10
 * in package com.commodity.trade.ui.page.register
 * with project trade
 *
 * @author Aaron
 */
public class VerifyCodeFragment extends Fragment implements ToFragmentListener {
    String[] perms = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    List <String> permissionsList = new ArrayList <>();


    int userType;

    @BindView(R.id.guideline2)
    Guideline guideline2;
    @BindView(R.id.guideline3)
    Guideline guideline3;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.captcha_view)
    InviteCodeView captchaView;
    @BindView(R.id.tv_register_invite_code_num)
    TextView tvRegisterInviteCodeNum;
    @BindView(R.id.textView3)
    TextView textView3;

    String type = "";
    int typeCode = 0;
    RegisterActivity activity;
    InviteRequest request = new InviteRequest();
    User user = BmobUser.getCurrentUser(User.class);
    String inviteCode;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.textView4)
    TextView textView4;
    private LoadingDialog loadingDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        activity = (RegisterActivity) getActivity();
        super.onActivityCreated(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_register_invite_code, container, false);
        ButterKnife.bind(this, view);
        setCaptchaViewCallBack();
        // 注册观察
        request.getInviteData().observe(this, inviteBean -> {
            if (inviteBean.getErrorCode() == 200) {
                final User user = BmobUser.getCurrentUser(User.class);
                System.out.println(user.getObjectId());
                user.setInvitorCode(inviteCode);
                user.setUserType(userType);
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e != null) {
                            Log.e("Bmob Update", "Update Filed" + e.getMessage());
                        } else {
                            // 添加邀请信息
                            addInviteMessage(inviteBean.getData().getMid(), user.getObjectId());
                        }
                    }
                });
            }
           else {
               showToast("失败");
               loadingDialog.dismiss();
               Log.e("更新失败", inviteBean.toString());
            }
        });
        return view;
    }

    private void addInviteMessage(String invitor, String newUser) {
        InviteMessageBox inviteMessageBox = new InviteMessageBox();
        inviteMessageBox.setInvitor(invitor);
        inviteMessageBox.setNewUser(newUser);
        inviteMessageBox.save(new SaveListener <String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    loadingDialog.dismiss();
                    Log.e("Bmob Update", "Updated");
                    startActivity(new Intent(activity, MainActivity.class));
                    activity.finish();
                }
                else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        int msg = event.getMessageInt();
        userType = msg;
        switch (msg) {
            case UserConfig.USER_TYPE_SELLER:
                type = "卖家";
                typeCode = UserConfig.USER_TYPE_SELLER;
                break;
            case UserConfig.USER_TYPE_MIDDLE:
                type = "中间人";
                typeCode = UserConfig.USER_TYPE_MIDDLE;
                break;
            case UserConfig.USER_TYPE_BUYER:
                type = "买家";
                typeCode = UserConfig.USER_TYPE_BUYER;
                break;
            default:
        }
        textView3.setText(type);
    }


    void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setCaptchaViewCallBack() {
        captchaView.setOnInputListener(code1 -> {
            String code = StringUtil.covertToLowerCases(code1);
            // 注册操作
            showToast(code);
            boolean isUsefulCode = checkInviteCode(code);
            if (!isUsefulCode) {
                showToast("验证码不可用");
                return;
            }
            updateInvitor(code);
        });
    }

    private void updateInvitor(String invitorCode) {
        loadingDialog = new LoadingDialog(activity, "处理中", R.mipmap.ic_dialog_loading);
        loadingDialog.show();
        inviteCode = invitorCode;
        // 1. 获取id
        String uid = user.getObjectId();
        // 2. 获取用户名
        String uName = user.getMobilePhoneNumber();
        // 请求
        request.getInvited(invitorCode, uid, uName, typeCode);
    }


    /**
     * 检查邀请码是否可用
     */
    private boolean checkInviteCode(String code) {
        return code.length() == 6;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity = null;
    }

    /**
     * qrCode扫描回调
     *
     * @param content qr
     */
    @Override
    public void onTypeClick(String content) {
        if (content != null && content.contains(Config.PREFIX_INVITE_R)) {
            String substring = content.substring(48);
            Log.e("SCAN RESULT", "个人邀请码" + substring);
            updateInvitor(substring);
        }
        else if (content != null && content.contains(Config.PREFIX_SHARE_R)) {
            Log.e("SCAN RESULT", "商品邀请码" + content.substring(47));
            showToast("无效邀请码");
        }
        else {
            Toast.makeText(activity, content, Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.iv_qr_code)
    public void onViewClicked() {
        getPermission();
        activity.startActivityForResult(new Intent(activity, CaptureActivity.class), 0x6324);
    }

    /**
     * 申请相关权限
     */
    private void getPermission() {
        permissionsList.clear();
        //判断哪些权限未授予
        for(String permission : perms){
            if(ActivityCompat.checkSelfPermission(activity,permission)!= PackageManager.PERMISSION_GRANTED){
                permissionsList.add(permission);
            }
        }

        //请求权限
        if(!permissionsList.isEmpty()){
            //将List转为数组
            String[] permissions = permissionsList.toArray(new String[permissionsList.size()]);
            ActivityCompat.requestPermissions(activity, permissions, 1);
        }
    }
}
