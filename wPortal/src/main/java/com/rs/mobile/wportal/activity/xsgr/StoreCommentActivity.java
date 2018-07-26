package com.rs.mobile.wportal.activity.xsgr;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rs.mobile.common.AppConfig;
import com.rs.mobile.common.C;
import com.rs.mobile.common.S;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.ViewPagerAdapter;
import com.rs.mobile.wportal.entity.CommentBean;
import com.rs.mobile.wportal.entity.DateDataBean;
import com.rs.mobile.wportal.fragment.xsgr.CommentFragment;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

import static com.rs.mobile.wportal.takephoto.CommonUtil.dip2px;

public class StoreCommentActivity extends BaseActivity {

    private List<Fragment> list;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    CommentBean bean;

    TextView tv_fen, tv_num, tv_star5, tv_star4, tv_star3, tv_star2, tv_star1;

    RatingBar ratingBar;

    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;


    int page = 1;
    int size = 5;

    private CommentFragment commentFragment1, commentFragment2, commentFragment3;
    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_my_comment);

        initView();

        setListener();

        initDate();

        initShopInfoData();
    }

    private void setListener() {

    }

    private void initDate() {
        list = new ArrayList<>();
        commentFragment1 = new CommentFragment();
        commentFragment2 = new CommentFragment();
        commentFragment3 = new CommentFragment();
        commentFragment1.setType(1);
        commentFragment2.setType(2);
        commentFragment3.setType(3);

        titles = new String[]{getResources().getString(R.string.commentall), getResources().getString(R.string.commentbad), getResources().getString(R.string.commentnosee)};
//        tabLayout.addTab(tabLayout.newTab().setText(titles[0]));
//        tabLayout.addTab(tabLayout.newTab().setText(titles[1]));
//        tabLayout.addTab(tabLayout.newTab().setText(titles[2]));
        list.add(commentFragment1);
        list.add(commentFragment2);
        list.add(commentFragment3);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this, list, titles);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        reflex(tabLayout);
    }


    private void initView() {
        tv_fen = (TextView) findViewById(R.id.textview_pingjunfen);
        tv_num = (TextView) findViewById(R.id.textview_num);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        progressBar1 = (ProgressBar) findViewById(R.id.progress_bar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progress_bar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progress_bar3);
        progressBar4 = (ProgressBar) findViewById(R.id.progress_bar4);
        progressBar5 = (ProgressBar) findViewById(R.id.progress_bar5);

        tv_star1 = (TextView) findViewById(R.id.textview_star1);
        tv_star2 = (TextView) findViewById(R.id.textview_star2);
        tv_star3 = (TextView) findViewById(R.id.textview_star3);
        tv_star4 = (TextView) findViewById(R.id.textview_star4);
        tv_star5 = (TextView) findViewById(R.id.textview_star5);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        LinearLayout close_btn = (LinearLayout) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void initShopInfoData() {

        HashMap<String, String> param = new HashMap<String, String>();

        param.put("lang_type", AppConfig.LANG_TYPE);
        param.put("token", S.getShare(StoreCommentActivity.this, C.KEY_JSON_TOKEN, ""));
        param.put("custom_code", S.getShare(StoreCommentActivity.this, C.KEY_JSON_CUSTOM_CODE, ""));
//        param.put("custom_code", "010530117822fbe4");
//        param.put("token", "186743935020f829f883e9fe-c8cf-4f60-9ed2-bd645cb1c118");
        param.put("pg", page + "");
        param.put("pagesize", "" + size);
        param.put("view_gbn", "1");
        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addSMPostRequest(new OkHttpHelper.CallbackLogic() {

            @Override
            public void onNetworkError(Request request, IOException e) {

            }

            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                bean = GsonUtils.changeGsonToBean(responseDescription, CommentBean.class);

                tv_fen.setText(bean.getAssess_avg());
                tv_num.setText(getResources().getString(R.string.gong) + bean.getAssess_cnt() + getResources().getString(R.string.tiao));
                ratingBar.setRating(Float.parseFloat(bean.getAssess_avg()));
                progressBar1.setProgress(Integer.parseInt(bean.getRating5()) * 100 / Integer.parseInt(bean.getAssess_cnt()));
                progressBar2.setProgress(Integer.parseInt(bean.getRating4()) * 100 / Integer.parseInt(bean.getAssess_cnt()));
                progressBar3.setProgress(Integer.parseInt(bean.getRating3()) * 100 / Integer.parseInt(bean.getAssess_cnt()));
                progressBar4.setProgress(Integer.parseInt(bean.getRating2()) * 100 / Integer.parseInt(bean.getAssess_cnt()));
                progressBar5.setProgress(Integer.parseInt(bean.getRating1()) * 100 / Integer.parseInt(bean.getAssess_cnt()));

                tv_star1.setText(bean.getRating1());
                tv_star2.setText(bean.getRating2());
                tv_star3.setText(bean.getRating3());
                tv_star4.setText(bean.getRating4());
                tv_star5.setText(bean.getRating5());

                String[] titles2 = new String[]{titles[0] + "(" + bean.getAssess_cnt() + ")", titles[1] + "(" +  bean.getRating1()+ ")", titles[2] + "(" + bean.getNot_conf() + ")"};
//                tabLayout.removeAllTabs();
//                tabLayout.addTab(tabLayout.newTab().setText(titles[0] + "(" + bean.getAssess_cnt() + ")"));
//                tabLayout.addTab(tabLayout.newTab().setText(titles[1] + "(" + (Integer.parseInt(bean.getRating2()) + Integer.parseInt(bean.getRating1())) + ")"));
//                tabLayout.addTab(tabLayout.newTab().setText(titles[2] + "(" + bean.getNot_conf() + ")"));
                viewPagerAdapter.setTitles(titles2);
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {

            }
        }, Constant.XS_BASE_URL + "AppSM/requestAssessList", param);

    }

}