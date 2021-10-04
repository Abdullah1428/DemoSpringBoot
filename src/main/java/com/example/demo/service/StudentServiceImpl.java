package com.example.demo.service;


import com.example.demo.dao.StudentRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addNewStudent(Student student) {
        Optional<Student> checkStudent = studentRepository.findStudentByEmail(student.getEmail());

        if(checkStudent.isPresent()) {
            throw new ResourceNotFoundException("email already taken!");
        }

        studentRepository.save(student);

        return student;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException(
                "student with id " + studentId + " does not exists"
        ));

        return student;
    }

    @Override
    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if(!exists) {
            throw new ResourceNotFoundException("student with id " + studentId + " does not exists");
        }

        studentRepository.deleteById(studentId);

    }

    @Override
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException(
                "student with id " + studentId + " does not exists"
        ));

        // System.out.println(name);
        // System.out.println(email);
        // System.out.println("This is me " + student);

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(),name)) {
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(),email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);

            if(studentOptional.isPresent()) {
                throw new ResourceNotFoundException("email already taken");
            }

            student.setEmail(email);
        }

        studentRepository.save(student);
    }
}
