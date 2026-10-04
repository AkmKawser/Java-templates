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
 *  RECORD CLASS  (Java 16+)
 * ============================================================================
 *
 * WHAT IT IS
 *   A compact class made to carry immutable data. You list the components in
 *   the header, and the compiler writes the rest:
 *       record Point(int x, int y) { }
 *   generates, for you:
 *     - a private final field for each component
 *     - a constructor taking all components (the canonical constructor)
 *     - an accessor for each component: p.x(), p.y()  (no "get" prefix)
 *     - equals(), hashCode(), and toString()  ->  Point[x=1, y=2]
 *
 * HOW IT WORKS
 *   - Implicitly extends java.lang.Record, so you can NOT write "extends".
 *   - Implicitly final: nobody can extend a record, and it cannot be abstract.
 *   - Fields are final, so a record is shallowly immutable.
 *   - It cannot declare extra INSTANCE fields beyond the components.
 *   - It CAN have: static fields and methods, extra constructors, normal
 *     methods, a compact constructor for validation, and nested types.
 *   - It CAN implement interfaces (see Student).
 *
 * MODIFIERS
 *   public / package-private at top level; all four when nested.
 *   "final" is allowed but redundant. abstract, sealed, non-sealed are NOT allowed.
 *   static is implicit when nested.
 *
 * RECORDS AND SEALED TYPES
 *   A record cannot be sealed, but it is a perfect permitted subclass of a
 *   sealed interface, because it is already final (see Shape and Circle).
 *
 * WHERE DECLARED
 *   Top-level, as a member, or local inside a method.
 *
 * RECORD vs NORMAL CLASS
 *   Use a record when the class is "just its data" (a DTO, a coordinate, a
 *   result). Use a normal class when you need mutable state or inheritance.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Top-level and nested records, a redundant final, a record in a sealed
 *   hierarchy, a record implementing interfaces, and a local record.
 * ============================================================================
 */
// Run: java RecordClass.java   (Java 17+)
public class RecordClass {
    protected record A(int v) { }            // PROTECTED (nested only), implicitly static
    private record B(int v) { }              // PRIVATE (nested only)
    record Item(String name) { }             // package-private nested

    public static void main(String[] args) {
        System.out.println(new Point(1, 2) + " " + new Pair(3, 4));   // top-level records
        System.out.println(new A(5) + " " + new B(6) + " " + new Item("pen"));
        System.out.println(new Id(9));                                // final is redundant
        Shape s = new Circle(2);                                      // record as a sealed subtype
        System.out.println(s);
        Student a = new Student("Ali"), b = new Student("Zed");       // IMPLEMENTS interfaces
        System.out.println(a.compareTo(b) < 0 ? "Ali before Zed" : "Zed before Ali");
        record Temp(int v) { }                                        // LOCAL record (Java 16+)
        System.out.println(new Temp(7));
        // abstract/sealed record, or "extends" -> x not allowed
    }
}
record Point(int x, int y) { }               // PUBLIC-style top-level record
record Pair(int a, int b) { }                // PACKAGE-PRIVATE
final record Id(int value) { }               // FINAL (allowed, redundant)
sealed interface Shape permits Circle { }
record Circle(double r) implements Shape { }
record Student(String name) implements java.io.Serializable, Comparable<Student> {
    public int compareTo(Student o) { return name.compareTo(o.name); }
}
