package lectureInterfaces;

public class Driver {
    public static void main(String[] args) {
        Creature me = new Creature();
        me.say("hi");
        me.shout(); //  the default version of shout from Speak interface will be invoked,
        // since Creature did not define this method
    }

}
