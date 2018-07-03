package com.cd.uap.json;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Map;

/**
 * Created by li.mingyang on 2018/5/2.
 */
public class JacksonToObject{

    @Test
    public void test1(){
        String json = "{\"sjh\":\"18668034037\",\"xm\":\"联系微号\",\"yljgid\":\"8f1d-3dcd-4bda-8a65-9bb1a83dd389\",\"rylx\":\"2\",\"id\":\"sj2\",\"zgzt\":\"1\",\"issh\":\"1\"}";
        Map<String, Object> userInfoMap = (Map<String, Object>) JSON.parse(json);
        System.out.println(userInfoMap.get("yljgid"));
    }

}
