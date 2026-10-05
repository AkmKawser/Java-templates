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
 *  LOCAL VARIABLE
 * ============================================================================
 *
 * WHAT IT IS
 *   A variable declared INSIDE a method, constructor, initializer block, or any
 *   other block. Loop counters (for (int i...)), try-with-resources variables,
 *   and pattern variables are all locals.
 *
 * SCOPE AND LIFETIME
 *   - Scope: from its declaration to the closing brace of its block.
 *   - Lifetime: created when the block runs, gone when the block ends.
 *   - Every method CALL gets its own fresh copy (this is why recursion works).
 *
 * STORAGE
 *   Local variables live in the method's stack frame. A primitive local holds
 *   the value. A reference local holds a reference; the object is on the heap.
 *
 * NO DEFAULT VALUE
 *   Fields get defaults (0, false, null). Locals get NOTHING. The compiler
 *   refuses to compile code that might read a local before it is assigned
 *   ("definite assignment").
 *
 * MODIFIERS
 *   Only "final" is allowed (plus annotations). public, protected, private,
 *   static, transient, and volatile are all compile errors, because a local
 *   is already private to its block and cannot belong to a class.
 *
 * var (Java 10+)
 *   "var city = "Paris";" lets the compiler infer String. Only for locals,
 *   and only when there is an initializer.
 *
 * SHADOWING AND REDECLARING
 *   A local may SHADOW a field of the same name (the field is then reached with
 *   this.name or ClassName.name). A local may NOT redeclare another local that
 *   is still in scope.
 *
 * CAPTURING
 *   Lambdas and inner/anonymous classes may use a local only if it is final or
 *   effectively final. They keep a copy, so the value must never change.
 *
 * COMMON MISTAKES
 *   - Using a local before assigning it on every path.
 *   - Using a loop variable after the loop.
 *   - Writing "public int x" inside a method.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Declaration, definite assignment, final locals, var, block scope, shadowing,
 *   per-call copies (recursion), and capture by a lambda.
 * ============================================================================
 */
// Run: java LocalVariable.java   (Java 17+)
public class LocalVariable {

    static int value = 100;                        // a FIELD, used below to show shadowing

    static int factorial(int n) {
        int result;                                // LOCAL: a fresh copy for EVERY call
        if (n <= 1) result = 1;
        else result = n * factorial(n - 1);
        return result;
    }

    static void shadowing() {
        int value = 5;                             // SHADOWS the static field "value"
        System.out.println("local value = " + value + ", field value = " + LocalVariable.value);
    }

    public static void main(String[] args) {
        // WHERE DECLARED: inside a method body
        int count = 5;
        String name = "Ali";
        System.out.println(name + " " + count);

        // NO DEFAULT VALUE: must be assigned before it is read
        int n;
        // System.out.println(n);                  // x error: variable might not have been initialized
        n = 3;
        System.out.println("n = " + n);

        // ACCESS MODIFIERS, STATIC, TRANSIENT, VOLATILE: x not allowed on locals
        // public int a = 1;                       // x
        // static int b = 2;                       // x
        // volatile int c = 3;                     // x

        // FINAL: allowed
        final int MAX = 10;
        // MAX = 11;                               // x cannot assign a value to a final variable
        final int later;                           // blank final: assign exactly once
        later = 42;
        System.out.println(MAX + " " + later);

        // var: the compiler infers the type (String here)
        var city = "Paris";
        System.out.println(city.length());

        // BLOCK SCOPE
        {
            int inner = 1;
            System.out.println("inner = " + inner);
        }
        // System.out.println(inner);              // x out of scope
        for (int i = 0; i < 3; i++) { System.out.print(i + " "); }
        System.out.println();
        // System.out.println(i);                  // x i exists only inside the loop

        // CANNOT REDECLARE a local that is still in scope
        int total = 0;
        // { int total = 1; }                      // x already defined

        // Several locals in one statement
        int a = 1, b = 2, c = a + b;
        System.out.println("c = " + c + ", total = " + total);

        // SHADOWING a field
        shadowing();

        // EACH CALL HAS ITS OWN COPY
        System.out.println("5! = " + factorial(5));

        // EFFECTIVELY FINAL: a lambda may capture it
        int base = 100;
        Runnable r = () -> System.out.println("lambda sees base = " + base);
        r.run();
        // base = 200;                             // x would break "effectively final"
    }
}
