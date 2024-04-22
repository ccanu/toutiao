/**
 * 
 */
package com.heima.utils.thread;

import com.heima.model.wemedia.pojos.WmUser;

/**
 * 
 */


public class WmThreadLocalUtil {
     
	private final static ThreadLocal<WmUser> WM_USER_THREAD_LOCAL = new ThreadLocal<>();
	
	
	// スレッドに保存
	public static void setUser(WmUser wmUser) {
		WM_USER_THREAD_LOCAL.set(wmUser);
		
	}
	
	// スレッドから取得
	public static WmUser getUser() {
		return WM_USER_THREAD_LOCAL.get();
	}
	
	//削除
	
	public static void clear() {
		WM_USER_THREAD_LOCAL.remove();
	}
	
}
