package com.rs.mobile.wportal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rs.mobile.wportal.R;

/**
 * @author zhaoyun
 * @desc 功能描述
 * @date 2015/9/6 17:05
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isVisible;
    protected boolean isPrepared = false;
    protected boolean isLazyLoaded = false;

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }

    protected void showNoData(View parentView) {
        parentView.findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.VISIBLE);
    }

    protected void hideNoData(View parentView) {
        parentView.findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.GONE);
    }

    protected void showNoData(final View parentView, int drawable, String text, final OnClickListener onClickListener) {
        parentView.findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.VISIBLE);
        ImageView img = (ImageView) parentView.findViewById(com.rs.mobile.common.R.id.no_data_img);
        TextView no_data_text = (TextView) parentView.findViewById(com.rs.mobile.common.R.id.no_data_text);
        final TextView no_data_btn = (TextView) parentView.findViewById(com.rs.mobile.common.R.id.no_data_btn);

        if (drawable != 0)
            img.setImageResource(drawable);
        if (!text.equals(""))
            no_data_text.setText(text);
        if (onClickListener != null) {
            no_data_btn.setVisibility(View.VISIBLE);
            no_data_btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    parentView.findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.GONE);
                    onClickListener.onClick(arg0);
                }
            });
        }

    }

    protected void showNoData(final View parentView, String text, final OnClickListener onClickListener) {
        parentView.findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.VISIBLE);
        ImageView img = (ImageView) parentView.findViewById(com.rs.mobile.common.R.id.no_data_img);
        TextView no_data_text = (TextView) parentView.findViewById(com.rs.mobile.common.R.id.no_data_text);
        final TextView no_data_btn = (TextView) parentView.findViewById(com.rs.mobile.common.R.id.no_data_btn);

        img.setVisibility(View.GONE);
        if (!text.equals(""))
            no_data_text.setText(text);
        if (onClickListener != null) {
            no_data_btn.setVisibility(View.VISIBLE);
            no_data_btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    parentView.findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.GONE);
                    onClickListener.onClick(arg0);
                }
            });
        }

    }

    protected void showData(View parentView) {
        parentView.findViewById(com.rs.mobile.common.R.id.no_data_view).setVisibility(View.GONE);
        parentView.findViewById(com.rs.mobile.common.R.id.no_data_btn).setVisibility(View.GONE);
    }
    // Bundle savedState;
    //
    // public BaseFragment(){
    // super();
    // }
    //
    // public void onActivityCreated(Bundle savedInstanceState) {
    // super.onActivityCreated(savedInstanceState);
    // if(!restoreStateFromArguments()) {
    // onFirstTimeLaunched();
    // }
    // }
    //
    // protected void onFirstTimeLaunched() {
    // }
    //
    // public void onSaveInstanceState(Bundle outState) {
    // super.onSaveInstanceState(outState);
    // saveStateToArguments();
    // }
    //
    // public void onDestroyView() {
    // super.onDestroyView();
    // saveStateToArguments();
    // }
    //
    // private void saveStateToArguments() {
    // if(getView() != null) {
    // savedState = saveState();
    // }
    //
    // if(savedState != null) {
    // Bundle b = this.getArguments();
    // b.putBundle("internalSavedViewState8954201239547", savedState);
    // }
    // }
    //
    // private boolean restoreStateFromArguments() {
    // Bundle b = getArguments();
    // savedState = b.getBundle("internalSavedViewState8954201239547");
    // if(savedState != null) {
    // restoreState();
    // return true;
    // } else {
    // return false;
    // }
    // }
    //
    // private void restoreState() {
    // if(savedState != null) {
    // // For Example
    // //tv1.setText(savedState.getString("text"));
    // onRestoreState(savedState);
    // }
    // }
    //
    // protected void onRestoreState(Bundle savedInstanceState) {
    // }
    //
    // private Bundle saveState() {
    // Bundle state = new Bundle();
    // // For Example
    // //state.putString("text", tv1.getText().toString());
    // onSaveState(state);
    // return state;
    // }
    //
    // protected void onSaveState(Bundle outState) {
    // }

}
