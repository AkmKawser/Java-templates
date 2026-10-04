/*
 * ============================================================================
 *  INNER CLASS (non-static member class)
 * ============================================================================
 *
 * WHAT IT IS
 *   A class declared inside another class WITHOUT "static". Every inner object
 *   is tied to one specific object of the outer class. Internally it stores a
 *   hidden reference to that outer object.
 *
 * CREATING ONE
 *   You need an outer object first:
 *       Outer o = new Outer();
 *       Outer.Inner i = o.new Inner();
 *   Inside an instance method of Outer you can simply write: new Inner().
 *
 * WHAT IT CAN ACCESS
 *   All members of the outer object, including private fields and methods,
 *   because it lives inside the outer class. If the inner class declares a
 *   field with the same name, use Outer.this.name to reach the outer's field.
 *
 * ACCESS MODIFIERS
 *   All four are allowed (public, protected, package-private, private).
 *
 * OTHER MODIFIERS
 *   abstract, final, sealed, non-sealed are allowed.
 *   static is NOT allowed on the class itself (that would make it a static nested
 *   class). Since Java 16 an inner class may declare static members.
 *
 * COMMON USES
 *   - An iterator that walks its owning collection
 *   - Event handlers that need the outer object's state
 *   - Objects that make no sense without their owner
 *
 * WATCH OUT
 *   Because of the hidden outer reference, an inner object keeps its outer
 *   object alive in memory. If the inner object lives longer than you expect,
 *   it can cause a memory leak. If you do not need the outer object, make the
 *   class static instead.
 *
 * HOW THE COMPILER SEES IT
 *   It becomes Outer$Inner.class.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Inner classes at all four access levels, abstract, final, sealed, reading
 *   the outer's private field, and implementing an interface.
 * ============================================================================
 */
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
