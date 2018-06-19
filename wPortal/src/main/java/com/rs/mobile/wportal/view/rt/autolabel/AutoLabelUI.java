package com.rs.mobile.wportal.view.rt.autolabel;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rs.mobile.wportal.R;

/*
 * Copyright (C) 2015 David Pizarro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class AutoLabelUI extends AutoViewGroup implements Label.OnClickCrossListener, Label.OnLabelClickListener {

    private static final String LOG_TAG = AutoLabelUI.class.getSimpleName();
    private static final int EMPTY = 0;
    private int labelsCounter = EMPTY;

    private final Context mContext;
    /** Label左侧textView的size */
    private int mTextSize;
    /** Label左侧textView的textColor */
    private ColorStateList mTextColor;
    private int DEFAULT_TEXT_COLOR = R.color.textColor_rt_toolbar_title;
    /** Label的background */
    private int mBackgroundResource;
    private int DEFAULT_LABEL_BACKGROUND = android.R.color.white;
    /** Label右侧的crossIcon的resId */
    private int mIconCross;
    /** Label的left、top、right、bottom的padding */
    private int mLabelPadding;
    private int mLabelPaddingLeft;
    private int mLabelPaddingTop;
    private int mLabelPaddingRight;
    private int mLabelPaddingBottom;
    /** 不同Label的marginLeft、marginTop、marginRight、marginBottom */
    private int mLabelMargin;
    private int mLabelMarginLeft;
    private int mLabelMarginTop;
    private int mLabelMarginRight;
    private int mLabelMarginBottom;

    private AutoLabelUISettings mAutoLabelUISettings;
    private int mMaxLabels = AutoLabelUISettings.DEFAULT_MAX_LABELS;
    private boolean mShowCross = AutoLabelUISettings.DEFAULT_SHOW_CROSS;
    private boolean mLabelsClickables = AutoLabelUISettings.DEFAULT_LABELS_CLICKABLES;

    private OnRemoveLabelListener listenerOnRemoveLabel;
    private OnLabelsCompletedListener listenerOnLabelsCompleted;
    private OnLabelsEmptyListener listenerOnLabelsEmpty;
    private OnLabelClickListener listenerOnLabelClick;

    /**
     * Default constructor
     */
    public AutoLabelUI(Context context) {
        this(context, null);
    }

    /**
     * Default constructor
     */
    public AutoLabelUI(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Default constructor
     */
    public AutoLabelUI(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        if (!isInEditMode()) {
            getAttributes(attrs);
        }
    }

    /**
     * Retrieve styles attributes
     */
    private void getAttributes(AttributeSet attrs) {
        TypedArray typedArray = mContext
                .obtainStyledAttributes(attrs, R.styleable.AutoLabelUI, 0, 0);

        if (typedArray != null) {
            try {
                mTextSize = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_text_size,
                        getResources().getDimensionPixelSize(R.dimen.text_size_m));
                mTextColor = typedArray.getColorStateList(R.styleable.AutoLabelUI_text_color);
                if (mTextColor == null) {
                    mTextColor = getResources().getColorStateList(DEFAULT_TEXT_COLOR);
                }
                mBackgroundResource = typedArray.getResourceId(R.styleable.AutoLabelUI_label_background_res,
                        DEFAULT_LABEL_BACKGROUND);
                mMaxLabels = typedArray.getInteger(R.styleable.AutoLabelUI_max_labels,
                        AutoLabelUISettings.DEFAULT_MAX_LABELS);
                mShowCross = typedArray.getBoolean(R.styleable.AutoLabelUI_show_cross,
                        AutoLabelUISettings.DEFAULT_SHOW_CROSS);
                mIconCross = typedArray.getResourceId(R.styleable.AutoLabelUI_icon_cross,
                        AutoLabelUISettings.DEFAULT_ICON_CROSS);
                mLabelsClickables = typedArray.getBoolean(R.styleable.AutoLabelUI_labels_clickable,
                        AutoLabelUISettings.DEFAULT_LABELS_CLICKABLES);

                mLabelPaddingLeft = mLabelPaddingTop = mLabelPaddingRight = mLabelPaddingBottom = mLabelPadding = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_label_padding,
                        getResources().getDimensionPixelSize(R.dimen.marginx2));
                mLabelPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_label_paddingLeft , mLabelPadding);
                mLabelPaddingTop = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_label_paddingTop , mLabelPadding);
                mLabelPaddingRight = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_label_paddingRight , mLabelPadding);
                mLabelPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_label_paddingBottom , mLabelPadding);

                mLabelMarginLeft = mLabelMarginTop = mLabelMarginRight = mLabelMarginBottom = mLabelMargin = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_label_margin , getResources().getDimensionPixelSize(R.dimen.margin));
                mLabelMarginLeft = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_label_marginLeft , mLabelMargin);
                mLabelMarginTop = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_label_marginTop , mLabelMargin);
                mLabelMarginRight = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_label_marginRight , mLabelMargin);
                mLabelMarginBottom = typedArray.getDimensionPixelSize(R.styleable.AutoLabelUI_label_marginBottom , mLabelMargin);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error while creating the view AutoLabelUI: ", e);
            } finally {
                typedArray.recycle();
            }
        }

    }

    /**
     * Method to add a Label if is possible.
     *
     * @param textLabel is the text of the label added using a LIST.
     * @param position  is the position of the label.
     */
    public boolean addLabelByIntegerTag(String textLabel, int position) {
        if (!checkLabelsCompleted()) {
            Label label = new Label(getContext(), mTextSize, mIconCross, mShowCross,
                    mTextColor, mBackgroundResource, mLabelsClickables, mLabelPaddingLeft , mLabelPaddingTop , mLabelPaddingRight , mLabelPaddingBottom);

            AutoViewGroup.LayoutParams lp = new AutoViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = mLabelMarginLeft;
            lp.topMargin = mLabelMarginTop;
            lp.rightMargin = mLabelMarginRight;
            lp.bottomMargin = mLabelMarginBottom;
            label.setLayoutParams(lp);

            label.setText(textLabel);
            label.setTag(Integer.valueOf(position));
            label.setOnClickCrossListener(this);
            label.setOnLabelClickListener(this);

            addView(label);
            increaseLabelsCounter();

            setLayoutTransition(new LayoutTransition());
            requestLayout();

            return true;
        }

        if (listenerOnLabelsCompleted != null) {
            listenerOnLabelsCompleted.onLabelsCompleted();
        }
        return false;
    }

    /**
     * Method to add a Label if is possible.
     *
     * @param textLabel is the text of the label added.
     */
    public boolean addLabel(String textLabel) {
        if (!checkLabelsCompleted()) {
            Label label = new Label(getContext(), mTextSize, mIconCross, mShowCross,
                    mTextColor, mBackgroundResource, mLabelsClickables, mLabelPaddingLeft , mLabelPaddingTop , mLabelPaddingRight , mLabelPaddingBottom);

            AutoViewGroup.LayoutParams lp = new AutoViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = mLabelMarginLeft;
            lp.topMargin = mLabelMarginTop;
            lp.rightMargin = mLabelMarginRight;
            lp.bottomMargin = mLabelMarginBottom;
            label.setLayoutParams(lp);

            label.setText(textLabel);
            label.setTag(textLabel);
            label.setOnClickCrossListener(this);
            label.setOnLabelClickListener(this);

            addView(label);
            increaseLabelsCounter();

            setLayoutTransition(new LayoutTransition());
            requestLayout();

            return true;
        }

        if (listenerOnLabelsCompleted != null) {
            listenerOnLabelsCompleted.onLabelsCompleted();
        }
        return false;
    }

    /**
     * @param labelText
     * @param position
     * @return
     * @desc �ı�position����text
     * @author zhaoyun
     */
    public boolean setLabelTextByIntegerTag(String labelText, int position) {
        Label view = (Label) findViewWithTag(position);
        if (view != null) {
            view.setText(labelText);
            view.setTag(Integer.valueOf(position));
            view.setOnClickCrossListener(this);
            view.setOnLabelClickListener(this);
            requestLayout();
            return true;
        }
        return false;
    }

    /**
     * @param labelText
     * @param tagLabelText
     * @return
     * @desc �ı�tagΪtagLabelText��label��text
     * @author zhaoyun
     */
    public boolean setLabelTextByLabelText(String labelText, String tagLabelText) {
        Label view = (Label) findViewWithTag(tagLabelText);
        if (view != null) {
            view.setText(labelText);
            view.setTag(labelText);
            view.setOnClickCrossListener(this);
            view.setOnLabelClickListener(this);
            requestLayout();
            return true;
        }
        return false;
    }

    /**
     * 检测AutoLabelUI添加的label的count是否达到上限
     * @return
     */
    private boolean checkLabelsCompleted() {
        return !(mMaxLabels == AutoLabelUISettings.DEFAULT_MAX_LABELS || getMaxLabels() > getLabelsCounter());
    }

    public int getLabelsCounter() {
        return labelsCounter;
    }

    private void increaseLabelsCounter() {
        this.labelsCounter++;
    }

    private void decreaseLabelsCounter() {
        this.labelsCounter--;
    }

    private void resetLabelsCounter() {
        this.labelsCounter = EMPTY;
    }

    /**
     * Method called when the cross icon is clicked.
     *
     * @param view the {@link Label} object.
     */
    @Override
    public void onClickCross(View view) {
        removeView(view);
        decreaseLabelsCounter();

        if (listenerOnRemoveLabel != null) {
            if (view.getTag() instanceof Integer) {
                listenerOnRemoveLabel.onRemoveLabel(view, (Integer) view.getTag());
            } else {
                listenerOnRemoveLabel.onRemoveLabel(view, -1);
            }
        }
        if (getLabelsCounter() == EMPTY) {
            if (listenerOnLabelsEmpty != null) {
                listenerOnLabelsEmpty.onLabelsEmpty();
            }
        }
        requestLayout();
    }

    /**
     * Method called when the {@link Label} object is clicked.
     *
     * @param v the {@link Label} object.
     */
    @Override
    public void onClickLabel(View v) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.equals(v)) {
                child.setSelected(true);
                ((Label) child).getLabelTextView().setSelected(true);
            } else {
                child.setSelected(false);
                ((Label) child).getLabelTextView().setSelected(false);
            }
        }
        if (listenerOnLabelClick != null) {
            listenerOnLabelClick.onClickLabel(v);
        }
    }

    public void setSelection(int position) {
        if (position < 0 || position > getChildCount() - 1) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == position) {
                child.setSelected(true);
                ((Label) child).getLabelTextView().setSelected(true);
            } else {
                child.setSelected(false);
                ((Label) child).getLabelTextView().setSelected(false);
            }
        }
    }

    /**
     * Method to remove a label using a list.
     *
     * @param labelToRemove the text of the {@link Label} to remove.
     */
    public boolean removeLabel(String labelToRemove) {
        Label view = (Label) findViewWithTag(labelToRemove);
        if (view != null) {
            removeView(view);
            decreaseLabelsCounter();
            if (getLabelsCounter() == EMPTY) {
                if (listenerOnLabelsEmpty != null) {
                    listenerOnLabelsEmpty.onLabelsEmpty();
                }
            }
            requestLayout();
            return true;
        }
        return false;
    }

    /**
     * Method to remove a label using a LIST.
     *
     * @param position of the item to remove.
     */
    public boolean removeLabelByIntegerTag(int position) {
        Label view = (Label) findViewWithTag(Integer.valueOf(position));
        if (view != null) {
            removeView(view);
            decreaseLabelsCounter();
            if (getLabelsCounter() == EMPTY) {
                if (listenerOnLabelsEmpty != null) {
                    listenerOnLabelsEmpty.onLabelsEmpty();
                }
            }
            requestLayout();
            return true;
        }
        return false;
    }

    public void clear() {
        removeAllViews();

        resetLabelsCounter();
        if (listenerOnLabelsEmpty != null) {
            listenerOnLabelsEmpty.onLabelsEmpty();
        }

        requestLayout();
    }

    public Label getLabel(int position) {
        return (Label) getChildAt(position);
    }

    public List<Label> getLabels() {
        ArrayList<Label> labels = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            labels.add(getLabel(i));
        }
        return labels;
    }

    public int getMaxLabels() {
        return mMaxLabels;
    }

    public void setMaxLabels(int maxLabels) {
        this.mMaxLabels = maxLabels;
    }

    public boolean isShowCross() {
        return mShowCross;
    }

    public void setShowCross(boolean showCross) {
        this.mShowCross = showCross;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public boolean isLabelsClickables() {
        return mLabelsClickables;
    }

    public void setLabelsClickables(boolean labelsClickables) {
        this.mLabelsClickables = labelsClickables;
    }

    public void setLabelPadding(int padding) {
        int newPadding;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newPadding = getResources().getDimensionPixelSize(padding);
        } catch (Resources.NotFoundException e) {
            newPadding = padding;
        }
        this.mLabelPadding = newPadding;
        this.mLabelPaddingLeft = this.mLabelPaddingTop = this.mLabelPaddingRight = this.mLabelPaddingBottom = newPadding;
    }

    public void setLabelPadding(int paddingLeft , int paddingTop , int paddingRight , int paddingBottom){
        int newPaddingLeft;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newPaddingLeft = getResources().getDimensionPixelSize(paddingLeft);
        } catch (Resources.NotFoundException e) {
            newPaddingLeft = paddingLeft;
        }
        this.mLabelPaddingLeft = newPaddingLeft;

        int newPaddingTop;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newPaddingTop = getResources().getDimensionPixelSize(paddingTop);
        } catch (Resources.NotFoundException e) {
            newPaddingTop = paddingTop;
        }
        this.mLabelPaddingTop = newPaddingTop;

        int newPaddingRight;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newPaddingRight = getResources().getDimensionPixelSize(paddingRight);
        } catch (Resources.NotFoundException e) {
            newPaddingRight = paddingRight;
        }
        this.mLabelPaddingRight = newPaddingRight;

        int newPaddingBottom;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newPaddingBottom = getResources().getDimensionPixelSize(paddingBottom);
        } catch (Resources.NotFoundException e) {
            newPaddingBottom = paddingBottom;
        }
        this.mLabelPaddingBottom = newPaddingBottom;
    }

    public void setLabelMargin(int margin) {
        int newMargin;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newMargin = getResources().getDimensionPixelSize(margin);
        } catch (Resources.NotFoundException e) {
            newMargin = margin;
        }
        this.mLabelMargin = newMargin;
        this.mLabelMarginLeft = this.mLabelMarginTop = this.mLabelMarginRight = this.mLabelMarginBottom = newMargin;
    }

    public void setLabelMargin(int marginLeft , int marginTop , int marginRight , int marginBottom){
        int newMarginLeft;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newMarginLeft = getResources().getDimensionPixelSize(marginLeft);
        } catch (Resources.NotFoundException e) {
            newMarginLeft = marginLeft;
        }
        this.mLabelMarginLeft = newMarginLeft;

        int newMarginTop;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newMarginTop = getResources().getDimensionPixelSize(marginTop);
        } catch (Resources.NotFoundException e) {
            newMarginTop = marginTop;
        }
        this.mLabelMarginTop = newMarginTop;

        int newMarginRight;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newMarginRight = getResources().getDimensionPixelSize(marginRight);
        } catch (Resources.NotFoundException e) {
            newMarginRight = marginRight;
        }
        this.mLabelMarginRight = newMarginRight;

        int newMarginBottom;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newMarginBottom = getResources().getDimensionPixelSize(marginBottom);
        } catch (Resources.NotFoundException e) {
            newMarginBottom = marginBottom;
        }
        this.mLabelMarginBottom = newMarginBottom;
    }

    public void setTextSize(int textSize) {
        int newSize;
        //兼容设置R.dimen.xxx和设置数字值
        try {
            newSize = getResources().getDimensionPixelSize(textSize);
        } catch (Resources.NotFoundException e) {
            newSize = textSize;
        }
        this.mTextSize = newSize;
    }

    public ColorStateList getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        ColorStateList newColor;
        try {
            newColor = getResources().getColorStateList(textColor);
        } catch (Resources.NotFoundException e) {
            newColor = getResources().getColorStateList(android.R.color.white);
        }
        this.mTextColor = newColor;
    }

    public int getBackgroundResource() {
        return mBackgroundResource;
    }

    public void setBackgroundResource(int backgroundResource) {
        this.mBackgroundResource = backgroundResource;
    }

    public int getIconCross() {
        return mIconCross;
    }

    public void setIconCross(int iconCross) {
        this.mIconCross = iconCross;
    }

    /**
     * Set a callback listener when a label is removed
     *
     * @param listener Callback instance.
     */
    public void setOnRemoveLabelListener(OnRemoveLabelListener listener) {
        this.listenerOnRemoveLabel = listener;
    }

    /**
     * Set a callback listener when there are the maximum number of labels.
     *
     * @param listener Callback instance.
     */
    public void setOnLabelsCompletedListener(OnLabelsCompletedListener listener) {
        this.listenerOnLabelsCompleted = listener;
    }

    /**
     * Set a callback listener when there are not labels.
     *
     * @param listener Callback instance.
     */
    public void setOnLabelsEmptyListener(OnLabelsEmptyListener listener) {
        this.listenerOnLabelsEmpty = listener;
    }

    /**
     * Set a callback listener when the {@link Label} is clicked.
     *
     * @param listener Callback instance.
     */
    public void setOnLabelClickListener(OnLabelClickListener listener) {
        this.listenerOnLabelClick = listener;
    }

    /**
     * This method sets the desired functionalities of the labels to make easy.
     *
     * @param autoLabelUISettings Object with all functionalities to make easy.
     */
    public void setSettings(AutoLabelUISettings autoLabelUISettings) {
        mAutoLabelUISettings = autoLabelUISettings;
        setMaxLabels(autoLabelUISettings.getMaxLabels());
        setShowCross(autoLabelUISettings.isShowCross());
        setBackgroundResource(autoLabelUISettings.getBackgroundResource());
        setTextColor(autoLabelUISettings.getTextColor());
        setTextSize(autoLabelUISettings.getTextSize());
        setIconCross(autoLabelUISettings.getIconCross());
        setLabelsClickables(autoLabelUISettings.isLabelsClickables());
        setLabelPadding(autoLabelUISettings.getLabelPadding());
    }

    /**
     * Save the state of the labels when orientation screen changed.
     */
    @Override
    public Parcelable onSaveInstanceState() {

        //save everything
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putParcelable("stateSettings", mAutoLabelUISettings);
        bundle.putSerializable("labelsAdded", (Serializable) getAllLabelsAdded());
        return bundle;
    }

    private List<LabelValues> getAllLabelsAdded() {
        List<LabelValues> listLabelValues = new ArrayList<>();
        int childcount = getChildCount();
        for (int i = 0; i < childcount; i++) {
            Label label = (Label) getChildAt(i);

            if (label.getTag() instanceof Integer) {
                listLabelValues.add(new LabelValues((int) label.getTag(), label.getText()));
            } else {
                listLabelValues.add(new LabelValues(-1, label.getText()));
            }
        }

        return listLabelValues;
    }

    /**
     * Retrieve the state of the labels when orientation screen changed.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            //load everything
            AutoLabelUISettings autoLabelUISettings = bundle.getParcelable("stateSettings");
            if (autoLabelUISettings != null) {
                setSettings(autoLabelUISettings);
            }

            resetLabelsCounter();

            List<LabelValues> labelsAdded = (List<LabelValues>) bundle
                    .getSerializable("labelsAdded");

            if (labelsAdded != null) {
                for (int i = 0; i < labelsAdded.size(); i++) {
                    LabelValues labelValues = labelsAdded.get(i);

                    if (labelValues.getKey() == -1) {
                        addLabel(labelValues.getValue());
                    } else {
                        addLabelByIntegerTag(labelValues.getValue(), labelValues.getKey());
                    }
                }
            }

            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * Interface for a callback when a label is removed.
     * Container Activity/Fragment must implement this interface
     */
    public interface OnRemoveLabelListener {

        /**
         * Callback when a label is removed.
         *
         * @param view     has been removed.
         * @param position of the item to remove.
         */
        void onRemoveLabel(View view, int position);
    }

    /**
     * Interface for a callback listener when there are the maximum number of labels.
     * Container Activity/Fragment must implement this interface
     */
    public interface OnLabelsCompletedListener {

        /**
         * Callback when there are the maximum number of labels.
         */
        void onLabelsCompleted();
    }

    /**
     * Interface for a callback listener when there are not labels.
     * Container Activity/Fragment must implement this interface
     */
    public interface OnLabelsEmptyListener {

        /**
         * Call when there are not labels.
         */
        void onLabelsEmpty();
    }

    /**
     * Interface for a callback listener when the {@link Label} is clicked.
     * Container Activity/Fragment must implement this interface.
     */
    public interface OnLabelClickListener {

        /**
         * Call when the {@link Label} is clicked.
         */
        void onClickLabel(View v);
    }
}