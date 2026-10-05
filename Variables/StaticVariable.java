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
 *  STATIC VARIABLE (class variable)
 * ============================================================================
 *
 * WHAT IT IS
 *   A field declared with the "static" keyword. There is exactly ONE copy for
 *   the whole class, shared by every object (and usable with no object at all).
 *
 * LIFETIME AND STORAGE
 *   Created when the class is loaded and initialised (the first time it is
 *   used) and lives until the class is unloaded, normally the end of the
 *   program. It is stored with the class, not inside objects.
 *
 * DEFAULT VALUES
 *   Same as instance fields: 0, false, '\u0000', or null.
 *
 * HOW TO ACCESS IT
 *   ClassName.variable is the correct way. objectReference.variable also
 *   compiles, but it is misleading, because it does not use the object at all.
 *
 * ACCESS MODIFIERS
 *   public, package-private, protected, private: same meaning as for instance
 *   variables.
 *
 * OTHER MODIFIERS
 *   final      a constant: "static final double PI". Naming convention:
 *              UPPER_SNAKE_CASE. A blank static final must be assigned in a
 *              static initializer block.
 *   transient / volatile   allowed (volatile is common for shared flags).
 *   Interface fields are implicitly public static final.
 *
 * STATIC INITIALIZER BLOCK
 *   static { ... } runs ONCE, when the class is initialised, before main body
 *   code that uses the class. Use it for complex setup of static fields.
 *
 * THE RULE ABOUT STATIC CONTEXT
 *   Static code (static methods, static blocks) cannot use instance variables
 *   directly, because it has no "this". It needs an object.
 *
 * WATCH OUT
 *   - Shared mutable static state is shared between threads and can cause race
 *     conditions unless you synchronize or use volatile/atomic classes.
 *   - "static" is NOT allowed on a local variable.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Sharing between objects, access with the class name, the four access levels,
 *   static final constants, a static block, interface constants, and the
 *   static-context rule.
 * ============================================================================
 */
// Run: java StaticVariable.java   (Java 17+)
public class StaticVariable {

    public static int publicStatic = 1;               // PUBLIC
    static int packageStatic = 2;                     // PACKAGE-PRIVATE
    protected static int protectedStatic = 3;         // PROTECTED
    private static int privateStatic = 4;             // PRIVATE

    static int count;                                 // default 0, shared by all objects
    static final double PI = 3.14159;                 // constant
    static final String APP;                          // blank static final
    static {                                          // STATIC BLOCK: runs once
        APP = "demo";
        System.out.println("static block ran once, APP = " + APP);
    }

    int instanceVar = 7;                              // an instance variable, for contrast

    StaticVariable() { count++; }                     // every object bumps the shared counter

    static void staticMethod() {
        System.out.println("inside static method, count = " + count);   // OK: static member
        // System.out.println(instanceVar);           // x no "this" in static context
        // To use an instance variable you need an object: new StaticVariable().instanceVar
    }

    interface Config { int MAX = 10; }                // implicitly public static final

    static class Other {
        void read() {
            System.out.println(StaticVariable.publicStatic + " " + StaticVariable.packageStatic
                    + " " + StaticVariable.protectedStatic);
            // System.out.println(StaticVariable.privateStatic);   // x outside the class
        }
    }

    public static void main(String[] args) {
        System.out.println("-- one shared copy");
        StaticVariable a = new StaticVariable();
        StaticVariable b = new StaticVariable();
        StaticVariable c = new StaticVariable();
        System.out.println("count via class name = " + StaticVariable.count);   // 3
        System.out.println("count via object a   = " + a.count);                // same variable (works, but discouraged)
        b.count = 10;                                                           // changes the one shared copy
        System.out.println("after b.count = 10, c sees " + c.count);

        System.out.println("-- instance vs static");
        a.instanceVar = 99;
        System.out.println("a.instanceVar = " + a.instanceVar + ", b.instanceVar = " + b.instanceVar);

        System.out.println("-- constants");
        System.out.println(PI + " " + Config.MAX);
        // PI = 3;                                    // x cannot assign a value to a final variable

        System.out.println("-- access levels");
        System.out.println(publicStatic + " " + privateStatic + " (private works inside the class)");
        new Other().read();

        staticMethod();

        // static int local = 1;                      // x "static" is not allowed on a local variable
    }
}
