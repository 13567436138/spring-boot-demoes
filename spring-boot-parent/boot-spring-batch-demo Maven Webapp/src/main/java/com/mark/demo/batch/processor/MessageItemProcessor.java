package com.mark.demo.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.mark.demo.batch.entity.Message;
import com.mark.demo.batch.entity.ProcessedMessage;

/*
*hxp(hxpwangyi@126.com)
*2017Äê10ÔÂ3ÈÕ
*
*/
public class MessageItemProcessor implements ItemProcessor<Message,ProcessedMessage> {

	public ProcessedMessage process(Message message) throws Exception {
		ProcessedMessage pm=new ProcessedMessage();
		pm.setContent(message.getContent()+"processed");
		pm.setMessageId(message.getMessageId());
		pm.setReceiver(message.getReceiver());
		pm.setReceiveTime(message.getReceiveTime());
		pm.setType(2);
		return pm;
	}

}
