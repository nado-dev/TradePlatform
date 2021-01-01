package com.commodity.trade.ui.page.announce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.commodity.trade.R;
import com.commodity.trade.entity.announce.Announce;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Aaron
 */
public class DetailedAnnounceActivity extends AppCompatActivity {

    @BindView(R.id.announce_detail_back)
    ImageView announceDetailBack;
    @BindView(R.id.announce_detail_title)
    TextView announceDetailTitle;
    @BindView(R.id.announce_detail_time)
    TextView announceDetailTime;
    @BindView(R.id.announce_detail_id)
    TextView announceDetailId;
    @BindView(R.id.announce_detail_content)
    TextView announceDetailContent;
    private Announce announce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_announce);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        this.announce = (Announce) intent.getSerializableExtra("info");
        initData();
    }

    private void initData() {
        announceDetailTitle.setText(announce.getTitle());
        announceDetailContent.setText(announce.getContent());
        announceDetailId.setText(String.valueOf(announce.getAnnounceId()));
        announceDetailTime.setText(announce.translateTimeStampToString());
    }

    @OnClick(R.id.announce_detail_back)
    public void onViewClicked() {
        finish();
    }
}