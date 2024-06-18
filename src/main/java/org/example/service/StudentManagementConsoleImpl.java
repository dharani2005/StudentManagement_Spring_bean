package org.example.service;

import org.example.data_access.StudentDao;
import org.example.models.Student;
import org.example.util.UserInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentManagementConsoleImpl implements StudentManagement {
    UserInputService scannerService;
    StudentDao studentDao;

    @Autowired

    public StudentManagementConsoleImpl(UserInputService scannerService, StudentDao studentDao) {
        this.scannerService = scannerService;
        this.studentDao = studentDao;
    }

    @Override
    public Student create() {
        int id = getUserInputId();
        String name = getUserInputName();
        return new Student(id, name);
    }

    @Override
    public Student save(Student student) {
        if (student == null) throw new IllegalArgumentException("student can not be null");
        Optional<Student> optionalStudent = Optional.ofNullable(studentDao.find(student.getId()));
        if (optionalStudent.isPresent()) throw new IllegalArgumentException("student already exists");
        studentDao.save(student);
        return student;
    }

    @Override
    public Student find(int id) {
        Optional<Student> optionalStudent = Optional.ofNullable(studentDao.find(id));
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        }
        throw new IllegalArgumentException("student not found");
    }

    @Override
    public Student remove(int id) {
        Optional<Student> optionalStudent = Optional.ofNullable(studentDao.find(id));
        if (optionalStudent.isPresent()) {
            studentDao.delete(id);
            return optionalStudent.get();
        }
        throw new IllegalArgumentException("student not found");
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public Student edit(Student student) {
        Optional<Student> optionalStudent = Optional.ofNullable(studentDao.find(student.getId()));
        if (!optionalStudent.isPresent()) throw new IllegalArgumentException("student not found");
        System.out.println("Edit student name : " + student.getId() + " : ");
        studentDao.delete(student.getId());
        studentDao.save(student);
        return student;
    }

    private String getUserInputName() {
        System.out.println("Enter student name: ");
        return scannerService.getString();
    }

    private int getUserInputId() {
        System.out.println("Enter student id: ");
        return scannerService.getInt();
    }
}
