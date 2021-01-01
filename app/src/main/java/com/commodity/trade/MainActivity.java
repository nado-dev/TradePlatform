package com.commodity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.commodity.mylibrary.weight.NoScrollViewPager;
import com.commodity.trade.ui.adapter.SectionsPagerAdapter;
import com.commodity.trade.ui.page.main.FragmentOne;
import com.commodity.trade.ui.page.main.FragmentTwo;
import com.commodity.trade.ui.page.main.ToFragmentListener;
import com.commodity.trade.util.Config;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    @BindView(R.id.nav_view_pager)
    NoScrollViewPager navViewPager;
    @BindView(R.id.container)
    ConstraintLayout container;
    ToFragmentListener mToFragmentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
        initBottomNavigationView();
    }

    private void initBottomNavigationView() {
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.m_navigation_home:
                    navViewPager.setCurrentItem(0);
                    break;
                case R.id.m_navigation_me:
                    navViewPager.setCurrentItem(1);
                    break;
                default:
            }
            return true;
        });
    }

    private void initViewPager() {
        // 配置ViewPager 用于设置左右滑动的页面
        navViewPager.setOffscreenPageLimit(2);
        List<Fragment> fragments = new ArrayList<>();
        FragmentOne fragmentOne = new FragmentOne();
        mToFragmentListener = fragmentOne;
        fragments.add(fragmentOne);
        fragments.add(new FragmentTwo());

        // 向View Pager内添加适配器 用于与数据的绑定
        navViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
        navViewPager.addOnPageChangeListener(this);
        navViewPager.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                navView.setSelectedItemId(R.id.m_navigation_home);
                break;
            case 1:
                navView.setSelectedItemId(R.id.m_navigation_me);
                break;
            default:
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            // 扫描二维码/条码回传
            if (requestCode == 0x5379 && resultCode == RESULT_OK) {
                if (data != null) {
                    String content = data.getStringExtra(Constant.CODED_CONTENT);
                    mToFragmentListener.onTypeClick(content);

            }
        }
    }
}