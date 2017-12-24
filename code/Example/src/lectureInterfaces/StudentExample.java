package lectureInterfaces; /** This class shows how we can sort a list of Student-s if we implemented Comparable in class Student. */
import java.util.*;

public class StudentExample {
    public static void main(String[] args) {
        demoComparable();
        demoComparator();
        demoAnonymousComparator();
    }

    /** Shows how if class Student implements Comparable, we can create a list of Students and sort it. */
    public static void demoComparable() {
        System.out.println("Using Comparable");
        Student st1 = new Student("Zheng", 3.5);
        Student st2 = new Student("Li", 3.7);
        List<Student> studentList = new ArrayList<>();
        studentList.add(st1);
        studentList.add(st2);
        Collections.sort(studentList); // calls compareTo method when it sorts students
        System.out.println("student List = " + studentList);
    }

    /** Shows how if we have Comparator to compare students according to some criteria,
     *  we can have a sorted list of Student-s in the TreeSet. TreeSet will use Comparator internally. */
    public static void demoComparator() {
        // Using Comparator
        Student st1 = new Student("Zheng", 3.5);
        Student st2 = new Student("Li", 3.7);
        System.out.println("\nUsing Comparator");
        Comparator<Student> comp1 = new StudentComparatorGPA();
        Comparator<Student> comp2 = new StudentComparatorName();
        Set<Student> studentsSet = new TreeSet<Student>(comp1);
        studentsSet.add(st1);
        studentsSet.add(st2);
        System.out.println(studentsSet);

        Set<Student> studentsSet2 = new TreeSet<Student>(comp2);
        studentsSet2.add(st1);
        studentsSet2.add(st2);
        System.out.println(studentsSet2);
    }

    /** Shows how to use an anonymous class to pass a Comparator to the TreeSet */
    public static void demoAnonymousComparator() {
        System.out.println("\nUsing Anonymous class");
        /*Student st1 = new Student("Zheng", 3.5);
        Student st2 = new Student("Li", 3.7);
        */

        Student st1 = new Student("Alice", 3.8);
        Student st2 = new Student("Jim", 3.5);
        Set<Student> studentsSet3 = new TreeSet<Student>(new Comparator<Student>() {
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        studentsSet3.add(st1);
        studentsSet3.add(st2);
        System.out.println(studentsSet3);
    }

}
