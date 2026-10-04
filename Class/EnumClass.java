// Run: java EnumClass.java   (Java 17+)
public class EnumClass {
    protected enum A { ONE }                 // PROTECTED (nested only), implicitly static
    private enum B { TWO }                   // PRIVATE (nested only)
    enum Day { MON, TUE }                    // package-private nested, implicitly static

    public static void main(String[] args) {
        System.out.println(Level.HIGH + " " + Color.GREEN);          // top-level enums
        System.out.println(A.ONE + " " + B.TWO + " " + Day.TUE);
        for (Operation op : Operation.values())                       // abstract method + constant bodies
            System.out.println(op + " -> " + op.apply(6, 3));
        System.out.println(Size.LARGE.describe());                    // IMPLEMENTS an interface
        enum Mode { ON, OFF }                                         // LOCAL enum (Java 16+)
        System.out.println(Mode.OFF + " ordinal " + Mode.OFF.ordinal());
        // abstract/final/sealed enum, or "extends" -> x not allowed
    }
}
enum Level { LOW, HIGH }                     // PUBLIC-style top-level enum
enum Color { RED, GREEN }                    // PACKAGE-PRIVATE
enum Operation {
    ADD { int apply(int a, int b) { return a + b; } },
    SUB { int apply(int a, int b) { return a - b; } };
    abstract int apply(int a, int b);        // every constant must implement it
}
interface Describable { String describe(); }
enum Size implements Describable {
    SMALL, LARGE;
    public String describe() { return "Size " + name(); }
}
