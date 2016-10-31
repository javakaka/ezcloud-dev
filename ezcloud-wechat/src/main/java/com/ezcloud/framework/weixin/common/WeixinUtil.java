package com.ezcloud.framework.weixin.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezcloud.framework.weixin.model.menu.AccessToken;
import com.ezcloud.framework.weixin.model.menu.AuthAccessToken;
import com.ezcloud.framework.weixin.model.menu.Button;
import com.ezcloud.framework.weixin.model.menu.CommonClickButton;
import com.ezcloud.framework.weixin.model.menu.CommonViewBottun;
import com.ezcloud.framework.weixin.model.menu.ComplexButton;
import com.ezcloud.framework.weixin.model.menu.WeiXinMenu;

/**
 *  * 公众平台通用接口工具类  
 */
public class WeixinUtil {
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
	// 获取access_token的接口地址（GET） 限2000（次/天）  
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 获取网页授权access_token的接口地址（GET）  
	public final static String auth_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
	// 获取ticket的接口 
	public final static String access_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	// 菜单创建（POST） 限1000（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 菜单删除（GET） 限1000（次/天）
	public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	// 通过普通接口获取用户基本信息
	public static String user_info_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 通过网页授权接口获取用户基本信息
	public static String url_auth_user_info_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//	拍卖服务号
	public final static String appId="wxa4db6002b27804cb";
	
	public final static String appSecret ="8ac14cfd3e38393b581609db6502e571";
	
	
//	//康必达服务号
//	public final static String appId="wx16edad686608edd8";
//	
//	public final static String appSecret ="b5b20a707d02b7c5badec86c3776d428";
	/**
	 ** 发起https请求并获取结果*
	 *@param requestUrl 请求地址
	 *@param requestMethod 请求方式（GET、POST）
	 *@param outputStr 提交的数据 
	 *@return
	 *JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)      
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr){ 
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化 
		TrustManager[] tm = { new MyX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		
		URL url = new URL(requestUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
		httpUrlConn.setSSLSocketFactory(ssf);
		
		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST
		httpUrlConn.setRequestMethod(requestMethod);
		
		if ("GET".equalsIgnoreCase(requestMethod))
		httpUrlConn.connect();
		
		// 当有数据需要提交时 
		if (null != outputStr) {
		OutputStream outputStream = httpUrlConn.getOutputStream();
		// 注意编码格式，防止中文乱码 
		outputStream.write(outputStr.getBytes("UTF-8"));
		outputStream.close();
		}
		
		// 将返回的输入流转换成字符串
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
		buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStream.close();
		inputStream = null;
		httpUrlConn.disconnect();
		jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		log.error("Weixin server connection timed out.");
		} catch (Exception e) {
		log.error("https request error:{}", e);
		}
		return jsonObject;
		//return null;
		};
		
		
		/** 
		 * 获取access_token 
		 * @param appid 凭证 
		 * @param appsecret 密钥 
		 * @return 
		 */
		public static AccessToken getAccessToken(String appid, String appsecret) {
			AccessToken accessToken = null;

			String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					accessToken = new AccessToken();
					accessToken.setToken(jsonObject.getString("access_token"));
					accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				} catch (JSONException e) {
					accessToken = null;
					// 获取token失败
					log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				}
			}
			return accessToken;
		}
		
		/** 
		 * 获取access_token 
		 * @param appid 凭证 
		 * @param appsecret 密钥 
		 * @return 
		 */
		public static AuthAccessToken getAuthAccessToken(String appid, String appsecret,String code) {
			AuthAccessToken accessToken = null;
			
			String requestUrl = auth_access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret).replace("CODE", code);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			log.info("jsonObject===========================>>"+jsonObject.toString());
//			System.out.println("jsonObject===========================>>"+jsonObject.toString());
			// 如果请求成功
			if (null != jsonObject) {
				try {
					accessToken = new AuthAccessToken();
					accessToken.setAccessToken(jsonObject.getString("access_token"));
					accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
					accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
					accessToken.setOpenid(jsonObject.getString("openid"));
					accessToken.setScope(jsonObject.getString("scope"));
//					accessToken.setUnionid(jsonObject.getString("unionid"));
				} catch (JSONException e) {
					e.printStackTrace();
					accessToken = null;
					// 获取token失败
					log.info("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				}
			}
			return accessToken;
		}
		/** 
		 * 获取ticket 
		 * @param appid 凭证 
		 * @param appsecret 密钥 
		 * @return 
		 */
		public static String getTicket(String access_token) {
			String ticket = null;
			
			String requestUrl = access_ticket_url.replace("ACCESS_TOKEN", access_token);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					ticket =jsonObject.getString("ticket");
				} catch (JSONException e) {
					ticket = null;
					// 获取ticket失败
					log.error("获取ticket失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				}
			}
			return ticket;
		}
		
	/**
		* 获取用户基本信息 50000000次
		* 参数说明
		* 	参数	说明
		* 	subscribe	用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
		* openid	用户的标识，对当前公众号唯一
		* nickname	用户的昵称
		* sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
		* city	用户所在城市
		* country	用户所在国家
		* province	用户所在省份
		* language	用户的语言，简体中文为zh_CN
		* headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
		* subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
		* unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
		* remark	公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
		* groupid	用户所在的分组ID
		 * @param access_token
		 * @param openid
		 * @return
		 */
		public static JSONObject getUserBaseInfo(String access_token,String openid) {
			String requestUrl = user_info_url.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			return jsonObject;
		}
		
		/**
		 * 返回字段：
		 *  openid	用户的唯一标识
		 *	nickname	用户昵称
		 *	sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
		 *	province	用户个人资料填写的省份
		 *	city	普通用户个人资料填写的城市
		 *	country	国家，如中国为CN
		 *	headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
		 *	privilege	用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
		 *	unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
		 * @param access_token
		 * @param openid
		 * @return
		 */
		public static JSONObject getUrlAuthUserBaseInfo(String access_token,String openid) {
			String requestUrl = url_auth_user_info_url.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			return jsonObject;
		}
		
		/** 
		 * 创建菜单 
		 * @param menu 菜单json
		 * @param accessToken 有效的access_token 
		 * @return 0表示成功，其他值表示失败 
		 */
		public static int createMenu(String menu, String accessToken) {
			int result = 0;
			
			// 拼装创建菜单的url
			String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
			// 将菜单对象转换成json字符串
			String jsonMenu = menu;
			// 调用接口创建菜单  
			JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
			
			if (null != jsonObject) {
				if (0 != jsonObject.getInt("errcode")) {
					result = jsonObject.getInt("errcode");
					log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				}
			}
			System.out.println("result========="+result);
			return result;
		}
		
		/** 
		 * 创建菜单 
		 * @param menu 菜单实例
		 * @param accessToken 有效的access_token 
		 * @return 0表示成功，其他值表示失败 
		 */
		public static int createMenu(WeiXinMenu menu, String accessToken) {
			int result = 0;
			
			// 拼装创建菜单的url
			String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
			// 将菜单对象转换成json字符串
			String jsonMenu = JSONObject.fromObject(menu).toString();
			// 调用接口创建菜单  
			JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
			
			if (null != jsonObject) {
				if (0 != jsonObject.getInt("errcode")) {
					result = jsonObject.getInt("errcode");
					log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				}
			}
			return result;
		}
		
		
		
		/** 
		 * 创建菜单 
		 * @param menu 菜单json
		 * @param accessToken 有效的access_token 
		 * @return 0表示成功，其他值表示失败 
		 */
		public static int deleteMenu(String accessToken) {
			int result = 0;
			// 拼装创建菜单的url
			String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
			// 调用接口删除菜单
			JSONObject jsonObject = httpRequest(url, "GET", null);
			
			if (null != jsonObject) {
				if (0 != jsonObject.getInt("errcode")) {
					result = jsonObject.getInt("errcode");
					log.error("删除菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				}
			}
			return result;
		}
		
		private static WeiXinMenu getMenu() {
			WeiXinMenu menu =new WeiXinMenu();
			CommonClickButton btn11 = new CommonClickButton();
			btn11.setName("日能耗分析");
			btn11.setType("click");
			btn11.setKey("report_day");
			
			CommonClickButton btn12 = new CommonClickButton();
			btn12.setName("周能耗分析");
			btn12.setType("click");
			btn12.setKey("report_week");
			
			CommonClickButton btn13 = new CommonClickButton();
			btn13.setName("月能耗分析");
			btn13.setType("click");
			btn13.setKey("report_month");
			
			CommonClickButton btn14 = new CommonClickButton();
			btn14.setName("年能耗分析");
			btn14.setType("click");
			btn14.setKey("report_year");
			
			CommonClickButton btn21 = new CommonClickButton();
			btn21.setName("近三天");
			btn21.setType("click");
			btn21.setKey("analysis_day");
			
			CommonClickButton btn22 = new CommonClickButton();
			btn22.setName("月对比");
			btn22.setType("click");
			btn22.setKey("analysis_month");
			
			CommonClickButton btn23 = new CommonClickButton();
			btn23.setName("年对比");
			btn23.setType("click");
			btn23.setKey("analysis_year");
			
			CommonViewBottun btn31 = new CommonViewBottun();
			btn31.setName("公司简介");
			btn31.setType("view");
			btn31.setUrl("http://kbdems.c43.163ns.cn/weixin/kbd/weixin/index.jsp");
			
//			CommonViewBottun btn32 = new CommonViewBottun();
//			btn32.setName("产品简介");
//			btn32.setType("view");
//			btn32.setUrl("www.ez-cloud.cn/kbd/weixin/product.jsp");
			
			CommonViewBottun btn32 = new CommonViewBottun();
			btn32.setName("联系我们");
			btn32.setType("view");
			btn32.setUrl("http://kbdems.c43.163ns.cn/weixin/kbd/weixin/contact.jsp");	
			
			CommonClickButton btn33 = new CommonClickButton();
			btn33.setName("绑定帐号");
			btn33.setType("click");
			btn33.setKey("bind");
			
			CommonClickButton btn34 = new CommonClickButton();
			btn34.setName("更新绑定");
			btn34.setType("click");
			btn34.setKey("update_bind");
			
			CommonClickButton btn35 = new CommonClickButton();
			btn35.setName("下载客户端");
			btn35.setType("click");
			btn35.setKey("download");
			
			ComplexButton mainBtn1 = new ComplexButton();
	        mainBtn1.setName("能耗分析");  
	        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14 });
	        
	        ComplexButton mainBtn2 = new ComplexButton();
	        mainBtn2.setName("能耗对比");  
	        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23 }); 
	        
	        ComplexButton mainBtn3 = new ComplexButton();
	        mainBtn3.setName("关于我们");
	        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33, btn34, btn35 }); 
			
	        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
			return menu;
		}
		
		
		private static WeiXinMenu getEZMenu() {
			WeiXinMenu menu =new WeiXinMenu();
			CommonViewBottun btn11 = new CommonViewBottun();
			btn11.setName("进入会场");
			btn11.setType("view");
//			btn11.setUrl("http://www.paipintang.com/paimai/index.htm");
//			btn11.setUrl("http://www.paipintang.com/paimai/paimaipage/site/index.do");
//			btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5bf17995fe86d1e6&redirect_uri=http%3a%2f%2fwww.paipintang.com%2fpaimai%2fpaimaipage%2fsite%2fbase-auth.do&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
			btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5bf17995fe86d1e6&redirect_uri=http%3a%2f%2ftestweixin.hqzgzs.com%2fcrds%2fwx%2fsite%2fbase-auth-score.jhtml&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
			
			CommonViewBottun btn12 = new CommonViewBottun();
			btn12.setName("热拍单品");
			btn12.setType("view");
			btn12.setUrl("http://www.paipintang.com/paimai/index-hot.htm");	
			
			CommonViewBottun btn13 = new CommonViewBottun();
			btn13.setName("我的");
			btn13.setType("view");
			btn13.setUrl("http://www.paipintang.com/paimai/index-user-home.htm");	
			
			CommonViewBottun btn21 = new CommonViewBottun();
			btn21.setName("东盟彩宝网");
			btn21.setType("view");
			btn21.setUrl("http://www.zzzb.tv");	
			
			CommonViewBottun btn31 = new CommonViewBottun();
			btn31.setName("微信小店");
			btn31.setType("view");
			btn31.setUrl("http://mp.weixin.qq.com/bizmall/mallshelf?id=&t=mall/list&biz=MzA5Nzk3MzQzNA==&shelf_id=5&showwxpaytitle=1#wechat_redirect");
			
			
			ComplexButton mainBtn1 = new ComplexButton();
	        mainBtn1.setName("进入拍卖");  
	        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13});
	        
	        ComplexButton mainBtn2 = new ComplexButton();
	        mainBtn2.setName("旗下网站");  
	        mainBtn2.setSub_button(new Button[] { btn21}); 
	        
//	        ComplexButton mainBtn3 = new ComplexButton();
//	        mainBtn3.setName("成功案例");
//	        mainBtn3.setSub_button(new Button[] { btn31, btn32}); 
			
	        menu.setButton(new Button[] { btn11, mainBtn2, btn31 });  
			return menu;
		}
		
		private static WeiXinMenu getSzhqWeixinMenu() {
			WeiXinMenu menu =new WeiXinMenu();
			CommonViewBottun btn11 = new CommonViewBottun();
			btn11.setName("华强信用");
			btn11.setType("view");
//			btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5bf17995fe86d1e6&redirect_uri=http%3A%2F%2Ftestweixin.hqzgzs.com%2Fcrds%2Fwx%2Fsite%2Fbase-auth-score.jhtml&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
			btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5bf17995fe86d1e6&redirect_uri=http%3A%2F%2Fweixin.sectc.cn%2Fcrds%2Fwx%2Fsite%2Fbase-auth-score.jhtml&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
			
			CommonViewBottun btn12 = new CommonViewBottun();
			btn12.setName("官网");
			btn12.setType("view");
			btn12.setUrl("http://weixin.sectc.cn/");
			
			CommonViewBottun btn21 = new CommonViewBottun();
			btn21.setName("粉丝社区");
			btn21.setType("view");
			btn21.setUrl("https://shequ.yunzhijia.com/thirdapp/forum/network/578f24cee4b057146d8e70e1");	
			
//			CommonViewBottun btn31 = new CommonViewBottun();
//			btn31.setName("微信小店");
//			btn31.setType("view");
//			btn31.setUrl("http://mp.weixin.qq.com/bizmall/mallshelf?id=&t=mall/list&biz=MzA5Nzk3MzQzNA==&shelf_id=5&showwxpaytitle=1#wechat_redirect");
			
			
			ComplexButton mainBtn1 = new ComplexButton();
			mainBtn1.setName("服务平台");  
			mainBtn1.setSub_button(new Button[] { btn11, btn12});
			
			ComplexButton mainBtn2 = new ComplexButton();
			mainBtn2.setName("粉丝互动");  
			mainBtn2.setSub_button(new Button[] { btn21}); 
			
		//	        ComplexButton mainBtn3 = new ComplexButton();
		//	        mainBtn3.setName("成功案例");
		//	        mainBtn3.setSub_button(new Button[] { btn31, btn32}); 
			
			menu.setButton(new Button[] { btn11,  mainBtn2});
			return menu;
		}
		
		public static void main(String s[])
		{
			/** 取acces_token**/
			String appId ="wx5bf17995fe86d1e6";
			String appSecret ="777ff79bed38a61fe142a7c821318d06";
			AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
			System.out.println("token ====="+at.getToken());
			String ticket =WeixinUtil.getTicket(at.getToken());
			System.out.println("ticket ====="+ticket);
			String menu ="";
			/**取自定义菜单（可通过取数据库代替）***/
//			WeiXinMenu mm =getMenu();
			WeiXinMenu mm =getSzhqWeixinMenu();
			JSONObject obj =JSONObject.fromObject(mm);
			menu =obj.toString();
			System.out.print("menu------------------"+menu);
			
			/**删除菜单*/
//			deleteMenu(at.getToken());
			
			/***创建菜单***/
			createMenu( menu, at.getToken());
//			String url =URLEncoder.encode("http://www.paipintang.com/paimai/paimaipage/site/base-auth.do");
//			System.out.println("url------>>"+url);
		}
//		public static void main(String[] args) {
////			String ticket ="sM4AOVdWfPE4DxkXGEs8VMvm8qyqzSae7c65al-PfWqOEvroJxHRONNTukI3fl4vlwqLTCZFJnwL_eKvQ7JMiw";
//			String token ="T_F193PaGWKmrGx_k50-UF6tKLPGuxNQy6EEm2c4sW-ZZ26m-rApfLVk-KY1YvmxdGzxp2tdRl-y2C0Zyh-u_jQm0F9GH5NRLhM3sF-ePlQSYFjAFAXDK";
//			String openid ="oulKnuCo9nCcqfx5lIzlzKBNBzyw";
//			JSONObject obj =getUserBaseInfo(token,openid);
//			String nickname =obj.getString("nickname");
//			System.out.println("----------nickname------>>"+nickname);
//		}
}
