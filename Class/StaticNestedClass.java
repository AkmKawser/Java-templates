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
