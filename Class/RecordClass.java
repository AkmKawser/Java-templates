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
