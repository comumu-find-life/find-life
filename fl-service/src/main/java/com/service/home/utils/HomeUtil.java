package com.service.home.utils;

import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeImage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


public class HomeUtil {

    static public PageRequest toPageRequest(int pageNumber, int pageSize, Sort sort) {
        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
