package com.service.home;

import com.core.home.model.Home;
import com.core.home.reposiotry.HomeRepository;
import com.core.user.model.User;
import com.service.home.dto.CityDto;
import com.service.home.dto.HomeCreateDto;
import com.service.home.dto.SimpleHomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeRepository homeRepository;

    public void save(User user, HomeCreateDto homeCreateDto) {
        // 코드 구현
        Home home = homeCreateDto.toEntity(user);

        homeRepository.save(home);
    }

    public List<SimpleHomeDto> findByCity(CityDto cityDto) {
        List<Home> byCity = homeRepository.findByCity(cityDto.getCityName());
        return toSimpleDtos(byCity);
    }

//    public HomeDto findById(Long id){
//        Optional<Home> home = homeRepository.findById(id);
//        return home.get().toDto();
//    }

    public List<SimpleHomeDto> findAllByPage(int pageNumber, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        List<Home> all = homeRepository.findAll(pageable).getContent();

        return toSimpleDtos(all);
    }

    private List<SimpleHomeDto> toSimpleDtos(List<Home> homes) {
        return homes.stream()
                .map(home -> toSimpleDto(home))
                .collect(Collectors.toList());
    }

    private SimpleHomeDto toSimpleDto(Home home){
        return SimpleHomeDto.builder()
                .build();
    }
}
