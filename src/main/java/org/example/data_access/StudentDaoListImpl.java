package org.example.data_access;

import org.example.models.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StudentDaoListImpl implements StudentDao {
    List<Student> students;

    public StudentDaoListImpl(List<Student> students) {
        this.students = new ArrayList<>();
    }

    @Override
    public Student save(Student student) {
        students.add(student);
        return student;
    }

    @Override
    public Student find(int id) {
        Optional<Student> studentOptional = students.stream()
                .filter(student -> student.getId() == id)
                .findFirst();
        return studentOptional.orElse(null);
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public void delete(int id) {
        Optional<Student> studentOptional = students.stream()
                .filter(student -> student.getId() == id)
                .findFirst();

        studentOptional.ifPresent(students::remove);
    }
}
