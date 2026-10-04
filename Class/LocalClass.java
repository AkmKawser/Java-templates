/*
 * ============================================================================
 *  LOCAL CLASS
 * ============================================================================
 *
 * WHAT IT IS
 *   A class declared INSIDE a method, constructor, or other block. It has a
 *   name, but it exists only from its declaration to the end of that block.
 *   Nothing outside the block can see it.
 *
 * NO ACCESS MODIFIERS
 *   public, protected, and private are NOT allowed. The block is already the
 *   entire scope, so those words would mean nothing.
 *
 * ALLOWED MODIFIERS
 *   abstract and final are allowed.
 *   static is NOT allowed.
 *   sealed and non-sealed are NOT allowed on local classes.
 *
 * CAPTURING LOCAL VARIABLES
 *   A local class can read local variables of the enclosing method, but only
 *   if they are final or EFFECTIVELY FINAL (assigned once and never changed).
 *   Why: the class keeps a COPY of the value, and the object can outlive the
 *   method call. If the original could change, the copy and the original would
 *   disagree. Java forbids that situation entirely.
 *
 * ENCLOSING INSTANCE
 *   In a non-static method, a local class also gets a reference to "this" of
 *   the outer object, like an inner class. In a static method (like main below),
 *   there is no outer object.
 *
 * LOCAL ENUM / RECORD / INTERFACE (Java 16+)
 *   These can also be declared locally. They are implicitly static, so they
 *   cannot capture local variables.
 *
 * WHEN TO USE ONE
 *   When you need a small helper type used only inside one method. If it is
 *   used once and needs no name, an anonymous class or a lambda is shorter.
 *
 * HOW THE COMPILER SEES IT
 *   It becomes a file like Outer$1Local.class.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Plain, abstract, and final local classes, capturing a variable, implementing
 *   an interface, and a local enum, record, and interface.
 * ============================================================================
 */
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
