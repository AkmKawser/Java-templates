/*
 *
 * ============================================================================
 *  1. KINDS OF METHODS
 * ============================================================================
 *
 *  A method is a named block of code that can take parameters and return a
 *  value. The kinds below are classified by DIFFERENT questions, so one method
 *  can belong to several at once (for example a public static final method).
 *
 *   #  KIND                 QUESTION IT ANSWERS / MEANING
 *  --  -------------------  ---------------------------------------------------
 *   1  Instance method      Belongs to an OBJECT. Called on an object, can use
 *                           "this" and instance variables.
 *   2  Static method        Belongs to the CLASS. Called with the class name, no
 *                           "this", cannot use instance members directly.
 *   3  Abstract method      Has NO body; declared with abstract (or in an
 *                           interface). Subclasses must supply the body.
 *   4  Final method         Cannot be overridden by a subclass.
 *   5  Overloaded method    Same name, DIFFERENT parameter lists, in one class.
 *                           Chosen at compile time.
 *   6  Overridden method    A subclass REPLACES an inherited method with the
 *                           same signature. Chosen at run time.
 *   7  Concrete method      Has a body (the opposite of abstract).
 *   8  Synchronized method  Only one thread at a time may run it (per lock).
 *   9  Getter / Setter      Accessor methods that read / write a private field.
 *  10  Constructor          Special method that builds an object when you write
 *                           "new". Same name as the class, no return type.
 *
 *  How they group:
 *    belongs to          instance (1) or static (2)
 *    has a body?         abstract (3) or concrete (7)
 *    inheritance         final (4), overridden (6)
 *    signature           overloaded (5)
 *    thread safety       synchronized (8)
 *    purpose             getter / setter (9)
 *    object creation     constructor (10)
 *  Also exist: native (implemented in C/C++), default / static / private methods
 *  inside interfaces, and strictfp (no effect since Java 17).
 *
 * ============================================================================
 *  2. THE TEMPLATES
 * ============================================================================
 *  Square brackets [ ] mean optional.
 *
 *  METHOD
 *    [annotations] [access] [modifiers] [<T>] ReturnType name([parameters])
 *        [throws Exception1, Exception2] {
 *        // body
 *    }
 *
 *  ABSTRACT METHOD (no body, ends with a semicolon)
 *    [annotations] [access] abstract [<T>] ReturnType name([parameters])
 *        [throws Exception1, Exception2] ;
 *
 *  CONSTRUCTOR (no return type; the name is the class name)
 *    [annotations] [access] ClassName([parameters]) [throws Exception1] {
 *        // optional first line: this(...)  or  super(...)
 *        // body
 *    }
 *
 *  GETTER AND SETTER (a naming convention, not new syntax)
 *    [access] Type getName() { return name; }          boolean: isName()
 *    [access] void setName(Type name) { this.name = name; }
 *
 *  METHODS INSIDE AN INTERFACE
 *    ReturnType name(...) ;                       abstract (implicitly public)
 *    default ReturnType name(...) { ... }         has a body, inherited
 *    static ReturnType name(...) { ... }          called as Interface.name()
 *    private ReturnType name(...) { ... }         helper for the other methods
 *
 *  Modifiers allowed in the [modifiers] slot of a method:
 *    static  final  abstract  synchronized  native  strictfp  default(interface)
 *
 *  Illegal combinations:
 *    abstract + private | static | final | synchronized | native
 *    private + protected | public (only one access level)
 *    Constructors accept ONLY the four access levels (no static, final,
 *    abstract, synchronized, native).
 *
 * ============================================================================
 *  3. WHAT EACH KEYWORD MEANS
 * ============================================================================
 *
 *  ACCESS (who can call it; at most one)
 *    public        Callable from anywhere.
 *    (no keyword)  Package-private: callable inside the same package.
 *    protected     Same package plus subclasses.
 *    private       Callable only inside the declaring class.
 *
 *  MODIFIERS
 *    static        Belongs to the class; no object needed; no "this".
 *    final         Cannot be overridden.
 *    abstract      No body; subclasses must implement it.
 *    synchronized  Acquires a lock first: the object's lock (instance method) or
 *                  the Class object's lock (static method).
 *    native        Implemented in another language (no Java body).
 *    default       In an interface: a method with a body that implementers inherit.
 *    strictfp      Old floating-point modifier; no effect since Java 17.
 *
 *  PARTS OF A METHOD
 *    ReturnType    The type of value returned, or void for none.
 *    name          The method's name (lowerCamelCase by convention).
 *    parameters    Variables that receive the caller's arguments.
 *    signature     The name plus the parameter TYPES in order. The return type is
 *                  NOT part of the signature.
 *    throws        Lists the checked exceptions the method may throw.
 *    <T>           Type parameter: makes the method generic.
 *    return        Ends the method and (if not void) gives back a value.
 *
 *  OTHER TERMS
 *    this          The current object. this(...) calls another constructor.
 *    super         The parent. super.method() calls the parent's version;
 *                  super(...) calls the parent's constructor.
 *    @Override     Asks the compiler to check that you really override something.
 *    overload      Same name, different parameters (compile-time choice).
 *    override      Same signature in a subclass (run-time choice).
 *    covariant return   An override may return a SUBTYPE of the original type.
 *    varargs       A final parameter written Type... that takes any number of values.
 *    polymorphism  The same call runs different code depending on the real type.
 *
 *  SYMBOLS USED IN THESE FILES
 *    x             Marks a line that is ILLEGAL and therefore commented out.
 */

/*
 * ============================================================================
 *  STATIC METHOD (class method)
 * ============================================================================
 *
 * WHAT IT IS
 *   A method declared with "static". It belongs to the CLASS, not to any object.
 *   You call it with the class name: Math.max(1, 2). No object is needed.
 *
 * WHAT IT CAN USE
 *   - static fields and other static methods directly
 *   - instance members ONLY through an object reference you create or receive
 *   - NOT "this" and NOT "super" (there is no current object)
 *
 * ACCESS MODIFIERS
 *   public, package-private, protected, private: same meaning as for any method.
 *
 * OTHER MODIFIERS
 *   final         legal; it also stops subclasses from hiding the method
 *   synchronized  locks the Class object (not an instance)
 *   abstract      ILLEGAL: a static method can never be overridden
 *   native        allowed
 *
 * STATIC METHODS ARE NOT OVERRIDDEN, THEY ARE HIDDEN
 *   If a subclass declares a static method with the same signature, it HIDES the
 *   parent's. Which one runs depends on the declared type of the reference, not
 *   on the real object (no run-time polymorphism). See Parent and Child below.
 *
 * INTERFACES
 *   An interface may have static methods. They are NOT inherited; call them as
 *   InterfaceName.method().
 *
 * COMMON USES
 *   Utility methods (Math, Arrays, Collections), factory methods
 *   (Integer.valueOf), and main, the program's entry point.
 *
 * COMMON MISTAKES
 *   - Using an instance field inside a static method.
 *   - Expecting a static method to be overridden.
 *   - Calling a static method through an object (it compiles, but is misleading).
 *
 * WHAT THIS FILE DEMONSTRATES
 *   The four access levels, the no-this rule, static final, a factory method,
 *   static synchronized, method hiding, and a static interface method.
 * ============================================================================
 */
// Run: java StaticMethod.java   (Java 17+)
public class StaticMethod {

    private int x = 5;                                    // an instance field, for contrast
    private static int created = 0;                       // a static field

    public static int publicAdd(int a, int b) { return a + b; }       // PUBLIC
    static int packageMul(int a, int b) { return a * b; }             // PACKAGE-PRIVATE
    protected static int protectedSub(int a, int b) { return a - b; } // PROTECTED
    private static int secret(int a) { return a * 100; }              // PRIVATE
    static int viaSecret(int a) { return secret(a); }

    static final int square(int n) { return n * n; }      // static + final: cannot be hidden

    static void noThis() {
        created++;                                        // OK: static member
        // System.out.println(this.x);                    // x there is no "this" in a static method
        // System.out.println(x);                         // x instance field needs an object
        StaticMethod obj = new StaticMethod();            // OK: reach instance members through an object
        System.out.println("x via an object = " + obj.x);
    }

    static StaticMethod create() {                        // FACTORY METHOD
        created++;
        return new StaticMethod();
    }

    static synchronized void lockOnClass() {              // lock is StaticMethod.class
        created++;
    }
    // abstract static void bad();                        // x abstract + static is illegal

    static class Parent {
        static String who() { return "Parent.who"; }
        String inst() { return "Parent.inst"; }
    }
    static class Child extends Parent {
        static String who() { return "Child.who"; }       // HIDES Parent.who (not an override)
        @Override String inst() { return "Child.inst"; }  // real override
    }

    interface Util {
        static String hi() { return "Util.hi"; }          // static interface method, not inherited
    }

    public static void main(String[] args) {
        System.out.println(publicAdd(2, 3) + " " + packageMul(2, 3) + " " + protectedSub(5, 2) + " " + viaSecret(2));
        System.out.println(StaticMethod.publicAdd(1, 1) + "  (called with the class name, no object)");
        System.out.println(Math.max(3, 9) + "  (Math.max is a static method)");
        System.out.println(square(6));

        noThis();
        create();
        lockOnClass();
        System.out.println("created counter = " + created);

        System.out.println("-- hiding vs overriding");
        Parent p = new Child();
        System.out.println(p.inst());                     // Child.inst  (overridden: real type wins)
        System.out.println(Parent.who() + " / " + Child.who());   // each class has its own static method
        // p.who() would run Parent.who: static methods follow the DECLARED type

        System.out.println(Util.hi());
        // class Impl implements Util {}  Impl.hi();      // x static interface methods are not inherited
    }
}
