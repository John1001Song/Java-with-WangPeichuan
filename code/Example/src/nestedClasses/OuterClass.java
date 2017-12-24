package nestedClasses;

public class OuterClass {
    private int outerVar;
    private int var;

    OuterClass(int outerVar) {
        this.outerVar = outerVar;
        var = 2 * outerVar;
    }
    public class InnerClass {
        private String innerVar;
        private int var = 2;
        InnerClass(String innerVar) {
            this.innerVar = innerVar;
        }
        public String toString() {
            return "innerClass: " + innerVar + ", outerVar: " + outerVar + "  Outer var = " + OuterClass.this.var;
        }
    }

    public String toString() {
        return "outerClass: " + var;
    }

    public static void main(String[] args) {
        OuterClass ex = new OuterClass(5);
        InnerClass inner = ex.new InnerClass("Hello");

        //OuterClass.InnerClass inner = ex.new InnerClass("hello");
        System.out.println(inner.toString());
        System.out.println(ex.toString());


    }
}
