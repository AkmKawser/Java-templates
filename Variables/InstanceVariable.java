/*
 *
 * ============================================================================
 *  1. KINDS OF VARIABLES
 * ============================================================================
 *
 *  A variable is a named storage location with a TYPE. Java has two families
 *  of types: PRIMITIVE (byte short int long float double char boolean), which
 *  hold the value itself, and REFERENCE (classes, interfaces, enums, records,
 *  arrays), which hold a reference to an object or null.
 *
 *  Where a variable is declared decides its kind:
 *    inside a class body, outside every method   -> field (instance or static)
 *    inside a method, constructor, or block      -> local variable
 *    inside the parentheses of a method header   -> parameter
 *
 *   #  KIND                MEANING
 *  --  ------------------  --------------------------------------------------
 *   1  Local variable      Declared inside a method, constructor, or block.
 *                          Lives only while that block runs. No default value.
 *   2  Instance variable   Field declared WITHOUT static. One separate copy
 *                          per object. Lives on the heap with the object.
 *   3  Static variable     Field declared WITH static (class variable). ONE
 *                          copy shared by every object of the class.
 *   4  Parameter           Variable in a method/constructor/lambda/catch
 *                          header. Receives a COPY of the caller's value.
 *   5  Reference variable  A variable whose type is a class, interface, enum,
 *                          record, or array. It holds a reference, not the
 *                          object. This is NOT a separate storage kind: a
 *                          local, instance, static, or parameter variable can
 *                          each be a reference variable.
 *
 *  Kinds 1 to 4 are classified by WHERE the variable lives. Kind 5 is
 *  classified by WHAT TYPE it has, so it overlaps with the others.
 *
 *  Default values (fields only; locals get none):
 *    byte short int long -> 0      float double -> 0.0
 *    char -> '\u0000'              boolean -> false      references -> null
 *
 * ============================================================================
 *  2. THE TEMPLATES
 * ============================================================================
 *  Square brackets [ ] mean optional.
 *
 *  FIELD (instance or static)
 *    [annotations] [access] [static] [final] [transient] [volatile]
 *        Type name [= value] [, name2 [= value2]] ;
 *
 *  LOCAL VARIABLE
 *    [final] Type name [= value] ;
 *    [final] var name = value ;              (Java 10+, type is inferred)
 *
 *  PARAMETER
 *    [final] Type name                       (inside the method's parentheses)
 *    Type... name                            (varargs, must be the LAST one)
 *
 *  REFERENCE VARIABLE
 *    ClassName name = new ClassName(args) ;  or  = null ;  or  = otherReference ;
 *
 *  Which modifiers each kind accepts:
 *    kind        access   static   final   transient   volatile
 *    local        no       no       yes      no          no
 *    instance     yes      no       yes      yes         yes
 *    static       yes      (is)     yes      yes         yes
 *    parameter    no       no       yes      no          no
 *  Illegal combination: final + volatile.
 *
 * ============================================================================
 *  3. WHAT EACH KEYWORD MEANS
 * ============================================================================
 *
 *  ACCESS (who can use the field; at most one; fields only)
 *    public        Visible everywhere.
 *    (no keyword)  Package-private: visible inside the same package.
 *    protected     Same package plus subclasses.
 *    private       Visible only inside the declaring class.
 *
 *  MODIFIERS
 *    static        The field belongs to the CLASS, not to any object.
 *    final         Can be assigned only once. For a reference variable, the
 *                  reference is fixed but the object it points to can change.
 *    transient     The field is skipped when the object is serialized.
 *    volatile      Every read/write goes to main memory, so all threads see
 *                  the latest value (visibility between threads).
 *
 *  OTHER TERMS
 *    var           Let the compiler infer the type (locals only).
 *    new           Creates an object and returns a reference to it.
 *    null          The reference that points to no object.
 *    this          The current object; used as this.name to reach a field
 *                  when a parameter or local has the same name.
 *    ...           Varargs: a parameter that accepts zero or more values.
 *    blank final   A final variable with no initial value; it must be assigned
 *                  exactly once (in a constructor, static block, or later).
 *    effectively final   A local/parameter assigned once and never changed;
 *                  lambdas and inner classes may capture it.
 *    scope         The region of code where the variable's name can be used.
 *    shadowing     A local or parameter hides a field of the same name.
 *    pass-by-value Java always passes a COPY of the variable's value. For a
 *                  reference, the copy is the reference, not the object.
 *
 *  SYMBOLS USED IN THESE FILES
 *    x             Marks a line that is ILLEGAL and therefore commented out.
 */

/*
 * ============================================================================
 *  INSTANCE VARIABLE (non-static field)
 * ============================================================================
 *
 * WHAT IT IS
 *   A variable declared inside a class body, outside every method, WITHOUT the
 *   static keyword. Each object gets its OWN copy. Changing it in one object
 *   does not affect any other object.
 *
 * LIFETIME AND STORAGE
 *   Created when the object is created with "new" and lives on the heap as part
 *   of that object. It disappears when the object is garbage collected.
 *
 * DEFAULT VALUES
 *   Fields are initialised automatically: 0 for numbers, '\u0000' for char,
 *   false for boolean, null for references.
 *
 * ACCESS MODIFIERS (choose at most one)
 *   public            any class can read/write it
 *   (none)            package-private: classes in the same package
 *   protected         same package plus subclasses
 *   private           only code inside this class (the usual choice; expose it
 *                     through getters and setters, see GetterSetterMethod)
 *
 * OTHER MODIFIERS
 *   final      assigned once: at the declaration, in an instance initializer
 *              block, or in every constructor ("blank final")
 *   transient  skipped during serialization (demo below)
 *   volatile   reads and writes are visible to all threads at once
 *   static     NOT here: it would make the field a static variable
 *   final + volatile is illegal.
 *
 * INITIALIZATION ORDER (per object)
 *   1. default values   2. field initializers and instance blocks, in the order
 *   written   3. the constructor body.
 *
 * HOW TO ACCESS IT
 *   From inside an instance method: name or this.name.
 *   From outside: objectReference.name (if the access level allows it).
 *
 * COMMON MISTAKES
 *   - Reading an instance variable from a static method without an object.
 *   - Forgetting to assign a blank final in a constructor.
 *   - Making fields public instead of private with accessors.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   The four access levels, final, transient, volatile, default values,
 *   separate copies per object, and initialization order.
 * ============================================================================
 */
// Run: java InstanceVariable.java   (Java 17+)
import java.io.*;

public class InstanceVariable {

    public String publicVar = "public";               // PUBLIC
    String packageVar = "package-private";            // PACKAGE-PRIVATE
    protected String protectedVar = "protected";      // PROTECTED
    private String privateVar = "private";            // PRIVATE

    final int id;                                     // FINAL (blank): assigned in the constructor
    final String type = "fixed";                      // FINAL with an initial value
    volatile boolean ready = false;                   // VOLATILE: visible across threads
    // final volatile int bad;                        // x final + volatile is illegal
    // static is not written here: it would create a static variable

    InstanceVariable(int id) { this.id = id; }        // blank final assigned exactly once

    String readPrivate() { return privateVar; }       // private is usable inside the class

    static class Defaults {                           // default values of every type
        byte b; short s; int i; long l; float f; double d; char c; boolean flag; String str; int[] arr;
    }

    static class Counter {                            // each object has its own copy
        int count;
        void inc() { count++; }
    }

    static class User implements Serializable {       // transient demo
        String name = "Ali";
        transient String password = "secret";         // NOT saved when serialized
    }

    static class Order {                              // initialization order demo
        int a = log("1) field initializer");
        { log("2) instance initializer block"); }
        Order() { log("3) constructor body"); }
        static int log(String s) { System.out.println("  " + s); return 0; }
    }

    static class Visitor {                            // a different class, same package
        void visit(InstanceVariable o) {
            System.out.println(o.publicVar + ", " + o.packageVar + ", " + o.protectedVar);
            // System.out.println(o.privateVar);      // x private: invisible outside the class
        }
    }

    public static void main(String[] args) throws Exception {
        InstanceVariable o1 = new InstanceVariable(1);
        InstanceVariable o2 = new InstanceVariable(2);
        System.out.println(o1.id + " " + o2.id + " " + o1.type + " " + o1.readPrivate());
        // o1.id = 5;                                 // x cannot assign a value to a final field
        new Visitor().visit(o1);

        System.out.println("-- default values");
        Defaults d = new Defaults();
        System.out.println(d.b + " " + d.s + " " + d.i + " " + d.l + " " + d.f + " " + d.d
                + " " + (int) d.c + " " + d.flag + " " + d.str + " " + d.arr);

        System.out.println("-- separate copy per object");
        Counter c1 = new Counter(), c2 = new Counter();
        c1.inc(); c1.inc(); c2.inc();
        System.out.println("c1 = " + c1.count + ", c2 = " + c2.count);

        System.out.println("-- transient");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bos)) { out.writeObject(new User()); }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()))) {
            User u = (User) in.readObject();
            System.out.println("name = " + u.name + ", password = " + u.password);
        }

        System.out.println("-- initialization order");
        new Order();

        System.out.println("-- volatile");
        Thread t = new Thread(() -> {
            try { Thread.sleep(50); } catch (InterruptedException e) { }
            o1.ready = true;
        });
        t.start();
        while (!o1.ready) { Thread.onSpinWait(); }
        System.out.println("main thread saw ready = true");
    }
}
