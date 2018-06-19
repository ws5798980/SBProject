package com.rs.mobile.wportal.view;

import com.rs.mobile.wportal.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 自定义组件：购买数量，带减少增加按钮 Created by dongjun on 2016/7/4.
 */
public class AmountView extends LinearLayout implements OnClickListener, TextWatcher {

	private static final String TAG = "AmountView";
	public int amount = 1; // 购买数量
	public int goods_storage = 1000; // 商品库存

	private OnAmountChangeListener mListener;

	public EditText etAmount;
	public ImageView btnDecrease;
	public ImageView btnIncrease;

	public AmountView(Context context) {
		this(context, null);
	}

	public AmountView(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater.from(context).inflate(R.layout.view_amount, this);
		etAmount = (EditText) findViewById(R.id.etAmount);
		btnDecrease = (ImageView) findViewById(R.id.btnDecrease);
		btnIncrease = (ImageView) findViewById(R.id.btnIncrease);
		btnDecrease.setOnClickListener(this);
		btnIncrease.setOnClickListener(this);
		etAmount.addTextChangedListener(this);

	}

	public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
		this.mListener = onAmountChangeListener;
	}

	public void setGoods_storage(int goods_storage) {
		this.goods_storage = goods_storage;
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btnDecrease) {
			if (amount >= 1) {
				amount--;
				etAmount.setText(amount + "");
			}
		} else if (i == R.id.btnIncrease) {
			if (amount < goods_storage) {
				amount++;
				etAmount.setText(amount + "");
			}
		}

		etAmount.clearFocus();

		if (mListener != null) {
			mListener.onAmountChange(this, amount);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	public void setEditText(int t) {
		etAmount.setText(t + "");
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (s.toString().isEmpty())
			return;
		amount = Integer.valueOf(s.toString());
		if (amount > goods_storage) {
			etAmount.setText(goods_storage + "");
			return;
		}

		if (mListener != null) {
			mListener.onAmountChange(this, amount);
		}
		if (amount < 1) {
			etAmount.setText("1");
			return;
		}
	}

	public interface OnAmountChangeListener {
		void onAmountChange(View view, int amount);
	}

}
