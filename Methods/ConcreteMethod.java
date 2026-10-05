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
 *  CONCRETE METHOD
 * ============================================================================
 *
 * WHAT IT IS
 *   A method that HAS A BODY, the opposite of an abstract method. It is a complete
 *   implementation that can run. Almost every method you write is concrete.
 *
 * "HAS A BODY" MEANS
 *   A pair of braces, even if they are empty:
 *       void empty() { }       // still concrete: it exists and does nothing
 *   A method that ends with a semicolon instead is abstract (or native).
 *
 * WHERE CONCRETE METHODS APPEAR
 *   - in any normal (concrete) class: ALL its methods must be concrete
 *   - in an abstract class, mixed with abstract ones
 *   - in an interface, as default, static, or private methods
 *   - in enums and records
 *
 * MODIFIERS
 *   Everything except abstract: the four access levels, static, final,
 *   synchronized, native (a native method has no Java body, but it is not abstract),
 *   and default (interface only).
 *
 * HOW A METHOD BECOMES CONCRETE
 *   A subclass turns an inherited abstract method into a concrete one by
 *   implementing it. A class is concrete (can use "new") only when every
 *   inherited abstract method has been implemented.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Concrete methods in a normal class, an empty-bodied method, an abstract class
 *   mixing concrete and abstract methods, a subclass making the abstract one
 *   concrete, and concrete (default/static/private) methods in an interface.
 * ============================================================================
 */
// Run: java ConcreteMethod.java   (Java 17+)
public class ConcreteMethod {

    static class Normal {
        void hello() { System.out.println("hello from a concrete method"); }
        void empty() { }                                  // empty body: still concrete
        public static int square(int n) { return n * n; } // static methods are concrete too
        public final int twice(int n) { return n * 2; }   // so are final ones
    }

    static abstract class Base {
        abstract String name();                           // ABSTRACT: no body
        String greet() { return "Hello, " + name(); }     // CONCRETE: has a body, uses the abstract one
    }
    static class Impl extends Base {
        String name() { return "Impl"; }                  // implementing it makes the method concrete
    }

    interface Walker {
        void walk();                                      // abstract: no body
        default String info() { return "default method (concrete)"; }        // concrete
        static String util() { return "static interface method (concrete)"; } // concrete
        private String helper() { return "private interface method (concrete)"; } // concrete
        default String viaHelper() { return helper(); }
    }
    static class Person implements Walker {
        public void walk() { System.out.println("walking"); }  // must implement the abstract method
    }

    // native methods have no Java body but are not abstract:
    // static native int count();                         // implemented in C/C++, needs JNI

    public static void main(String[] args) {
        Normal n = new Normal();
        n.hello();
        n.empty();
        System.out.println(Normal.square(4) + " " + n.twice(5));

        Base b = new Impl();                              // Impl is concrete: all abstract methods implemented
        System.out.println(b.greet());
        // new Base();                                    // x Base is abstract

        Person p = new Person();
        p.walk();
        System.out.println(p.info());
        System.out.println(Walker.util());
        System.out.println(p.viaHelper());
    }
}
