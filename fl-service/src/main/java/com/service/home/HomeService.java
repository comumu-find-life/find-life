package com.service.home;

import com.core.mapper.HomeMapper;
import com.core.domain.home.dto.HomeGeneratorRequest;
import com.core.domain.home.dto.HomeUpdateRequest;
import com.infra.file.FileService;
import com.core.domain.user.dto.UserInformationResponse;
import com.core.domain.home.model.Home;
import com.core.domain.home.model.HomeAddress;
import com.core.domain.home.model.HomeImage;
import com.core.domain.home.model.HomeStatus;
import com.core.domain.home.repository.HomeImageRepository;
import com.core.domain.home.repository.HomeRepository;
import com.service.home.utils.LatLng;
import com.service.user.UserService;
import com.infra.utils.OptionalUtil;
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

import static com.core.exception.ExceptionMessages.NOT_EXIST_HOME_ID;


@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {

    private final FileService fileService;
    private final HomeRepository homeRepository;
    private final UserService userService;
    private final HomeMapper homeMapper;
    private final HomeImageRepository homeImageRepository;

    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public Long save(final HomeGeneratorRequest homeCreateDto, final List<MultipartFile> files, final LatLng latLng) {
        Home home = homeMapper.toEntity(homeCreateDto, getLoggedInUserId());
        if (!files.isEmpty() && !files.get(0).getOriginalFilename().isEmpty()) {
            home.setImages(uploadHomeImages(home, files));
        }
        home.setLatLng(latLng.getLat(), latLng.getLng());
        return homeRepository.save(home).getId();
    }


    @Transactional
    public Long update(final HomeUpdateRequest homeUpdateDto) {
        Home home = homeRepository.findById(homeUpdateDto.getHomeId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXIST_HOME_ID));
        homeMapper.updateHomeFromDto(homeUpdateDto, home.getHomeInfo());
        HomeAddress homeAddress = home.getHomeAddress();
        homeMapper.updateAddressFromDto(homeUpdateDto.getHomeAddress(), homeAddress);
        homeRepository.save(home);
        return home.getId();
    }


    @Transactional
    public void updateHomeImages(final Long homeId, final List<MultipartFile> files) {
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(homeId), NOT_EXIST_HOME_ID);
        if (!files.isEmpty() && !files.get(0).getOriginalFilename().isEmpty()) {
            home.addImages(uploadHomeImages(home, files));
        }
    }

    @Transactional
    public void deleteHomeImage(final List<String> imageUrls) {
        imageUrls.stream()
                .map(imageUrl -> homeImageRepository.findByImageUrl(imageUrl))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(homeImage -> {
                    homeImageRepository.delete(homeImage);
                    fileService.deleteFile(homeImage.getImageUrl());
                });
    }

    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public void delete(final Long homeId) {
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(homeId), NOT_EXIST_HOME_ID);
        homeRepository.delete(home);
    }


    @Transactional
    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public void changeStatus(final Long homeId, final String status) {
        HomeStatus homeStatus = HomeStatus.fromString(status);
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(homeId), NOT_EXIST_HOME_ID);
        home.setStatus(homeStatus);
    }

    private List<HomeImage> uploadHomeImages(final Home home, final List<MultipartFile> files) {
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
