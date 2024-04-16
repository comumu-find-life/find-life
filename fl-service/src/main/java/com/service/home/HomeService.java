package com.service.home;

import com.core.home.model.Home;
import com.core.home.reposiotry.HomeRepository;
import com.core.user.model.User;
import com.service.home.dto.HomeDto;
import com.service.home.dto.SimpleHomeDto;
import com.service.home.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeRepository homeRepository;
    private HomeMapper homeMapper;

    public Long save(User user, HomeDto homeCreateDto) {
        // 코드 구현
        Home home = homeMapper.toEntity(homeCreateDto);
        //이제 여기서 게시글 허용,거부 하는 로직 만들어 추가
        return homeRepository.save(home).getId();
    }

//    public HomeDto findById(Long homeId){
//        Home home = homeRepository.findById(homeId).get();
//
//    }

    public void update(User user, HomeDto updateHome) {
        Optional<Home> home = homeRepository.findById(updateHome.getId());
        Home updateEntity = homeMapper.toEntity(updateHome);
        home.get().update(updateEntity);
    }

    public List<SimpleHomeDto> findByCity(String cityName) {
        List<Home> byCity = homeRepository.findByCity(cityName);
        return toSimpleDtos(byCity);
    }


    // 페이징으로 조회
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
