/*
 * ============================================================================
 *  CONCRETE CLASS
 * ============================================================================
 *
 * WHAT IT IS
 *   A normal, fully implemented class. Every method has a body, so you can
 *   create objects from it with "new". It is the default kind of class: you
 *   get one simply by NOT writing "abstract" in front of "class".
 *
 * WHERE IT CAN BE DECLARED
 *   - top-level (directly in a file)
 *   - member of another class (static nested or inner)
 *   - inside a method (local class)
 *
 * ACCESS MODIFIERS (choose at most one)
 *   public            visible everywhere; the file name must match the class
 *   (nothing)         package-private: visible only inside the same package
 *   protected         NESTED ONLY: package + subclasses of the outer class
 *   private           NESTED ONLY: visible only inside the outer class
 *   A top-level class cannot be protected or private, because there is no
 *   enclosing class for those words to refer to.
 *
 * OTHER MODIFIERS
 *   final             nobody can extend it
 *   sealed            only the classes listed in "permits" can extend it
 *   static            NESTED ONLY: no outer object is needed
 *   abstract          NOT allowed here: it would turn this into an abstract class
 *
 * CLAUSES
 *   extends X         exactly ONE parent class (default parent is Object)
 *   implements A, B   any number of interfaces
 *
 * GOOD TO KNOW
 *   - If you write no constructor, the compiler adds a no-argument one.
 *   - A concrete class that extends an abstract class or implements an
 *     interface MUST implement every abstract method it inherits.
 *   - Fields default to 0, false, or null if you do not initialise them.
 *
 * COMMON MISTAKES
 *   - Writing "private class X" or "protected class X" at top level.
 *   - Putting two public top-level classes in one file.
 *   - Writing "class C extends A, B" (Java has no multiple class inheritance).
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Every legal combination for a concrete class, with a comment on each.
 *   Illegal lines are commented out (marked with x) so the file compiles.
 * ============================================================================
 */
// Run: java ConcreteClass.java   (Java 17+)
public class ConcreteClass {                       // PUBLIC top-level concrete class

    protected class P1 { String hi() { return "protected nested class"; } }  // PROTECTED (nested only)
    private class P2 { String hi() { return "private nested class"; } }      // PRIVATE (nested only)
    static class Helper { String hi() { return "static nested class"; } }    // STATIC (nested only)

    public static void main(String[] args) {
        System.out.println(new Engine().hi());                 // package-private
        ConcreteClass outer = new ConcreteClass();
        System.out.println(outer.new P1().hi());
        System.out.println(outer.new P2().hi());
        System.out.println(new Helper().hi());
        System.out.println("final class constant: " + Constants.MAX);
        System.out.println("sealed Base -> " + new Child().name());
        System.out.println("extends: " + new Dog().sound());
        Athlete a = new Athlete();
        System.out.println("implements: " + a.run() + ", " + a.swim());
        // ABSTRACT: adding it would make this an abstract class (different kind)
        // protected class X {}  private class Y {}  static class Z {}   // x top-level
    }
}

class Engine { String hi() { return "package-private class"; } }   // PACKAGE-PRIVATE
final class Constants { static final int MAX = 5; }                  // FINAL
sealed class Base permits Child { String name() { return "Base"; } } // SEALED
final class Child extends Base { String name() { return "Child"; } }
class Animal { String sound() { return "..."; } }                    // EXTENDS one class
class Dog extends Animal { String sound() { return "Woof"; } }
interface Runner { default String run() { return "running"; } }
interface Swimmer { default String swim() { return "swimming"; } }
class Athlete implements Runner, Swimmer { }                         // IMPLEMENTS many
