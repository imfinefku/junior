package com.junior.program.common;

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
	 * 失败
	 * 
	 * @return
	 */
	public static <T> CommonResult<T> failed() {
		return new CommonResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
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
}
