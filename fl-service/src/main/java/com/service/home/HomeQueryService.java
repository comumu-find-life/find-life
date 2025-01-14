package com.service.home;

import com.common.home.mapper.HomeMapper;
import com.common.home.response.HomeInformationResponse;
import com.common.home.response.HomeOverviewResponse;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.repository.HomeRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.common.utils.OptionalUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.service.home.HomeMessages.NOT_EXIST_HOME_ID;
import static com.service.user.UserMessages.NOT_EXIT_USER_ID;

@Service
@RequiredArgsConstructor
public class HomeQueryService {

    private final HomeRepository homeRepository;
    private final UserRepository userRepository;
    private final HomeMapper homeMapper;


//    @Cacheable(value = "homeCache", key = "'home_' + #id")
    public HomeInformationResponse findById(final Long id) {
        Home entity = OptionalUtil.getOrElseThrow(homeRepository.findById(id), NOT_EXIST_HOME_ID);
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(entity.getUserIdx()), NOT_EXIT_USER_ID);
        return homeMapper.toHomeInformation(entity, user);
    }

    /**
     * 모든 집 게시글 조회
     */
    @Cacheable(value = "homeOverviewCache", key = "'allHomes'")
    public List<HomeOverviewResponse> findAllHomes() {
        List<Home> homes = homeRepository.findAllSellHome();
        return homes.stream()
                .map(home -> {
                    Optional<User> optionalUser = userRepository.findById(home.getUserIdx());
                    return optionalUser.map(user -> homeMapper.toSimpleHomeDto(home, user)); // User가 있으면 매핑
                })
                .filter(Optional::isPresent) // Optional이 비어 있지 않은 경우만 필터링
                .map(Optional::get) // Optional에서 값 추출
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자의 집 게시물 모두 조회
     */
    public List<HomeOverviewResponse> findByUserId(final Long userIdx) {
        List<HomeOverviewResponse> response = new ArrayList<>();
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userIdx), NOT_EXIT_USER_ID);
        List<Home> homes = homeRepository.findByUserId(userIdx);
        homes.forEach(home -> {
            response.add(homeMapper.toSimpleHomeDto(home, user));
        });
        return response;
    }

    /**
     * 찜 목록 게시글 조회
     */
    public List<HomeOverviewResponse> findFavoriteHomes(final List<Long> homeIds) {
        return homeIds.stream()
                .map(homeRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(home -> {
                    Optional<User> optionalUser = userRepository.findById(home.getUserIdx());
                    return optionalUser
                            .map(user -> Stream.of(homeMapper.toSimpleHomeDto(home, user))) // User가 존재하면 매핑
                            .orElseGet(Stream::empty); // User가 없으면 빈 Stream 반환
                })
                .collect(Collectors.toList());
    }


    public List<HomeOverviewResponse> findByCity(final String cityName) {
        List<Home> homes = homeRepository.findByCity(cityName);
        return homes.stream()
                .map(home -> {
                    User user = userRepository.findById(home.getUserIdx())
                            .orElseThrow(() -> new EntityNotFoundException(NOT_EXIT_USER_ID + home.getUserIdx()));
                    return homeMapper.toSimpleHomeDto(home, user);
                })
                .collect(Collectors.toList());
    }
}
