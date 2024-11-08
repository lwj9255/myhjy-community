package com.hhu.easy_poi.service;

import com.hhu.easy_poi.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();

    void save(List<Course> courses);
}
