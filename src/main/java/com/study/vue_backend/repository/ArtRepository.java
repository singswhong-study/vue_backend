package com.study.vue_backend.repository;

import com.study.vue_backend.entity.Art;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ArtRepository extends Repository<Art, Long> {
    List<Art> findAll();
}
