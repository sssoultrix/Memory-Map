package com.cemetery.cemetery_management.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "plots")
public class Plot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String plotNumber;

    @Column(nullable = false)
    private String section;

    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlotStatus status = PlotStatus.AVAILABLE;

    private Double area;
    
    // Координаты для Яндекс Карт
    private Double latitude;  // Широта
    private Double longitude; // Долгота
    
    @Column(length = 500)
    private String locationDescription;
    
    @Column(name = "yandex_map_link")
    private String yandexMapLink;

    public enum PlotStatus {
        AVAILABLE("Доступен"),
        OCCUPIED("Занят"),
        RESERVED("Зарезервирован");

        private final String displayName;

        PlotStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
} 