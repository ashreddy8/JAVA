public class Vehicle {
    
    // Defining the attributes of the Vehicle class
    public String make; // Make of the vehicle
    public String model; // Model of the vehicle
    public String color; // Color of the vehicle
    public double price; // Price of the vehicle 
    public float enginePower; // Engine power in HP
    private int noOfWheels; // Number of wheels
    private int noOfCylinders; // Number of cylinders
    private String mfgCode; // Manufacturing number
    private boolean manual; // Manual transmission or not
    private boolean is_working; // Whether the vehicle is working or not
    private int speed; // Current speed of the vehicle
    private int speedLimit; // Speed limit of the vehicle

    // Getter method for mfgCode
    public String getMfgCode() { 
        return mfgCode;
    }

    // Setter method for mfgCode
    public void setMfgCode(String mfgCode) { 
        this.mfgCode = mfgCode;
    }

    // Getter method for noOfCylinders
    public int getNoOfCylinders() { 
        return noOfCylinders;
    }

    // Setter method for noOfCylinders
    public void setNoOfCylinders(int noOfCylinders) { 
        this.noOfCylinders = noOfCylinders;
    }

    // Setter method for speedLimit
    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    // Default constructor
    public Vehicle() { 
        make = "BMW";
        model = "GT3";
        color = "Black";
        price = 150000.00;
        enginePower = 450.5f;
        noOfWheels = 4;
        noOfCylinders = 6;
        mfgCode = "BMW2024GT3";
    }

    // Parameterized constructors                                                       
    public Vehicle(String bMake, String mName, String c, double p) {                    
        make = bMake;
        model = mName;
        color = c;
        price = p;
    }

    public Vehicle(String c, double p, boolean a) {
        color = c;
        price = p;
        manual = a;
    }

    // Copy constructor
    public Vehicle(Vehicle v) {
        this.make = v.make;
        model = v.model;
        price = v.price;
        color = v.color;
        enginePower = v.enginePower;
        noOfWheels = v.noOfWheels;
        noOfCylinders = v.noOfCylinders;
        mfgCode = v.mfgCode;
        manual = v.manual;
        is_working = v.is_working;
    }


    // Generic methods
    public void drive(int initSpeed) {
    this.speed = initSpeed;
        System.out.println("The vehicle is running at the speed of " + initSpeed + " km/h.");
    }

    public void stop() {
        this.is_working = false;
        System.out.println("The vehicle has been stopped.");

    }

    public void start() {
        this.is_working = true;
        System.out.println("The vehicle is starting.");
    }

    public float calculateMileage(float distance, float fuelConsumed) {
        return distance / fuelConsumed; // Mileage in km/l
    }

    public int changeSpeed(int newSpeed) {
        if (newSpeed > speedLimit) {
            System.out.println("Speed exceeds the limit! Setting speed to speed limit: " + speedLimit + " km/h.");
            this.speed = speedLimit;
        } else {
            this.speed = newSpeed;
            System.out.println("Speed changed to: " + newSpeed + " km/h.");
        }
        return this.speed;
    }

    public void displayInfo() {
        System.out.print("\n\n");
        System.out.println("========== Vehicle Details: ==========");
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Color: " + color);
        System.out.println("Price: $" + price);
        System.out.println("Engine Power: " + enginePower + " HP");
        System.out.println("Number of Wheels: " + noOfWheels);
        System.out.println("Number of Cylinders: " + noOfCylinders);
        System.out.println("Manufacturing Code: " + mfgCode);
        System.out.println("Manual Transmission: " + manual);
        System.out.println("Is Working: " + is_working);
        System.out.println("Current Speed: " + speed + " km/h");
        System.out.println("Speed Limit: " + speedLimit + " km/h");
        System.out.print("\n\n");
    }
}

public class Map<K, V> {
    final K key;
    V value;
    Map<K, V> next;

    public Map(K key, V value, Map<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }
}
