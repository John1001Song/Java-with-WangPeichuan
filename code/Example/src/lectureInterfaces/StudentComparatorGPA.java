package lectureInterfaces;

import java.util.Comparator;

/** A class that implements Comparator Interface that compares Students based on GPA */
public class StudentComparatorGPA implements Comparator<Student> {
    final static double EPS  = 0.001;
    @Override
    public int compare(Student o1, Student o2) {
       if (Math.abs(o1.getGPA() - o2.getGPA()) < EPS ) {
           return 0;
       }
       else
       if (o1.getGPA() < o2.getGPA())
               return -1;
           else
               return 1;

    }
}
