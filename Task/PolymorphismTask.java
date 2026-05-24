public class PolymorphismTask {
    // Base Class Vehicle
    abstract static class Vehicle {
        public abstract double speed();
    }

    // Child Class Car
    static class Car extends Vehicle {
        double engineSize;
        double fuelCapacity;

        Car() {}

        Car(double engineSize, double fuelCapacity) {
            this.engineSize = engineSize;
            this.fuelCapacity = fuelCapacity;
        }

        @Override
        public double speed() {
            return engineSize * fuelCapacity * 0.5;
        }
    }

    // Child Class Bike
    static class Bike extends Vehicle {
        double wheelSize;
        double frameWeight;

        Bike() {}

        Bike(double wheelSize, double frameWeight) {
            this.wheelSize = wheelSize;
            this.frameWeight = frameWeight;
        }

        @Override
        public double speed() {
            return (frameWeight / wheelSize) * 10;
        }
    }

    // Child Class Boat
    static class Boat extends Vehicle {
        double hullLength;
        double displacement;

        Boat() {}

        Boat(double hullLength, double displacement) {
            this.hullLength = hullLength;
            this.displacement = displacement;
        }

        @Override
        public double speed() {
            return (displacement / hullLength) * 3;
        }
    }

    public static void main(String[] args) {
        // Polymorphic array of Vehicle
        Vehicle[] vehicles = new Vehicle[3];

        // Storing child class objects
        vehicles[0] = new Car(3.0, 40.0);
        vehicles[1] = new Bike(20.0, 10.0);
        vehicles[2] = new Boat(40.0, 200.0);

        // Dynamic method dispatch
        for (Vehicle v : vehicles) {
            System.out.println(v.getClass().getSimpleName() + " Speed = " + v.speed());
        }
    }
}