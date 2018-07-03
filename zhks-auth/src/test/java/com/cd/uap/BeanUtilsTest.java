package com.cd.uap;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

public class BeanUtilsTest {

	@Test
	public void test1() {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("roleName", "aaa");
		Role role = new Role();
		BeanUtils.copyProperties(hashMap, role);
		System.out.println(role.getRoleName());
	}
	
}
