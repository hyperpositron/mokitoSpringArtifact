package com.mokitoSpringGroup.mokitoSpringArtifact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.InOrderImpl;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.internal.verification.VerificationModeFactory.times;

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

        Mockito.when(studentValueGenerator.generateAge()).thenReturn(50); /*это конструкция для написания поведения
        (ЯВЛЯЕТСЯ ПРЕДПОЧТИТЕЛЬНЕЕ) - при задании поведение метода generateAge() сначало выполняется вызов ещё не
        переопределенной его версии и только потом в недрах мокито происходит переопределение*/
        /* Mockito.doReturn(50).when(studentValueGenerator).generateAge(); // во второй же такого вызова не происходит
        методу стабер when передается не посредственно Мок, а уже у возвращенного этим методом объекта того же типа
        но другой природы совершаеться вызов переопределяемого метода , эта разница всё и переопределяет*/
        university.addStudent(student);
        List<Student> expected = university.getAllStudents();
        assertEquals(expected.get(0).getAge(), 50);
    }

    @Test
    public void getAllStudentsOver50Years() {
        /* если нужно задать реакцию налюбой вызов этого метода не зависимо от аргументов нужно воспользоваться методом
         MockitoAny*/
        Mockito.when(studentValueGenerator.generateAgeInRange(anyInt(),anyInt()/*(50, 100)*/)).thenReturn(55); // это
        /* конструкция для написания поведения (ЯВЛЯЕТСЯ ПРЕДПОЧТИТЕЛЬНЕЕ) - при задании поведение метода generateAge()
         сначало выполняется вызов ещё не переопределенной его версии и только потом в недрах мокито происходит
         переопределение*/
        university.addStudentInRange(student,50,50);// если же нам не требуется что бы Мок реализовал
        // только на определенное значение аргумента можно использовать непосредственно это значение (50, 100)
        List<Student> expected = university.getAllStudents();
        assertEquals(expected.get(0).getAge(), 55);

    }

    @Test  /*Можем подсчитывать кол-во вызовов методов, т.е. проверять вызвался метод вообще или например проверять
    что метод был вызван 2 раза иначе завершать тест с провалом.Для этого есть метод Mackito.verify()*/
    public void getAllStudentsWithCountGenerate() {
        assertNotNull(studentValueGenerator); // убедиться что генератор не null

        Mockito.when(studentValueGenerator.generateAge()).thenReturn(50);
        university.addStudent(student);
        List<Student> expected = university.getAllStudents();
        assertEquals(expected.get(0).getAge(), 50);
        /* и добавим её в конец теста */
        Mockito.verify(studentValueGenerator,Mockito.times(2)).generateAge();/* попросим
        Mockito проследить что бы метод был вызван 2 раза допишем Mockito.times(2)*/
    }

    @Test  /*Проверить вызовов методов в правельной последовательности */
    public void getAllStudentsInOrder() {
        Mockito.when(studentValueGenerator.generateAgeInRange(50,100)).thenReturn(55);

        university.addStudentInRange(student,50,100);

        InOrder inOrder = Mockito.inOrder(studentValueGenerator);

        List<Student> expected = university.getAllStudents();

        inOrder.verify(studentValueGenerator,times (2)).generateAge();
        inOrder.verify(studentValueGenerator).generateAgeInRange(anyInt(),anyInt());

        assertEquals(expected.get(0).getAge(), 55);
    }

}
