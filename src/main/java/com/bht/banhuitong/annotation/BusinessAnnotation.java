package com.bht.banhuitong.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface BusinessAnnotation {
	
	/**
	 * 权限码
	 * @return
	 */
	int perm() default 0;
	
	/**
	 * 是否需要记录业务日志
	 * @return
	 */
	boolean recordLog() default false;
	
	/**
	 * 服务需要，每个模块单独排名（凡是返回List<Map<String,String>>的服务都需要定义此列，一遍下载定位服务）
	 * @return
	 */
	int serviceno() default 0;
}
