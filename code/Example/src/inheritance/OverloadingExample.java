package inheritance;

public class OverloadingExample {

    public void disp(char c) {
        System.out.println(c);
    }


    public void disp(char c, int num) {
        System.out.println(c + " " + num);
    }
}
