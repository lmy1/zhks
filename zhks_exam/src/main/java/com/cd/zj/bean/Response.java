package com.cd.zj.bean;


public class Response {
	
	public  class  Result{
		public String code;
		public String message;
		public Result(String code,String message) {
			this.code=code;
			this.message=message;
		}
	}
	
	private int status;
	
	private Result error;
	
	private Object data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Result getResult() {
		return error;
	}

	public void setResult(String code,String message) {
		this.error=new Result(code, message);
	}
}
