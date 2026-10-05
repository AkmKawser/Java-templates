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
 *  SYNCHRONIZED METHOD
 * ============================================================================
 *
 * WHAT IT IS
 *   A method declared with "synchronized". Before the body runs, the thread must
 *   acquire a lock (monitor); it releases the lock when the method ends, even if
 *   an exception is thrown. While one thread holds the lock, other threads that
 *   need the SAME lock wait.
 *
 * WHICH LOCK?
 *   synchronized instance method  -> the lock of "this" (that one object)
 *   synchronized static method    -> the lock of the Class object (ClassName.class)
 *   So two threads using DIFFERENT objects do not block each other, but a
 *   synchronized instance method and a synchronized static method use different
 *   locks and do not exclude each other.
 *
 * WHY IT IS NEEDED
 *   count++ looks like one step but is three: read, add, write. If two threads
 *   interleave, updates are lost (a race condition). Synchronizing makes the
 *   whole read-modify-write one atomic unit and also makes the result visible
 *   to the next thread that takes the lock.
 *
 * REENTRANT
 *   A thread that already holds a lock may enter another synchronized method
 *   that needs the same lock without blocking itself.
 *
 * WHERE IT IS ALLOWED
 *   instance methods and static methods.
 *   NOT allowed: constructors, abstract methods, and variables.
 *   An overriding method does not inherit "synchronized"; it can add or drop it.
 *
 * SYNCHRONIZED BLOCK
 *   synchronized (this) { ... } protects only a few lines and can use any object
 *   as the lock. Prefer blocks when only part of the method needs protection.
 *
 * COSTS
 *   Threads wait, and careless locking can cause deadlock. For simple counters
 *   consider AtomicInteger.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   A safe versus an unsafe counter with two threads, a static synchronized
 *   method, reentrancy, and the equivalent synchronized block.
 * ============================================================================
 */
// Run: java SynchronizedMethod.java   (Java 17+)
public class SynchronizedMethod {

    static class Safe {
        private int count;
        static int total;

        synchronized void inc() { count++; }              // lock = this
        synchronized int get() { return count; }
        static synchronized void incTotal() { total++; }  // lock = Safe.class

        synchronized void outer() { inner(); }            // REENTRANT: same thread, same lock
        synchronized void inner() { count += 0; }

        void block() {                                    // same effect, smaller protected region
            synchronized (this) { count++; }
        }
        // synchronized Safe() { }                        // x constructors cannot be synchronized
        // synchronized abstract void m();                // x abstract methods cannot be synchronized
    }

    static class Unsafe {
        int count;
        void inc() { count++; }                           // NOT synchronized: updates can be lost
    }

    static void runTwice(Runnable task) throws InterruptedException {
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();                             // wait for both threads
    }

    public static void main(String[] args) throws InterruptedException {
        final int N = 100_000;

        Safe safe = new Safe();
        runTwice(() -> { for (int i = 0; i < N; i++) safe.inc(); });
        System.out.println("synchronized: " + safe.get() + " (always " + 2 * N + ")");

        Unsafe unsafe = new Unsafe();
        runTwice(() -> { for (int i = 0; i < N; i++) unsafe.inc(); });
        System.out.println("not synchronized <= " + 2 * N + "? " + (unsafe.count <= 2 * N)
                + "  (usually LESS than " + 2 * N + ": lost updates)");

        runTwice(() -> { for (int i = 0; i < N; i++) Safe.incTotal(); });
        System.out.println("static synchronized: " + Safe.total);

        safe.outer();                                     // reentrancy: no deadlock
        safe.block();
        System.out.println("reentrant call and synchronized block worked, count = " + safe.get());
    }
}
