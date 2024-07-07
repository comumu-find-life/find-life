package com.service.home.utils;

import org.springframework.data.domain.PageRequest;


public class HomeUtil {

    static public PageRequest toPageRequest(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }
}
