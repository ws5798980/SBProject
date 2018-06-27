package com.rs.mobile.wportal.activity.xsgr;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.ViewPagerAdapter;
import com.rs.mobile.wportal.fragment.xsgr.MyCommodityFragment;
import com.rs.mobile.wportal.fragment.xsgr.MyCommodityFragment2;
import com.rs.mobile.wportal.view.DividerItemDecoration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import listpoplibrary.ListPopWindowManager;

import static com.rs.mobile.wportal.takephoto.CommonUtil.dip2px;

public class CommodityManagementActivity extends BaseActivity {
    private List<Fragment> list;
    ViewPagerAdapter viewPagerAdapter;
    MyCommodityFragment myCommodityFragment;
    MyCommodityFragment2 myCommodityFragment2;
    TabLayout tabLayout;
    ViewPager viewPager;
    View viewLine;
    View contentView;
    RecyclerView recyclerView;
    private LinearLayout select, closeBtn;
    private String[] titles;
    private PopupWindow popupWindow;
    private List<String> mData = new ArrayList<>();
    MyPopupWinAdapter popAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_management);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        myCommodityFragment = new MyCommodityFragment();
        myCommodityFragment2 = new MyCommodityFragment2();
        titles = new String[]{getResources().getString(R.string.neworder), getResources().getString(R.string.done)};
        list.add(myCommodityFragment);
        list.add(myCommodityFragment2);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this, list, titles);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        reflex(tabLayout);
    }

    private void initView() {
        closeBtn = (LinearLayout) findViewById(R.id.close_btn);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewLine = findViewById(R.id.view_line);
        select = (LinearLayout) findViewById(R.id.layout_select);
        initType();
    }

    private void initType() {
        contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.commodity_type, null, false);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view);
        EditText all = (EditText) contentView.findViewById(R.id.commodity_name);
        all.setText("全部分类");
        ImageView add = (ImageView) contentView.findViewById(R.id.commodity_img);
        add.setImageResource(R.drawable.icon_add_category);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mData.contains("")) {
                    popAdapter.addItem("",popAdapter.getItemCount());
//                    recyclerView.getAdapter().notifyDataSetChanged();
                }else {

                }

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, R.drawable.divide_bg));
        popAdapter = new MyPopupWinAdapter(getApplicationContext(), mData);
        recyclerView.setAdapter(popAdapter);
    }

    private void initListener() {
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout title = (LinearLayout) findViewById(R.id.my_title);
//                int titleHeight = title.getHeight();
                int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                title.measure(h, 0);
                //标题栏高度
                int titleHeight = title.getMeasuredHeight();
                //状态栏高度
                int statusHeight = getStatusBarHeight(CommodityManagementActivity.this);
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                //屏幕高度
                int screenHeight = metrics.heightPixels;
                //popupwindow高度
                int height = screenHeight - titleHeight - statusHeight;
                popupWindow = ListPopWindowManager.getInstance().showCommonPopWindow(contentView, viewLine, CommodityManagementActivity.this, false, height);
                initList();
            }
        });
    }

    private void initList() {
        List<String> list = new ArrayList<>();
        list.add("K记饭桶");
        list.add("人气明星套餐");
        list.add("鸡翅/鸡排");
        list.add("小食/配餐");
        list.add("饮料/果汁");
        mData.clear();
        mData.addAll(list);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public class MyPopupWinAdapter extends RecyclerView.Adapter<MyPopupWinAdapter.ListViewHolder> {
        private Context context;
        private List<String> mdatas;

        public MyPopupWinAdapter(Context context, List<String> datas) {
            this.context = context;
            this.mdatas = datas;
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            View view = mInflater.inflate(R.layout.commodity_type_item, parent,
                    false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            String type = mdatas.get(position);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.img.setVisibility(View.GONE);
                    holder.done.setVisibility(View.VISIBLE);
                    holder.name.setEnabled(true);

                    holder.name.setSelectAllOnFocus(true);//设置全选
                    new Handler().postDelayed(new Runnable() {//加上延时
                        public void run() {
                            holder.name.setFocusable(true);
                            holder.name.setFocusableInTouchMode(true);
                            holder.name.requestFocus();//请求获得焦点
                            //调用系统输入法
                            InputMethodManager inputManager = (InputMethodManager) holder.name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.showSoftInput(holder.name, 0);
                        }
                    }, 200);
                }
            });
            holder.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.img.setVisibility(View.VISIBLE);
                    holder.done.setVisibility(View.GONE);
                    holder.name.setEnabled(false);
                    holder.name.clearFocus();
                }
            });
            holder.setData(type);

        }

        public void addItem(String text, int position) {
            mData.add(position, text);
            notifyItemInserted(position);

            new Handler().postDelayed(new Runnable() {//加上延时
                public void run() {
                    ListViewHolder holder = (ListViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                    holder.img.setVisibility(View.GONE);
                    holder.done.setVisibility(View.VISIBLE);
                    holder.name.setEnabled(true);
                    holder.name.setFocusable(true);
                    holder.name.setFocusableInTouchMode(true);
                    holder.name.requestFocus();//请求获得焦点
                    //调用系统输入法
                    InputMethodManager inputManager = (InputMethodManager) holder.name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(holder.name, 0);
                }
            }, 100);
        }

        @Override
        public int getItemCount() {
            return mdatas != null ? mdatas.size() : 0;
        }

        public class ListViewHolder extends RecyclerView.ViewHolder {
            private EditText name;
            private ImageView img;
            private Button done;

            public ListViewHolder(View itemView) {
                super(itemView);
                name = (EditText) itemView.findViewById(R.id.commodity_name);
                img = (ImageView) itemView.findViewById(R.id.commodity_img);
                done = (Button) itemView.findViewById(R.id.edit_done);
            }

            public void setData(String type) {
                name.setText(type);
            }
        }
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
}
