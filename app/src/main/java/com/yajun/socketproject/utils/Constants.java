package com.yajun.socketproject.utils;

public class Constants {

	public static final class INTENT_KEY{
		public static final String MENU_TO_GOODS_LIST = "category_menu";
		public static final int LOGIN_REQUEST_CODE = 20000;
		public static final int LOGIN_RESULT_SUCCESS_CODE = 20001;
		public static final int REQUEST_CART_TO_DETAIL = 50000;
		/**���ҵľ�������࣬����ʱˢ�µ�¼��Ϣ*/
		public static final int REQUEST_MOREACTIVITY = 60000;
		/**��GoodsInfo���󴫸���Ʒ�б����*/
		public static final String INFO_TO_DETAIL = "goodsinfo_to_detail";
		/**���ҵĹ�ע��ת����ҳ*/
		public static final String FROM_FAVOR = "from_favor";
		/**��������ת�����ﳵ*/
		public static final String FROM_DETAIL = "from_detail";
		/**ˢ�¹��ﳵ��Ʒ��*/
		public static final String REFRESH_INCART = "refresh_incart";
		/**ͨ��Bmob��¼�ɹ�*/
		public static final String LOGIN_BMOB_SUCCESS = "bmob_success";
		/**ע��*/
		public static final String LOGOUT = "logout";
	}
	
	public static final class BROADCAST_FILTER {
		public static final String FILTER_CODE = "broadcast_filter";
		public static final String EXTRA_CODE = "broadcast_intent";
	}
	
	public static final class URL{
		/**Ӧ���Ƽ�*/
		public static final String APPS = "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/apps/apps.txt";
		/**�˵�*/
		public static final String MENUJSON = "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/menujson/menujson.txt";
	}
	
}
