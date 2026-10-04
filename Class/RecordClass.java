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
