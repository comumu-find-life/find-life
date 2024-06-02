package com.service.home;

import com.core.home.model.Home;
import com.core.home.reposiotry.HomeRepository;
import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.request.HomeGeneratorRequest;
import com.service.home.dto.LatLng;
import com.service.home.dto.HomeOverviewResponse;
import com.service.home.mapper.HomeMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class HomeServiceTest {

    @Test
    void test(){

    }
}