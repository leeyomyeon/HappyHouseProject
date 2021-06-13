package com.ssafy.happyhouse.aptxmlparser.service;

import com.ssafy.happyhouse.aptxmlparser.mapper.AptXmlParserMapper;
import com.ssafy.happyhouse.aptxmlparser.model.AptXmlParserModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AptXmlParserServiceImpl implements AptXmlParserService{

    @Autowired
    AptXmlParserMapper mapper;

    @Override
    public List<AptXmlParserModel> getAptTradeList(String dongCode, String searchDate) throws Exception {
        String serviceKey = "nfmORmn9WYCnu3yj8JCd%2BlBECLl5XOYZDdxYo03vQwTDmx2Uo5Mrjjke2E6dhKGe8QFi5FyoN3NdefRqzLigQw%3D%3D";
        String serviceKeyDecoded = URLDecoder.decode(serviceKey, "UTF-8");

        String url = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade" +
                "?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode(serviceKeyDecoded, "UTF-8") + /*Service Key*/
                "&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "=" + URLEncoder.encode(dongCode, "UTF-8") + /*각 지역별 코드*/
                "&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "=" + URLEncoder.encode(searchDate, "UTF-8");/*URL*//*월 단위 신고자료*/

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(url);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("item");
        List<AptXmlParserModel> list = new LinkedList<>();
        for(int i = 0; i < nList.getLength(); i++){
            Node nNode = nList.item(i);
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                AptXmlParserModel model = new AptXmlParserModel();
                Element eElement = (Element) nNode;
                System.out.println("############################################");
                String dealAmount = getTagValue("거래금액", eElement);
                System.out.println("거래금액 : " + dealAmount);
                model.setDealAmount(dealAmount);

                String buildYear = getTagValue("건축년도", eElement);
                System.out.println("건축년도 : " + buildYear);
                model.setBuildYear(buildYear);

                String dealYear = getTagValue("년", eElement);
                System.out.println("년 : " + dealYear);
                model.setDealYear(dealYear);

                String dong = getTagValue("법정동", eElement);
                System.out.println("법정동 : " + dong);
                model.setDong(dong);

                String name = getTagValue("아파트", eElement);
                System.out.println("아파트 : " + name);
                model.setName(name);

                String dealMonth = getTagValue("월", eElement);
                System.out.println("월 : " + dealMonth);
                model.setDealMonth(dealMonth);

                String dealDay = getTagValue("일", eElement);
                System.out.println("일 : " + dealDay);
                model.setDealDay(dealDay);

                String area = getTagValue("전용면적", eElement);
                System.out.println("전용면적 : " + area);
                model.setArea(area);

                String jibun = getTagValue("지번", eElement);
                System.out.println("지번 : " + jibun);
                model.setJibun(jibun);

                String code = getTagValue("지역코드", eElement);
                System.out.println("지역코드 : " + code);
                model.setCode(code);

                String floor = getTagValue("층", eElement);
                System.out.println("층 : " + floor);
                model.setFloor(floor);

                String addr = mapper.getAddress(code, dong.trim());
                if(!jibun.equals("")) {
                    addr += "+"+jibun;
                }

                Map<String, String> map = getPosition(addr);

                String lat = map.get("lat");
                System.out.println("위도 : " + lat);
                model.setLat(lat);

                String lng = map.get("lng");
                System.out.println("경도 : " + lng);
                model.setLng(lng);

                list.add(model);
            }
        }

        return list;
    }

    private static String getTagValue(String tag, Element eElement) {
        if(eElement.getElementsByTagName(tag).item(0) == null) {
            return "";
        }
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public static Map<String, String> getPosition(String addr) throws Exception {
        Map<String ,String> map = new HashMap<>();
        String key = "AIzaSyAuky8HKJn2rlcyXa14_Due8ZXkx_1nvHY";
        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+ addr +"&key="+ key);
//        디버그용
//        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=서울특별시+종로구+청운동&key=AIzaSyAuky8HKJn2rlcyXa14_Due8ZXkx_1nvHY");

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String line;
        String result = "";

        while((line = br.readLine()) != null) {
            result = result.concat(line);
        }
//        System.out.println(result);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(result);
        JSONArray jsonArray = (JSONArray) jsonObject.get("results");
        JSONObject geometry = (JSONObject) ((JSONObject) jsonArray.get(0)).get("geometry");
        JSONObject location = (JSONObject) geometry.get("location");
        map.put("lat", location.get("lat").toString());
        map.put("lng", location.get("lng").toString());
        return map;
    }

}
