// Run: java LocalClass.java   (Java 17+)
public class LocalClass {
    public static void main(String[] args) {
        int count = 5;                                   // effectively final

        class A { String hi() { return "plain local class"; } }   // no access modifier allowed
        // public class A2 {}                            // x access modifiers not allowed

        abstract class B { abstract String hi(); }                // ABSTRACT
        class BImpl extends B { String hi() { return "local abstract class"; } }

        final class C { String hi() { return "local final class"; } }  // FINAL

        // sealed class D permits E {}                   // x local classes cannot be sealed
        // static class F {}                             // x cannot be static

        class G implements Runnable {                             // EXTENDS Object, IMPLEMENTS
            public void run() { System.out.println("local captures count = " + count); }
        }

        System.out.println(new A().hi());
        System.out.println(new BImpl().hi());
        System.out.println(new C().hi());
        new G().run();

        // Local enum, record, interface (Java 16+). They are implicitly static.
        enum Mode { ON, OFF }
        record Temp(int v) { }
        interface Greeter { String greet(); }
        Greeter g = () -> "local interface";
        System.out.println(Mode.ON + " " + new Temp(7) + " " + g.greet());
    }
}
