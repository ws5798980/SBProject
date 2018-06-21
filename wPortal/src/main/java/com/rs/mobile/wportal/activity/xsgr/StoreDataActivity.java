package com.rs.mobile.wportal.activity.xsgr;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.ViewPagerAdapter;
import com.rs.mobile.wportal.fragment.xsgr.MyDateDayFragment;
import com.rs.mobile.wportal.fragment.xsgr.MyDateFreeFragment;
import com.rs.mobile.wportal.fragment.xsgr.MyDateMonthFragment;
import com.rs.mobile.wportal.fragment.xsgr.MyDateWeekFragment;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rs.mobile.wportal.takephoto.CommonUtil.dip2px;

public class StoreDataActivity extends BaseActivity {


    LoopView loopView1, loopView2, loopView3, loopView4, loopView5, loopView6;

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

                int position = tab.getPosition();
                viewPager.setCurrentItem(position);


                if (tab.getPosition() == 3) {


                    Dialog dialog = new Dialog(StoreDataActivity.this, R.style.dialog_holo_dark);
                    dialog.setContentView(R.layout.dialog_datechoose);

                    loopView1 = (LoopView) dialog.findViewById(R.id.lv1);
                    loopView2 = (LoopView) dialog.findViewById(R.id.lv2);
                    loopView3 = (LoopView) dialog.findViewById(R.id.lv3);
                    loopView4 = (LoopView) dialog.findViewById(R.id.lv4);
                    loopView5 = (LoopView) dialog.findViewById(R.id.lv5);
                    loopView6 = (LoopView) dialog.findViewById(R.id.lv6);

                    final List<String> mOptionsItems = new ArrayList<>();
                    final List<String> mOptionsItems2 = new ArrayList<>();
                    final List<String> mOptionsItems3 = new ArrayList<>();
                    final List<String> mOptionsItems4 = new ArrayList<>();
                    for (int i = 2017; i < 2050; i++) {
                        mOptionsItems.add(i + "年");
                    }
                    for (int i = 1; i < 12; i++) {
                        mOptionsItems2.add(i + "月");
                    }


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                    String nowdate = simpleDateFormat.format(new Date(System.currentTimeMillis()));

                    String year = nowdate.substring(0, 4);
                    String month = nowdate.substring(4, 6);
                    String day = nowdate.substring(6, 8);

                    int y = Integer.parseInt(year);
                    int m = Integer.parseInt(month);


                    if (month.substring(0, 1).equals("0")) {
                        month = month.substring(1, 2);
                    }
                    if (day.substring(0, 1).equals("0")) {
                        day = day.substring(1, 2);
                    }


                    if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
                        switch (m) {
                            case 1:
                            case 3:
                            case 5:
                            case 7:
                            case 8:
                            case 10:
                            case 12:
                                for (int i = 1; i <= 31; i++) {

                                    mOptionsItems3.add(i + "日");
                                    mOptionsItems4.add(i + "日");
                                }
                                break;

                            case 4:
                            case 6:
                            case 9:
                            case 11:
                                for (int i = 1; i <= 30; i++) {
                                    mOptionsItems3.add(i + "日");
                                    mOptionsItems4.add(i + "日");
                                }
                                break;
                            case 2:
                                for (int i = 1; i <= 29; i++) {
                                    mOptionsItems3.add(i + "日");
                                    mOptionsItems4.add(i + "日");
                                }
                                break;
                        }

                    } else {
                        switch (m) {
                            case 1:
                            case 3:
                            case 5:
                            case 7:
                            case 8:
                            case 10:
                            case 12:
                                for (int i = 1; i <= 31; i++) {
                                    mOptionsItems3.add(i + "日");
                                    mOptionsItems4.add(i + "日");
                                }
                                break;

                            case 4:
                            case 6:
                            case 9:
                            case 11:
                                for (int i = 1; i <= 30; i++) {
                                    mOptionsItems3.add(i + "日");
                                    mOptionsItems4.add(i + "日");
                                }
                                break;
                            case 2:
                                for (int i = 1; i <= 28; i++) {
                                    mOptionsItems3.add(i + "日");
                                    mOptionsItems4.add(i + "日");
                                }
                                break;
                        }
                    }

                    loopView3.setItems(mOptionsItems3);
                    loopView6.setItems(mOptionsItems4);

                    loopView1.setListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(int index) {
                            mOptionsItems3.clear();
                            int y = Integer.parseInt(mOptionsItems.get(index).substring(0, 4));

                            String month = mOptionsItems2.get(loopView2.getSelectedItem());
                            int m = 0;
                            if (month.length() == 3) {
                                m = Integer.parseInt(month.substring(0, 2));
                            } else {
                                m = Integer.parseInt(month.substring(0, 1));
                            }

                            if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
                                switch (m) {
                                    case 1:
                                    case 3:
                                    case 5:
                                    case 7:
                                    case 8:
                                    case 10:
                                    case 12:
                                        for (int i = 1; i <= 31; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;

                                    case 4:
                                    case 6:
                                    case 9:
                                    case 11:
                                        for (int i = 1; i <= 30; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;
                                    case 2:
                                        for (int i = 1; i <= 29; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;
                                }

                            } else {
                                switch (m) {
                                    case 1:
                                    case 3:
                                    case 5:
                                    case 7:
                                    case 8:
                                    case 10:
                                    case 12:
                                        for (int i = 1; i <= 31; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;

                                    case 4:
                                    case 6:
                                    case 9:
                                    case 11:
                                        for (int i = 1; i <= 30; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;
                                    case 2:
                                        for (int i = 1; i <= 28; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;
                                }
                            }

                            loopView3.setItems(mOptionsItems3);

                        }
                    });


                    loopView2.setListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(int index) {
                            mOptionsItems3.clear();
                            String month = mOptionsItems2.get(index);
                            int m = 0;
                            if (month.length() == 3) {
                                m = Integer.parseInt(month.substring(0, 2));
                            } else {
                                m = Integer.parseInt(month.substring(0, 1));
                            }

                            int y = Integer.parseInt(mOptionsItems.get(loopView1.getSelectedItem()).substring(0, 4));

                            if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
                                switch (m) {
                                    case 1:
                                    case 3:
                                    case 5:
                                    case 7:
                                    case 8:
                                    case 10:
                                    case 12:
                                        for (int i = 1; i <= 31; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;

                                    case 4:
                                    case 6:
                                    case 9:
                                    case 11:
                                        for (int i = 1; i <= 30; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;
                                    case 2:
                                        for (int i = 1; i <= 29; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;
                                }

                            } else {
                                switch (m) {
                                    case 1:
                                    case 3:
                                    case 5:
                                    case 7:
                                    case 8:
                                    case 10:
                                    case 12:
                                        for (int i = 1; i <= 31; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;

                                    case 4:
                                    case 6:
                                    case 9:
                                    case 11:
                                        for (int i = 1; i <= 30; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;
                                    case 2:
                                        for (int i = 1; i <= 28; i++) {
                                            mOptionsItems3.add(i + "日");
                                        }
                                        break;
                                }
                            }

                            loopView3.setItems(mOptionsItems3);

                        }
                    });


                    loopView4.setListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(int index) {
                            mOptionsItems4.clear();
                            int y = Integer.parseInt(mOptionsItems.get(index).substring(0, 4));

                            String month = mOptionsItems2.get(loopView5.getSelectedItem());
                            int m = 0;
                            if (month.length() == 3) {
                                m = Integer.parseInt(month.substring(0, 2));
                            } else {
                                m = Integer.parseInt(month.substring(0, 1));
                            }

                            if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
                                switch (m) {
                                    case 1:
                                    case 3:
                                    case 5:
                                    case 7:
                                    case 8:
                                    case 10:
                                    case 12:
                                        for (int i = 1; i <= 31; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;

                                    case 4:
                                    case 6:
                                    case 9:
                                    case 11:
                                        for (int i = 1; i <= 30; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;
                                    case 2:
                                        for (int i = 1; i <= 29; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;
                                }

                            } else {
                                switch (m) {
                                    case 1:
                                    case 3:
                                    case 5:
                                    case 7:
                                    case 8:
                                    case 10:
                                    case 12:
                                        for (int i = 1; i <= 31; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;

                                    case 4:
                                    case 6:
                                    case 9:
                                    case 11:
                                        for (int i = 1; i <= 30; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;
                                    case 2:
                                        for (int i = 1; i <= 28; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;
                                }
                            }

                            loopView6.setItems(mOptionsItems4);

                        }
                    });


                    loopView5.setListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(int index) {
                            mOptionsItems4.clear();
                            String month = mOptionsItems2.get(index);
                            int m = 0;
                            if (month.length() == 3) {
                                m = Integer.parseInt(month.substring(0, 2));
                            } else {
                                m = Integer.parseInt(month.substring(0, 1));
                            }

                            int y = Integer.parseInt(mOptionsItems.get(loopView4.getSelectedItem()).substring(0, 4));

                            if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
                                switch (m) {
                                    case 1:
                                    case 3:
                                    case 5:
                                    case 7:
                                    case 8:
                                    case 10:
                                    case 12:
                                        for (int i = 1; i <= 31; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;

                                    case 4:
                                    case 6:
                                    case 9:
                                    case 11:
                                        for (int i = 1; i <= 30; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;
                                    case 2:
                                        for (int i = 1; i <= 29; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;
                                }

                            } else {
                                switch (m) {
                                    case 1:
                                    case 3:
                                    case 5:
                                    case 7:
                                    case 8:
                                    case 10:
                                    case 12:
                                        for (int i = 1; i <= 31; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;

                                    case 4:
                                    case 6:
                                    case 9:
                                    case 11:
                                        for (int i = 1; i <= 30; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;
                                    case 2:
                                        for (int i = 1; i <= 28; i++) {
                                            mOptionsItems4.add(i + "日");
                                        }
                                        break;
                                }
                            }

                            loopView6.setItems(mOptionsItems4);

                        }
                    });


                    loopView1.setItemsVisibleCount(5);
                    loopView1.setTextSize(15);
                    loopView1.setCenterTextColor(getResources().getColor(R.color.theme_green));
                    loopView1.setOuterTextColor(getResources().getColor(R.color.textcolor_dp_666));
                    loopView1.setDividerColor(getResources().getColor(R.color.background_window));
                    loopView1.setItems(mOptionsItems);

                    loopView2.setItemsVisibleCount(5);
                    loopView2.setTextSize(15);
                    loopView2.setCenterTextColor(getResources().getColor(R.color.theme_green));
                    loopView2.setOuterTextColor(getResources().getColor(R.color.textcolor_dp_666));
                    loopView2.setDividerColor(getResources().getColor(R.color.background_window));
                    loopView2.setItems(mOptionsItems2);

                    loopView3.setItemsVisibleCount(5);
                    loopView3.setTextSize(15);
                    loopView3.setCenterTextColor(getResources().getColor(R.color.theme_green));
                    loopView3.setOuterTextColor(getResources().getColor(R.color.textcolor_dp_666));
                    loopView3.setDividerColor(getResources().getColor(R.color.background_window));
                    loopView3.setItems(mOptionsItems3);

                    loopView4.setItemsVisibleCount(5);
                    loopView4.setTextSize(15);
                    loopView4.setCenterTextColor(getResources().getColor(R.color.theme_green));
                    loopView4.setOuterTextColor(getResources().getColor(R.color.textcolor_dp_666));
                    loopView4.setDividerColor(getResources().getColor(R.color.background_window));
                    loopView4.setItems(mOptionsItems);

                    loopView5.setItemsVisibleCount(5);
                    loopView5.setTextSize(15);
                    loopView5.setCenterTextColor(getResources().getColor(R.color.theme_green));
                    loopView5.setOuterTextColor(getResources().getColor(R.color.textcolor_dp_666));
                    loopView5.setDividerColor(getResources().getColor(R.color.background_window));
                    loopView5.setItems(mOptionsItems2);

                    loopView6.setItemsVisibleCount(5);
                    loopView6.setTextSize(15);
                    loopView6.setCenterTextColor(getResources().getColor(R.color.theme_green));
                    loopView6.setOuterTextColor(getResources().getColor(R.color.textcolor_dp_666));
                    loopView6.setDividerColor(getResources().getColor(R.color.background_window));
                    loopView6.setItems(mOptionsItems4);


                    loopView1.setInitPosition(mOptionsItems.indexOf(year + "年"));
                    loopView4.setInitPosition(mOptionsItems.indexOf(year + "年"));

                    loopView2.setInitPosition(mOptionsItems2.indexOf(month + "月"));
                    loopView5.setInitPosition(mOptionsItems2.indexOf(month + "月"));

                    loopView3.setInitPosition(mOptionsItems3.indexOf(day + "日"));
                    loopView6.setInitPosition(mOptionsItems4.indexOf(day + "日"));

                    Button okButton = (Button) dialog.findViewById(R.id.bt_ok);

                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.i("xyz", mOptionsItems.get(loopView1.getSelectedItem()) + mOptionsItems2.get(loopView2.getSelectedItem()) + mOptionsItems3.get(loopView3.getSelectedItem()));

                            Log.i("xyz", mOptionsItems.get(loopView4.getSelectedItem()) + mOptionsItems2.get(loopView5.getSelectedItem()) + mOptionsItems4.get(loopView6.getSelectedItem()));

                        }
                    });

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

//
//    private void setDatePickerDividerAndTextColor(DatePicker datePicker) {
//
//        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);
//
//
//        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
//        for (int i = 0; i < mSpinners.getChildCount(); i++) {
//            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);
//
//
//            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
//            for (Field pf : pickerFields) {
//                if (pf.getName().equals("mSelectorWheelPaint")) {
//                    pf.setAccessible(true);
//                    Paint paint = new Paint();
//                    paint.setTextSize(sp2px(this, 15));
//                    paint.setTextAlign(Paint.Align.CENTER);
//                    paint.setColor(getResources().getColor(R.color.theme_green));
//                    try {
//                        pf.set(picker, paint);
//                    } catch (IllegalArgumentException e) {
//                        e.printStackTrace();
//                    } catch (Resources.NotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//
//            }
//            for (Field pf : pickerFields) {
//                if (pf.getName().equals("mSelectionDivider")) {
//                    pf.setAccessible(true);
//                    try {
//                        pf.set(picker, new ColorDrawable(this.getResources().getColor(R.color.background_window)));
//                    } catch (IllegalArgumentException e) {
//                        e.printStackTrace();
//                    } catch (Resources.NotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//
//            }
//
//            for (Field pf : pickerFields) {
//                if (pf.getName().equals("mSelectionDividersDistance")) {
//                    pf.setAccessible(true);
//                    try {
////                       pf.set(picker.getHeight(), 20);
//                        pf.set(picker, dip2px(this, 30));//按照需求在此处修改
//                    } catch (IllegalArgumentException e) {
//                        e.printStackTrace();
//                    } catch (Resources.NotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//
//            for (Field pf : pickerFields) {
//                if (pf.getName().equals("mSelectorElementHeight")) {
//                    pf.setAccessible(true);
//                    try {
////                       pf.set(picker.getHeight(), 20);
//                        pf.set(picker, dip2px(this, 30));//按照需求在此处修改
//                    } catch (IllegalArgumentException e) {
//                        e.printStackTrace();
//                    } catch (Resources.NotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//
//    }
//
//    private void changeDataPickerTextColor() {
//        try {
//            Field f[] = NumberPicker.class.getDeclaredFields();
//            for (Field field : f) {
//                if (field.getName().equals("mYearSpinner")) {
//                    field.setAccessible(true);
//                    Object yearPicker = new Object();
//                    yearPicker = field.get(NumberPicker.class);
//                    View childpicker;
//                    childpicker = (View) findViewById(Resources.getSystem().getIdentifier("year", "id", "android"));
//                    EditText textview = (EditText) childpicker.findViewById(Resources.getSystem().getIdentifier("numberpicker_input", "id", "android"));
//                    // textview.setTextSize(26);
//                    // textview.setPadding(-3, 0, -3, 0);
//                    textview.setTextColor(getResources().getColor(R.color.theme_green));
//                    // System.out.println("ss1:"+field);
//                }
//
//
//                if (field.getName().equals("mDaySpinner")) {
//                    field.setAccessible(true);
//                    Object yearPicker = new Object();
//                    yearPicker = field.get(NumberPicker.class);
//                    ((View) yearPicker).setVisibility(View.VISIBLE);
//                    ((View) yearPicker).setPadding(-2, 0, -2, 0);
//
//
//                    View childpicker;
//                    childpicker = (View) findViewById(Resources.getSystem().getIdentifier("month", "id", "android"));
//                    EditText textview = (EditText) childpicker.findViewById(Resources.getSystem().getIdentifier("numberpicker_input", "id", "android"));
//                    // textview.setTextSize(26);
//                    // textview.setPadding(-3, 0, -3, 0);
//                    textview.setTextColor(getResources().getColor(R.color.theme_green));
//                }
//
//
//                if (field.getName().equals("mMonthSpinner")) {
//                    field.setAccessible(true);
//                    Object yearPicker = new Object();
//                    yearPicker = field.get(NumberPicker.class);
//                    // ((View) yearPicker).setVisibility(View.VISIBLE);
//                    // ((View) yearPicker).setPadding(-2, 0, -2, 0);
//                    //
//                    View childpicker;
//                    childpicker = (View) findViewById(Resources.getSystem().getIdentifier("day", "id", "android"));
//                    EditText textview = (EditText) childpicker.findViewById(Resources.getSystem().getIdentifier("numberpicker_input", "id", "android"));
//                    // textview.setTextSize(26);
//                    // textview.setPadding(-3, 0, -3, 0);
//                    textview.setTextColor(getResources().getColor(R.color.theme_green));
//                }
//
//
//            }
//        } catch (SecurityException e) {
//            Log.d("ERROR", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            Log.d("ERROR", e.getMessage());
//        } catch (IllegalAccessException e) {
//            Log.d("ERROR", e.getMessage());
//        } catch (Exception e) {
//            Log.d("ERROR", e.getMessage());
//        }
//    }
//
//    public
//    static int sp2px(Context context, float
//            spValue) {
//
//        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//
//        return
//                (int) (spValue * fontScale + 0.5f);
//
//    }

}