public class Main {
    public static void main(String[] args) {
        Vehicle defaultCar = new Vehicle();
        Vehicle ParameterizedCar1 = new Vehicle("Audi", "A6", "White", 1000000.00);
        Vehicle ParameterizedCar2 = new Vehicle("Red", 800000.00, true);
        Vehicle CopyCar = new Vehicle(defaultCar);

        Vehicle[] garage = new Vehicle[] {defaultCar, ParameterizedCar1, ParameterizedCar2, CopyCar};

        for (Vehicle car: garage) {
            car.setSpeedLimit(120);
        }

        for (Vehicle car : garage) {
            car.start();
            car.drive(60);
            car.displayInfo();
            car.stop();
            System.out.println();
        }
    }
}