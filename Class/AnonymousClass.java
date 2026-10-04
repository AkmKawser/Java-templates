/*
 * ============================================================================
 *  ANONYMOUS CLASS
 * ============================================================================
 *
 * WHAT IT IS
 *   A class with NO NAME, declared and instantiated in a single expression:
 *       new Runnable() { public void run() { ... } };
 *   The type after "new" decides what it is:
 *       new SomeClass()  { ... }  -> an anonymous SUBCLASS of SomeClass
 *       new SomeInterface() { ... } -> an anonymous class IMPLEMENTING it
 *
 * LIMITS
 *   - No modifiers of any kind (no access modifier, no abstract/final/static).
 *   - No "extends" or "implements" keywords: the new-expression supplies the
 *     parent. It can extend one class OR implement one interface, not both.
 *   - No constructor, because it has no name. Arguments written in new X(args)
 *     go to the PARENT's constructor. You can use an instance initializer
 *     block { ... } for setup instead.
 *   - Only a class can be anonymous (not an enum, record, or interface).
 *   - Cannot be reused: each expression creates a one-off class.
 *
 * CAPTURING VARIABLES
 *   Same rule as local classes: captured local variables must be final or
 *   effectively final.
 *
 * ANONYMOUS CLASS vs LAMBDA
 *   For an interface with ONE abstract method, a lambda is shorter:
 *       Runnable r = () -> System.out.println("hi");
 *   Use an anonymous class when you need to
 *     - extend a class (not just an interface),
 *     - implement an interface with several methods,
 *     - keep fields or state inside the object.
 *   In a lambda, "this" means the surrounding object. In an anonymous class,
 *   "this" means the anonymous object itself.
 *
 * HOW THE COMPILER SEES IT
 *   It becomes Outer$1.class, Outer$2.class, and so on.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Anonymous subclass of Object, anonymous implementation of Runnable, and an
 *   anonymous subclass of an abstract class, with a captured variable.
 * ============================================================================
 */
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
