package com.mokitoSpringGroup.mokitoSpringArtifact;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
public class University {
    private Map<Integer, Student> allStudent = new HashMap<>();
    private int countId = 1;

    public void addStudent(Student student){
        if (allStudent == null){
            allStudent=new HashMap<>();
        }

        student.setId(countId);
        allStudents.put(countId, student);
        countId++;
    }
    public void addStudentInRange(Student student, int minYear, int maxYear){
        if (allStudents == null) {
            allStudents = new HashMap<Integer, Student>();
        }
        student.setId(countId);
        allStudent.put(countId,student);
        countId++;
    }

    public List<Student> getAllStudents(){
        return new ArrayList<Student>(allStudent.values());
    }

}
