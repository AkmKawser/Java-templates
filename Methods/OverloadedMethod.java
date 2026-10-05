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
 *  OVERLOADED METHOD
 * ============================================================================
 *
 * WHAT IT IS
 *   Several methods in the SAME class (or inherited into it) with the SAME name
 *   but DIFFERENT parameter lists. It is called compile-time polymorphism
 *   because the compiler picks the method from the argument types.
 *
 * WHAT MAKES TWO METHODS DIFFERENT
 *   The parameter list must differ in at least one of:
 *     - the NUMBER of parameters      print(int)     vs print(int, int)
 *     - the TYPES of parameters       print(int)     vs print(String)
 *     - the ORDER of the types        print(int, String) vs print(String, int)
 *   These do NOT count:
 *     - the return type alone         int f(int) and double f(int) is an ERROR
 *     - parameter names               f(int a) and f(int b) is an ERROR
 *     - throws clauses
 *   Access modifiers, static, final, and throws MAY differ between overloads.
 *
 * HOW THE COMPILER CHOOSES (in this order)
 *   1. exact match or widening       (int -> long -> float -> double)
 *   2. boxing / unboxing             (int <-> Integer)
 *   3. varargs                       (int... )
 *   Within a phase the MOST SPECIFIC method wins. If two are equally specific,
 *   the call is ambiguous and does not compile.
 *
 * OVERLOADING vs OVERRIDING
 *   overload: same class, different parameters, chosen at COMPILE time.
 *   override: subclass, same signature, chosen at RUN time (see OverriddenMethod).
 *
 * CONSTRUCTORS
 *   Constructors can be overloaded in the same way (see ConstructorMethod).
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Overloads by count, type, and order, widening, boxing, varargs resolution,
 *   different return types with different parameters, and the illegal cases.
 * ============================================================================
 */
// Run: java OverloadedMethod.java   (Java 17+)
public class OverloadedMethod {

    static void print(int x)           { System.out.println("print(int) " + x); }
    static void print(long x)          { System.out.println("print(long) " + x); }
    static void print(double x)        { System.out.println("print(double) " + x); }
    static void print(Integer x)       { System.out.println("print(Integer) " + x); }
    static void print(Object x)        { System.out.println("print(Object) " + x); }
    static void print(String s)        { System.out.println("print(String) " + s); }
    static void print(int a, int b)    { System.out.println("print(int, int) " + a + "," + b); }       // different COUNT
    static void print(int a, String b) { System.out.println("print(int, String) " + a + "," + b); }    // different TYPES
    static void print(String a, int b) { System.out.println("print(String, int) " + a + "," + b); }    // different ORDER
    static void print(int... xs)       { System.out.println("print(int...) length " + xs.length); }    // VARARGS

    // static int print(int x) { return x; }              // x same parameters, only the return type differs
    // static void print(int y) { }                       // x only the parameter NAME differs

    static int add(int a, int b)          { return a + b; }       // return types may differ
    static double add(double a, double b) { return a + b; }       // when the parameters differ
    static int add(int a, int b, int c)   { return a + b + c; }

    public static void main(String[] args) {
        print(5);                          // exact match: int
        print('a');                        // char widens to int (prints 97)
        print(5L);                         // long
        print(5.0f);                       // float widens to double
        print(5.0);                        // double
        print(Integer.valueOf(5));         // exact: Integer
        print("hi");                       // String is more specific than Object
        print((Object) "text");            // cast to Object picks print(Object)
        print(1, 2);                       // two ints (a non-varargs match wins first)
        print(1, "a");                     // (int, String)
        print("a", 1);                     // (String, int): the ORDER is part of the signature
        print();                           // only the varargs method fits
        print(1, 2, 3);                    // varargs
        // print(null);                    // x ambiguous: String and Integer are equally specific

        System.out.println(add(1, 2) + " " + add(1.5, 2.5) + " " + add(1, 2, 3));
    }
}
