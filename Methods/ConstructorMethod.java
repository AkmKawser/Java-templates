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
 *  CONSTRUCTOR METHOD
 * ============================================================================
 *
 * WHAT IT IS
 *   A special block of code that runs when you create an object with "new". It
 *   sets up the new object's state. It looks like a method but is not one:
 *     - its name is EXACTLY the class name
 *     - it has NO return type (not even void)
 *     - it is not inherited and cannot be called like a normal method
 *
 * DEFAULT CONSTRUCTOR
 *   If you write no constructor, the compiler adds a public no-argument one. As
 *   soon as you write ANY constructor, the compiler adds none.
 *
 * ACCESS MODIFIERS (the ONLY modifiers a constructor accepts)
 *   public        anyone can create objects
 *   (none)        only the same package
 *   protected     same package and subclasses
 *   private       only the class itself (Singleton, utility class, factory methods)
 *   static, final, abstract, synchronized, native are all ILLEGAL.
 *
 * OVERLOADING AND CHAINING
 *   - A class may have many constructors with different parameters (overloading).
 *   - this(...) calls another constructor of the SAME class.
 *   - super(...) calls a constructor of the PARENT class.
 *   - Either call must be the FIRST statement. If you write neither, the compiler
 *     inserts super() with no arguments. The parent must therefore have an
 *     accessible no-argument constructor, or you must call super(...) yourself.
 *
 * ORDER OF EVENTS WHEN YOU WRITE new
 *   1. memory is allocated and fields get default values
 *   2. the parent constructor runs (super)
 *   3. this class's field initializers and instance blocks run, in order
 *   4. the rest of this constructor body runs
 *
 * SPECIAL CASES
 *   abstract class   has constructors; subclasses reach them through super(...)
 *   enum             constructors are implicitly private
 *   record           a canonical constructor is generated; a COMPACT constructor
 *                    can validate the components
 *   anonymous class  cannot declare a constructor (it has no name)
 *   throws           a constructor may declare checked exceptions
 *
 * COMMON MISTAKES
 *   - Writing a return type (void Person()) turns it into an ordinary method!
 *   - Forgetting that a parent with only a parameterized constructor needs super(...).
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Default, parameterized, overloaded, chained, copy, and parent constructors,
 *   all four access levels (Singleton, utility class), initialization order,
 *   an abstract-class constructor, an enum and a record constructor, and the
 *   void-constructor mistake.
 * ============================================================================
 */
// Run: java ConstructorMethod.java   (Java 17+)
public class ConstructorMethod {

    static class Person {
        private String name;
        private int age;
        { System.out.println("  [instance initializer block]"); }       // runs before the constructor body

        Person() { this("Unknown", 0); System.out.println("  no-arg done"); }   // this(...) chains
        Person(String name) { this(name, 0); }                                   // overloaded
        Person(String name, int age) {                                           // the "main" constructor
            System.out.println("  Person(String, int)");
            this.name = name;
            this.age = age;
        }
        Person(Person other) { this(other.name, other.age); }                    // COPY constructor
        @Override public String toString() { return name + "/" + age; }
        // static Person() { }                            // x constructors cannot be static
        // final Person(int a) { }                        // x ... or final, abstract, synchronized
    }

    static class Student extends Person {
        Student(String name) {
            super(name, 18);                              // PARENT constructor call (first statement)
            System.out.println("  Student ctor");
        }
    }

    static class Defaulted { int x; }                     // no constructor written: the compiler adds one

    static class Access {                                 // the four access levels
        public Access(int a) { }                          // PUBLIC
        Access(String s) { }                              // PACKAGE-PRIVATE
        protected Access(double d) { }                    // PROTECTED
        private Access(char c) { }                        // PRIVATE
        static Access viaPrivate() { return new Access('c'); }   // only code inside the class can use it
    }

    static class Singleton {                              // private constructor: one instance only
        private static final Singleton INSTANCE = new Singleton();
        private Singleton() { System.out.println("  Singleton created once"); }
        static Singleton get() { return INSTANCE; }
    }

    static class Util {                                   // private constructor: cannot be instantiated
        private Util() { }
        static int twice(int n) { return n * 2; }
    }

    static abstract class Base {
        Base() { System.out.println("  Base constructor (abstract class)"); }
    }
    static class Impl extends Base { }                    // the implicit super() reaches Base()

    static class Risky {
        Risky() throws Exception { throw new Exception("constructor failed"); }   // throws is allowed
    }

    enum Color {
        RED(255), GREEN(128);
        final int value;
        Color(int value) { this.value = value; }          // implicitly private
    }

    record Point(int x, int y) {
        Point {                                           // COMPACT constructor: validation
            if (x < 0 || y < 0) throw new IllegalArgumentException("negative");
        }
        Point() { this(0, 0); }                           // extra constructor must call the canonical one
    }

    static class Gotcha {
        void Gotcha() { }                                 // NOT a constructor: it has a return type (void)!
    }

    public static void main(String[] args) {
        System.out.println("new Person():");
        System.out.println(new Person());
        System.out.println("new Person(\"Ali\"):");
        Person ali = new Person("Ali");
        System.out.println("copy constructor:");
        Person copy = new Person(ali);
        System.out.println(copy);
        System.out.println("new Student(\"Sara\"):");
        System.out.println(new Student("Sara"));

        System.out.println("default constructor: " + new Defaulted().x);
        new Access(1); new Access("s"); new Access(1.5); Access.viaPrivate();
        // new Access('c');                               // x private constructor outside Access

        System.out.println("Singleton:");
        System.out.println(Singleton.get() == Singleton.get());
        System.out.println(Util.twice(4));
        // new Util();                                    // x private constructor

        System.out.println("abstract class constructor:");
        new Impl();

        try {
            new Risky();
        } catch (Exception e) {
            System.out.println("caught: " + e.getMessage());
        }

        System.out.println(Color.RED.value + " " + new Point() + " " + new Point(1, 2));
        try {
            new Point(-1, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("compact constructor rejected: " + e.getMessage());
        }

        new Gotcha().Gotcha();                            // it is just a method; the compiler added a real constructor
        System.out.println("Gotcha: void Gotcha() is an ordinary method");
    }
}
