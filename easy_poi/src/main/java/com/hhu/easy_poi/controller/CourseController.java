package com.hhu.easy_poi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hhu.easy_poi.entity.Course;
import com.hhu.easy_poi.service.CourseService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/findAll")
    public String findAll(Model model){
        List<Course> courses = courseService.findAll();
        System.out.println(courses);
        model.addAttribute("courses",courses);
        return "index";
    }

    @RequestMapping("/importExcel")
    public String importExcel(MultipartFile excelFile) throws Exception {
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        params.setTitleRows(1);

        List<Course> list = ExcelImportUtil.importExcel(excelFile.getInputStream(), Course.class, params);
        list.forEach(System.out::println);
        courseService.save(list);
        return "redirect:/course/findAll";
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws Exception {
        List<Course> courses = courseService.findAll();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("课程列表", "课程信息"), Course.class, courses);
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode("课程信息列表.xls","UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        outputStream.close();
        workbook.close();

    }
}
