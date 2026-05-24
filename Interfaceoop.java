// package interfaceoop;
// =========================================================
// Single Java file demonstrating all OOP concepts:
// 1. Encapsulation  -> private fields + public getters/setters
// 2. Inheritance    -> Car & Motorcycle extend Vehicle
// 3. Polymorphism   -> overriding + Vehicle reference to subclass objects
// 4. Abstraction    -> abstract class Vehicle with abstract methods
// 5. Interface      -> Drivable interface implemented by Vehicle
// =========================================================


// ---------- INTERFACE ----------
// An interface defines a contract. Any class that implements it
// must provide implementations for its abstract methods.
interface Drivable {

    // abstract methods (implicitly public and abstract)
    void start();
    void stop();

    // default method — has a body, can be inherited as-is
    default void describe() {
        System.out.println("This is a drivable vehicle.");
    }
}


// ---------- ABSTRACTION ----------
// Abstract class: cannot be instantiated. Used as a base class.
// Notice it also implements the Drivable interface.
abstract class Vehicle implements Drivable {

    // ---------- ENCAPSULATION ----------
    // Field is private — outside classes cannot access it directly.
    // Access is controlled via getter/setter below.
    private String brand;

    // Abstract class can still have a constructor
    public Vehicle(String brand) {
        System.out.println("Vehicle constructor called");
        this.brand = brand;
    }

    // Abstract methods — must be overridden by concrete subclasses
    abstract double calculateFuelEfficiency();

    @Override
    public abstract String toString();

    // Concrete method: getter (encapsulation)
    public String getBrand() {
        return brand;
    }

    // Concrete method: setter (encapsulation)
    public void setBrand(String brand) {
        this.brand = brand;
    }
}


// ---------- INHERITANCE ----------
// Car "is-a" Vehicle, so it extends Vehicle.
class Car extends Vehicle {

    private double distanceTravelled;   // encapsulated field
    private double fuelConsumed;      // encapsulated field


    public Car(String brand, double distanceTravelled, double fuelConsumed ) {
        super(brand);        // call parent constructor
        System.out.println("Car constructor called");
        this.distanceTravelled = distanceTravelled;
        this.fuelConsumed = fuelConsumed;
    }

    // Encapsulation: getter / setter
    public double getdistanceTravelled()              { return distanceTravelled; }
    public void   setdistanceTravelled(double distanceTravelled) { this.distanceTravelled = distanceTravelled; }
    public double getfuelConsumed()              { return fuelConsumed; }
    public void   setfuelConsumed(double fuelConsumed) { this.fuelConsumed = fuelConsumed; }


    // ---------- POLYMORPHISM (method overriding) ----------
    @Override
    double calculateFuelEfficiency() {
        return distanceTravelled / fuelConsumed ;
    }

    @Override
    public String toString() {
        return "Car brand is " + getBrand()
            + "  and fuel efficiency is :"  + calculateFuelEfficiency () + " km/1 ";
    }

    // Implementing the Drivable interface method
    @Override
    public void start() {
        System.out.println("Car " + getBrand() + " engine started" );
    }
    @Override
    public void stop() {
        System.out.println("Car " + getBrand() + "engine stopped");
    }
}


// Another subclass — also demonstrates inheritance & polymorphism
class Motorcycle extends Vehicle {

    private int engineCapacity;
    private double mileage;

    public Motorcycle(String brand, int engineCapacity, double mileage) {
        super(brand);
        System.out.println("Motorcycle constructor called");
        this.engineCapacity = engineCapacity;
        this.mileage  = mileage;
    }

    // Encapsulation: getters / setters
    public int getengineCapacity()              { return engineCapacity; }
    public void setengineCapacity(int engineCapacity) { this.engineCapacity = engineCapacity; }
    public double getmileage()               { return mileage; }
    public void   setmileage(double mileage)   { this.mileage = mileage; }

    @Override
    double calculateFuelEfficiency() {
        return mileage;
    }

    @Override
    public String toString() {
        return "Motorcycle brand is " + getBrand()
                + " and fuel efficiency is : " + calculateFuelEfficiency() + " km/1 ";
    }

    @Override
    public void start() {
        System.out.println("Motorcycle" + getBrand() + " engine started");
    }
    @Override
    public void stop() {
        System.out.println("Motorcycle" + getBrand() + " engine stopped");
    }
}


// ---------- DRIVER CLASS ----------
public class Interfaceoop {
    public static void main(String[] args) {

        // POLYMORPHISM: a Vehicle reference points to a Car / Motorcycle object.
        // The correct overridden method is chosen at runtime (dynamic dispatch).
        Vehicle v1 = new Car("Toyota", 500, 40);
        Vehicle v2 = new Motorcycle("Yamaha", 150, 45.5);

        System.out.println(v1.toString());
        System.out.println(v2.toString());

        // Interface methods called polymorphically
        v1.start();
        v1.stop();
        v2.start();
        v2.stop();

        // Default method inherited from the Drivable interface
        v1.describe();
        v2.describe();

        // Encapsulation in action — change brand via setter, read via getter
        v1.setBrand("Prado");
        System.out.println("Updated " + v1.toString());
    }
}
