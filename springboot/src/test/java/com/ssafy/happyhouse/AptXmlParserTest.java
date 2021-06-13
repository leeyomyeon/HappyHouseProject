package com.ssafy.happyhouse;

import com.ssafy.happyhouse.aptxmlparser.service.AptXmlParserService;
import com.ssafy.happyhouse.aptxmlparser.service.AptXmlParserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class AptXmlParserTest {

    @Autowired
    AptXmlParserService service = new AptXmlParserServiceImpl();

    @Test
    public void parserTest() throws Exception {
//        service.getAptTradeList("11110", "202102");
        Map<String ,String> map = AptXmlParserServiceImpl.getPosition("");
        System.out.println(map.get("lat"));
        System.out.println(map.get("lng"));
    }
}
