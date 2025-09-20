package com.study.vue_backend.service;

import com.study.vue_backend.entity.Art;
import com.study.vue_backend.repository.ArtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtService {

    private final ArtRepository artRepository;

    // 생성자 주입
    public ArtService(ArtRepository artRepository) {
        this.artRepository = artRepository;
    }

    public List<Art> getArts(){
        return artRepository.findAll();
    }

}
