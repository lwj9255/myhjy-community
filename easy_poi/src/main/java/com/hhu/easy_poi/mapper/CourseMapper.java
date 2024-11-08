package com.hhu.easy_poi.mapper;

import com.hhu.easy_poi.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CourseMapper {

    // 查询全部
    List<Course> findAll();

    // 插入记录
    void save(Course course);
}
