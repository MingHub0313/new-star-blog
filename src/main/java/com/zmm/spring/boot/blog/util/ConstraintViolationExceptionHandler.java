package com.zmm.spring.boot.blog.util;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 1805783671
 * @version ConstraintViolationExceptionHandler-1.0
 * @time 2018年12月31日 下午7:24:23
 * @Desc 描述  处理Bean验证,如果验证过程中有问题,可以抛出多个异常.
 * 			  条件通过Bean中注解
 */
public class ConstraintViolationExceptionHandler {

	/**
	 * 
	 * @Desc 描述--- 获取批量异常信息
	 * @方法返回类型 String
	 * @author 1805783671
	 * @时间 2018年12月31日 下午7:24:53
	 * @param e
	 * @return
	 */
	public static String getMessage(ConstraintViolationException e) {
		List<String> msgList = new ArrayList<>();
		for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
			msgList.add(constraintViolation.getMessage());
        }
		String messages = StringUtils.join(msgList.toArray(), ";");
		return messages;
	}
}
