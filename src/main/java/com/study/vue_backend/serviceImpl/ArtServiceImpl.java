package com.study.vue_backend.serviceImpl;

import com.study.vue_backend.entity.Art;
import com.study.vue_backend.repository.ArtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtServiceImpl {

    private final ArtRepository artRepository;

    // 생성자 주입
    public ArtServiceImpl(ArtRepository artRepository) {
        this.artRepository = artRepository;
    }

    public List<Art> getArts(){
        return artRepository.findAll();
    }

}
