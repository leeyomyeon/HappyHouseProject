package com.ssafy.happyhouse.newscroller.mapper;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsCrollerMapper {
    String getCityCode(String city);
    String getGugunCode(@Param("gugun") String gugun, @Param("cityCode")String cityCode);
}
