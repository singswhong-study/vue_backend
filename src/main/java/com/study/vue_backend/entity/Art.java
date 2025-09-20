package com.study.vue_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(schema = "gallery", name = "art")
public class Art {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long art_id;

    @Column(length = 50)
    private String name;

    @Column(length = 100)
    private String img_path;

    private Integer price;

    private Integer discount_per;
}
