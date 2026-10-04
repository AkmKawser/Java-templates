// Run: java ConcreteClass.java   (Java 17+)
public class ConcreteClass {                       // PUBLIC top-level concrete class

    protected class P1 { String hi() { return "protected nested class"; } }  // PROTECTED (nested only)
    private class P2 { String hi() { return "private nested class"; } }      // PRIVATE (nested only)
    static class Helper { String hi() { return "static nested class"; } }    // STATIC (nested only)

    public static void main(String[] args) {
        System.out.println(new Engine().hi());                 // package-private
        ConcreteClass outer = new ConcreteClass();
        System.out.println(outer.new P1().hi());
        System.out.println(outer.new P2().hi());
        System.out.println(new Helper().hi());
        System.out.println("final class constant: " + Constants.MAX);
        System.out.println("sealed Base -> " + new Child().name());
        System.out.println("extends: " + new Dog().sound());
        Athlete a = new Athlete();
        System.out.println("implements: " + a.run() + ", " + a.swim());
        // ABSTRACT: adding it would make this an abstract class (different kind)
        // protected class X {}  private class Y {}  static class Z {}   // x top-level
    }
}

class Engine { String hi() { return "package-private class"; } }   // PACKAGE-PRIVATE
final class Constants { static final int MAX = 5; }                  // FINAL
sealed class Base permits Child { String name() { return "Base"; } } // SEALED
final class Child extends Base { String name() { return "Child"; } }
class Animal { String sound() { return "..."; } }                    // EXTENDS one class
class Dog extends Animal { String sound() { return "Woof"; } }
interface Runner { default String run() { return "running"; } }
interface Swimmer { default String swim() { return "swimming"; } }
class Athlete implements Runner, Swimmer { }                         // IMPLEMENTS many
