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
 *  STATIC NESTED CLASS
 * ============================================================================
 *
 * WHAT IT IS
 *   A class declared INSIDE another class with the "static" keyword. It is a
 *   normal class that happens to live in the outer class's namespace. It is
 *   NESTED but not INNER, because it has no link to an outer object.
 *
 * KEY IDEA: NO OUTER INSTANCE
 *   You create it without any outer object:
 *       new Outer.Nested();
 *   It holds no hidden reference to an Outer object, so:
 *   - it can directly use the outer's STATIC members, including private ones;
 *   - it cannot directly use the outer's INSTANCE members. It would need an
 *     Outer object passed in (see class I below).
 *   - the outer class CAN access the nested class's private members.
 *
 * ACCESS MODIFIERS
 *   All four are allowed: public, protected, package-private, private.
 *   private static is common for helper classes that nobody else should see.
 *
 * OTHER MODIFIERS
 *   abstract, final, sealed, non-sealed are all allowed.
 *   It may declare static members of its own.
 *
 * COMMON USES
 *   - Builder pattern: Pizza.Builder
 *   - Grouping a helper with the class that owns it: Map.Entry
 *   - Keeping a small private data holder out of the public API
 *
 * HOW THE COMPILER SEES IT
 *   It becomes a separate file named Outer$Nested.class.
 *
 * STATIC NESTED vs INNER
 *   static nested  -> no outer object, created with new Outer.Nested()
 *   inner          -> needs an outer object, created with outer.new Inner()
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Static nested classes at all four access levels, abstract, final, sealed,
 *   and implements, plus how to reach the outer's members correctly.
 * ============================================================================
 */
// Run: java StaticNestedClass.java   (Java 17+)
public class StaticNestedClass {
    private int x = 10;
    private static int y = 20;

    public static class A { String hi() { return "public static nested"; } }
    static class B { String hi() { return "package-private static nested"; } }
    protected static class C { String hi() { return "protected static nested"; } }
    private static class D { String hi() { return "private static nested"; } }
    abstract static class E { abstract String hi(); }                       // ABSTRACT
    static class EImpl extends E { String hi() { return "abstract static nested"; } }
    static final class F { String hi() { return "final static nested"; } }  // FINAL
    sealed static class G permits H { }                                     // SEALED
    static final class H extends G { String hi() { return "sealed static nested"; } }

    static class I {
        int readStatic() { return y; }                         // OK: static member of outer
        int readInstance(StaticNestedClass o) { return o.x; }  // OK only through an outer OBJECT
        // int bad() { return x; }                             // x no outer instance
    }
    static class J implements Runnable {                       // EXTENDS Object, IMPLEMENTS
        public void run() { System.out.println("static nested implements Runnable"); }
    }

    public static void main(String[] args) {
        System.out.println(new StaticNestedClass.A().hi());    // no outer object needed
        System.out.println(new B().hi());
        System.out.println(new C().hi());
        System.out.println(new D().hi());
        System.out.println(new EImpl().hi());
        System.out.println(new F().hi());
        System.out.println(new H().hi());
        System.out.println(new I().readStatic() + " " + new I().readInstance(new StaticNestedClass()));
        new J().run();
    }
}
