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
 *  INNER CLASS (non-static member class)
 * ============================================================================
 *
 * WHAT IT IS
 *   A class declared inside another class WITHOUT "static". Every inner object
 *   is tied to one specific object of the outer class. Internally it stores a
 *   hidden reference to that outer object.
 *
 * CREATING ONE
 *   You need an outer object first:
 *       Outer o = new Outer();
 *       Outer.Inner i = o.new Inner();
 *   Inside an instance method of Outer you can simply write: new Inner().
 *
 * WHAT IT CAN ACCESS
 *   All members of the outer object, including private fields and methods,
 *   because it lives inside the outer class. If the inner class declares a
 *   field with the same name, use Outer.this.name to reach the outer's field.
 *
 * ACCESS MODIFIERS
 *   All four are allowed (public, protected, package-private, private).
 *
 * OTHER MODIFIERS
 *   abstract, final, sealed, non-sealed are allowed.
 *   static is NOT allowed on the class itself (that would make it a static nested
 *   class). Since Java 16 an inner class may declare static members.
 *
 * COMMON USES
 *   - An iterator that walks its owning collection
 *   - Event handlers that need the outer object's state
 *   - Objects that make no sense without their owner
 *
 * WATCH OUT
 *   Because of the hidden outer reference, an inner object keeps its outer
 *   object alive in memory. If the inner object lives longer than you expect,
 *   it can cause a memory leak. If you do not need the outer object, make the
 *   class static instead.
 *
 * HOW THE COMPILER SEES IT
 *   It becomes Outer$Inner.class.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Inner classes at all four access levels, abstract, final, sealed, reading
 *   the outer's private field, and implementing an interface.
 * ============================================================================
 */
// Run: java InnerClass.java   (Java 17+)
public class InnerClass {
    private int x = 10;

    public class A { String hi() { return "public inner"; } }
    class B { String hi() { return "package-private inner"; } }
    protected class C { String hi() { return "protected inner"; } }
    private class D { String hi() { return "private inner"; } }
    abstract class E { abstract String hi(); }                       // ABSTRACT
    class EImpl extends E { String hi() { return "abstract inner"; } }
    final class F { String hi() { return "final inner"; } }          // FINAL
    sealed class G permits H { }                                     // SEALED
    final class H extends G { String hi() { return "sealed inner"; } }
    class J { int show() { return x; } }                             // uses outer's private field
    class K implements Runnable {                                    // IMPLEMENTS
        public void run() { System.out.println("inner implements Runnable, x = " + x); }
    }
    // static class S {}   // would be a static nested class (different kind)

    public static void main(String[] args) {
        InnerClass o = new InnerClass();                             // outer object is required
        System.out.println(o.new A().hi());
        System.out.println(o.new B().hi());
        System.out.println(o.new C().hi());
        System.out.println(o.new D().hi());
        System.out.println(o.new EImpl().hi());
        System.out.println(o.new F().hi());
        System.out.println(o.new H().hi());
        InnerClass.J j = o.new J();
        System.out.println("outer's private x = " + j.show());
        o.new K().run();
    }
}
