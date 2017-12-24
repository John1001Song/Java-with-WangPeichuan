package lectureInterfaces;

/** OuterClass of how a class can implement Comparable interface */
public class Student  implements Comparable<Student> {
    final static double EPS = 0.0001;

    private String name;
    private double averageGPA;

    public Student(String name, double averageGPA) {
        this.name = name;
        this.averageGPA = averageGPA;
    }

    public String getName() {
        return name;
    }

    public double getGPA() {
        return averageGPA;
    }

    public String toString() {
        return name + " : " + averageGPA;
    }

    @Override
    public int compareTo(Student o) {
        // compares by name
        return this.name.compareTo(o.name);

        /*// comment the line above and uncomment the block below
        // if you want to compare by GPA instead
        if  (Math.abs(this.averageGPA  - o.averageGPA) < EPS) {
            return 0;
        }
        else if (this.averageGPA < o.averageGPA) {
            return -1;
        }
        else
            return 1;
    */

    }
}
