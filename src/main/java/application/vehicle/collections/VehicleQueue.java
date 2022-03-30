package application.vehicle.collections;

import application.vehicle.Vehicle;


public class VehicleQueue {
    private Vehicle[] queue;
    private int maxSize; // максимальное количество элементов в очереди
    private int size;  // текущее количество элементов в очереди
    private int front;
    private int rear;

    public VehicleQueue(int maxSize) {
        this.maxSize = maxSize;
        queue = new Vehicle[maxSize];
        rear = -1;
        front = 0;
        size = 0;
    }

    public void enqueue(Vehicle elem) {
        if (rear == maxSize - 1) {  // циклический перенос
            rear = -1;
        }
        queue[++rear] = elem;  //увеличение Rear и вставка
        size++;  // увеличение количества элементов в очереди
    }

    public Vehicle dequeue() {
        Vehicle temp = queue[front++]; // получаем первый элемент из очереди
        if (temp == null) throw new IllegalStateException();
        if (front == maxSize) { // циклический перенос
            front = 0;
        }
        System.out.println(temp + " - вымыто");
        size--; // уменьшаем количество элементов в очереди
        return temp;
    }

    public Vehicle peek() {
        if (size == 0) throw new IllegalStateException();
        return queue[front + 1];
    }

    public int size() {
        return size;
    }


   /* public static void main(String[] args) {
        VehicleCollection vehicleCollection = new VehicleCollection();
        vehicleCollection.init();
        //  vehicleCollection.display();

        VehicleQueue queueForWashing = new VehicleQueue(10);
        ArrayList<Vehicle> appliction.vehicle = (ArrayList<Vehicle>) vehicleCollection.getVehicleList();

        for (Vehicle auto : appliction.vehicle) {
            queueForWashing.enqueue(auto);
        }
        System.out.println(queueForWashing.peek());
        while (queueForWashing.size() > 0) {
            queueForWashing.dequeue();
        }

    }*/

}
