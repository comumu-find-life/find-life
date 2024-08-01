package com.service.home.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


public class HomeUtil {

    static public PageRequest toPageRequest(int pageNumber, int pageSize, Sort sort) {
        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
