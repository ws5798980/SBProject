package com.rs.mobile.wportal.activity.xsgr;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.ViewPagerAdapter;
import com.rs.mobile.wportal.fragment.xsgr.MyDateDayFragment;
import com.rs.mobile.wportal.fragment.xsgr.MyDateFreeFragment;
import com.rs.mobile.wportal.fragment.xsgr.MyDateMonthFragment;
import com.rs.mobile.wportal.fragment.xsgr.MyDateWeekFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.rs.mobile.wportal.takephoto.CommonUtil.dip2px;

public class StoreDataActivity extends BaseActivity {


    int i=0;


    private List<Fragment> list;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    private MyDateDayFragment myDateDayFragment;
    private MyDateWeekFragment myDateWeekFragment;
    private MyDateMonthFragment myDateMonthFragment;
    private MyDateFreeFragment myDateFreeFragment;

    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xs_my_data);

        initView();

        setListener();

        initDate();
    }

    private void setListener() {





        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position=tab.getPosition();
                viewPager.setCurrentItem(position);


                if (tab.getPosition() == 3) {


                    Dialog dialog = new Dialog(StoreDataActivity.this,R.style.dialog_holo_dark);
                    DatePicker datePicker= (DatePicker) dialog.findViewById(R.id.date1);

                    dialog.setContentView(R.layout.dialog_datechoose);
                    setDatePickerDividerAndTextColor(datePicker);
                    dialog.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initDate() {
        list = new ArrayList<>();
        myDateDayFragment = new MyDateDayFragment();
        myDateWeekFragment = new MyDateWeekFragment();
        myDateMonthFragment = new MyDateMonthFragment();
        myDateFreeFragment = new MyDateFreeFragment();
        titles = new String[]{getResources().getString(R.string.today), getResources().getString(R.string.week), getResources().getString(R.string.month), getResources().getString(R.string.free)};
        tabLayout.addTab(tabLayout.newTab().setText(titles[0]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[1]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[2]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[3]));

        list.add(myDateDayFragment);
        list.add(myDateWeekFragment);
        list.add(myDateMonthFragment);
        list.add(myDateFreeFragment);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this, list, titles);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
//        tabLayout.setupWithViewPager(viewPager);
        reflex(tabLayout);
    }


    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);


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



    private void setDatePickerDividerAndTextColor(DatePicker datePicker) {

        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);

        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);


            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectorWheelPaint")) {
                    pf.setAccessible(true);
                    Paint paint = new Paint();
                    paint.setTextSize(15);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setColor(getResources().getColor(R.color.theme_green));
                    try {
                        pf.set(picker, paint);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }

            }
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(this.getResources().getColor(R.color.white)));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }

            }
        }

    }


}
