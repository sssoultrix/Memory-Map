package com.cemetery.cemetery_management.controllers;

import com.cemetery.cemetery_management.models.Plot;
import com.cemetery.cemetery_management.repositories.PlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/plots")
public class PlotController {

    @Autowired
    private PlotRepository plotRepository;

    @GetMapping
    public String listPlots(Model model) {
        model.addAttribute("plots", plotRepository.findAll());
        model.addAttribute("newPlot", new Plot());
        return "plots/list";
    }

    @GetMapping("/{id}")
    public String viewPlot(@PathVariable Long id, Model model) {
        Plot plot = plotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Участок не найден"));
        model.addAttribute("plot", plot);
        return "plots/view";
    }

    @GetMapping("/create")
    public String createPlotForm(Model model) {
        model.addAttribute("plot", new Plot());
        return "plots/create";
    }

    @PostMapping("/create")
    public String createPlot(@ModelAttribute Plot plot, RedirectAttributes redirectAttributes) {
        try {
            if (plotRepository.existsByPlotNumber(plot.getPlotNumber())) {
                redirectAttributes.addFlashAttribute("error",
                        "Участок с таким номером уже существует");
                return "redirect:/plots/create";
            }
            plotRepository.save(plot);
            redirectAttributes.addFlashAttribute("success",
                    "Участок успешно создан");
            return "redirect:/plots";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Ошибка при создании участка: " + e.getMessage());
            return "redirect:/plots/create";
        }
    }

    @GetMapping("/{id}/edit")
    public String editPlotForm(@PathVariable Long id, Model model) {
        Plot plot = plotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Участок не найден"));
        model.addAttribute("plot", plot);
        return "plots/edit";
    }

    @PostMapping("/{id}/edit")
    public String updatePlot(@PathVariable Long id, @ModelAttribute Plot plot,
            RedirectAttributes redirectAttributes) {
        try {
            Plot existingPlot = plotRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Участок не найден"));

            // Проверяем, не пытаемся ли мы изменить номер участка на уже существующий
            if (!existingPlot.getPlotNumber().equals(plot.getPlotNumber()) &&
                    plotRepository.existsByPlotNumber(plot.getPlotNumber())) {
                redirectAttributes.addFlashAttribute("error",
                        "Участок с таким номером уже существует");
                return "redirect:/plots/" + id + "/edit";
            }

            existingPlot.setPlotNumber(plot.getPlotNumber());
            existingPlot.setSection(plot.getSection());
            existingPlot.setStatus(plot.getStatus());
            existingPlot.setWidth(plot.getWidth());
            existingPlot.setLength(plot.getLength());
            existingPlot.setLatitude(plot.getLatitude());
            existingPlot.setLongitude(plot.getLongitude());

            plotRepository.save(existingPlot);
            redirectAttributes.addFlashAttribute("success",
                    "Участок успешно обновлен");
            return "redirect:/plots";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Ошибка при обновлении участка: " + e.getMessage());
            return "redirect:/plots/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deletePlot(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            plotRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success",
                    "Участок успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Ошибка при удалении участка: " + e.getMessage());
        }
        return "redirect:/plots";
    }
}