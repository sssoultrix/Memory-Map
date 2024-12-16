package com.cemetery.cemetery_management.repositories;

import com.cemetery.cemetery_management.models.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlotRepository extends JpaRepository<Plot, Long> {
    List<Plot> findBySection(String section);
    List<Plot> findByStatus(Plot.PlotStatus status);
    boolean existsByPlotNumber(String plotNumber);
} 