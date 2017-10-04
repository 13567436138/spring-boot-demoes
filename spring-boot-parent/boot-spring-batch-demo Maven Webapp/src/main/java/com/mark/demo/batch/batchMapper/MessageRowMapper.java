package com.mark.demo.batch.batchMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.mark.demo.batch.entity.Message;

/*
*hxp(hxpwangyi@126.com)
*2017Äê10ÔÂ3ÈÕ
*
*/
public class MessageRowMapper implements RowMapper<Message> {

	public Message mapRow(ResultSet rs, int i) throws SQLException {
		Message message=new Message();
		message.setContent(rs.getString("content"));
		message.setMessageId(rs.getLong("messageId"));
		message.setReceiver(rs.getString("receiver"));
		message.setReceiveTime(new Date(rs.getLong("receiveTime")));
		message.setType(rs.getInt("type"));
		return message;
	}

}
