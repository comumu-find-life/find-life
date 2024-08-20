package com.service.home;

import com.common.home.mapper.HomeMapper;
import com.common.home.request.HomeGeneratorRequest;
import com.common.home.request.HomeUpdateRequest;
import com.common.home.response.HomeInformationResponse;
import com.common.home.response.HomeOverviewResponse;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeAddress;
import com.core.api_core.home.model.HomeImage;
import com.core.api_core.home.model.HomeStatus;
import com.core.api_core.home.reposiotry.HomeRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.service.file.FileService;
import com.service.home.utils.LatLng;
import com.service.utils.OptionalUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.service.home.HomeMessages.NOT_EXIST_HOME;
import static com.service.home.HomeMessages.NOT_EXIST_HOME_ID;
import static com.service.home.utils.HomeUtil.*;
import static com.service.user.UserMessages.NOT_EXIT_USER_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {

    private final FileService fileService;
    private final HomeRepository homeRepository;
    private final UserRepository userRepository;
    private final HomeMapper homeMapper;

    /**
     * 집 게시글 등록
     */


    public Long save(HomeGeneratorRequest homeCreateDto, List<MultipartFile> files, LatLng latLng) {
        Home home = homeMapper.toEntity(homeCreateDto);

        //이미지, 위치 정보 저장
        log.info("homeCreateDto.getGender()");
        log.info(homeCreateDto.getGender() + "");
        if (!files.isEmpty() && !files.get(0).getOriginalFilename().isEmpty()) {
            home.setImages(generateHomeImages(home, files));
        }

        home.setLatLng(latLng.getLat(), latLng.getLng());
        return homeRepository.save(home).getId();
    }

    /**
     * 집 게시글 수정
     */
    @Transactional
    public Long update(HomeUpdateRequest homeUpdateDto) {
        Home home = homeRepository.findById(homeUpdateDto.getHomeId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXIST_HOME));

        // Home 엔티티를 업데이트
        homeMapper.updateHomeFromDto(homeUpdateDto, home);
        // 기존 HomeAddress를 가져와서 업데이트
        HomeAddress homeAddress = home.getHomeAddress();
        homeMapper.updateAddressFromDto(homeUpdateDto.getHomeAddress(), homeAddress);

        // 변경 사항 저장
        homeRepository.save(home);

        return home.getId();
    }

    /**
     * 집 게시글 단일 조회(집 정보 + 작성자 정보) 로직
     */
    public HomeInformationResponse findById(Long id) {
        Home entity = OptionalUtil.getOrElseThrow(homeRepository.findById(id), NOT_EXIST_HOME_ID);
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(entity.getUserIdx()), NOT_EXIT_USER_ID);
        return homeMapper.toHomeInformation(entity, user);
    }

    /**
     * 모든 집 게시글 조회
     */
    public List<HomeOverviewResponse> findAllHomes() {
        List<HomeOverviewResponse> response = new ArrayList<>();
        List<Home> homes = homeRepository.findAllSellHome();
        homes.stream().forEach(home -> {
            User user = OptionalUtil.getOrElseThrow(userRepository.findById(home.getUserIdx()), NOT_EXIT_USER_ID);
            response.add(homeMapper.toSimpleHomeDto(home, user));
        });
        return response;
    }

    public List<HomeOverviewResponse> findByUserIds(Long user1Id, Long user2Id) {
        List<Home> homes = homeRepository.findByUserIds(user1Id, user2Id);
        List<HomeOverviewResponse> response = new ArrayList<>();
        homes.stream().forEach(home -> {
            User user = OptionalUtil.getOrElseThrow(userRepository.findById(home.getUserIdx()), NOT_EXIT_USER_ID);
            response.add(homeMapper.toSimpleHomeDto(home, user));
        });
        return response;
    }


    public List<HomeOverviewResponse> findByUserId(Long userIdx) {
        List<HomeOverviewResponse> response = new ArrayList<>();
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userIdx), NOT_EXIT_USER_ID);
        List<Home> homes = homeRepository.findByUserIdx(userIdx);
        homes.stream().forEach(home -> {
            response.add(homeMapper.toSimpleHomeDto(home, user));
        });
        return response;
    }

    /**
     * 찜 목록 게시글 조회
     */
    public List<HomeOverviewResponse> findFavoriteHomes(List<Long> homeIds) {
        return homeIds.stream()
                .map(homeRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(home -> {
                    User user = userRepository.findById(home.getUserIdx()).orElseThrow(() -> new EntityNotFoundException(NOT_EXIT_USER_ID + home.getUserIdx()));
                    return homeMapper.toSimpleHomeDto(home, user);
                })
                .collect(Collectors.toList());
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
    public List<HomeOverviewResponse> findByCity(String cityName) {
        List<Home> homes = homeRepository.findByCity(cityName);
        List<HomeOverviewResponse> listResponse = homes.stream()
                .map(home -> {
                    User user = userRepository.findById(home.getUserIdx()).orElseThrow(() -> new EntityNotFoundException(NOT_EXIT_USER_ID + home.getUserIdx()));
                    return homeMapper.toSimpleHomeDto(home, user);
                })
                .collect(Collectors.toList());
        return listResponse;
    }

    public List<HomeOverviewResponse> findAllByPage(int pageNumber, int pageSize) {
//        List<Home> homes = homeRepository.findAll(toPageRequest(pageNumber, pageSize)).getContent();
        List<Home> homes = homeRepository.findAll(toPageRequest(pageNumber, pageSize, Sort.by("createDate").descending())).getContent();
        List<HomeOverviewResponse> listResponse = homes.stream()
                .map(home -> {
                    User user = userRepository.findById(home.getUserIdx()).orElseThrow(() -> new EntityNotFoundException(NOT_EXIT_USER_ID + home.getUserIdx()));
                    return homeMapper.toSimpleHomeDto(home, user);
                })
                .collect(Collectors.toList());
        return listResponse;
    }

    /**
     * 집 게시글 상태 변경 (판매 완료, 재판매)
     */
    @Transactional
    public void changeStatus(Long homeId, String status) {
        HomeStatus homeStatus = HomeStatus.fromString(status);
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(homeId), NOT_EXIST_HOME_ID);
        home.setStatus(homeStatus);
        homeRepository.save(home);
    }

    private List<HomeImage> generateHomeImages(Home home, List<MultipartFile> files) {
        List<HomeImage> response = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = fileService.toUrls(file);
            response.add(HomeImage.builder()
                    .home(home)
                    .imageUrl(url)
                    .build());
            fileService.fileUpload(file, url);
        }
        return response;
    }


}
