package com.rs.mobile.wportal.activity.xsgr;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.MainADAdapter;
import com.rs.mobile.wportal.adapter.xsgr.MainBusinessAdapter;
import com.rs.mobile.wportal.entity.MainADListItem;
import com.rs.mobile.wportal.entity.MainBusinessListItem;

import java.util.ArrayList;
import java.util.List;

public class XsHomeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView1, mRecyclerView2;
    private MainADAdapter mAdapter1;
    private MainBusinessAdapter mAdapter2;

    private List<MainADListItem> mDataList1;
    private List<MainBusinessListItem> mDataList2;

    private Integer tb_totalCount = 16;
    private Integer side_totalCount = 10;
    private ImageView[] iv_topH = new ImageView[tb_totalCount];
    private ImageView[] iv_bottomH = new ImageView[tb_totalCount];
    private ImageView[] iv_leftH = new ImageView[side_totalCount];
    private ImageView[] iv_rightH = new ImageView[side_totalCount];
    private TextView[] iv_topCollect = new TextView[tb_totalCount];
    private TextView[] tv_topCollect2 = new TextView[side_totalCount];
    private TextView[] tv_topCollect3 = new TextView[side_totalCount];
    private TextView[] tv_topCollect4 = new TextView[tb_totalCount];
    private TextView tv_dial_btn;
    private ImageView btnScan;

    private ScrollView scrollView, leftScrollView, rightScrollView;

    public ScrollView sv_TopNumber1, sv_TopNumber2, sv_TopNumber3, sv_TopNumber4;

    public String[] ret_TotalNumber = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_home);

        initView();
    }

    private void initView(){
        mRecyclerView1 = (RecyclerView) findViewById(R.id.rv_list1);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this));
        mDataList1 = new ArrayList<>();
        for(int i=0;i<4;i++){
            MainADListItem item = new MainADListItem();
            item.imgRes = R.drawable.banner02;
            mDataList1.add(item);
        }
        mAdapter1 = new MainADAdapter(R.layout.list_item_main_ad, mDataList1);
        mRecyclerView1.setAdapter(mAdapter1);

        mRecyclerView2 = (RecyclerView) findViewById(R.id.rv_list2);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        mDataList2 = new ArrayList<>();
        for(int i=0;i<4;i++){
            MainBusinessListItem item = new MainBusinessListItem();
            item.imgRes = R.drawable.img_shop03;
            item.businessNm = "shop name";
            item.address = "1111111";
            item.distance = "800m";
            item.heart = R.drawable.icon_shop_heart;
            item.ratingBar = (float) 4.5;
            mDataList2.add(item);
        }
        mAdapter2 = new MainBusinessAdapter(R.layout.list_item_main_business, mDataList2);
        mRecyclerView2.setAdapter(mAdapter2);


        sv_TopNumber1 = (ScrollView)findViewById(R.id.TopNumber1);
        sv_TopNumber2 = (ScrollView)findViewById(R.id.TopNumber2);
        sv_TopNumber3 = (ScrollView)findViewById(R.id.TopNumber3);
        sv_TopNumber4 = (ScrollView)findViewById(R.id.TopNumber4);

        for(int i = 0; i < ret_TotalNumber.length; i++)
        {
            ret_TotalNumber[i] = "1";
        }

        tv_dial_btn = (TextView)findViewById(R.id.dial_btn);
        tv_dial_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String retTotal = ret_TotalNumber[0] + ret_TotalNumber[1] + ret_TotalNumber[2] + ret_TotalNumber[3];
                Toast.makeText(getApplicationContext(), retTotal, Toast.LENGTH_LONG).show();
            }
        });


        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        leftScrollView = (ScrollView) findViewById(R.id.LeftScrollView);
        rightScrollView = (ScrollView) findViewById(R.id.RightScrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                leftScrollView.getParent().requestDisallowInterceptTouchEvent(false);
                rightScrollView.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        leftScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        rightScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        int resID = 0;
        String packName = "";
        String resourceStringID = "";
        for(int i=0; i < tb_totalCount; i++)
        {
            resourceStringID = "topH" + Integer.toString((i+1));
            packName = this.getPackageName(); // 패키지명
            resID = getResources().getIdentifier(resourceStringID, "id", packName);
            iv_topH[i] =  (ImageView)findViewById(resID);

            resourceStringID = "topCollect1_" + Integer.toString((i+1));
            resID = getResources().getIdentifier(resourceStringID, "id", packName);
            iv_topCollect[i] = (TextView)findViewById(resID);

            iv_topH[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0; i < iv_topH.length; i++) {
                        String resIDs = "topH" + Integer.toString((i+1));
                        if (v.getId() == getResources().getIdentifier(resIDs, "id", getApplicationContext().getPackageName()))
                        {
                            scrollToView(iv_topCollect[i], sv_TopNumber1 , 0);
                            ret_TotalNumber[0] = Integer.toString(i+1);
                        }
                    }
                }
            });

            resourceStringID = "bottomH" + Integer.toString((i+1));
            packName = this.getPackageName();
            resID = getResources().getIdentifier(resourceStringID, "id", packName);
            iv_bottomH[i] = (ImageView)findViewById(resID);

            resourceStringID = "topCollect4_" + Integer.toString((i+1));
            resID = getResources().getIdentifier(resourceStringID, "id", packName);
            tv_topCollect4[i] = (TextView)findViewById(resID);

            iv_bottomH[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0; i < iv_rightH.length; i++) {
                        String resIDs = "bottomH" + Integer.toString((i+1));
                        if (v.getId() == getResources().getIdentifier(resIDs, "id", getApplicationContext().getPackageName()))
                        {
                            scrollToView(tv_topCollect4[i], sv_TopNumber4, 0);
                            ret_TotalNumber[3] = Integer.toString(i+1);
                        }
                    }
                }
            });
        }

        //세로 번호
        for(int i=0; i < side_totalCount; i++)
        {
            resourceStringID = "leftH" + Integer.toString((i+1));
            packName = this.getPackageName();
            resID = getResources().getIdentifier(resourceStringID, "id", packName);
            iv_leftH[i] = (ImageView)findViewById(resID);

            resourceStringID = "topCollect2_" + Integer.toString((i+1));
            resID = getResources().getIdentifier(resourceStringID, "id", packName);
            tv_topCollect2[i] = (TextView)findViewById(resID);

            iv_leftH[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0; i < iv_leftH.length; i++)
                    {
                        String resIDs = "leftH" + Integer.toString((i+1));
                        if (v.getId() == getResources().getIdentifier(resIDs, "id", getApplicationContext().getPackageName()))
                        {
                            scrollToView(tv_topCollect2[i], sv_TopNumber2 , 0);
                            ret_TotalNumber[1] = Integer.toString(i+1);
                        }
                    }
                }
            });

            resourceStringID = "rightH" + Integer.toString((i+1));
            packName = this.getPackageName();
            resID = getResources().getIdentifier(resourceStringID, "id", packName);
            iv_rightH[i] = (ImageView)findViewById(resID);

            resourceStringID = "topCollect3_" + Integer.toString((i+1));
            resID = getResources().getIdentifier(resourceStringID, "id", packName);
            tv_topCollect3[i] = (TextView)findViewById(resID);

            iv_rightH[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0; i < iv_rightH.length; i++)
                    {
                        String resIDs = "rightH" + Integer.toString((i+1));
                        if(v.getId() == getResources().getIdentifier(resIDs, "id", getApplicationContext().getPackageName()))
                        {
                            scrollToView(tv_topCollect3[i], sv_TopNumber3, 0);
                            ret_TotalNumber[2] = Integer.toString(i+1);
                        }
                    }
                }
            });
        }
    }




    public static void scrollToView(View view, final ScrollView scrollView, int count) {
        if (view != null && view != scrollView) {
            count += view.getTop();
            scrollToView((View) view.getParent(), scrollView, count);
        } else if (scrollView != null) {
            final int finalCount = count;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, finalCount);
                }
            }, 200);
        }
    }
}
