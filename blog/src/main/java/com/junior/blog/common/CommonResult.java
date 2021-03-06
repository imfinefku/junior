package com.junior.blog.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 返回对象
 * 
 * @author junior
 *
 */
public class CommonResult<T> {

	/** 状态码 */
	private int code;

	/** 提示信息 */
	private String message;

	/** 返回数据 */
	private T data;

	/** 数据总量 */
	private int count;

	private CommonResult(int code, String message, T data, int count) {
		this.code = code;
		this.message = message;
		this.data = data;
		this.count = count;
	}

	private CommonResult(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 成功
	 * 
	 * @return
	 */
	public static <T> CommonResult<T> success() {
		return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
	}

	/**
	 * 成功
	 * 
	 * @param data
	 * @return
	 */
	public static <T> CommonResult<T> success(T data) {
		return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
	}

	/**
	 * 成功
	 * 
	 * @param data
	 * @return
	 */
	public static <T> CommonResult<T> success(T data, int count) {
		return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data, count);
	}

	/**
	 * 失败
	 * 
	 * @return
	 */
	public static <T> CommonResult<T> failed() {
		return new CommonResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
	}

	/**
	 * 失败
	 * 
	 * @return
	 */
	public static <T> CommonResult<T> failed(String message) {
		return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
	}

	/**
	 * 参数验证失败返回结果
	 */
	public static <T> CommonResult<T> validateFailed() {
		return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null);
	}

	/**
	 * 参数验证失败返回结果
	 * 
	 * @param message 提示信息
	 */
	public static <T> CommonResult<T> validateFailed(String message) {
		return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
	}

	/**
	 * 未登录返回结果
	 */
	public static <T> CommonResult<T> unauthorized(T data) {
		return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
	}

	/**
	 * 未授权返回结果
	 */
	public static <T> CommonResult<T> forbidden(T data) {
		return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
	}

	/**
	 * 参数校验失败
	 * 
	 * @param bindingResult
	 * @return
	 */
	public static CommonResult<List<String>> checkError(BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		List<String> errorList = new ArrayList<String>();
		for (FieldError fe : fieldErrors) {
			errorList.add(fe.getDefaultMessage());
		}
		return new CommonResult<List<String>>(ResultCode.VALIDATE_FAILED.getCode(), "参数校验失败", errorList);
	}
}
