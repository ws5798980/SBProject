
package com.rs.mobile.common;

import java.util.HashMap;

/**
 * Constant
 * 
 * @author Lee
 * @date 2017-3-10
 */
public class C {

	/***************************************************************************************
	 *****************************************url 정보**************************************
	 ***************************************************************************************/

//	public static final String BASE_URL = "http://192.168.2.29:8488";//포탈
//	public static final String BASE_PLAYER_URL_KR = "http://192.168.2.220:89";//video player
//	public static final String ALIPAY="http://192.168.2.230:8088/Payment/CreateOrder_AliPay";//알리페이
//	public static final String UNIONPAY="http://192.168.2.230:8088/Payment/CreateOrder_UnionPay";//유니온
//	public static final String URL_POINT = "http://192.168.2.201/pl_Point/api/PointGetListAndBalance";//포인트
//	public static final String WPAYMENT = "http://192.168.2.201/wPayment/api/wPayment";//去支付
//	public static final String WPAYMENT_OFFLINE="http://192.168.2.201/wPayment/api/wPayment_Offline ";
//	public static final String GET_ZIP_CODE = "http://192.168.2.179:82/api/ycZipCode/getZipCode";
//	public static final String CHANGE_PW="http://192.168.2.179:82/api/MemberInfo/changePassword";
//	public static final String CreateOfflinePosOrder = "http://192.168.26.6:8089/Order/CreateOfflinePosOrder";
//	public static final String WPAYMENT_OFFLINE_POS = "http://192.168.2.179:82/api/QRPayment/posQRAppPayment";
//	public static final String CHECK_LOGIN = "http://192.168.2.165:89/ws2016/srvJoinModule/10_Login/checkLogin_0911";
	
//	 	public static final String BASE_URL = "https://portal."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+"";
//		public static final String BASE_PLAYER_URL_KR = "http://klcm."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":89";
//		public static final String ALIPAY="https://pay."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+"/Payment/CreateOrder_AliPay";
//		public static final String UNIONPAY="https://pay."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+"/Payment/CreateOrder_UnionPay";
//		public static final String URL_POINT = "https://api."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+"/pl_Point/api/PointGetListAndBalance";
//		public static final String WPAYMENT="https://api."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+"/wpayment/api/wPayment";
//		public static final String WPAYMENT_OFFLINE="https://api."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+"/wPayment/api/wPayment_Offline ";
//		public static final String GET_ZIP_CODE = "http://api1."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":82/api/ycZipCode/getZipCode";
//		public static final String CHANGE_PW="http://api1."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":82/api/MemberInfo/changePassword";
//		public static final String CreateOfflinePosOrder = "http://192.168.26.6:8089/Order/CreateOfflinePosOrder" ;
//		public static final String WPAYMENT_OFFLINE_POS="http://api1."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":89/ws2016/wsRecharge/40_Barcode/getBarcodeAuthority.aspx";
//		public static final String CHECK_LOGIN = "http://member."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":89/ws2016/srvJoinModule/10_Login/checkLogin_0911";
//	 dxbhtm
/*	public static final String BASE_URL = "https://portal.shelongwang.com:443";
	public static final String BASE_PLAYER_URL_KR = "http://klcm.shelongwang.com:8071";
	public static final String ALIPAY="https://pay.shelongwang.com:8442/Payment/CreateOrder_AliPay";
	public static final String UNIONPAY="https://pay.shelongwang.com:8442/Payment/CreateOrder_UnionPay";
	public static final String URL_POINT = "https://api.shelongwang.com:8444/pl_Point/api/PointGetListAndBalance";
	public static final String WPAYMENT="https://api.shelongwang.com:8444/wpayment/api/wPayment";
	public static final String WPAYMENT_OFFLINE="https://api.shelongwang.com:8444/wPayment/api/wPayment_Offline ";
	public static final String GET_ZIP_CODE = "http://api1.shelongwang.com:82/api/ycZipCode/getZipCode";
	public static final String CHANGE_PW="http://api1.shelongwang.com:82/api/MemberInfo/changePassword";
	public static final String WPAYMENT_OFFLINE_POS="http://api1.shelongwang.com:8074/ws2016/wsRecharge/40_Barcode/getBarcodeAuthority.aspx";
	public static final String CHECK_LOGIN = "http://member.shelongwang.com:89/ws2016/srvJoinModule/10_Login/checkLogin_0911";*/

//
	public static final String BASE_URL = "http://portal."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8488";
//	public static final String BASE_URL = "http://"+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8488";
	/*
	수정일 / 수정인 : 2017.12.25 Jack Kim
	회원가입 Direct 접속을 위한 수정
	 */
	public static final String BASE_MEMBER_RS_URL = "http://member."+(AppConfig.CHOOSE.equals("CN")?"osunggiga.cn":"osunggiga.com")+":8800";
	public static final String BASE_RS_MEMBER_URL = "http://rsmember."+(AppConfig.CHOOSE.equals("CN")?"osunggiga.cn":"osunggiga.com")+":8800";
	/*==========================*/
	public static final String BASE_PLAYER_URL_KR = "http://klcm."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8071";
	public static final String ALIPAY="http://pay."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8089/Payment/CreateOrder_AliPay";
	public static final String UNIONPAY="http://pay."+(AppConfig.CHOOSE.equals("CN")?"osunggiga.cn":"osunggiga.com")+":8089/Payment/CreateOrder_UnionPay";
	public static final String URL_POINT = "http://api."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8083/pl_Point/api/PointGetListAndBalance";
	public static final String WPAYMENT="http://api."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8083/wpayment/api/wPayment";
	public static final String GIGA_PAYMENT = "http://api1."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":82/api/wPay/requestQRpayment";
	public static final String WPAYMENT_OFFLINE="http://api."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8083/wPayment/api/wPayment_Offline ";
	public static final String GET_ZIP_CODE = "http://api1."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":82/api/ycZipCode/getZipCode";
	public static final String CHANGE_PW="http://api1."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":82/api/MemberInfo/changePassword";
	public static final String WPAYMENT_OFFLINE_POS="http://api1."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8074/ws2016/wsRecharge/40_Barcode/getBarcodeAuthority.aspx";
	public static final String CHECK_LOGIN = "http://member."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":89/ws2016/srvJoinModule/10_Login/checkLogin_0911";
	public static final String BASE_UPLOAD_IMG_URL = "http://mall."+(AppConfig.CHOOSE.equals("CN")?"gigawon.cn":"gigawon.co.kr")+":8800";
//

	//주소로 위도 경도 가져오기
	public static final String GET_GEOCODE_LOCATION = "http://restapi.amap.com/v3/geocode/geo";

	//이미지 업로드
	public static final String PERSNAL_IMAGE_UPLOAD_PATH = "/Common/FileUploader.ashx";
	//
	public static final String STORE_IMAGE_UPLOAD_PATH = "/Common/StoreFileUploader.ashx";
	 
	//이미자 다운로드
	public static final String PERSNAL_IMAGE_DOWNLOAD_PATH = "/MediaUploader/wsProfile/";
	
	// 외식 결제
	public static final String RT_ADD_PAYMENT_ADVANCE = "/AddPaymentAdvance";

	/***************************************************************************************
	 **************************************로그인 회원가입**************************************
	 ***************************************************************************************/

	//NickName Change
	public static final String REQUEST_NICK_NAME_CHANGE = "/api/member/editProfile";

	// SMS 발송 호출 HPnum
	public static final String GET_AUTH_NUMBER_URL = "/Member/SMSAuthNumSend";

	// 회원가입 호출 HPnum Password
	public static final String REGIST_MENBERSHIP_URL = "/Member/AddMember";
	
	// 회원가입 호출 HPnum Password 암호화
	/*
	수정일 / 수정인 : 2017.12.25 Jack Kim
	회원가입 서버의 Direct 접속을 위한 수정
	 */
	//public static final String REGIST_MENBERSHIP_ENC_URL = "/Member/AddMemberENC";
	public static final String REGIST_MEMBERSHIP_ENC_URL = "/api/Login/joinMember";

	// SMS 인증 확인
	public static final String AUTH_SMS_URL = "/Member/MemberSMSAuth";

	// 회원 로그인 MemberID, Password
	public static final String LOGIN_URL = "/Member/MemberLogin";

	// 회원 로그인 MemberID, Password 암호화
	/*
	수정일 / 수정인 : 2017.12.25 Jack Kim
	로그인 API 변경으로 인한 수정
	 */
	public static final String LOGIN_ENC_URL = "/Member/MemberLoginENC";
	public static final String LOGIN_ENC_RS_URL = "/api/Login/memberLogin";

	//RS Create URL
	public static final String BASE_URL_APISELLER = "http://api1."+(AppConfig.CHOOSE.equals("CN")?"osunggiga.cn":"osunggiga.com")+":7778";

	//RS APLLY API
	public static final String RS_CREATE_APISELLER_DOCTOR = "/api/apiSeller/joinDoctorSeller";
	public static final String RS_CREATE_APISELLER_STUDENT = "/api/apiSeller/joinStudentSeller";
	public static final String RS_CREATE_APISELLER_WEISANG = "/api/apiSeller/joinWeixinSeller";



	// 회원 로그아웃
	public static final String LOGOUT_URL = "/Member/MemberLogOut";
	
	//비밀번호 변경
	public static final String SET_PASSWORD = "/Member/ForgotSetPassword";
	
	//비밀번호 문자 인증
	public static final String CHANGE_PASSWORD_SMS = "/Member/SMSAuthNumSendByPWD";
	
	/***************************************************************************************
	 ******************************************위치******************************************
	 ***************************************************************************************/	
	//GET_LOCATION의 키
	public static final String GEOCODE_KEY = "af2bdfb6f62d73f35fa0214dc0439284";
	
	// http://222.240.51.146:8488/Location/GetMyLocation?type=1&lon=113.027417&lat=28.184747
	public static final String LOCATION_GET_MY_LOCATION = "/Location/GetMyLocation?type=";

	// http://222.240.51.146:8488/Location/GetAreaAndSquare?zipcode=410000&cityName=长沙
	public static final String LOCATION_GET_MY_AREA_SQUARE = "/Location/GetAreaAndSquare?zipcode=";
	/***************************************************************************************
	 ******************************************key******************************************
	 ***************************************************************************************/
	
	public static final String KEY_JSON_FM_SPAY_USER_ID = "sPay_user_id";

	public static final String KEY_JSON_FM_ORDER_NO = "order_no";

	public static final String KEY_JSON_FM_ORDER_AMOUNT = "order_amount";

	public static final String KEY_JSON_FM_SPAYPWD = "spayPWD";

	public static final String KEY_JSON_FM_SCORE = "score";

	public static final String KEY_JSON_FM_LEVEL = "level";

	public static final String KEY_JSON_FM_CONTENT = "content";

	public static final String KEY_JSON_FM_HASCOLLECTION = "hasCollection";

	public static final String KEY_JSON_FM_ORDERDATE = "orderDate";

	public static final String KEY_JSON_FM_INVALID_DATE = "invalid_date";

	public static final String KEY_JSON_FM_PAGEINDEX = "pageindex";

	public static final String KEY_JSON_FM_PAGESIZE = "pagesize";

	public static final String KEY_JSON_FM_ORDERSTATUS = "orderStatus";

	public static final String KEY_JSON_FM_ORDERCODE = "order_code";

	public static final String KEY_JSON_FM_ORDERPRICE = "order_price";

	public static final String KEY_JSON_FM_ORDERNUM = "total_num";

	public static final String KEY_JSON_FM_ORDER_GROUPGOODLIST = "gourpGoodList";

	public static final String KEY_JSON_FM_STATUS = "status";

	public static final String KEY_JSON_FM_ITEM_CODE = "item_code";

	public static final String KEY_JSON_FM_ITEM_QUANTITI = "item_quantity";

	public static final String KEY_JSON_FM_ITEM_DESCRIBE = "item_describe";

	public static final String KEY_JSON_FM_ITEM_PRICE = "item_price";

	public static final String KEY_JSON_FM_ITEM_IMAGE_URL = "image_url";

	public static final String KEY_JSON_FM_ITEM_NAME = "item_name";

	public static final String KEY_JSON_FM_STOCK_UNIT = "stock_unit";

	public static final String KEY_JSON_FM_HEAD_URL = "head_url";

	public static final String KEY_JSON_FM_POINT = "point";

	public static final String KEY_JSON_FM_POINT_CONTENT = "content";

	public static final String KEY_JSON_FM_POINT_TIME = "time";

	public static final String KEY_JSON_FM_POINT_NUMBER = "number";

	public static final String KEY_JSON_FM_COUPONS_COUNT = "coupons_count";

	public static final String KEY_JSON_FM_COLLECTION_COUNT = "collection_count";

	public static final String KEY_JSON_FM_MOBILE = "mobile";

	public static final String KEY_JSON_FM_ORDERS = "orders";

	public static final String KEY_JSON_FM_ORDER_STATUS = "order_status";

	public static final String KEY_JSON_FM_ORDER_COUNT = "order_count";

	public static final String KEY_JSON_FM_EXP_MOBILE = "expMobile";

	public static final String KEY_JSON_FM_ITEM_URL = "item_url";

	public static final String KEY_JSON_FM_BILL = "bill";

	public static final String KEY_JSON_FM_COUNT = "count";

	public static final String KEY_JSON_FM_EXP_FOLLOW = "expFollow";

	public static final String KEY_JSON_FM_EXP_Date = "expDate";

	public static final String KEY_JSON_FM_EXP_LOCATION = "expLocation";

	public static final String KEY_JSON_FM_HASCURRENT = "hasCurrent";

	public static final String KEY_JSON_FM_USER_NAME = "user_name";

	public static final String KEY_JSON_FM_USER_TEXT = "text";

	public static final String KEY_JSON_FM_REAL_PRICE = "real_price";

	public static final String KEY_JSON_FM_EXPSTATUS = "expStatus";

	public static final String KEY_JSON_FM_PAYMENT = "payment";

	public static final String KEY_JSON_FM_FREIGHT = "freight";

	public static final String KEY_JSON_FM_DISTRIBUTION = "distribution";

	public static final String KEY_JSON_FM_ADDRESS = "address";

	public static final String KEY_JSON_FM_GOODSLIST = "goods_list";

	public static final String KEY_JSON_FM_AD_ID = "ad_id";

	public static final String KEY_JSON_FM_GOOD_NUM = "good_num";

	public static final String KEY_JSON_FM_BANNER_URL = "banner_url";

	public static final String KEY_JSON_FM_UNIT_PRICE = "unit_price";

	public static final String KEY_JSON_FM_CATEGORY_CODE = "category_code";

	public static final String KEY_JSON_FM_CATEGORY_NAME = "category_name";

	public static final String KEY_JSON_FM_CATEGORY_NAME_EN = "category_name_en";

	public static final String KEY_JSON_FM_ICON_URL = "icon_url";

	public static final String KEY_JSON_FM_DELIVER_ID = "deliver_id";

	public static final String KEY_JSON_FM_DELIVER_NAME = "deliver_name";

	public static final String KEY_JSON_FM_TO_ADDRESS = "to_address";

	public static final String KEY_JSON_FM_LATITUDE = "latitude";

	public static final String KEY_JSON_FM_LONGITUDE = "longitude";

	public static final String KEY_JSON_FM_DEFAULT_ADD = "default_add";

	public static final String KEY_JSON_FM_BUSINESS_ID = "business_id";

	public static final String KEY_JSON_FM_SUPPLIER_ID = "supplier_id";

	public static final String KEY_JSON_FM_ID = "id";

	public static final String KEY_JSON_FM_LOCATION = "location";

	public static final String KEY_JSON_FM_NAME = "name";

	public static final String KEY_JSON_FM_HASDEFAULT = "hasDefault";

	public static final String KEY_JSON_FM_GUID = "guid";

	public static final String KEY_JSON_FM_REFUNDTYPE = "refundType";

	public static final String KEY_JSON_FM_REFUNDPRICE = "refundPrice";

	public static final String KEY_JSON_FM_SUBMITDATE = "submitDate";

	public static final String KEY_JSON_FM_REFUNDBILL = "refundBill";

	public static final String KEY_JSON_FM_REFDATE = "refDate";

	public static final String KEY_JSON_FM_REFLOCATION = "refLocation";

	public static final String KEY_JSON_FM_REFFOLLOW = "refFollow";

	public static final String KEY_JSON_FM_COUPON_CLASSIFY = "key1";

	public static final String KEY_JSON_FM_COUPON_TIME = "key2";

	public static final String KEY_JSON_FM_COUPON_MONEY = "key3";

	public static final String KEY_JSON_FM_COUPON_MORETHAN = "key4";

	public static final String KEY_JSON_FM_COUPON_NEEDMONEY = "key5";

	public static final String KEY_JSON_STATUS = "status";

	public static final String KEY_JSON_ITEM_CODE = "item_code";

	public static final String KEY_JSON_BANNERAD = "bannerAD";

	public static final String KEY_JSON_ADIMAGE = "ad_image";

	public static final String KEY_JSON_LINK_URL = "link_url";

	public static final String KEY_JSON_EVENTAD = "eventAD";

	public static final String KEY_JSON_SUB_TITLE = "sub_title";

	public static final String KEY_JSON_ICON = "icon";

	public static final String KEY_JSON_IMAGES = "images";

	public static final String KEY_JSON_AD_TITLE = "ad_title";

	public static final String KEY_JSON_NEWAD = "newAD";

	public static final String KEY_JSON_LIST = "list";

	public static final String KEY_JSON_ITEM_NAME = "item_name";

	public static final String KEY_JSON_ITEM_PRICE = "item_price";

	public static final String KEY_JSON_IMAGE_URL002 = "image_url";

	public static final String KEY_JSON_LIKEAD = "likeAD";

	public static final String KEY_SP_HISTORYKEY = "historyKey";

	public static final String KEY_SP_HISTORYKEY_level1 = "historyKey_level1";

	public static final String KEY_KR_SEARCH_HISTORY = "kr_search_history";

	public static final String SM_JSON_URL = "/fmarket_json/main_data.js";

	public static final String EXCOMPLE_URL = "http://img5.imgtn.bdimg.com/it/u=2462909806,3628453085&fm=23&gp=0.jpg";

	public static final String KEY_JSON_DATA = "data";

	public static final String KEY_JSON_IMAGE_URL = "imgUrl";

	public static final String KEY_JSON_IMAGE_URL_1 = "imageUrl";

	public static final String KEY_JSON_HEADER_IMAGE = "headerImage";

	public static final String KEY_JSON_PEPLE_LIKE_DATA = "peplelikeData";

	public static final String KEY_JSON_TASTEFRESH = "tastefresh";

	public static final String KEY_JSON_MOST_FRESH_DATA = "MostfreshData";

	public static final String KEY_JSON_MOST_FRESH = "mostFresh";

	public static final String KEY_JSON_HOT_FRESH = "hotFresh";

	public static final String KEY_JSON_PREFERENCE = "preference";

	public static final String KEY_BANNER_IMAGE = "bannerImage";

	public static final String KEY_JSON_VERSION = "ver";

	public static final String KEY_JSON_PURCHASEDATA = "purchaseData";

	public static final String KEY_JSON_BROADCAST = "broadcastData";

	public static final String KEY_JSON_BANNER = "bannerData";

	public static final String KEY_JSON_MOVIE = "movieData";

	public static final String KEY_JSON_VOTE = "voteData";

	public static final String KEY_JSON_BRANCH = "branchData";

	public static final String KEY_JSON_NEWS = "newsData";

	public static final String KEY_JSON_TITLE = "title";

	public static final String KEY_JSON_CONTENT = "content";

	public static final String KEY_JSON_PRICE = "price";

	public static final String KEY_JSON_INTRO = "intro";

	public static final String KEY_JSON_DATE = "date";

	public static final String KEY_JSON_END_DATE = "endDate";

	public static final String KEY_JSON_TIME = "time";

	public static final String KEY_JSON_REPLY_COUNT = "ReplyCount";

	public static final String KEY_JSON_COUNT = "count";

	public static final String KEY_JSON_READ = "read";

	public static final String KEY_JSON_URL = "url";

	public static final String KEY_JSON_NAME = "name";

	public static final String KEY_JSON_NICK_NAME = "nickName";
	public static final String KEY_JSON_BUSINEISS_TYPE = "businessType";
	public static final String KEY_JSON_TOKEN = "token";
	
	public static final String KEY_JSON_DEVICE_ID = "deviceNo";

	public static final String KEY_JSON_CUSTOM_CODE = "customCode";

	public static final String KEY_JSON_CUSTOM_ID = "customID";

	public static final String KEY_JSON_CUSTOM_NAME = "customName";

	public static final String KEY_JSON_PROFILE_IMG = "ProfileImg";

	public static final String KEY_JSON_DIV_CODE = "DivCode";

	public static final String KEY_JSON_SSOID = "ssoID";

	public static final String KEY_JSON_SSO_REGIKEY = "ssoRegikey";

	public static final String KEY_JSON_MALL_HOME_ID = "mallHoemID";

	public static final String KEY_JSON_POINT_CARD_NO = "pointCardNo";

	public static final String KEY_JSON_PARENT_ID = "ParentID";



	/*
	추가일 / 추가인 : 2017.12.25 Jack Kim
	로그인 API 변경으로 인하여 파라미터가 추가됨
	 */
	public static final String KEY_S_ID = "s_id"; //Null값
	public static final String KEY_VER = "ver";
	public static final String KEY_LANG_TYPE = "lang_type";

	public static final String KEY_JSON_PLACE = "place"; 

	public static final String KEY_JSON_BANNER_TYPE = "bannerType";

	public static final String KEY_JSON_CURRENT_PAGE = "currentPage";

	public static final String KEY_JSON_NEXT_PAGE = "nextPage";

	public static final String KEY_JSON_TYPE = "type";

	public static final String KEY_JSON_REPLY_DATA = "ReplyData";

	public static final String KEY_JSON_RECOMMAND_DATA = "RecommandData";

	public static final String KEY_JSON_REPLY_RECOMMAND_DATA = "ReplyRecommand";

	public static final String KEY_JSON_SCRAB_DATA = "ScrabData";

	public static final String KEY_JSON_SHARE_DATA = "ShareData";

	public static final String KEY_JSON_SEQ_BANNER = "bannerSeq";

	public static final String KEY_JSON_UNIQUE_ID = "uniqueId";

	public static final String KEY_JSON_DISPLAY_NAME = "displayName";

	public static final String KEY_JSON_VIDEOS = "videos";

	public static final String KEY_JSON_TICKETS = "tickets";

	public static final String KEY_JSON_VOTES = "votes";

	public static final String KEY_INTENT_URL = "url";

	public static final String KEY_REQUEST_SEQ_NEWS = "NewsSeq";

	public static final String KEY_REQUEST_SEQ_REPLY = "ReplySeq";

	public static final String KEY_REQUEST_SEQ_NEWS_REPLY = "NewsReplySeq";

	public static final String KEY_JSON_SEQ_SHARE = "shareSeq";

	public static final String KEY_JSON_VIDEO_TYPES = "videoTypes";

	public static final String KEY_JSON_VIDEO_LIST = "videoList";

	public static final String KEY_JSON_HOT_SEARCH = "hotSearch";
	
	public static final String KEY_JSON_LANG_TYPE="chn";

	public static final String KEY_REQUEST_ID = "id";

	/*
	수정일 / 수정인 : 2017.12.25 Jack Kim
	회원가입 API변경으로 인한 파라미터 이름 수정
	 */

	public static final String KEY_REQUEST_MEMBER_ID_TOW = "memberID";
	public static final String KEY_REQUEST_MEMBER_ID = "memid";

	/*
	수정일 : 2018. 01. 26
	회원수정 사항 추천인
	 */
	public static final String KEY_REQUEST_PARENT_ID = "parent_id";
	public static final String KEY_REQUEST_CUSTOME_LEVEL1 = "cusomt_lev1";

	/*
	수정일 / 수정인 : 2017.12.25 Jack Kim
	회원가입 API 변경으로 인한 파라미터 수정
	 */
	public static final String KEY_REQUEST_HN = "HPnum";
	public static final String KEY_REQUEST_MEMID = "memid";
	//public static final String KEY_REQUEST_PW = "Password";
	public static final String KEY_REQUEST_PW = "mempwd";
	public static final String KEY_REQUEST_AN = "AuthNum";

	public static final String KEY_ORDER_BY = "orderBy";

	public static final String KEY_REQUEST_SEARCH_KEY = "searchKey";

	public static final String KEY_REQUEST_SEQ_SHARE = "ShareSeq";

	public static final String KEY_RESPONSE_MSG = "msg";

	public static final String KEY_RESPONSE_FLAG = "flag";
	/*
	수정일 / 수정인 : 2017.12.25 Jack Kim
	로그인 API가 변경되면서 flag 코드를 추가한다
	 */
	public static final String VALUE_RESPONSE_SUCCESS = "success";
	public static final String VALUE_RESPONSE_SUCCESS_NUM = "1501";

	public static final String VALUE_RESPONSE_DUPLICATE = "duplicate";

	public static final String EXTRA_KEY_SELLERTYPE_CONDITION = "sellerTypeCondition";

	public static final String EXTRA_KEY_MEMBERID = "memberID";
	
	public static final String EXTRA_KEY_RESERVEID = "reserveID";
	
	public static final String EXTRA_KEY_ORDERNUM = "orderNum";
	
	public static final String EXTRA_KEY_RESERVESTATUS = "reserveStatus";

	/***************************************************************************************
	 ****************************************SHARED*****************************************
	 ***************************************************************************************/
	
	public static final String KEY_SHARED_ICON_PATH = "shared_icon_path";

	public static final String KEY_SHARED_KNICK_NAME = "shared_knick_name";

	public static final String KEY_SHARED_MY_AFFILISTE = "shared_key_my_affiliste";

	public static final String KEY_PREFERENCE_PLAY_WIFI_ONLY = "pref_play_wifi_only";

	public static final String KEY_PREFERENCE_DOWNLOAD_WIFI_ONLY = "pref_download_wifi_only";
	
	public static final String KEY_SHARED_NOTICE_VER = "shared_notice_ver";
	
	public static final String KEY_SHARED_EVENT_VER = "shared_event_ver";
	
	public static final String KEY_SHARED_INTRODUCE = "shared_introduce";
	
	public static final String KEY_SHARED_AGREE_GET_MY_LOCATION = "shared_agree_get_my_location";
	
	public static final String KEY_SHARED_PHONE_NUMBER = "shared_phone_number";

	/***************************************************************************************
	 *************************************Web interface*************************************
	 ***************************************************************************************/
	
	public static final String KEY_URL_SCHEME = "yuchang";

	public static final String KEY_URL_CALL_VIEW = "call_view";

	public static final String KEY_URL_CALL_LOGIN = "login_view";

	public static final String VALUE_URL_CALL_LOGIN = "LoginActivity";

	public static final String KEY_URL_RESEVER_RESTRAUNT = "reserverRestraunt";

	public static final String KEY_URL_PAY_FROM_QR = "payFromQR";

	// activity request code
	public static final int ACTIVITY_REQUEST_CODE_LOGIN = 1000;

	// web_app interface
	public static final String SCHEME = "yucheng";

	public static final String HOST_HISTORY_BACK = "historyBack";

	public static final String HOST_CALL_LOGIN = "callLogin";

	public static final String HOST_CALL_LOGOUT = "callLogout";

	public static final String HOST_CALL_SCRIPT = "callScript";

	public static final String HOST_CALL_SCRIPT_AFTER_LOGIN = "callScriptAfterLogin";
	
	public static final String HOST_CALL_CUSTOM_CENTER = "callCustomCenter";

	public static final String HOST_SHARE_AS_MESSENGER = "shareAsMessenger";
	
	public static final String HOST_WRITE_SHARED = "writeShared";
	
	public static final String HOST_PAY_POS = "posPay";

	public static final String SCRIPT_NAME = "scriptName";

	public static final String KEY = "key";

	public static final String VALUE = "value";

	public static final String MEMID = "memid";

	public static final String JAVA_SCRIPT = "javascript:";
	
	public static final String HOST_CALL_CLOSE_WEB = "closeWeb";

	public static HashMap<String, String> INTERFACE_PARAMS = new HashMap<String, String>();

	public static final String INTENT_FILTER_SEND_MESSANGE = "com.rs.mobile.wportal.intent.Messager_Msg";

	/***************************************************************************************
	 ****************************************App code***************************************
	 ***************************************************************************************/
	
	public static final String TYPE_DEPARTMENT_STORE = "0";

	public static final String TYPE_RESTRAUNT = "1";

	public static final String TYPE_FRESH_MART = "2";

	public static final String TYPE_HOTEL = "3";

	public static final String TYPE_PAYMENT = "4";

	public static final String TYPE_PARKING = "5";

	public static final String TYPE_MESSANGER = "6";

	public static final String TYPE_MEDIA = "7";

	public static final String TYPE_PORTAL = "8";
	
	public static final String TYPE_EATING_TOGETHER = "9";
	
	public static final String KEY_DIV_CODE="div_code";

	public static String DIV_CODE = "2";
	
	public static String DIV_NAME = "威海";
	
	/***************************************************************************************
	 **************************************PERMISSIONS**************************************
	 ***************************************************************************************/

	public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

	public static final int PERMISSIONS_REQUEST_ACCESS_READ_WRITE_EXTERNAL_STORAGE = 2;

	public static final int PERMISSIONS_REQUEST_ACCESS_CAMERA = 3;

	public static final int PERMISSIONS_REQUEST_ACCESS_READ_PHONE_STATE = 4;
	
	/***************************************************************************************
	 ****************************************flag code**************************************
	 ***************************************************************************************/
	
	public static boolean alreadyOpenLocationDialog = false;
	
	public static final String EXTRA_KEY_DIVCODE = "divCode";

	//RS Create
	//rs_Hospital Json Create Data
	public static final String RS_KEY_HOSPITAL_LANGTYPE = "lang_type";
	public static final String RS_KEY_HOSPITAL_CUSTOM_CODE = "custom_code";
	public static final String RS_KEY_HOSPITAL_CUSTOM_ID = "custom_id";
	public static final String RS_KEY_HOSPITAL_SELLER_TYPE = "seller_type";
	public static final String RS_KEY_HOSPITAL_NAME = "hospital_name";
	public static final String RS_KEY_HOSPITAL_PERSON_NAME = "name";
	public static final String RS_KEY_HOSPITAL_TEL_NO = "tel_no";
	public static final String RS_KEY_HOSPITAL_HP_NO = "hp_no";
	public static final String RS_KEY_HOSPITAL_IDNUMBER = "IDNumber";
	public static final String RS_KEY_HOSPITAL_ADDRESS = "addr";

	//Student
	public static final String RS_KEY_STUDENT_LANGTYPE = "lang_type";
	public static final String RS_KEY_STUDENT_CUSTOM_CODE = "custom_code";
	public static final String RS_KEY_STUDENT_CUSTOM_ID = "custom_id";
	public static final String RS_KEY_STUDENT_SELLER_TYPE = "seller_type";
	public static final String RS_KEY_STUDENT_SCHOOL_NAME ="school_name";
	public static final String RS_KEY_STUDENT_DEPARTMENT = "department";
	public static final String RS_KEY_STUDENT_NAME = "name";
	public static final String RS_KEY_STUDENT_HP_NO = "hp_no";
	public static final String RS_KEY_STUDENT_IDNUMBER = "IDNumber";
	public static final String RS_KEY_STUDENT_ADDRESS = "addr";

	//Weisang
	public static final String RS_KEY_WEISANG_LANGTYPE = "lang_type";
	public static final String RS_KEY_WEISANG_CUSTOM_CODE = "custom_code";
	public static final String RS_KEY_WEISANG_CUSTOM_ID = "custom_id";
	public static final String RS_KEY_WEISANG_SELLER_TYPE = "seller_type";
	public static final String RS_KEY_WEISANG_NAME = "name";
	public static final String RS_KEY_WEISANG_HP_NO = "hp_no";

	//Payment URL
	public static final String PAY_MENT_TOTAL_URL = "http://pay.gigao2o.com";
	public static final String PAY_MENT_TOTAL_PATH = "/RSPaymentService/Payment/Request_Payment_Order";

	//wechat_app_id
	public static final String RS_WECHAT_APPID = "wx44b262db87f2718a";
	
}