package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        List<Employee> topSalaries = statisticsService.getTopSalaries();
        Map<Employee, Long> topAttendance = statisticsService.getTopAttendanceDays();
        Map<String, Long> employeeCountByDept = statisticsService.getEmployeeCountByDepartment();

        model.addAttribute("topSalaries", topSalaries);
        model.addAttribute("topAttendance", topAttendance);
        model.addAttribute("employeeCountByDept", employeeCountByDept);
        return "statistics/statistics";
    }
}
