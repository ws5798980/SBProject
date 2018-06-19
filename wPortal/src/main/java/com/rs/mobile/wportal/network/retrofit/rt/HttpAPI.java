package com.rs.mobile.wportal.network.retrofit.rt;

import com.rs.mobile.wportal.biz.rt.ReserveListModel;
import com.rs.mobile.wportal.biz.rt.ReserveDetailModel;

import android.support.annotation.NonNull;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface HttpAPI {

	/** RT主页 */
	String PATH_RESTAURANT_MAINPAGE = "MainPage";
	/** RT主页上拉加载 */
	String PATH_RESTAURANT_MAINPAGERESTAURANTLIST = "MainPageRestaurantList";
	/** RT商家列表筛选条件 */
	String PATH_RESTAURANT_COMMONCODE = "CommonCode";
	/** RT商家搜索热词 */
	String PATH_RESTAURANT_RECOMMANDKEYWORD = "RecommandKeyword";
	/** RT商家列表 */
	String PATH_RESTAURANT_RESTAURANTLIST = "RestaurantList";
	/** RT商家提供菜品列表 */
	String PATH_RESTAURANT_RESTAURANTMENULIST = "RestaurantMenuList";
	/** RT商家提供菜类列表 */
	String PATH_RESTAURANT_MENUCATEGORYLIST = "MenuCategoryList";
	/** RT商家详情 */
	String PATH_RESTAURANT_RESTAURANTVIEW = "RestaurantView";
	/** RT订单列表 */
	String PATH_RESERVE_ARRRESERVELIST = "/ArrReserveList";
	/** RT订单信息 */
	String PATH_RESERVE_GETRESERVE = "/GetReserve";

	@POST(PATH_RESERVE_ARRRESERVELIST)
	Observable<ReserveListModel> ArrReserveList(
			@NonNull @Query("memberID") String memberID,
			@NonNull @Query("reserveType") String reserveType,
			@Query("currentPage") int currentPage);

	@POST(PATH_RESERVE_GETRESERVE)
	Observable<ReserveDetailModel> GetReserve(
			@NonNull @Query("memberID") String memberID,
			@NonNull @Query("reserveID") String reserveID,
			@NonNull @Query("orderNum") String orderNum,
			@NonNull @Query("reserveStatus") String reserveStatus);

}
