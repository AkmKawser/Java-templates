// Run: java AnonymousClass.java   (Java 17+)
public class AnonymousClass {
    public static void main(String[] args) {
        int base = 100;                                  // effectively final

        // EXTENDS: comes from new <Class>()
        Object o = new Object() {
            @Override public String toString() { return "anonymous subclass of Object"; }
        };
        System.out.println(o);

        // IMPLEMENTS: comes from new <Interface>()
        Runnable r = new Runnable() {
            public void run() { System.out.println("anonymous Runnable, base = " + base); }
        };
        r.run();

        // Anonymous subclass of an abstract class
        Greeter g = new Greeter() { String greet() { return "anonymous Greeter"; } };
        System.out.println(g.greet());

        // x no modifiers, x no constructor, x cannot extend AND implement at once
    }
}
abstract class Greeter { abstract String greet(); }
