package io.pdf.core.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import io.pdf.core.common.LocalizedTextUtil;
/**
 * Plugin Base Exception 
 * @author yangzz
 *
 */
@SuppressWarnings("serial")
public class BaseException extends RuntimeException{
	
	private static Log logger= LogFactory.getLog(BaseException.class);
	
	private String detailMessage;

	private Class<?> caller;
	//error code
	private long code= -1;
	//error arguments
	private Object[] args =null;

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(long code) {
		this(code,null,null,null);
	}
	
	public BaseException(long code,Class<?> caller) {
		this(code,null,null,caller);
	}
	
	public BaseException(long code, Object[] args) {
		this(code,args,null,null);
	}
	
	public BaseException(long code, Object[] args,Class<?> caller) {
		this(code,args,null,caller);
	}
	
	public BaseException(long code, Throwable cause) {
		this(code,null,cause,null);
	}
	
	public BaseException(long code, Throwable cause,Class<?> caller) {
		this(code,null,cause,caller);
	}
	
	public BaseException(long code, Object[] args,Throwable cause) {
		this(code,args,cause,null);
	}
	
	public BaseException(long code, Object[] args,Throwable cause,Class<?> caller) {
		super(cause);
		this.code= code;
		this.args= args;
		this.caller= caller;
		this.detailMessage= getInternalDetailMessage();
	}

	public long getCode() {
		return code;
	}
	
	/**
	 * get arguments, for example: {0}...{n}
	 * @return Object[]
	 */
	public Object[] getArgs(){
		return args;
	}
	
	@Override
	public String getMessage() {
		if(-1== this.code){
			return super.getMessage();
		}
		return detailMessage;
	}
	
	private String getInternalDetailMessage(){
		Class<?> clazz= getClass();
		if(null!= caller){
			clazz= caller;
		}
		else if(clazz== BaseException.class){
			clazz= Object.class; 
		}
		Locale locale= LocaleContextHolder.getLocale();
		try{
			return LocalizedTextUtil.getMessage(clazz, String.valueOf(code), args, locale);
		}catch(NoSuchMessageException e){
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		if(response.isCommitted()){
			return;
		}
		//set error code
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
		//set error type
		response.setContentType("application/json;charset=UTF-8");
		//set error context
//		JsonObject result= new JsonObject();
//		result.addProperty("status", "error");
//		if(code!= -1){
//			result.addProperty("code", this.code);
//		}
//		result.addProperty("message", getMessage());
//		try {
//			response.getWriter().write(result.toString());
//			response.getWriter().flush();
//			response.getWriter().close();
//		} catch (IOException e) {
//			logger.error("Write stream error", e);
//		}
	}
}
