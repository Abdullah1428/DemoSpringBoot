package com.example.demo.service;

import com.example.demo.model.Student;

import java.util.List;

public interface StudentService {

    // adding new student
    Student addNewStudent(Student student);

    // getting all students
    List<Student> getStudents();

    // getting student by id
    Student getStudent(Long studentID);

    // deleting student by id
    void deleteStudent(Long studentId);

    // updating student
    void updateStudent(Long studentId,String name, String email);

}
