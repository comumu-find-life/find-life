package com.service.home;

import com.common.home.mapper.HomeMapper;
import com.common.home.request.HomeGeneratorRequest;
import com.common.home.request.HomeUpdateRequest;
import com.common.image.FileService;
import com.common.user.response.UserInformationResponse;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeAddress;
import com.core.api_core.home.model.HomeImage;
import com.core.api_core.home.model.HomeStatus;
import com.core.api_core.home.repository.HomeImageRepository;
import com.core.api_core.home.repository.HomeRepository;
import com.service.home.utils.LatLng;
import com.service.user.UserService;
import com.common.utils.OptionalUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.service.home.HomeMessages.NOT_EXIST_HOME;
import static com.service.home.HomeMessages.NOT_EXIST_HOME_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {

    private final FileService fileService;
    private final HomeRepository homeRepository;
    private final UserService userService;
    private final HomeMapper homeMapper;
    private final HomeImageRepository homeImageRepository;

    /**
     * 집 게시글 등록
     */
    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public Long save(HomeGeneratorRequest homeCreateDto, List<MultipartFile> files, LatLng latLng) {
        Home home = homeMapper.toEntity(homeCreateDto, getLoggedInUserId());
        if (!files.isEmpty() && !files.get(0).getOriginalFilename().isEmpty()) {
            home.setImages(uploadHomeImages(home, files));
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
        System.out.println("UPDATEEEEE");
        System.out.println(homeUpdateDto.getType());
        System.out.println(homeUpdateDto.isCanParking());
        System.out.println(homeUpdateDto.getResidentCount());
        homeMapper.updateHomeFromDto(homeUpdateDto, home.getHomeInfo());
        HomeAddress homeAddress = home.getHomeAddress();
        homeMapper.updateAddressFromDto(homeUpdateDto.getHomeAddress(), homeAddress);
        homeRepository.save(home);
        return home.getId();
    }


    /**
     * 새로운 집 이미지 추가
     */
    @Transactional
    public void updateHomeImages(Long homeId, List<MultipartFile> files) {
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(homeId), NOT_EXIST_HOME);
        if (!files.isEmpty() && !files.get(0).getOriginalFilename().isEmpty()) {
            home.addImages(uploadHomeImages(home, files));
        }
    }

    /**
     * 집 이미지 삭제
     */
    @Transactional
    public void deleteHomeImage(List<String> imageUrls) {
        imageUrls.stream()
                .map(imageUrl -> homeImageRepository.findByImageUrl(imageUrl))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(homeImage -> {
                    homeImageRepository.delete(homeImage); // DB에서 삭제
                    fileService.deleteFile(homeImage.getImageUrl()); // S3에서 삭제
                });
    }


    /**
     * 집 게시글 삭제
     * 캐시 무효화: 집 게시글이 삭제되면 전체 집 목록 캐시를 삭제
     */
    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public void delete(Long homeId) {
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(homeId), NOT_EXIST_HOME);
        homeRepository.delete(home);
    }


    /**
     * 집 게시글 상태 변경 (판매 완료, 재판매)
     * 캐시 무효화: 상태 변경 시 전체 집 목록 캐시를 삭제
     */
    @Transactional
    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public void changeStatus(Long homeId, String status) {
        HomeStatus homeStatus = HomeStatus.fromString(status);
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(homeId), NOT_EXIST_HOME_ID);
        home.setStatus(homeStatus);
    }

    /**
     * 집 게시물 url 생성 && 서버 업로드
     */
    private List<HomeImage> uploadHomeImages(Home home, List<MultipartFile> files) {
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

    private Long getLoggedInUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformationResponse user = userService.findByEmail(email);
        return user.getId();
    }

}
