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
