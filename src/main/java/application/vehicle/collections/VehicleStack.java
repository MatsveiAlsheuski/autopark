package application.vehicle.collections;

import application.vehicle.Vehicle;

public class VehicleStack {
    private Vehicle[] stack;
    private int maxSize; // максимальное количество элементов стеке
    private int size;  // текущее количество элементов в стеке
    private int front;

    public VehicleStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new Vehicle[maxSize];
        front = -1;
        size = 0;
    }

    public void push(Vehicle elem) {
        if (front < maxSize) {  // циклический перенос
            stack[++front] = elem;  //увеличение Rear и вставка
            size++;  // увеличение количества элементов в очереди
            System.out.println(elem + " - заехала");
        }
    }

    public Vehicle peek() {
        if (size == 0) throw new IllegalStateException();
        return stack[front];
    }

    public Vehicle pop() {
        if (size == 0) throw new IllegalStateException();
        Vehicle temp = stack[front--]; // получаем первый элемент из очереди
        size--; // уменьшаем количество элементов в очереди
        System.out.println(temp + " - выехала");
        return temp;
    }

    public int size() {
        return size;
    }


  /*  public static void main(String[] args) throws IOException {
        VehicleCollection vehicleCollection = new VehicleCollection();
        vehicleCollection.init();
        // vehicleCollection.display();

        VehicleStack garage = new VehicleStack(10);
        ArrayList<Vehicle> appliction.vehicle = (ArrayList<Vehicle>) vehicleCollection.getVehicleList();
        for (Vehicle auto : appliction.vehicle) {
            garage.push(auto);
        }

        while (garage.size() > 0) {
            garage.pop();
        }
    }*/

}
