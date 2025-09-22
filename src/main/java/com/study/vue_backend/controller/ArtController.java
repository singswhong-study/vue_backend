package com.study.vue_backend.controller;

import com.study.vue_backend.entity.Art;
import com.study.vue_backend.serviceImpl.ArtServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArtController {

    private final ArtServiceImpl artServiceImpl;

    // 생성자 주입
    public ArtController(ArtServiceImpl artServiceImpl) {
        this.artServiceImpl = artServiceImpl;
    }

    @GetMapping("/api/art/items")
    public ResponseEntity<List<Art>> getItems(){
        return ResponseEntity.ok(artServiceImpl.getArts());
    }

}
