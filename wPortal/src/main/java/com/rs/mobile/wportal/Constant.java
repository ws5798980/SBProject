package com.rs.mobile.wportal;

/**
 * Constant
 *
 * @author Lee
 * @date 2017-3-10
 */
public class Constant {

    /***************************************************************************************
     ***************************************** url 정보**************************************
     ***************************************************************************************/

//	public static final String SM_BASE_URL = "http://192.168.2.230:81";// 신선
//	public static final String BASE_URL_KR = "http://192.168.2.220:81";// 광락
//	public static final String BASE_URL_RT = "http://192.168.2.183";// 외식
//	public static final String BASE_URL_DP = "http://192.168.2.179:82";// 百货域名
//	public static final String BASE_URL_DP1 = "http://192.168.2.230:81";// 百货主页
//	public static final String BASE_URL_HT = "http://192.168.2.147:84";//
//	public static final String BASE_URL_SSO = "http://192.168.2.179:8080";// SSO
//	public static final String BASE_URL_YC = "http://192.168.2.201/ImApi/api/pl_CheckGeoFenceActivity";
//	public static final String BASE_URL_ORDER = "http://192.168.2.152:8866";

    // public static final String SM_BASE_URL = "http://pay.gigawon.co.kr:81";
    // public static final String BASE_URL_KR =
    // "http://klcm.gigawon.co.kr:81";
    // public static final String BASE_URL_RT =
    // "http://foodapi.gigawon.co.kr:843";
    // public static final String BASE_URL_DP =
    // "http://api1.gigawon.co.kr:82";
    // public static final String BASE_URL_DP1 =
    // "http://pay.gigawon.co.kr:81";
    // public static final String BASE_URL_HT =
    // "http://hotel.gigawon.co.kr:84";
    // public static final String BASE_URL_SSO =
    // "http://api1.gigawon.co.kr:8088";// SSO
    // public static final String BASE_URL_YC =
    // "https://api.gigawon.co/ImApi/api/pl_CheckGeoFenceActivity";

    public static final String XS_BASE_URL = "http://mall.gigawon.co.kr:8800/api/";
    public static final String XSGR_TEST_URL = "http://gigaMerchantManager.gigawon.co.kr:8825/";
    public static final String XS_MEMBER_URL = "http://member.gigawon.co.kr:8808/api/";

    public static final String SM_BASE_URL = "http://pay.gigawon.co.kr:81";
    public static final String BASE_URL_KR =
            "http://klcm.dxbhtm.com:8072";
    public static final String BASE_URL_RT =
            "http://foodapi.dxbhtm.com:843";
    public static final String BASE_URL_DP =
            "http://api1.dxbhtm.com:82";
    public static final String BASE_URL_DP1 =
            "http://pay.dxbhtm.com:81";
    public static final String BASE_URL_HT =
            "http://hotel.dxbhtm.com:8863";
    public static final String BASE_URL_SSO =
            "http://api1.dxbhtm.com:8088";// SSO
    public static final String BASE_URL_YC =
            "http://api.dxbhtm.com:8444/ImApi/api/pl_CheckGeoFenceActivity";
    public static final String BASE_URL_ORDER =
            "http://editapi.dxbhtm.com:8866";
    public static final String BASE_URL_UPDATE = "https://api.dxbhtm.com:8444/appapi/userapi";

    public static final String BASE_REFERENCE_URL = "http://api1.dxbhtm.com:7778";


    /***************************************************************************************
     *************************************** 포털 개인센터**************************************
     ***************************************************************************************/
    public static final String PERSONAL_REFERENCE_SELECT_URL = "/api/apiMember/checkParent";
    public static final String PERSONAL_REFERENCE_CHECK_REFERENCE_URL = "/api/apiMember/checkParent";
    public static final String PERSONAL_REFERENCE_MATTING_INPUT = "/api/apiMember/matchingParent";
    public static final String PERSONAL_REFERENCE_AUTO_MATTING = "/api/apiMember/doAutoMatching";

    // 退出登录SSO接口
    public static final String PERSNAL_REPLY = "/MyInfo/MyReply";

    // 개인정보 리플
    public static final String SSO_LOGOUT = "/api/apiSSO/setLogout";

    // 내가 한 좋아요
    public static final String PERSNAL_RECOMMAND = "/MyInfo/MyRecommand";

    // 내 댓글에 누군가가 좋아요 한 리스트
    public static final String PERSNAL_MY_RECOMMAND = "/MyInfo/MyReplyRecommand";

    // 북마크
    public static final String PERSNAL_MY_SCRAB = "/MyInfo/MyScrab";

    // 공유
    public static final String PERSNAL_MY_SHARE = "/MyInfo/MyShare";

    // 뎃글 삭제
    public static final String PERSNAL_DEL_NEWS_REPLY = "/NewsView/DelNewsReply";

    // 추천 취소
    public static final String PERSNAL_DEL_RECOMMAND = "/NewsView/SetRecommand";

    // 스크랩 취소
    public static final String PERSNAL_DEL_BOOKMAEK = "/NewsView/SetScrab";

    // 공유취소
    public static final String PERSNAL_DEL_SHARE = "/NewsView/DelShare";

    // 개인정보 가져오기
    public static final String PERSNAL_GET_INFO = "/Member/GetMemberProfile";

    // 개인정보 입력하기
    public static final String PERSNAL_SET_INFO = "/Member/SetMemberProfile";

    /***************************************************************************************
     ****************************************** 광락******************************************
     ***************************************************************************************/

    // 메인
    public static final String KR_MAIN_JSON = "/Api/KLHome";

    // 비디오 가져오기
    public static final String KR_VIDEO_LIST = "/api/VideoList";

    // 비디오 검색
    public static final String KR_VIDEO_LIST_SEARCH = "/api/Search";

    // 투표 검색
    public static final String KR_VOTE_LIST_SEARCH = "/api/Activity";

    // 설정 구매내역 검색
    public static final String KR_MY_PURCHAR = "/api/MyKuangle";

    // 콘서트
    public static final String KR_PLAY_LIST = "/api/ArtCenter";

    // 영화검색
    public static final String KR_CINEMA_LIST = "/api/Cinema";

    // 영화상세
    public static final String KR_CINEMA_DETAIL = "/api/Movie";

    public static final String KR_ARTCENTER_DETAIL = "/api/ArtCenter?showId=";

    // 투표상세
    public static final String KR_VOTE_DETAIL = "/api/Project";

    // 选择场次
    public static final String KR_SCREENING = "/api/Screening?movieId=";

    /***************************************************************************************
     ****************************************** 포탈******************************************
     ***************************************************************************************/

    public static final String INTRO_JSON_URL = "/JsonCreate/JsonIntroCreate";

    public static final String MAIN_JSON_URL = "/JsonCreate/JsonBannerCreate";

    /***************************************************************************************
     ****************************************** 신선******************************************
     ***************************************************************************************/

    public static final String GET_MAINQUERY_URL = "/FreshMart/Main/GetMainQuery ";

    public static final String GET_GOODSOFDETAILS = "/FreshMart/Goods/GetGoodsOfDetails";

    public static final String GET_GOODSOFCOMMENT = "/FreshMart/User/GetGoodsOfComment";

    public static final String PAYMENT_STATUSSYNC = "/Order/UpdatePaymentStatusSync";

    public static boolean SMMY_REFRESH = false;

    public static boolean DPMY_REFRESH = false;
    // 获取用户收藏夹
    public static final String GET_USERFAVORITE_LIST = "/FreshMart/User/GetUserFavoritesOfList";

    // 删除我的收藏
    public static final String DEL_USER_FAVOURITES = "/FreshMart/User/DelUserFavorites";

    // 给评论点赞
    public static final String SEND_LIKES = "/FreshMart/User/SendLikes";

    // 搜索结果
    public static final String SEARCH_GOODS = "/FreshMart/Goods/SearchGoods";

    // 新增地址
    public static final String ADD_USERSHOP_ADDRESS = "/FreshMart/User/AddUserShopAddress";

    // 获取我的购物车列表
    public static final String GET_USER_SHOPINGCARTLIST = "/FreshMart/User/GetUserShopCartOfList";

    // 编辑我的购物车
    public static final String UPDATE_USERSHOPCART = "/FreshMart/User/UpdateUserShopCart";

    // 获取自提站点列表
    public static final String GET_PICKUP_SITELIST = "/FreshMart/Common/GetPickUpSiteList";

    // 获得我的收货地址
    public static final String GET_USER_SHOPADDRESS_LIST = "/FreshMart/User/GetUserShopAddressOfList";

    // 删除收获地址
    public static final String DELETE_USERSHOPADDRESS = "/FreshMart/User/DelUserShopAddress";

    // 退款申请
    public static final String SubmitRefundApply = "/Refund/SubmitRefundApply";

    // 提交快递单号
    public static final String SubmitRefundDeliveryInfo = "/Refund/SubmitRefundDeliveryInfo";

    public static final String GETMYCOUPON = "/FreshMart/Coupon/GetMyCoupons";

    public static final String GetRefundOrderItemList = "/Refund/GetRefundOrderItemList";

    // 退货进程
    public static final String GET_GOODSRETURN_DETAIL = "/refund/GetRefundProcessLog";

    // 获取订单列表
    public static final String GET_USERORDER_LIST = "/FreshMart/Order/GetUserOrderOfList";

    // 增加到我的购物车
    public static final String ADD_USER_SHOPCART = "/FreshMart/User/AddUserShopCart";

    // 增加到我的收藏
    public static final String ADD_USER_FAVORITES = "/FreshMart/User/AddUserFavorites";

    // 发表商品评价
    public static final String ADD_SENDCOMMENT = "/FreshMart/User/SendComment";

    // 我的
    public static final String GET_USERINFO = "/FreshMart/User/GetUserInfo";

    // 物流详情
    public static final String GET_GOODSEXPRESSOF_LIST = "/FreshMart/User/GetGoodsExpressOfList";

    // 确认收货
    public static final String CONFIRM_ORDER_DELIVERY = "/Order/ConfirmOrderOfDelivery";

    // 取消订单
    public static final String CANCEL_ORDER = "/Order/CancelOrder";

    // 最新鲜列表
    public static final String GET_BEST_FRESH_OFLIST = "/FreshMart/Main/GetBestFreshOfList";

    // 提醒发货
    public static final String ORDER_REMIND = "/User/Remind";

    // 获取订单详情
    public static final String GET_ORDER_DETAIL = "/FreshMart/Order/GetOrderOfDetails";

    // 修改评价
    public static final String UPDATE_COMMENT = "/User/UpdateComment";

    // 获得评价
    public static final String GETCOMMENTODFORDERCODE = "/User/GetCommentOfOrderCode";

    // 今日特惠
    public static final String GETTODAY_FRESHLISt = "/FreshMart/Main/GetTodayFreshOfList";

    // 人气生鲜
    public static final String GETHOT_FRESHLIST = "/FreshMart/Main/GetHotFreshOfList";

    // 新品尝鲜
    public static final String GETNEW_FRESHLIST = "/FreshMart/Main/GetNewFreshOfList";

    // 分类列表
    public static final String GET_CATEGORY_LIST = "/FreshMart/Goods/GetCategoryOfList";

    // 设置默认收获地址
    public static final String SET_DEFAULT_SHOPADDRESS = "/FreshMart/User/SetDefaultShopAddress";

    // 编辑收获地址
    public static final String UPDATE_USER_SHOPADDRESS = "/FreshMart/User/UpdateUserShopAddress";

    // 提交订单前检查
    public static final String CHECK_BEFORE_CREATEORDER = "/FreshMart/Order/CheckBeforeCreateOrder";

    // 提交订单
    public static final String CREATE_ORDER = "/FreshMart/Order/CreateOrder";

    // 根据分类编号获取
    public static final String GETGOODSLIST_CATEGROY = "/FreshMart/Goods/GetGoodsListOfCategroy";

    // 删除我的购物车
    public static final String DELUSERSHOPCART = "/FreshMart/User/DelUserShopCart";

    // 获取退款原因
    public static final String GET_RUFUND_REASON = "/Common/GetRefundReasonMsgByList";

    // 我的评价
    public static final String GetUserOfComment = "/User/GetUserOfComment";

    // 删除订单
    public static final String DeleteOrder = "/order/DeleteOrder";

    // 订单评价多商品
    public static final String GetOrderPendingAssessOfList = "/Order/GetOrderPendingAssessOfList";

    /***************************************************************************************
     ****************************************** department******************************************
     ***************************************************************************************/
    public static final String DP_GETHOMEPAGE = "/api/ycO2O/getO2OMainList30";

    // 品牌列表
    public static final String DP_GETBRANDLIST = "/api/ycO2O/getBrandMainList";

    // 产品列表
    public static final String DP_GETGOODSLIST = "/FreshMart/Goods/GetGoodsList";

    // 活动列表
    public static final String DP_LISTACTIVITY = "/api/ycO2o/ListActivity";

    // 活动详情
    public static final String DP_ACTIVITYDETAIL = "/api/ycO2o/ActivityDetail";

    // // 商品详情
    // public static final String DP_GETGOODSOFDETAIL =
    // "/Goods/GetGoodsOfDetails";

    // 楼层详情
    public static final String DP_GET_FLOORDETAIL = "/api/ycO2o/getFloorDetail";

    // // 新增地址
    // public static final String DP_ADD_USERSHOP_ADDRESS =
    // "/User/AddUserShopAddress";
    //
    // // 设置默认收获地址
    // public static final String DP_SET_DEFAULT_SHOPADDRESS =
    // "/User/SetDefaultShopAddress";
    //
    // // 编辑收获地址
    // public static final String DP_UPDATE_USER_SHOPADDRESS =
    // "/User/UpdateUserShopAddress";
    //
    // public static final String DP_DELETE_USERSHOPADDRESS =
    // "/User/DelUserShopAddress";
    //
    // public static final String DP_PAYMENT_STATUSSYNC =
    // "/Order/UpdatePaymentStatusSync";
    //
    // // 确认收货
    // public static final String DP_CONFIRM_ORDER_DELIVERY =
    // "/Order/ConfirmOrderOfDelivery";
    //
    // // 取消订单
    // public static final String DP_CANCEL_ORDER = "/Order/CancelOrder";
    //
    // // 获取订单列表
    // public static final String DP_GET_USERORDER_LIST =
    // "/Order/GetUserOrderOfList";
    //
    // // 获取我的购物车列表
    // public static final String DP_GET_USER_SHOPINGCARTLIST =
    // "/User/GetUserShopCartOfList";
    //
    // // 提交订单前检查
    // public static final String DP_CHECK_BEFORE_CREATEORDER =
    // "/Order/CheckBeforeCreateOrder";
    //
    // // 增加到我的购物车
    // public static final String DP_ADD_USER_SHOPCART =
    // "/User/AddUserShopCart";
    //
    // // 增加到我的收藏
    // public static final String DP_ADD_USER_FAVORITES =
    // "/User/AddUserFavorites";
    //
    // // 发表商品评价
    // public static final String DP_ADD_SENDCOMMENT = "/User/SendComment";
    //
    // // 删除我的购物车
    // public static final String DP_DELUSERSHOPCART = "/User/DelUserShopCart";
    //
    // // 编辑我的购物车
    // public static final String DP_UPDATE_USERSHOPCART =
    // "/User/UpdateUserShopCart";
    //
    // // 获取用户收藏夹
    // public static final String DP_GET_USERFAVORITE_LIST =
    // "/User/GetUserFavoritesOfList";
    //
    // public static final String DP_GetRefundOrderItemList =
    // "/Refund/GetRefundOrderItemList";
    //
    // // 退款申请
    // public static final String DP_SubmitRefundApply =
    // "/Refund/SubmitRefundApply";
    //
    // // 退货进程
    // public static final String DP_GET_GOODSRETURN_DETAIL =
    // "/refund/GetRefundProcessLog";
    //
    // // 我的
    // public static final String DP_GET_USERINFO = "/User/GetUserInfo";
    //
    // // 提交订单
    // public static final String DP_CREATE_ORDER = "/Order/CreateOrder";
    //
    // // 获得我的收货地址
    // public static final String DP_GET_USER_SHOPADDRESS_LIST =
    // "/User/GetUserShopAddressOfList";

    // 我的商城
    public static final String Dp_GET_MALLMAIN = "/api/ycO2o/getMallMain";

    // 分类列表
    public static final String DP_GET_CATEGORY_LIST = "/FreshMart/Goods/GetCategoryOfList";

    // public static final String DP_GET_GOODSOFCOMMENT =
    // "/User/GetGoodsOfComment";
    //
    // // 给评论点赞
    // public static final String DP_SEND_LIKES = "/User/SendLikes";
    //
    // // 获取订单详情
    // public static final String DP_GET_ORDER_DETAIL =
    // "/Order/GetOrderOfDetails";
    //
    // // 物流详情
    // public static final String DP_GET_GOODSEXPRESSOF_LIST =
    // "/User/GetGoodsExpressOfList";
    //
    // // 提交快递单号
    // public static final String DP_SubmitRefundDeliveryInfo =
    // "/Refund/SubmitRefundDeliveryInfo";
    //
    // public static final String DP_GETMYCOUPON = "/Coupon/GetMyCoupons";
    /***************************************************************************************
     ****************************************** 외식******************************************
     ***************************************************************************************/
    // 메인
    public static final String RT_MAIN = "/MainPage";

    /**
     * 订餐主页，上拉加载更多
     */
    public static final String RT_MAIN_LIST = "/MainPageRestaurantList";

    // 식당 상세
    public static final String RT_RESTAURANT_DETAIL = "/RestaurantView?saleCustomCode=";

    // 식당 리스트
    public static final String RT_RESTAURANT_LIST = "/RestaurantList";

    /**
     * 获取菜系
     */
    public static final String RT_COMMONCODE = "/CommonCode";

    // 예약
    public static final String RT_ADD_RESERVE = "/AddReserve";

    // 메뉴리스트
    public static final String RT_MENU_LIST = "/RestaurantMenuList?customCode=";

    /**
     * 热词搜索
     */
    public static final String RT_RECOMMANDKEYWORD = "/RecommandKeyword";

    // QR예약
    public static final String RT_QR_CODE_RESEVER = "/AddQRCodeReserve";

    // order
    public static final String RT_ADD_ORDER = "/AddOrder";

    // evaluate
    public static final String RT_ADD_COMMENT = "/AddComment";

    // order
    public static final String RT_GET_ORDER = "/GetOrderInfo";

    // 쿠폰 리스트`

    public static final String RT_ARR_COUPON = "/ArrCoupon";

    // 쿠폰 적용
    public static final String RT_GET_COUPON_CAL = "/GetCouponCal";

    // 포인트, 쿠폰 적용 적용
    public static final String RT_ADD_UNPAY_ORDER = "/UpdateUnpayOrder";

    // 예약 취소
    public static final String RT_CANCEL_RESERVE = "/SetCancelReserve";

    // 좋아요 추가
    public static final String RT_ADD_FAVORITE = "/AddFavorite";

    // 좋아요 리스트
    public static final String RT_MY_FAVORITE = "/MyFavorite";

    // 평가 리스트
    public static final String RT_MY_COMMENT = "/MyComment";

    // qr로 주문 받기
    public static final String RT_SET_ORDER_BY_QR = "/SetOrderByQR";

    // 골든벨 코드 받기
    public static final String RT_GET_GOLDEN_BELL_CODE = "/ArrGoldenBellCode";

    // 같이 먹기 그룹 생성하기
    public static final String RT_CREATE_EATING_GROUP = "/AddGroup";

    // 참여자 정보 가져오기
    public static final String RT_GET_ARR_GROUP_MEMBER = "/ArrGroupMember";

    // 같이먹기 참가
    public static final String RT_JOIN_GROUP = "/SetJoinGroup";

    // 참여자 삭제
    public static final String RT_DELETE_MEMBER = "/SetGroupMemberCancel";

    // 메뉴 리셋
    public static final String RT_RESET_MENU = "/DelGroupMemberOrder";

    // 메뉴 리셋
    public static final String RT_ADD_GROUP_ORDER = "/AddGroupMemberOrder";

    // 메뉴 리셋
    public static final String RT_GET_GROUP_INVITE_INFO = "/GetGroupInviteInfo";

    // 그룹 메뉴 오더 생성
    public static final String RT_CREATE_GROUP_ORDER = "/AddGroupOrder";

    // 룰렛게임
    public static final String RT_GAME_ROULETTE = "/Game/RunRoulette";

    // 룰렛게임
    public static final String RT_GAME_JUMPING = "/Game/RunRacing";

    // 슬롯머신게임
    public static final String RT_GAME_SLOT = "/Game/RunSlot";

    // 랜덤게임
    public static final String RT_GAME_RANDUM = "/Game/RunRandom";

    // 개인정보 가져오기
    public static final String RT_GET_MY_INFO = "/MyInfo";

    // 예약 가능여부 확인하기
    public static final String RT_GET_RESERVE_POSSIBLE = "/GetReservePossible";

    // 예약현황
    public static final String RT_RESERVE_STATUS_TABLE = "/DinnerTable/ReserveTableList";

    // hotel api
    public static final String HT_MAIN_GETDATA = "/api/Main_GetData";

    public static final String HT_GET_CITY = "/api/Hotel_GetLocationCity";

    public static final String HT_GET_HOTEL_DETAIL = "/api/Hotel_GetInfo";

    public static final String HT_GET_HOTEL_INTRODUCE = "/api/Hotel_GetDetail";

    public static final String HT_GET_ROOM_DETAIL = "/api/Hotel_GetRoomTypeDetail";

    public static final String HT_GET_ALBUM = "/api/Hotel_GetAlbum";

    public static final String HT_GET_RATELIST = "/api/Hotel_GetRatedList";

    public static final String HT_GET_PREPAY = "/api/Hotel_GetPrepayment";

    public static final String HT_CREATE_ORDER = "/api/Hotel_CreateOrder";

    public static final String HT_GET_ORDERDETAIL = "/api/Hotel_GetOrderDetail";

    public static final String HT_GET_ORDERLIST = "/api/Hotel_GetOrderList";

    public static final String HT_ADD_EVALUATE = "/api/Hotel_AddRated";

    public static final String HT_ADD_CANCEL = "/api/Hotel_CancelOrder";

    public static final String HT_DELE_ORDER = "/api/Hotel_DelOrder";

    public static final String HT_GET_QR = "/api/Hotel_GetQRText";

    public static final String HT_GetRetainTimeList = "/api/Hotel_GetRetainTimeList";

    public static final String HT_GetCancelMoney = "/api/Hotel_GetCancelMoney";

    // 공유
    public static final String GETSCANINFO = "/goods/GetScanInfo";

    // 线下超市

    // 获取超市入场票据
    public static final String MK_GETTICKETS = "/freshmart/OfflineShopping/GetTickets";

    // 检查票据是否有效
    public static final String MK_CHECKTICKETS = "/freshmart/OfflineShopping/CheckTicketsInfo";

    // 获取购物车列表
    public static final String MK_CARTSLIST = "/freshmart/OfflineShopping/GetOfflineCartsList";

    // 编辑购物车
    public static final String MK_ModifyOfflineCarts = "/freshmart/OfflineShopping/ModifyOfflineCarts";

    // 删除购物车商品
    public static final String MK_DeleteOfflineCarts = "/freshmart/OfflineShopping/DeleteOfflineCarts";

    // 扫码新增购物车
    public static final String MK_AddOfflineCarts = "/freshmart/OfflineShopping/AddOfflineCarts";

    // 生成订单
    public static final String MK_CreateOfflineCartsOrder = "/freshmart/OfflineShopping/CreateOfflineCartsOrder";

    // 支付成功后回调后台
    public static final String UpdateRepeatPayStatus = "/freshmart/OfflineShopping/UpdateRepeatPayStatus";

    // 获取出门二维码
    public static final String MK_GetOutSideQRCode = "/freshmart/OfflineShopping/GetOutSideQRCode";

    // 线下超市列表
    public static final String REQEUSTORDERLIST = "/api/apiOrder/requestOrderList";

    // foodorderlist
    public static final String MK_FOODORDERLIST = "/api/apiOrder/requestFoodOrderList";

    // 食品订单详情
    public static final String MK_FOOD_DETAIL = "/api/apiOrder/requestFoodOrderDetail";

    // mk_orderdetail
    public static final String MK_ORDERDETAIL = "/api/apiOrder/requestOrderDetail";

    /**
     * XSGR URL
     */
    // 商品管理 在售中 列表
    public static final String COMMODITY_ON = "product/queryList";
    // 商品管理 在售中 下架商品
    public static final String COMMODITY_CHANGESTATE = "product/setProductsellstate";
    // 商品管理 已下架 刪除商品
    public static final String COMMODITY_DELPRODUCT = "product/Delproduct";
    // 商品管理 分类列表
    public static final String COMMODITY_QUERYCATEGORY = "category/querycategory";
    // 商品管理 分类列表
    public static final String COMMODITY_CATEGORYADD = "category/addcategory";
    // 商品管理 分类列表
    public static final String COMMODITY_CATEGORYEDIT = "category/updatecategory";
    // 商品管理 分类列表
    public static final String COMMODITY_CATEGORYDEL = "category/delcategory";
    // 商品管理 重新编辑 请求数据
    public static final String COMMODITY_QUERYDETAIL = "product/queryproductbyGroudId";
    // 商品管理 重新编辑 删除规格
    public static final String COMMODITY_DELSPEC = "product/DelSpce";
    // 商品管理 重新编辑 删除口味
    public static final String COMMODITY_DELFLAVOR = "product/DelFlavor";
    // 商品管理 重新编辑 查询分类
    public static final String COMMODITY_QUERYTYPEBYID = "product/queryproductbyGroudId";
}