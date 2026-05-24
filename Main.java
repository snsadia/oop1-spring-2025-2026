abstract class Vehicle {
    protected String brand;

public abstract double calculateFuelEfficiency();
public abstract String toString();

    public Vehicle(String brand) {
        this.brand = brand;
        System.out.println("Vehicle constructor called");
    }

    
    public String getBrand() {
        return brand;
    }
}

class Car extends Vehicle {

    double distanceTravelled;
    double fuelConsumed;

    public Car(String brand, double distanceTravelled, double fuelConsumed) {
        super(brand);
        this.distanceTravelled = distanceTravelled;
        this.fuelConsumed = fuelConsumed;
        System.out.println("Car constructor called");
    }

    @Override
    public double calculateFuelEfficiency() {
        return distanceTravelled / fuelConsumed;
    }

    @Override
    public String toString() {
        return "Car brand is " + getBrand() + " and fuel efficiency is: " + calculateFuelEfficiency() + " km/l";
    }
}

class Motorcycle extends Vehicle {
  
    int engineCapacity;
    double mileage;

    public Motorcycle(String brand, int engineCapacity, double mileage) {
        super(brand);
        this.engineCapacity = engineCapacity;
        this.mileage = mileage;
        System.out.println("Motorcycle constructor called");
    }

    @Override
    public double calculateFuelEfficiency() {
        return mileage; 
    }

    @Override
    public String toString() {
        return "Motorcycle brand is " + getBrand() + " and fuel efficiency is: " + calculateFuelEfficiency() + " km/l";
    }
}

public class Main {
    public static void main(String[] args) {

        Vehicle myCar = new Car("Toyota", 700.0, 50.0);
        Vehicle myMotorcycle = new Motorcycle("Yamaha", 200, 30.5);

    
        System.out.println(myCar.toString());
        System.out.println(myMotorcycle.toString());
    }
}