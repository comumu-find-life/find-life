package com.service.home;

import com.core.home.model.Home;
import com.core.home.reposiotry.HomeRepository;
import com.core.user.model.User;
import com.service.home.dto.HomeDto;
import com.service.home.dto.SimpleHomeDto;
import com.service.home.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final HomeRepository homeRepository;
    private final HomeMapper homeMapper;

    public Long save(HomeDto homeCreateDto) {
        // 코드 구현
        Home home = homeMapper.toHomeEntity(homeCreateDto);
        //이제 여기서 게시글 허용,거부 하는 로직 만들어 추가
        return homeRepository.save(home).getId();
    }

//    public List<SimpleHomeDto> findByPage(){
//        homeRepository.findAll()
//    }


    public void update(HomeDto updateHome) {
        Optional<Home> home = homeRepository.findById(updateHome.getId());
        Home updateEntity = homeMapper.toEntity(updateHome);
        home.get().update(updateEntity);
    }

    public HomeDto findById(Long id) {
        Optional<Home> entity = homeRepository.findById(id);
        return homeMapper.toDto(entity.get());
    }

    public void delete(Long id) {
        Optional<Home> entity = homeRepository.findById(id);
        homeRepository.delete(entity.get());
    }

//    public List<SimpleHomeDto> findByCity(String cityName) {
//        List<Home> byCity = homeRepository.findByCity(cityName);
//        return toSimpleDtos(byCity);
//    }
//
//
//    // 페이징으로 조회
//    public List<SimpleHomeDto> findAllByPage(int pageNumber, int pageSize) {
//        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
//        List<Home> all = homeRepository.findAll(pageable).getContent();
//        return toSimpleDtos(all);
//    }



}
