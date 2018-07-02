package com.rs.mobile.wportal.activity.xsgr;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.rs.mobile.common.D;
import com.rs.mobile.common.activity.BaseActivity;
import com.rs.mobile.common.network.OkHttpHelper;
import com.rs.mobile.common.util.GsonUtils;
import com.rs.mobile.wportal.Constant;
import com.rs.mobile.wportal.R;
import com.rs.mobile.wportal.adapter.xsgr.ViewPagerAdapter;
import com.rs.mobile.wportal.biz.xsgr.QueryCategoryList;
import com.rs.mobile.wportal.fragment.xsgr.MyCommodityFragment;
import com.rs.mobile.wportal.fragment.xsgr.MyCommodityFragment2;
import com.rs.mobile.wportal.view.DividerItemDecoration;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import listpoplibrary.ListPopWindowManager;
import okhttp3.Request;

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
    private List<QueryCategoryList.DataBean> mData = new ArrayList<>();
    MyPopupWinAdapter popAdapter;
    private String catergoryId = "";

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
        titles = new String[]{getResources().getString(R.string.selling), getResources().getString(R.string.selling_done)};
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
                boolean canAdd = true;
                for (int i = 0;i<mData.size();i++){
                    if (mData.get(i).isAdd() == true){
                        canAdd = false;
                    }
                }
                if (canAdd) {
                    QueryCategoryList.DataBean bean = new QueryCategoryList.DataBean();
                    bean.setCustom_code("01071390009abcde");
                    bean.setId("");
                    bean.setImage_url("");
                    bean.setLevel_name("");
                    bean.setPid("0");
                    bean.setRid("0");
                    bean.setRank("0");
                    bean.setAdd(true);
                    popAdapter.addItem(bean, popAdapter.getItemCount());
//                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {

                }

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, R.drawable.divide_bg));
        popAdapter = new MyPopupWinAdapter(getApplicationContext(), R.layout.commodity_type_item, mData);
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
                catergoryId = "-1";
                requestCategoryList(catergoryId,false);
            }
        });
    }

    private void requestCategoryList(String catergoryId, final boolean isShow) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
        params.put("CategoryId", catergoryId);

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                QueryCategoryList entity = GsonUtils.changeGsonToBean(responseDescription, QueryCategoryList.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(entity.getStatus())) {
//                    if (list.size() <= 0) {
//                        list = entity.getData();
//                    }
                    if (entity.getData() != null && entity.getData().size() > 0) {
                        if (mData.size() > 0) {
                            mData.clear();
                        }
                        if (!isShow){
                            showCategoryPop();
                        }
                        mData.addAll(entity.getData());
                        recyclerView.getAdapter().notifyDataSetChanged();
                    } else {
                    }

                } else {
                    Toast.makeText(CommodityManagementActivity.this, entity.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {

            }

            @Override
            public void onNetworkError(Request request, IOException e) {

            }
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_QUERYCATEGORY, GsonUtils.createGsonString(params));
    }

    private void showCategoryPop() {
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
        popupWindow = ListPopWindowManager.getInstance().showCommonPopWindow(contentView, viewLine,
                CommodityManagementActivity.this, false, height);
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

    public class MyPopupWinAdapter extends BaseQuickAdapter<QueryCategoryList.DataBean, BaseViewHolder> {
        private Context context;
        private List<QueryCategoryList.DataBean> mdatas;

        public MyPopupWinAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<QueryCategoryList.DataBean> data) {
            super(layoutResId, data);
            this.context = context;
        }

//        @Override
//        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            LayoutInflater mInflater = LayoutInflater.from(context);
//            View view = mInflater.inflate(R.layout.commodity_type_item, parent,
//                    false);
//            return new ListViewHolder(view);
//        }

        @Override
        protected void convert(final BaseViewHolder helper, final QueryCategoryList.DataBean item) {
            final EditText name;
            final ImageView img;
            final Button done;
            img = helper.getView(R.id.commodity_img);
            name = helper.getView(R.id.commodity_name);
            done = helper.getView(R.id.edit_done);
            name.setText(item.getLevel_name() + "");
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    img.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                    name.setEnabled(true);

                    name.setSelectAllOnFocus(true);//设置全选
                    new Handler().postDelayed(new Runnable() {//加上延时
                        public void run() {
                            name.setFocusable(true);
                            name.setFocusableInTouchMode(true);
                            name.requestFocus();//请求获得焦点
                            //调用系统输入法
                            InputMethodManager inputManager = (InputMethodManager) name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.showSoftInput(name, 0);
                        }
                    }, 200);
                }
            });
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String edit = name.getText().toString().trim();
                    final String method;
                    HashMap<String, Object> params = new HashMap<>();
                    if (item.isAdd()){
                         method = Constant.COMMODITY_CATEGORYADD;
                        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
                        params.put("lang_type", "kor");
                        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
                        params.put("level_name", edit);
                        params.put("image_url", "");
                        params.put("rank", 0);
                        params.put("pid", 0);
                        params.put("rid", 0);
                    }else {
                         method = Constant.COMMODITY_CATEGORYEDIT;
                        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
                        params.put("lang_type", "kor");
                        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
                        params.put("level_name", edit);
                        params.put("image_url", "");
                        params.put("rank", item.getRank());
                        params.put("id", item.getId());
                    }

                    if (edit.length()!= 0){

                        OkHttpHelper okHttpHelper = new OkHttpHelper(context);
                        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
                            @Override
                            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                                QueryCategoryList entity = GsonUtils.changeGsonToBean(responseDescription, QueryCategoryList.class);
                                Log.i("123123", "responseDescription=" + responseDescription);
                                if ("1".equals(entity.getStatus())) {
                                    done.setVisibility(View.GONE);
                                    img.setVisibility(View.VISIBLE);
                                    requestCategoryList(catergoryId,true);

                                } else {
                                    Toast.makeText(CommodityManagementActivity.this, entity.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onBizFailure(String responseDescription, JSONObject data, String flag) {

                            }

                            @Override
                            public void onNetworkError(Request request, IOException e) {

                            }
                        }, Constant.XSGR_TEST_URL + method, GsonUtils.createGsonString(params));
                    }else {
                        Toast.makeText(context,"请填写分类名",Toast.LENGTH_SHORT).show();
                    }



                }
            });
            helper.getView(R.id.right).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    D.showDialog(CommodityManagementActivity.this, -1, "提示", "確定刪除此商品？", "确定", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            D.alertDialog.dismiss();
                            if (item.isAdd()){
                                mData.remove(helper.getAdapterPosition());
                                popAdapter.notifyItemRemoved(helper.getAdapterPosition());
                            }else {
                                delProduct(item.getId(),helper.getAdapterPosition());
                                EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);
                                easySwipeMenuLayout.resetStatus();
                            }

                        }
                    });


                }
            });
            helper.getView(R.id.right_menu_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    D.showDialog(CommodityManagementActivity.this, -1, "提示", "確定刪除此商品？", "确定", new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            D.alertDialog.dismiss();
                            if (item.isAdd()){
                                mData.remove(helper.getAdapterPosition());
                                popAdapter.notifyItemRemoved(helper.getAdapterPosition());
                            }else {
                                delProduct(item.getId(),helper.getAdapterPosition());
                                EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);
                                easySwipeMenuLayout.resetStatus();
                            }
                        }
                    });

                }
            });
            if (item.isAdd()) {
                new Handler().postDelayed(new Runnable() {//加上延时
                    public void run() {
                        img.setVisibility(View.GONE);
                        done.setVisibility(View.VISIBLE);
                        name.setEnabled(true);
                        name.setFocusable(true);
                        name.setFocusableInTouchMode(true);
                        name.requestFocus();//请求获得焦点
                        //调用系统输入法
                        InputMethodManager inputManager = (InputMethodManager) name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(name, 0);
                    }
                }, 100);
            }
        }


        public void addItem(QueryCategoryList.DataBean text, int position) {
            mData.add(position, text);
            notifyItemInserted(position);
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

    private void delProduct(String id, final int position) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("custom_code", "01071390009abcde");//S.get(XsStoreListActivity.this, C.KEY_JSON_CUSTOM_CODE)
        params.put("lang_type", "kor");
        params.put("token", "01071390009abcde64715017-0c81-4ef9-8b21-5e48c64cb455");//S.get(getActivity(), C.KEY_JSON_TOKEN)
        params.put("id", id);

        OkHttpHelper okHttpHelper = new OkHttpHelper(this);
        okHttpHelper.addPostRequest(new OkHttpHelper.CallbackLogic() {
            @Override
            public void onBizSuccess(String responseDescription, JSONObject data, String flag) {
                QueryCategoryList entity = GsonUtils.changeGsonToBean(responseDescription, QueryCategoryList.class);
                Log.i("123123", "responseDescription=" + responseDescription);
                if ("1".equals(entity.getStatus())) {
                    Toast.makeText(CommodityManagementActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                    mData.remove(position);
                    popAdapter.notifyItemRemoved(position);
                } else {
                    Toast.makeText(CommodityManagementActivity.this, entity.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onBizFailure(String responseDescription, JSONObject data, String flag) {
                Log.e("responseDescription",responseDescription);
//                Log.e("JSONObject",data.toString());
                Log.e("flag145",flag);

            }

            @Override
            public void onNetworkError(Request request, IOException e) {

            }
        }, Constant.XSGR_TEST_URL + Constant.COMMODITY_CATEGORYDEL, GsonUtils.createGsonString(params));
    }
}
