/** This class shows how we can sort a list of Student-s if we implemented Comparable in class Student. */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentExample {
    public static void main(String[] args) {
        Student st1 = new Student("Zheng", 3.5);
        Student st2 = new Student("Li", 3.7 );

        List<Student> studentList = new ArrayList<>();
        studentList.add(st1);
        studentList.add(st2);
        Collections.sort(studentList); // calls compareTo method when it sorts students
        System.out.println("student List = " + studentList);

    }
}
