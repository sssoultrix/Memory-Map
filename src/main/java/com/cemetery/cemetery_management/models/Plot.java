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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlotStatus status = PlotStatus.AVAILABLE;

    private Double width;
    private Double length;
    private Double latitude;
    private Double longitude;

    public enum PlotStatus {
        AVAILABLE,    // Доступен
        OCCUPIED,     // Занят
        RESERVED      // Зарезервирован
    }
} 