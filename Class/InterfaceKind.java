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
