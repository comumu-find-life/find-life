package com.service.home;

import com.core.home.model.Home;
import com.core.home.model.HomeStatus;
import com.core.home.reposiotry.HomeRepository;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.service.home.dto.HomeOverviewResponse;
import com.service.home.dto.request.HomeGeneratorRequest;
import com.service.home.dto.LatLng;
import com.service.home.dto.response.HomeInformationResponse;
import com.service.home.mapper.HomeMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.service.home.utils.HomeUtil.*;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeRepository homeRepository;
    private final UserRepository userRepository;
    private final HomeMapper homeMapper;

    /**
     * 집 게시글 등록
     */
    public Long save(HomeGeneratorRequest homeCreateDto, LatLng latLng) {
        Home home = homeMapper.toHomeEntity(homeCreateDto);
        home.setLatLng(latLng.getLat(), latLng.getLng());
        return homeRepository.save(home).getId();
    }

    /**
     * 집 게시글 단일 조회(집 정보 + 작성자 정보) 로직
     */
    public HomeInformationResponse findById(Long id) {
        Home entity = homeRepository.findById(id).get();
        User user = userRepository.findById(entity.getUserId()).get();
        return homeMapper.toHomeInformation(entity, user);
    }

    /**
     * 모든 집 게시글 조회 (맵 화면에서 사용)
     */
    public List<HomeOverviewResponse> findAllHomes() {
        List<HomeOverviewResponse> response = new ArrayList<>();
        List<Home> homes = homeRepository.findAll();
        homes.stream().forEach(home -> {
            response.add(homeMapper.toSimpleHomeDto(home));
        });
        return response;
    }


    /**
     * 찜 목록 게시글 조회
     */
    public List<HomeOverviewResponse> findFavoriteHomes(List<Long> homeIds) {
        return homeIds.stream()
                .map(homeId -> {
                    Optional<Home> byId = homeRepository.findById(homeId);
                    return homeMapper.toSimpleHomeDto(byId.get());
                }).collect(Collectors.toList());
    }

    /**
     * 집 게시글 삭제
     */
    public void delete(Long id) {
        Optional<Home> entity = homeRepository.findById(id);
        homeRepository.delete(entity.get());
    }

    /**
     * city 이름으로 모든 집 조회
     */
    public List<HomeOverviewResponse> findByCity(String cityName, int pageNumber, int pageSize) {
        List<Home> homes = homeRepository.findByCity(cityName);
        System.out.println("dd");
        return toListOverview(homes, homeMapper);
    }

    // 페이징으로 조회
    public List<HomeOverviewResponse> findAllByPage(int pageNumber, int pageSize) {
        List<Home> homes = homeRepository.findAll(toPageRequest(pageNumber, pageSize)).getContent();
        return toListOverview(homes, homeMapper);
    }

    /**
     * 집 게시글 상태 변경 (판매완료, 재판매)
     */
    public void changeStatus(Long homeId) {
        System.out.println("ASDASDASD");
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new EntityNotFoundException("Home not found with id " + homeId));
        System.out.println("ASDASDASD22");
        home.setStatus(HomeStatus.SOLD_OUT);
        homeRepository.save(home); // 변경 사항을 저장합니다.
    }

}
