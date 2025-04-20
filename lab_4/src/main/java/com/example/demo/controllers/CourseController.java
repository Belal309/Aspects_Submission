package com.example.demo.controllers;



import com.example.demo.annotations.RateLimit;
import com.example.demo.entities.Course;
import com.example.demo.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RateLimit(limit = 2, duration = 20, timeUnit = TimeUnit.SECONDS, keyPrefix = "getCourses")
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/details/{courseId}")
    public ResponseEntity<Course> getCourseDetails(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }
    @GetMapping("/lock/{courseId}")
    public ResponseEntity<String> processAccount(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.registerStudentToCourse(courseId));
    }
}
