/*
 *
 * ============================================================================
 *  1. KINDS OF CLASSES
 * ============================================================================
 *
 *  Java has FOUR places where a type can be declared:
 *    top-level   directly in a .java file
 *    member      inside another class's body (static nested or inner)
 *    local       inside a method, constructor, or block
 *    anonymous   inside an expression, with no name
 *  Everything except top-level is called NESTED. Nested types that are not
 *  static are called INNER (inner member, local, anonymous).
 *
 *   #  KIND               ONE-LINE MEANING
 *  --  -----------------  ---------------------------------------------------
 *   1  Concrete class     Normal, complete class. Can be created with "new".
 *   2  Abstract class     Incomplete class. Cannot use "new"; meant to be extended.
 *   3  Final class        Cannot be extended.
 *   4  Static nested      Member class marked static. No outer object needed.
 *   5  Inner class        Member class without static. Tied to an outer object.
 *   6  Local class        Named class declared inside a method or block.
 *   7  Anonymous class    Nameless class declared and created in one expression.
 *   8  Enum class         Fixed set of named constants. Extends java.lang.Enum.
 *   9  Record class       Immutable data carrier. Extends java.lang.Record.
 *  10  Interface          A contract (not a class, but acts like one).
 *  11  Super class        A ROLE: the class that is extended (the parent).
 *  12  Sub class          A ROLE: the class that extends another (the child).
 *
 *  Kinds 11 and 12 are relationships, not separate kinds of class. Any class
 *  can be a super class, a sub class, or both at once.
 *
 * ============================================================================
 *  2. THE FOUR TEMPLATES
 * ============================================================================
 *  Square brackets [ ] mean optional. Clause order is always:
 *  extends, then implements, then permits.
 *
 *  CLASS
 *    [annotations] [access] [modifiers] class Name [<T>]
 *        [extends Super] [implements I1, I2] [permits A, B] {
 *        // fields, constructors, methods, initializer blocks, nested types
 *    }
 *
 *  INTERFACE
 *    [annotations] [access] [modifiers] interface Name [<T>]
 *        [extends I1, I2] [permits A, B] {
 *        // constants, abstract / default / static / private methods, nested types
 *    }
 *
 *  ENUM
 *    [annotations] [access] [modifiers] enum Name [implements I1, I2] {
 *        CONSTANT_ONE, CONSTANT_TWO;      // constants come first
 *        // fields, constructors, methods
 *    }
 *
 *  RECORD
 *    [annotations] [access] [modifiers] record Name [<T>](Type a, Type b)
 *        [implements I1, I2] {
 *        // compact constructor, extra constructors, methods, static members
 *    }
 *
 *  Modifiers you may write in the [modifiers] slot:
 *    class      abstract, final, sealed, non-sealed, static (nested only)
 *    interface  abstract (redundant), sealed, non-sealed, static (nested, implicit)
 *    enum       none (static is implicit when nested)
 *    record     final (redundant); static is implicit when nested
 *
 * ============================================================================
 *  3. WHAT EACH KEYWORD MEANS
 * ============================================================================
 *
 *  ACCESS (who can see it; choose at most one)
 *    public        Visible everywhere. A public top-level type must be in a
 *                  file with the same name.
 *    (no keyword)  Package-private. Visible only inside the same package.
 *    protected     Same package plus subclasses. NESTED types only.
 *    private       Visible only inside the enclosing class. NESTED types only.
 *
 *  MODIFIERS (what the type is allowed to do)
 *    abstract      Incomplete; cannot be instantiated; may contain abstract
 *                  methods that subclasses must implement.
 *    final         Cannot be extended.
 *    sealed        Only the classes listed in "permits" may extend/implement it.
 *    non-sealed    On a child of a sealed type: reopens it so anyone can extend.
 *    static        On a nested class: no outer object is needed.
 *
 *  KIND KEYWORDS (what sort of type you are declaring)
 *    class         A normal class.
 *    interface     A contract of methods; no constructors, no instance state.
 *    enum          A fixed set of named constants.
 *    record        An immutable data carrier with generated methods.
 *
 *  CLAUSES (relationships to other types)
 *    extends       Inherit from ONE parent class. For an interface: from MANY
 *                  interfaces. Not allowed on enums and records.
 *    implements    Promise to provide the methods of one or more interfaces.
 *                  Not allowed on an interface.
 *    permits       Lists the allowed direct subtypes of a sealed type. May be
 *                  left out if they are all in the same file.
 *
 *  OTHER PARTS OF THE TEMPLATE
 *    Name          The type's name (UpperCamelCase by convention).
 *    annotations   Metadata written with @, such as @Override or
 *                  @FunctionalInterface. They go before the access modifier.
 *    <T>           Type parameter: makes the type generic, e.g. Box<T>.
 *    (Type a, ...) Record components: they become the record's private final
 *                  fields, constructor parameters, and accessors.
 *    CONSTANT_ONE  Enum constants: the fixed instances of the enum.
 *    strictfp      Old floating-point modifier. No effect since Java 17.
 *
 *  SYMBOLS USED IN THESE FILES
 *    x             Marks a line that is ILLEGAL and therefore commented out.
 *    implicit      The compiler adds the modifier for you; you need not write it.
 *    redundant     Legal to write, but the compiler already adds it.
 *    nested only   Allowed only when the type is declared inside another type.
 *    effectively final   A local variable assigned once and never changed.
 * ############################################################################
 */

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
