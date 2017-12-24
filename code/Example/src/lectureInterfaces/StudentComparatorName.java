package lectureInterfaces;

import java.util.Comparator;

/** A class that implements Comparator Interface that compares Students based on name. */
public class StudentComparatorName implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
      return o1.getName().compareTo(o2.getName())
;
    }
}
