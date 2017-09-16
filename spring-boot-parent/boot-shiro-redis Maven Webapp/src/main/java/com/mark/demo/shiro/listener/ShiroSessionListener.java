package com.mark.demo.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
*hxp(hxpwangyi@126.com)
*2017年9月16日
*
*/
public class ShiroSessionListener implements SessionListener {
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public void onStart(Session session) {
		logger.info(session.getId()+":onStart");
	}

	@Override
	public void onStop(Session session) {
		logger.info(session.getId()+":onStop");
	}

	@Override
	public void onExpiration(Session session) {
		logger.info(session.getId()+":onExpiration");
	}

}
