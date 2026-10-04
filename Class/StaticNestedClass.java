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
