package com.service.home;

import com.common.home.mapper.HomeMapper;
import com.common.home.response.HomeInformationResponse;
import com.common.home.response.HomeOverviewResponse;
import com.common.home.response.HomeOverviewWrapper;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.QHome;
import com.core.api_core.home.repository.HomeRepository;
import com.core.api_core.user.model.QUser;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.common.utils.OptionalUtil;
import com.querydsl.core.Tuple;
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

    public HomeInformationResponse findById(final Long id) {
        Tuple result = OptionalUtil.getOrElseThrow(homeRepository.findHomeAndUserById(id),NOT_EXIST_HOME_ID);
        Home home = result.get(0, Home.class);
        User user = result.get(1, User.class);
        return homeMapper.toHomeInformation(home, user);
    }

    /**
     * 모든 집 게시글 조회
     */
    @Cacheable(value = "homeOverviewCache", key = "'allHomes'")
    public HomeOverviewWrapper findAllHomes() {
        List<Tuple> tuples = homeRepository.findAllSellHome();  // 데이터베이스에서 결과 가져오기
        System.out.println("tuples -- " + tuples.size());
        List<HomeOverviewResponse> homeOverviewResponses  = tuples.stream()
                .map(tuple -> {
                    Home home = tuple.get(QHome.home);
                    User user = tuple.get(QUser.user);
                    return homeMapper.toSimpleHomeDto(home, user);  // DTO로 변환
                })
                .toList();

        return new HomeOverviewWrapper(homeOverviewResponses);
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
        List<Tuple> favoriteHomes = homeRepository.findFavoriteHomes(homeIds);
        System.out.println("homeIdsss");
        System.out.println(homeIds.size());
        System.out.println(favoriteHomes.size());
        return favoriteHomes.stream()
                .map(tuple -> {
                    Home home = tuple.get(0, Home.class);
                    User user = tuple.get(1, User.class);
                    return homeMapper.toSimpleHomeDto(home, user);
                })
                .collect(Collectors.toList());
//        return homeIds.stream()
//                .map(homeRepository::findById)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .flatMap(home -> {
//                    Optional<User> optionalUser = userRepository.findById(home.getUserIdx());
//                    return optionalUser
//                            .map(user -> Stream.of(homeMapper.toSimpleHomeDto(home, user))) // User가 존재하면 매핑
//                            .orElseGet(Stream::empty); // User가 없으면 빈 Stream 반환
//                })
//                .collect(Collectors.toList());
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
