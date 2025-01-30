package com.core.domain.home.repository;

import com.core.domain.home.model.HomeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeImageRepository extends JpaRepository<HomeImage, Long> {

    Optional<HomeImage> findByImageUrl(String imageUrl);

}
