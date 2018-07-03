package com.cd.zhks.utils;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
/**
 * Created by ma.xiaofeng on 2018/4/17.
 */
public class LoginUserUtils {

    /**
     * 从请求头（header）中拿到用户信息
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> appUserInfo() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String userInfo = request.getHeader("userInfo");
		userInfo=URLDecoder.decode(userInfo,"UTF-8");
		if (StringUtils.isEmpty(userInfo)) {
			throw new Exception("无法获取该用户信息");
		}
		Map<String, Object> userInfoMap = (Map<String, Object>) JSON.parse(userInfo);
		return userInfoMap;
	}

}
