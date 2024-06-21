package com.service.home;

import com.core.home.model.Home;
import com.core.home.model.HomeImage;
import com.core.home.model.HomeStatus;
import com.core.home.reposiotry.HomeRepository;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.service.file.FileService;
import com.service.home.dto.response.HomeOverviewResponse;
import com.service.home.dto.request.HomeGeneratorRequest;
import com.service.home.dto.geocoding.LatLng;
import com.service.home.dto.request.HomeUpdateRequest;
import com.service.home.dto.response.HomeInformationResponse;
import com.service.home.mapper.HomeMapper;
import com.service.utils.OptionalUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.service.home.utils.HomeUtil.*;

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
        home.setImages(generateHomeImages(home, files));
        home.setLatLng(latLng.getLat(), latLng.getLng());
        return homeRepository.save(home).getId();
    }


    /**
     * 집 게시글 수정
     */
    @Transactional
    public Long update(HomeUpdateRequest homeUpdateDto) {
        Home home = homeRepository.findById(homeUpdateDto.getHomeId())
                .orElseThrow(() -> new EntityNotFoundException("Home not found"));
        homeMapper.updateHomeFromDto(homeUpdateDto, home);

        homeRepository.save(home);
        return home.getId();
    }


    /**
     * 집 게시글 단일 조회(집 정보 + 작성자 정보) 로직
     */
    public HomeInformationResponse findById(Long id) {
        Home entity = OptionalUtil.getOrElseThrow(homeRepository.findById(id), "Home not found with id");
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(entity.getUserIdx()), "User not found with id");
        return homeMapper.toHomeInformation(entity, user);
    }

    /**
     * 모든 집 게시글 조회 (맵 화면에서 사용)
     */
    public List<HomeOverviewResponse> findAllHomes() {
        List<HomeOverviewResponse> response = new ArrayList<>();
        List<Home> homes = homeRepository.findAll();
        homes.stream().forEach(home -> {
            User user = OptionalUtil.getOrElseThrow(userRepository.findById(home.getUserIdx()), "User not found with id");
            response.add(homeMapper.toSimpleHomeDto(home, user));
        });
        return response;
    }

    public List<HomeOverviewResponse> findByUserIdx(Long userIdx) {
        List<HomeOverviewResponse> response = new ArrayList<>();
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userIdx), "User not found with id");
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
                    User user = userRepository.findById(home.getUserIdx()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + home.getUserIdx()));
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
    public List<HomeOverviewResponse> findByCity(String cityName, int pageNumber, int pageSize) {
        List<Home> homes = homeRepository.findByCity(cityName);
        List<HomeOverviewResponse> listResponse = homes.stream()
                .map(home -> {
                    User user = userRepository.findById(home.getUserIdx()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + home.getUserIdx()));
                    return homeMapper.toSimpleHomeDto(home, user);
                })
                .collect(Collectors.toList());
        log.info(listResponse.get(0).getAddress());
        log.info(listResponse.get(0).getAddress());
        log.info(listResponse.get(0).getAddress());
        log.info(listResponse.get(0).getAddress());
        log.info(listResponse.get(0).getAddress());
        log.info(listResponse.get(0).getAddress());
        return listResponse;
        // toListOverview(homes, homeMapper);
    }

    // 페이징으로 조회
    public List<HomeOverviewResponse> findAllByPage(int pageNumber, int pageSize) {
        List<Home> homes = homeRepository.findAll(toPageRequest(pageNumber, pageSize)).getContent();
        List<HomeOverviewResponse> listResponse = homes.stream()
                .map(home -> {
                    User user = userRepository.findById(home.getUserIdx()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + home.getUserIdx()));
                    return homeMapper.toSimpleHomeDto(home, user);
                })
                .collect(Collectors.toList());
        return listResponse;
    }

    /**
     * 집 게시글 상태 변경 (판매완료, 재판매)
     */
    public void changeStatus(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new EntityNotFoundException("Home not found with id " + homeId));
        home.setStatus(HomeStatus.SOLD_OUT);
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
