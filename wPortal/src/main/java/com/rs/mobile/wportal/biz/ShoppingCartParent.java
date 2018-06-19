package com.rs.mobile.wportal.biz;

import java.util.List;

public class ShoppingCartParent {

	public ShoppingCartParent(String div_code, String div_name, boolean isChoosed_parent,
			List<ShoppingCart> shoppingCarts) {
		super();
		this.div_code = div_code;
		this.div_name = div_name;
		this.isChoosed_parent = isChoosed_parent;
		this.shoppingCarts = shoppingCarts;
	}

	private String div_code;
	private String div_name;
	private boolean isChoosed_parent;

	public boolean isChoosed_parent() {
		return isChoosed_parent;
	}

	public void setChoosed_parent(boolean isChoosed_parent) {
		this.isChoosed_parent = isChoosed_parent;
	}

	private List<ShoppingCart> shoppingCarts;

	public String getDiv_code() {
		return div_code;
	}

	public void setDiv_code(String div_code) {
		this.div_code = div_code;
	}

	public String getDiv_name() {
		return div_name;
	}

	public void setDiv_name(String div_name) {
		this.div_name = div_name;
	}

	public List<ShoppingCart> getShoppingCarts() {
		return shoppingCarts;
	}

	public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
		this.shoppingCarts = shoppingCarts;
	}

}
