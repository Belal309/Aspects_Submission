package com.example.demo.services;

import com.example.demo.annotations.DistributedLock; // Make sure this matches your actual annotation package
import java.util.concurrent.TimeUnit;

import com.example.demo.entities.Course;
import com.example.demo.repo.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);
    private static final String COURSES_CACHE_KEY = "courses:all";
    private static final Duration CACHE_TTL = Duration.ofMinutes(5);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private Redis redisClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Course> getAllCourses() {
        try {
            String cachedJson = redisClient.get(COURSES_CACHE_KEY);
            if (cachedJson != null) {
                logger.info("Cache hit – returning courses from Redis");
                return objectMapper.readValue(cachedJson, new TypeReference<List<Course>>() {});
            }
        } catch (Exception e) {
            logger.error("Error reading courses from Redis", e);
        }

        logger.info("Cache miss – querying database for courses");
        List<Course> courses = courseRepository.findAll();

        try {
            String json = objectMapper.writeValueAsString(courses);
            redisClient.set(COURSES_CACHE_KEY, json, CACHE_TTL);
            logger.info("Courses cached with TTL: {}", CACHE_TTL);
        } catch (Exception e) {
            logger.error("Error writing courses to Redis", e);
        }

        return courses;
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
    }
    @DistributedLock(
            keyPrefix = "course:registration:lock",
            keyIdentifierExpression = "#courseId",
            leaseTime = 10,
            timeUnit = TimeUnit.SECONDS
    )
    public String registerStudentToCourse(Long courseId) {
        try {
            logger.info("Registering student to course ID: {} by thread {}", courseId, Thread.currentThread().getName());
            Thread.sleep(10000); // Simulate registration processing delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Registration interrupted for course ID: " + courseId;
        }

        return "Student registered successfully to course ID: " + courseId;
    }
}

