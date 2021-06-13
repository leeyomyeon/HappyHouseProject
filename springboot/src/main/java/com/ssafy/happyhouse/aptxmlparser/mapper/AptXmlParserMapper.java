package com.ssafy.happyhouse.aptxmlparser.mapper;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AptXmlParserMapper {
    String getAddress(@Param("code") String code, @Param("dong") String dong);
}
