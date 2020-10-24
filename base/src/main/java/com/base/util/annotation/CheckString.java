package com.base.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.base.util.validation.CheckStringValidation;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CheckStringValidation.class)
public @interface CheckString {
	
	boolean haode() default false;
	
	String message() default "字符串不满足规则";
	
	/** validate自定义注解必带两属性 */
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
