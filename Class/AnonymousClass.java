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
