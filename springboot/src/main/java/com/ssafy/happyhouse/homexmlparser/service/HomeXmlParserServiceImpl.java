package com.ssafy.happyhouse.homexmlparser.service;

import com.ssafy.happyhouse.homexmlparser.model.HomeXmlParserModel;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

@Service
public class HomeXmlParserServiceImpl implements HomeXmlParserService {

    @Override
    public List<HomeXmlParserModel> getHomeTradeList(String dongCode, String searchDate) throws Exception {
        String serviceKey = "nfmORmn9WYCnu3yj8JCd%2BlBECLl5XOYZDdxYo03vQwTDmx2Uo5Mrjjke2E6dhKGe8QFi5FyoN3NdefRqzLigQw%3D%3D";

        String serviceKeyDecoded = URLDecoder.decode(serviceKey, "UTF-8");
        String url = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHTrade" +
                "?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode(serviceKeyDecoded, "UTF-8") + /*Service Key*/
                "&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "=" + URLEncoder.encode(dongCode == null ? "11110" : dongCode, "UTF-8") + /*각 지역별 코드*/
                "&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "=" + URLEncoder.encode(searchDate == null ? "202105" : searchDate, "UTF-8");/*URL*//*월 단위 신고자료*/

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(url);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("item");
        List<HomeXmlParserModel> list = new LinkedList<>();
        for(int i = 0; i < nList.getLength(); i++){
            Node nNode = nList.item(i);
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                HomeXmlParserModel model = new HomeXmlParserModel();
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

                String name = getTagValue("연립다세대", eElement);
                System.out.println("연립다세대 : " + name);
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

                list.add(model);
            }
        }

        return list;
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }
}
