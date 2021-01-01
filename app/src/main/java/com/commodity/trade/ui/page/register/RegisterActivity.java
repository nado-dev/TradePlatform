package com.commodity.trade.ui.page.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.commodity.trade.R;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Aaron
 */
public class RegisterActivity extends AppCompatActivity {

    public static final int FRAG_CHOOSE_IDENTITY = 0;
    public static final int FRAG_CHOOSE_INVITE_CODE = 1;

    public ChooseIdentityFragment chooseIdentityFragment;
    public VerifyCodeFragment verifyCodeFragment;
    private ToFragmentListener mToFragmentListener;

    int currFragment = 0;

    @BindView(R.id.img_tab_back)
    ImageView imgTabBack;
    @BindView(R.id.tv_login_title)
    TextView tvLoginTitle;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.fragment)
    FrameLayout fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        chooseIdentityFragment = new ChooseIdentityFragment();
        verifyCodeFragment = new VerifyCodeFragment();

        mToFragmentListener = verifyCodeFragment;
        navigateToVerifyCodeFragment(FRAG_CHOOSE_IDENTITY);
    }


    @OnClick({R.id.tv_login_title, R.id.img_tab_back})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_tab_back) {
            backToLastFragmentOrExit();
        }
    }

    private void backToLastFragmentOrExit() {
        if (currFragment == FRAG_CHOOSE_IDENTITY) {
            finish();
        }
        if (currFragment == FRAG_CHOOSE_INVITE_CODE) {
            navigateToVerifyCodeFragment(FRAG_CHOOSE_IDENTITY);
        }
    }


    public void navigateToVerifyCodeFragment(int fragmentType) {
        if (fragmentType == FRAG_CHOOSE_IDENTITY) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, chooseIdentityFragment);
            hideAllFragment();
            transaction.show(chooseIdentityFragment);
            transaction.commit();
            currFragment = FRAG_CHOOSE_IDENTITY;
        }
        if (fragmentType == FRAG_CHOOSE_INVITE_CODE) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment, verifyCodeFragment);
            hideAllFragment();
            transaction.show(verifyCodeFragment);
            transaction.commit();
            currFragment = FRAG_CHOOSE_INVITE_CODE;
        }
    }

    public void hideAllFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (chooseIdentityFragment != null) {
            transaction.hide(chooseIdentityFragment);
        }
        if (verifyCodeFragment != null) {
            transaction.hide(verifyCodeFragment);
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0x6324 && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                if (null != mToFragmentListener ) {
                    Log.e("FWY", content);
                    mToFragmentListener.onTypeClick(content);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}