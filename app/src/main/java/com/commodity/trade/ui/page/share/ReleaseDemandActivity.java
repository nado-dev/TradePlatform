package com.commodity.trade.ui.page.share;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.commodity.mylibrary.util.UriUtil;
import com.commodity.mylibrary.weight.LoadingDialog;
import com.commodity.trade.R;
import com.commodity.trade.entity.message_bean.ShareMessageBox;
import com.commodity.trade.entity.order.Order;
import com.commodity.trade.entity.share.ShareBean;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.entity.user.UserConfig;
import com.commodity.trade.network.request.share.ShareRequest;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * @author Aaron
 */
public class ReleaseDemandActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_UPLOAD_FILE = 0x6324;

    String docPath;
    User currentUser;

    @BindView(R.id.repaire_back)
    ImageView repairBack;
    @BindView(R.id.order_name)
    EditText edtOrderName;
    @BindView(R.id.order_unit_price)
    EditText edtOrderUnitPrice;
    @BindView(R.id.order_amount)
    EditText edtOrderAmount;
    @BindView(R.id.order_commission)
    EditText edtOrderCommission;
    @BindView(R.id.order_document_desc)
    TextView orderDocumentDesc;
    @BindView(R.id.order_document)
    LinearLayout orderDocument;
    @BindView(R.id.order_detail)
    EditText edtOrderDetail;
    @BindView(R.id.order_submit)
    Button orderSubmit;
    @BindView(R.id.order_invite_code)
    TextView orderInviteCode;
    @BindView(R.id.order_setter)
    ScrollView orderSetter;
    @BindView(R.id.order_show_success)
    LinearLayout orderShowSuccess;
    private String orderName;
    private float orderUnitPrice;
    private int orderAmount;
    private float orderCommission;
    private String orderDescription;
    private BmobFile bmobFile;
    private LoadingDialog loadingDialog;
    private String docUrl;
    private Order order;
    ShareRequest shareRequest = new ShareRequest();
    private String newInviteCode;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_demand);
        ButterKnife.bind(this);
        checkUserType();
        MutableLiveData <ShareBean> shareData = shareRequest.getShareData();
        shareData.observe(this, shareBean -> {
            if (shareBean.getErrorCode() == 200) {
                updateShareInfo(order.getObjectId(), false, newInviteCode, currentUser.getObjectId());
            }
        });
    }

    /**
     * 检查是否是SELLER
     */
    private void checkUserType() {
        currentUser = BmobUser.getCurrentUser(User.class);
        if (currentUser.getUserType() != UserConfig.USER_TYPE_SELLER) {
            shortToast("用户类型错误");
//            getWindow().getDecorView().setVisibility(View.GONE);
        }

    }

    private void shortToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.repaire_back, R.id.order_document, R.id.order_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.repaire_back:
                finish();
                break;
            case R.id.order_document:
                selectDocument();
                break;
            case R.id.order_submit:
                submitInfo();
                break;
            default:
        }
    }

    private void goDetail() {

    }

    /**
     * 上传信息
     */
    private void submitInfo() {
        checkAndInitInput();
        this.loadingDialog = new LoadingDialog(this, "正在登录...", R.mipmap.ic_dialog_loading);
        loadingDialog.show();
//        updateDocument();
        docUrl = "";
        updateOrderTable();
    }


    private void checkAndInitInput() {
        String orderName = edtOrderName.getText().toString();
        String sUnitPrice = edtOrderUnitPrice.getText().toString();
        String sAmount = edtOrderAmount.getText().toString();
        String sCommission = edtOrderCommission.getText().toString();
        String detail = edtOrderDetail.getText().toString();

        if (TextUtils.isEmpty(orderName) || TextUtils.isEmpty(sUnitPrice) || TextUtils.isEmpty(sAmount) ||
                TextUtils.isEmpty(sCommission)) {
            shortToast("输入有误");
            loadingDialog.dismiss();
        }

        float orderUnitPrice = Float.parseFloat(sUnitPrice);
        int orderAmount = Integer.parseInt(sAmount);
        float orderCommission = Float.parseFloat(sCommission);

        this.orderName = orderName;
        this.orderUnitPrice = orderUnitPrice;
        this.orderAmount = orderAmount;
        this.orderCommission = orderCommission;
        this.orderDescription = detail;

        // 文件初始化
//        File f = UriUtil.getFileByUri(this, uri);
//        this.bmobFile = new BmobFile(f);
    }

    /**
     * 上传合同文件
     */
    void selectDocument() {
        // 打开系统的文件选择器
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        this.startActivityForResult(intent, REQUEST_CODE_UPLOAD_FILE);
    }


    /**
     * 获取文件的真实路径
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            // 用户未选择任何文件，直接返回
            return;
        }
        // 获取用户选择文件的URI
        Uri uri = data.getData();
        this.uri = uri;
        Log.e("FWY", uri.toString());
        // 通过ContentProvider查询文件路径
        ContentResolver resolver = this.getContentResolver();
        String path = "";
        Cursor cursor = resolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // 未查询到，说明为普通文件，可直接通过URI获取文件路径
            path = uri.getPath();
            return;
        }
        if (cursor.moveToFirst()) {
            // 多媒体文件，从数据库中获取文件的真实路径
            path = cursor.getString(cursor.getColumnIndex("_data"));
        }
        if ("".equals(path)) {
            shortToast("上传失败");
            cursor.close();
            return;
        }
        docPath = path;
        // 更新状态信息
        orderDocumentDesc.setText("已选择");
        cursor.close();

//        updateDocument(docPath);
    }

    private void updateDocument() {
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Log.e("Bmob File", "上传文件成功:" + bmobFile.getFileUrl());
                    docUrl = bmobFile.getFileUrl();
                    // 更新数据表
                    updateOrderTable();
                } else {
                    e.printStackTrace();
                    Log.e("Bmob File", "上传文件失败：" + e.getMessage());
                    // test TODO
                    docUrl = "";
                    updateOrderTable();
                }
            }
        });
    }

    /**
     * 更新数据表
     */
    private void updateOrderTable() {
        this.order = new Order();
        order.setBuyerId(currentUser.getObjectId());
        order.setItemName(orderName);
        order.setCommission(orderCommission);
        order.setDocumentUrl(docUrl);
        order.setDetailDescription(orderDescription);
        order.setItemAmount(orderAmount);
        order.setUnitPrice(orderUnitPrice);
        order.save(new SaveListener <String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.d("Bmob", "插入成功");
                    // 上传Neo4j
                    updateNeo4j();
                } else {
                    Log.d("Bmob", "更新失败" + e.getMessage());
                }
            }
        });
    }

    private void updateNeo4j() {
        this.newInviteCode = order.getObjectId().substring(0, 3) + currentUser.getObjectId().substring(2, 5);
        String shareId = order.getObjectId();
        shareRequest.getShared(newInviteCode, shareId, currentUser.getUsername(), currentUser.getObjectId(),
                UserConfig.USER_TYPE_SELLER);
    }

    private void updateShareInfo(String shareId, boolean isFinish, String shareInviteCode, String newComer) {
        ShareMessageBox shareMessageBox = new ShareMessageBox();
        shareMessageBox.setFinish(isFinish);
        shareMessageBox.setShareId(shareId);
        shareMessageBox.setShareFrom(shareInviteCode);
        shareMessageBox.setShareTo(newComer);
        shareMessageBox.save(new SaveListener <String>() {
            @Override
            public void done(String s, BmobException e) {
                if ( null == e) {
                    loadingDialog.dismiss();
                    orderSetter.setVisibility(View.GONE);
                    orderShowSuccess.setVisibility(View.VISIBLE);
                    String s1 = "您的邀请码是"+newInviteCode;
                    orderInviteCode.setText(s1);
                    Toast.makeText(ReleaseDemandActivity.this, "参与成功，交易达成，请尽快联系买方，按照承诺完成交易", Toast.LENGTH_LONG).show();
                }
                else{
                    loadingDialog.dismiss();
                    e.printStackTrace();
                }
            }
        });
    }
}