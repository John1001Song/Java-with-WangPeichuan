package lectureInterfaces;

/** OuterClass showing an interface with a default method */
interface Speak {
    public void say(String greeting);

    default public void shout() {
        System.out.println("Let's fight!");
    }

}
