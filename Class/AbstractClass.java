// Run: java AbstractClass.java   (Java 17+)
public class AbstractClass {

    protected abstract class P1 { abstract String name(); }              // PROTECTED (nested)
    class P1Impl extends P1 { String name() { return "protected abstract inner"; } }
    private abstract class P2 { abstract String name(); }                // PRIVATE (nested)
    class P2Impl extends P2 { String name() { return "private abstract inner"; } }
    abstract static class Base { abstract String hi(); }                  // STATIC (nested)
    static class BaseImpl extends Base { String hi() { return "abstract static nested"; } }

    public static void main(String[] args) {
        // new Vehicle();  // x cannot instantiate an abstract class
        AbstractClass o = new AbstractClass();
        System.out.println(o.new P1Impl().name());
        System.out.println(o.new P2Impl().name());
        System.out.println(new BaseImpl().hi());
        System.out.println(new Circle().area());                          // PUBLIC abstract Shape
        System.out.println(new Report().render());                        // PACKAGE-PRIVATE abstract Template
        System.out.println(new Card().pay() + " / " + new Cash().pay());  // abstract + sealed
        System.out.println(new Dog().name() + " is a pet");               // EXTENDS
        System.out.println(new Sparrow().fly());                          // IMPLEMENTS
    }
}

abstract class Shape { abstract double area(); }                          // PUBLIC-style abstract class
class Circle extends Shape { double area() { return 3.14; } }
abstract class Template { String render() { return "template: " + body(); } abstract String body(); }
class Report extends Template { String body() { return "report body"; } }
abstract sealed class Payment permits Card, Cash { abstract String pay(); }
final class Card extends Payment { String pay() { return "card"; } }
non-sealed class Cash extends Payment { String pay() { return "cash"; } }
abstract class Animal { String name() { return "animal"; } }
abstract class Pet extends Animal { }                                     // EXTENDS
class Dog extends Pet { String name() { return "Dog"; } }
interface Flyer { String fly(); }
abstract class Bird implements Flyer { }                                  // IMPLEMENTS (leaves fly() open)
class Sparrow extends Bird { public String fly() { return "sparrow flies"; } }
