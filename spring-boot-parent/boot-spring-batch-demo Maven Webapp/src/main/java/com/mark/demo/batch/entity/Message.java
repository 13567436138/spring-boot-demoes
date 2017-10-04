package com.mark.demo.batch.entity;

import java.util.Date;

/*
*hxp(hxpwangyi@126.com)
*2017Äê10ÔÂ3ÈÕ
*
*/
public class Message {
	private Long messageId;
	private Date receiveTime;
	private String content;
	private String receiver;
	private int type;
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
