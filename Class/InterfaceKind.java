/*
 * ============================================================================
 *  INTERFACE  (a type, not a class, but it acts like one)
 * ============================================================================
 *
 * WHAT IT IS
 *   A CONTRACT: it says what a type can do without saying how. A class that
 *   "implements" the interface promises to provide every abstract method.
 *   Interfaces are how Java gets multiple inheritance of TYPE.
 *
 * WHAT IT CAN CONTAIN (and the implicit modifiers)
 *   - fields:             implicitly public static final (constants only)
 *   - abstract methods:   implicitly public abstract
 *   - default methods:    a method WITH a body that implementers inherit
 *   - static methods:     utility methods called as Interface.method()
 *   - private methods:    helpers shared by default methods (Java 9+)
 *   - nested types
 *   It has NO constructors and NO instance fields.
 *
 * CLAUSES
 *   extends   MANY interfaces:  interface C extends A, B { }
 *   implements is NOT allowed on an interface. Classes implement interfaces.
 *   A class can implement many interfaces:  class X implements A, B { }
 *
 * MODIFIERS
 *   public / package-private at top level; all four when nested.
 *   abstract  allowed but redundant (every interface is already abstract)
 *   final     NOT allowed (an interface exists to be implemented)
 *   sealed / non-sealed allowed, to limit who can implement it
 *   static    implicit when nested, never written
 *
 * FUNCTIONAL INTERFACE
 *   An interface with exactly ONE abstract method can be written as a lambda
 *   (see Drawable and Greeter in this file's main method).
 *
 * DEFAULT METHOD CONFLICT
 *   If a class implements two interfaces with the same default method, it must
 *   override it itself and may pick one with  A.super.method().
 *
 * INTERFACE vs ABSTRACT CLASS
 *   Interface: many per class, no state, no constructors, pure contract.
 *   Abstract class: one per class, can hold state and constructors.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Interfaces at every access level, abstract (redundant), sealed with
 *   final/non-sealed implementers, extending many interfaces, and the four
 *   kinds of members (abstract, default, static, constant).
 * ============================================================================
 */
// Run: java InterfaceKind.java   (Java 17+)
public class InterfaceKind {
    protected interface PA { String hi(); }             // PROTECTED (nested only), implicitly static
    private interface PB { String hi(); }               // PRIVATE (nested only)
    interface Callback { void done(); }                 // package-private nested

    static class ImplA implements PA { public String hi() { return "protected nested interface"; } }
    static class ImplB implements PB { public String hi() { return "private nested interface"; } }

    public static void main(String[] args) {
        System.out.println(new ImplA().hi());
        System.out.println(new ImplB().hi());
        Callback cb = () -> System.out.println("Callback done");
        cb.done();
        Drawable d = () -> "Drawable (top-level)";
        System.out.println(d.draw());
        System.out.println(new Car().name() + ", " + new Bike().name());   // sealed interface
        C1 c = new C1Impl();                                               // extends MANY interfaces
        System.out.println(c.a() + " " + c.b());
        Greeter g = () -> "hello";                                         // implicit public abstract
        System.out.println(g.hello() + " / " + g.bye() + " / " + Greeter.util() + " / MAX=" + Greeter.MAX);
        // final interface, or "interface X implements Y" -> x not allowed
    }
}
interface Drawable { String draw(); }                   // PUBLIC-style top-level
abstract interface Old { }                              // ABSTRACT: allowed but redundant
sealed interface Vehicle permits Car, Bike { String name(); }
final class Car implements Vehicle { public String name() { return "Car"; } }
non-sealed class Bike implements Vehicle { public String name() { return "Bike"; } }
interface A1 { String a(); }
interface B1 { String b(); }
interface C1 extends A1, B1 { }                         // an interface EXTENDS many interfaces
class C1Impl implements C1 { public String a() { return "A"; } public String b() { return "B"; } }
interface Greeter {
    String hello();                                     // public abstract
    default String bye() { return "bye"; }              // default method
    static String util() { return "static util"; }      // static method
    int MAX = 10;                                       // public static final
}
