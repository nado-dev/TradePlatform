package com.commodity.trade.ui.page.person_info;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.commodity.mylibrary.weight.LoadingDialog;
import com.commodity.trade.R;
import com.commodity.trade.entity.user.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.UpdateListener;

public class PersonInfoActivity extends AppCompatActivity {

    @BindView(R.id.userinfo_back)
    ImageView userinfoBack;
    @BindView(R.id.userinfo_uid)
    TextView userinfoUid;
    @BindView(R.id.userinfo_uname)
    TextView userinfoUname;
    @BindView(R.id.userinfo_phone_verify)
    TextView usernfoPhoneVerify;
    @BindView(R.id.userinfo_phone)
    TextView userinfoPhone;
    @BindView(R.id.userinfo_invite_code)
    TextView userinfoInviteCode;
    @BindView(R.id.userinfo_user_type)
    TextView userinfoUserType;
    @BindView(R.id.userinfo_register_time)
    TextView userinfoRegisterTime;
    @BindView(R.id.userinfo_update_1)
    Button userinfoUpdate1;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);

        loadUserInfo();
    }

    private void loadUserInfo() {
        currentUser = BmobUser.getCurrentUser(User.class);
        String id = currentUser.getObjectId();
        String username = currentUser.getUsername();
        Boolean phoneNumberVerified = currentUser.getMobilePhoneNumberVerified();
        if (phoneNumberVerified == null) {
            phoneNumberVerified = true;
        }
        String phoneNumber = currentUser.getMobilePhoneNumber();
        String invitorCode = id.substring(0,6);
        String userType = currentUser.convertTypeToString(currentUser.getUserType());
        String createdAt = currentUser.getCreatedAt();

        userinfoUid.setText(id);
        userinfoUname.setText(username);
        usernfoPhoneVerify.setText(phoneNumberVerified?"是":"否");
        userinfoPhone.setText(phoneNumber);
        userinfoInviteCode.setText(invitorCode);
        userinfoUserType.setText(userType);
        userinfoRegisterTime.setText(createdAt);
    }

    @OnClick({R.id.userinfo_back, R.id.userinfo_update_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userinfo_back:
                finish();
                break;
            case R.id.userinfo_update_1:
                EditAndUpdateInfo();
                break;
            default:
        }
    }

    private void EditAndUpdateInfo() {
       // 更新用户名
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).setTitle("输入新的用户名")
                .setIcon(android.R.drawable.ic_menu_edit)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        LoadingDialog dialog = new LoadingDialog(PersonInfoActivity.this, "加载中",R.mipmap.ic_dialog_loading);
                        dialog.show();
                        String string = et.getText().toString();
                        currentUser.setUsername(string);
                        currentUser.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e != null) {
                                    Toast.makeText(getApplicationContext(),e.getMessage() ,Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    e.printStackTrace();
                                    return;
                                }
                                Toast.makeText(getApplicationContext(),"更新成功" ,Toast.LENGTH_LONG).show();
                                // 更新缓存
                                BmobUser.fetchUserInfo(new FetchUserInfoListener <BmobUser>() {
                                    @Override
                                    public void done(BmobUser bmobUser, BmobException e) {
                                        if (e == null) {
                                            Log.d("FWY", "update User" + bmobUser.getUsername());
                                            // 刷新页面
                                            loadUserInfo();
                                            dialog.dismiss();
                                        }
                                        else {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }).setNegativeButton("取消",null).show();
    }
}