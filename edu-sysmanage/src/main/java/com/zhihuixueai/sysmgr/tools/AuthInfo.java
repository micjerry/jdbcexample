package com.zhihuixueai.sysmgr.tools;

import java.util.Base64;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhihuixueai.common.util.aes.AESManager;
import com.zhihuixueai.common.util.id.SnowidGenerator;
import com.zhihuixueai.sysmgr.db.model.users.SysUser;

public class AuthInfo {
	private static final Logger logger = LoggerFactory.getLogger(AuthInfo.class);
	
	public static void makeUserAuth(SysUser user, String rawpass) {
		if (null == user || StringUtils.isEmpty(rawpass))
			return;

		if (user.getUser_id() == null) {
			long userid = SnowidGenerator.getInstance().nextId();
			user.setUser_id(String.valueOf(userid));
		}
		
		if (user.getSalt() == null) {
			user.setSalt(UUID.randomUUID().toString().substring(0, 16));
		}
		
		String key = user.getUser_id();
		if (key.length() < 16 ) {
			key = key + key;
		}
		
		key = key.substring(0, 16);
		String iv = user.getSalt();
		
		try {
			byte[] cipherText = AESManager.encrypt(key.getBytes(), iv.getBytes(), rawpass.getBytes());
			String cipherStr = new String(cipherText, "ISO-8859-1");
			String cipherpass = Base64.getEncoder().encodeToString(cipherStr.getBytes("ISO-8859-1"));
			user.setPassword(cipherpass);
			logger.debug("reset user:{} acdd: {} scd:", user.getUser_id(), user.getSalt(), user.getPassword());
		} catch (Exception e) {
			logger.error("encrypt raw pass failed key:{}, iv:{}, raw:{}, exception:{}", key, iv, rawpass, e);
			return;
		} 
		
		return;
	}
}
