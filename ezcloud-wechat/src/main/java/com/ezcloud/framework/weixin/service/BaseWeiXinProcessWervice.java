package com.ezcloud.framework.weixin.service;

import com.ezcloud.framework.weixin.model.Message;
import com.ezcloud.framework.weixin.model.OutMessage;

public abstract class BaseWeiXinProcessWervice {
	/**
	 * 对微信请求进行业务处理
	 * @author Administrator
	 */
		/****接收微信发来的请求消息类型**/
		//接收的文本消息
		public static final String REQUEST_MSG_TYPE_TEXT ="text";
		//接收的图片消息
		public static final String REQUEST_MSG_TYPE_IMAGE ="image";
		//接收的语音消息
		public static final String REQUEST_MSG_TYPE_VOICE ="voice";
		//接收的语音识别消息
		public static final String REQUEST_MSG_TYPE_VOICE_RECOGNITION ="voice_recognition";
		//接收的视频消息
		public static final String REQUEST_MSG_TYPE_VIDEO ="video";
		//接收的地理位置消息
		public static final String REQUEST_MSG_TYPE_LOCATION ="location";
		//接收的链接消息
		public static final String REQUEST_MSG_TYPE_LINK ="link";
		//接收的事件消息
		public static final String REQUEST_MSG_TYPE_EVENT ="event";
		//接收的关注事件消息
		public static final String REQUEST_MSG_TYPE_EVENT_SUBSCRIBE ="subscribe";
		//接收的取消关注事件消息
		public static final String REQUEST_MSG_TYPE_EVENT_UNSUBSCRIBE ="unsubscribe";
		//接收的未关注时扫描二维码消息
		public static final String REQUEST_MSG_TYPE_EVENT_SCAN_UNSUBSCRIBE ="scan_unsubscribe";
		//接收的已关注公众号时扫描二维码的消息
		public static final String REQUEST_MSG_TYPE_EVENT_SCAN_SUBSCRIBE ="scan_subscribe";
		//接收的上报地理位置事件消息
		public static final String REQUEST_MSG_TYPE_EVENT_LOCATION ="LOCATION";
		//接收的用户点击事件消息
		public static final String REQUEST_MSG_TYPE_EVENT_CLICK ="CLICK";
		//接收用户的自定义菜单点击事件消息
		public static final String REQUEST_MSG_TYPE_EVENT_VIEW ="VIEW";
		//接收用户的自定义菜单点击  scancode_push：扫码推事件的事件推送
		public static final String REQUEST_MSG_TYPE_EVENT_SCANCODE_PUSH ="scancode_push";
		//接收用户的自定义菜单点击 scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框的事件推送
		public static final String REQUEST_MSG_TYPE_EVENT_SCANCODE_WAITMSG ="scancode_waitmsg";
		//自定义菜单点击 pic_sysphoto：弹出系统拍照发图的事件推送
		public static final String REQUEST_MSG_TYPE_EVENT_PIC_SYSPHOTO ="pic_sysphoto";
		//自定义菜单点击 pic_photo_or_album：弹出拍照或者相册发图的事件推送
		public static final String REQUEST_MSG_TYPE_EVENT_PIC_PHOTO_OR_ALBUM ="pic_photo_or_album";
		//自定义菜单点击  pic_weixin：弹出微信相册发图器的事件推送
		public static final String REQUEST_MSG_TYPE_EVENT_PIC_WEIXIN ="pic_weixin";
		//自定义菜单点击  location_select：弹出地理位置选择器的事件推送
		public static final String REQUEST_MSG_TYPE_EVENT_LOCATION_SELECT ="location_select";
		/***返回的被动响应消息类型**/
		//返回文本消息
	    public static final String RESPONSE_MESSAGE_TYPE_TEXT = "text";
	    //返回图片消息
	    public static final String RESPONSE_MESSAGE_TYPE_IMAGE = "image";
	  //返回语音消息
	    public static final String RESPONSE_MESSAGE_TYPE_VOICE = "voice";
	  //返回视频消息
	    public static final String RESPONSE_MESSAGE_TYPE_VIDEO = "video";
	  //返回音乐消息
	    public static final String RESPONSE_MESSAGE_TYPE_MUSIC = "music";
	  //返回图文消息
	    public static final String RESPONSE_MESSAGE_TYPE_NEWS = "news";
	    
	    /***返回的客服消息类型**/
	    //客服文本消息
	    public static final String RESPONSE_CUSTOM_MESSAGE_TYPE_TEXT = "text";
	    //客服图片消息
	    public static final String RESPONSE_CUSTOM_MESSAGE_TYPE_IMAGE = "image";
	    //客服语音消息
	    public static final String RESPONSE_CUSTOM_MESSAGE_TYPE_VOICE = "voice";
	    //客服视频消息
	    public static final String RESPONSE_CUSTOM_MESSAGE_TYPE_VIDEO = "video";
	    //客服音乐消息
	    public static final String RESPONSE_CUSTOM_MESSAGE_TYPE_MUSIC = "music";
	    //客服图文消息
	    public static final String RESPONSE_CUSTOM_MESSAGE_TYPE_NEWS = "news";
	    
		private Message outMsg;
		
		//处理文本请求
		public abstract  OutMessage handleTextMsgRequest(Object msg);
		//处理图片请求
		public abstract OutMessage handleImageMsgRequest(Object msg);
		//处理普通语音请求
		public abstract OutMessage handleVoiceMsgRequest(Object msg);
		//处理普通语音请求
		public abstract OutMessage handleVoiceRecognitionMsgRequest(Object msg);
		//处理视频请求
		public abstract OutMessage handleVideoMsgRequest(Object msg);
		//处理位置信息请求
		public abstract OutMessage handleLocationMsgRequest(Object msg);
		//处理链接信息请求
		public abstract OutMessage handleLinkMsgRequest(Object msg);
		//处理事件
		public abstract OutMessage handleEventMsgRequest(Object msg);
		//已关注时扫描二维码事件
		public abstract OutMessage handleScanSubscribeEventMsgRequest(Object msg);
		//未关注时扫描二维码事件
		public abstract OutMessage handleScanUnSubscribeEventMsgRequest(Object msg);
		//处理上报地理位置事件
		public abstract OutMessage handleLocationEventMsgRequest(Object msg);
		//处理自定义菜单点击拉取消息事件
		public abstract OutMessage handleClickEventMsgRequest(Object msg);
		//处理自定义菜单点击拉取消息事件
		public abstract OutMessage handleClickViewEventMsgRequest(Object msg);
		//处理自定义菜单点击拉取消息事件
		public abstract OutMessage handleClickScancodePushEventMsgRequest(Object msg);
		//自定义菜单点击 scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框的事件推送
		public abstract OutMessage handleClickScancodeWaitmsgEventMsgRequest(Object msg);
		//自定义菜单点击 pic_sysphoto：弹出系统拍照发图的事件推送
		public abstract OutMessage handleClickPicSysphotoEventMsgRequest(Object msg);
		//自定义菜单点击 pic_photo_or_album：弹出拍照或者相册发图的事件推送
		public abstract OutMessage handleClickPicPhotoOrAlbumEventMsgRequest(Object msg);
		//自定义菜单点击  pic_weixin：弹出微信相册发图器的事件推送
		public abstract OutMessage handleClickPicWeixinEventMsgRequest(Object msg);
		//自定义菜单点击  location_select：弹出地理位置选择器的事件推送
		public abstract OutMessage handleClickLocationSelectEventMsgRequest(Object msg);
		//处理关注事件
		public abstract OutMessage handleSubscribeEventMsgRequest(Object msg);
		//处理取消关注事件
		public abstract OutMessage handleUnSubscribeEventMsgRequest(Object msg);

}