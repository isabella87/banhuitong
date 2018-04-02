package com.bht.banhuitong;

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
}
