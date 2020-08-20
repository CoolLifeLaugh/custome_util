package com.insist.json;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fanping.jwq@alibaba-inc.com
 * @Date 2020-06-15 20:38
 */
public class JsonTest {

    public static void main(String[] args) {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setObligatory(true);
        searchDTO.setShow(true);
        Map<String,SearchDTO> map = new HashMap<>();
        map.put("resCode", searchDTO);
        SearchDTO searchDTO2 = new SearchDTO();
        searchDTO2.setObligatory(true);
        searchDTO2.setShow(true);
        searchDTO.setCode("resCode2");
        map.put("resCode2", searchDTO2);

        String str = JSON.toJSONString(map);
        System.out.println(str);
    }
}
