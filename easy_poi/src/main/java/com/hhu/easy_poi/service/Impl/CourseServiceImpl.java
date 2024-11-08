package com.hhu.easy_poi.service.Impl;

import com.hhu.easy_poi.entity.Course;
import com.hhu.easy_poi.mapper.CourseMapper;
import com.hhu.easy_poi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findAll() {
        return courseMapper.findAll();
    }

    @Override
    public void save(List<Course> courses) {
        courses.forEach(course -> {
            course.setCid(null);// 自动生成ID,不需要使用excel中的编号
            courseMapper.save(course);
        });
    }

}
