package com.study.vue_backend.controller;

import com.study.vue_backend.entity.Art;
import com.study.vue_backend.service.ArtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArtController {

    private final ArtService artService;

    // 생성자 주입
    public ArtController(ArtService artService) {
        this.artService = artService;
    }

    @GetMapping("/api/art/items")
    public ResponseEntity<List<Art>> getItems(){
        return ResponseEntity.ok(artService.getArts());
    }

}
