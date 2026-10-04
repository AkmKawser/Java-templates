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
