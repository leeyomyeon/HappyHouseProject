package com.ssafy.happyhouse.searchCI.mapper;

import com.ssafy.happyhouse.searchCI.model.SearchCIModel;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchCIMapper {
    List<SearchCIModel> searchCI(@Param("shopName") String shopName,
                                     @Param("sidoName") String sidoName,
                                     @Param("sigunguName") String sigunguName);
}
