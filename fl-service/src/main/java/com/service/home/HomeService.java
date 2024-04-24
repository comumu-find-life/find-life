package com.service.home;

import com.core.home.model.Home;
import com.core.home.reposiotry.HomeRepository;
import com.core.user.model.User;
import com.service.home.dto.HomeDto;
import com.service.home.dto.SimpleHomeDto;
import com.service.home.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeRepository homeRepository;
    private final HomeMapper homeMapper;

    public Long save(HomeDto homeCreateDto) {
        // 코드 구현
        Home home = homeMapper.toHomeEntity(homeCreateDto);
        // 검증 기능 구현
        return homeRepository.save(home).getId();
    }

    public void update(HomeDto updateHome) {
        Optional<Home> home = homeRepository.findById(updateHome.getId());
        Home updateEntity = homeMapper.toEntity(updateHome);
        home.get().update(updateEntity);
    }

    public HomeDto findById(Long id) {
        Optional<Home> entity = homeRepository.findById(id);
        return homeMapper.toDto(entity.get());
    }

    public List<SimpleHomeDto> findFavoriteHomes(List<Long> homeIds) {
        return homeIds.stream()
                .map(homeId -> {
                    Optional<Home> byId = homeRepository.findById(homeId);
                    return homeMapper.toSimpleHomeDto(byId.get());
                }).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Optional<Home> entity = homeRepository.findById(id);
        homeRepository.delete(entity.get());
    }

    public List<SimpleHomeDto> findByCity(String cityName, int pageNumber, int pageSize) {
        List<Home> homes = homeRepository.findByCity(cityName, toPageRequest(pageNumber, pageSize));
        return toSimpleDtos(homes);
    }

    // 페이징으로 조회
    public List<SimpleHomeDto> findAllByPage(int pageNumber, int pageSize) {
        List<Home> homes = homeRepository.findAll(toPageRequest(pageNumber, pageSize)).getContent();
        return toSimpleDtos(homes);
    }

    private PageRequest toPageRequest(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }

    private List<SimpleHomeDto> toSimpleDtos(List<Home> homes) {
        List<SimpleHomeDto> simpleHomeDtos = new ArrayList<>();
        homes.stream()
                .forEach(home -> {
                    SimpleHomeDto simpleHomeDto = homeMapper.toSimpleHomeDto(home);
                    simpleHomeDtos.add(simpleHomeDto);
                });
        return simpleHomeDtos;
    }


}
