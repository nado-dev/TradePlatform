package com.commodity.trade.ui.page.message;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.commodity.trade.R;
import com.commodity.trade.ui.adapter.SectionsPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.msg_nav_view)
    BottomNavigationView msgNavView;
    @BindView(R.id.msg_nav_view_pager)
    ViewPager msgNavViewPager;
    @BindView(R.id.msg_detail_back)
    ImageView msgDetailBack;
    @BindView(R.id.container)
    LinearLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initViewPager();
        initBottomNavigationView();
    }

    private void initBottomNavigationView() {
        msgNavView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.m_navigation_invite:
                    msgNavViewPager.setCurrentItem(0);
                    break;
                case R.id.m_navigation_share:
                    msgNavViewPager.setCurrentItem(1);
                    break;
                default:
            }
            return true;
        });
    }

    private void initViewPager() {
        // 配置ViewPager 用于设置左右滑动的页面
        msgNavViewPager.setOffscreenPageLimit(2);
        List <Fragment> fragments = new ArrayList <>();
        fragments.add(new InviteMessageFragment());
        fragments.add(new ShareMessageFragment());

        // 向View Pager内添加适配器 用于与数据的绑定
        msgNavViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
        msgNavViewPager.addOnPageChangeListener(this);
        msgNavViewPager.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                msgNavView.setSelectedItemId(R.id.m_navigation_invite);
                break;
            case 1:
                msgNavView.setSelectedItemId(R.id.m_navigation_share);
                break;
            default:
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.msg_detail_back)
    public void onViewClicked() {
        finish();
    }
}