// Run: java FinalClass.java   (Java 17+)
public class FinalClass {

    protected final class P1 { String hi() { return "protected final inner"; } }  // PROTECTED (nested)
    private final class P2 { String hi() { return "private final inner"; } }      // PRIVATE (nested)
    static final class Config { String hi() { return "static final nested"; } }   // STATIC (nested)

    public static void main(String[] args) {
        FinalClass o = new FinalClass();
        System.out.println(o.new P1().hi());
        System.out.println(o.new P2().hi());
        System.out.println(new Config().hi());
        System.out.println(new Money().show());                 // public-style final class
        System.out.println(new Helper().show());                // package-private final class
        System.out.println(new Cat().show());                   // final class that extends
        System.out.println(new Report().show());                // final class that implements
        // class Hack extends Money {}          // x cannot extend a final class
        // abstract final / sealed final        // x contradictions
    }
}

final class Money { String show() { return "Money (final)"; } }
final class Helper { String show() { return "Helper (final, package-private)"; } }
class Animal { }
final class Cat extends Animal { String show() { return "Cat extends Animal"; } }
interface Printable { }
final class Report implements Printable { String show() { return "Report implements Printable"; } }
