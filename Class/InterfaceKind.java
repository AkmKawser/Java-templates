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
