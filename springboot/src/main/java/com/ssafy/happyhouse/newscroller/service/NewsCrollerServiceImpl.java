package com.ssafy.happyhouse.newscroller.service;

import com.ssafy.happyhouse.newscroller.mapper.NewsCrollerMapper;
import com.ssafy.happyhouse.newscroller.model.NewsCrollerModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class NewsCrollerServiceImpl implements NewsCrollerService{

    @Autowired
    NewsCrollerMapper mapper;

    // http://localhost:8078/newscroller/서울특별시/금천구
    @Override
    public List<NewsCrollerModel> getNewsList(String city, String gugun) throws Exception {
        List<NewsCrollerModel> list = new LinkedList<>();
        String cityCode = mapper.getCityCode(city);
        String gugunCode = mapper.getGugunCode(gugun, cityCode.substring(0, 2));

        String URL = "https://land.naver.com/news/region.nhn?city_no=" + cityCode + "&dvsn_no=" + gugunCode;

        Document doc = Jsoup.connect(URL).get();
        Elements newsList = doc.select(".section_headline li");

        for(Element news : newsList) {
            String title = news.select("dt").text();
            String url = news.select("a").attr("abs:href");
            String desc = news.select("dd").text();

            list.add(new NewsCrollerModel(title, url, desc));
        }

        return list;
    }
}
