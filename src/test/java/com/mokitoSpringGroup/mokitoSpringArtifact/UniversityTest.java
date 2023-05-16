package com.mokitoSpringGroup.mokitoSpringArtifact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class) // что бы анатация Mokito (@Mock) работала без дополнительных настроек
public class UniversityTest {
    private final Student student = new Student("Александр", true);
    @Mock // заглушка для класса studentValueGenerator
    private StudentValueGenerator studentValueGenerator;

    private University university;

    @BeforeEach // указывает что метод будет вызываться перед каждым запуском каждого теста
    public void setUp() {
        university = new University(studentValueGenerator);// передадим Mock в конструктор Университета
    }

    @Test  // переопределяли поведение метода без параметров
    public void getAllStudents() {
        assertNotNull(studentValueGenerator); // убедиться что генератор не null

        Mockito.when(studentValueGenerator.generateAge()).thenReturn(50); // это конструкция для написания поведения (ЯВЛЯЕТСЯ ПРЕДПОЧТИТЕЛЬНЕЕ) - при задании поведение метода generateAge() сначало выполняется вызов ещё не переопределенной его версии и только потом в недрах мокито происходит переопределение
        //Mockito.doReturn(50).when(studentValueGenerator).generateAge(); // во второй же такого вызова не происходит методу стабер when передается не посредственно Мок, а уже у возвращенного этим методом объекта того же типа но другой природы совершаеться вызов переопределяемого метода , эта разница всё и переопределяет
        university.addStudent(student);
        List<Student> expected = university.getAllStudents();
        assertEquals(expected.get(0).getAge(), 50);
    }
}
