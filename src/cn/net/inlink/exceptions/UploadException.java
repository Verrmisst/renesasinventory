package cn.net.inlink.exceptions;

/**
 * 自定义上传异常
 * @author Yz
 *
 */
public class UploadException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	
	public UploadException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	

	public UploadException() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
