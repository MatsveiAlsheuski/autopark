import vehicle.Vehicle;
import vehicle.collections.*;
import vehicle.technical.Fixer;
import vehicle.technical.MechanicService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStreamAPI {
    public static void main(String[] args) throws IOException {
        VehicleCollection vehicleCollection = new VehicleCollection();
        vehicleCollection.loadRents("rents");
        vehicleCollection.loadTypes("types");
        //vehicleCollection.loadVehicles("vehicles");
        //ArrayList<Vehicle> vehicle = (ArrayList<Vehicle>) vehicleCollection.getVehicleList();
        Fixer mechanicService = new MechanicService();

        List<Vehicle> brokenVehicle1 = vehicleCollection.loadVehicles("vehicles").stream()
                //   .filter(x -> !mechanicService.detectBreaking(x).isEmpty())         /**поиск неисправностей и запись их в файл*/
                .filter(x -> mechanicService.isBroken(x))                               /**поиск неисправных машини используя файл,*/
                //.distinct()                                                           /**убирает дубликаты без дубликатов*/
                .collect(Collectors.toList());
        brokenVehicle1.stream().forEach(System.out::println);
        System.out.println();
/**сортировув по полличеству неисправностей*/
        ComparatorByDefectCount comparatorByDefectCount = new ComparatorByDefectCount();
        List<Vehicle> brokenVehicle2 = brokenVehicle1.stream().sorted(comparatorByDefectCount).collect(Collectors.toList());
        brokenVehicle2.stream().forEach(System.out::println);
        System.out.println();
/**сортировув по налогу за месяц*/
        ComparatorByTaxPerMonth comparatorByTaxPerMonth = new ComparatorByTaxPerMonth();
        List<Vehicle> brokenVehicle3 = brokenVehicle1.stream().sorted(comparatorByTaxPerMonth).collect(Collectors.toList());
        brokenVehicle3.stream().forEach(System.out::println);
        System.out.println();
/**найти Volkswagen*/
        List<Vehicle> brokenVehicle4 = brokenVehicle1.stream().filter(x -> x.getModelName().substring(0, 10)
                .equals("Volkswagen")).collect(Collectors.toList());
        brokenVehicle4.stream().forEach(System.out::println);
        System.out.println();
/**найти самый новый Volkswagen*/
        ComparatorByManufactureYear comparatorByManufactureYear = new ComparatorByManufactureYear();
        System.out.println(brokenVehicle4.stream().max(comparatorByManufactureYear).get());
        System.out.println();
/**Логика мойки машин*/
        VehicleCollection vehicleCollection2 = new VehicleCollection();
        vehicleCollection2.loadRents("rents");
        vehicleCollection2.loadTypes("types");
        vehicleCollection2.loadVehicles("vehicles");
        ArrayList<Vehicle> vehicle = (ArrayList<Vehicle>) vehicleCollection2.getVehicleList();
        VehicleQueue queueForWashing = new VehicleQueue(10);

        vehicle.forEach(queueForWashing::enqueue);
        //Stream.of(queueForWashing).map(x -> x.dequeue());
        //Stream.of(queueForWashing).forEach(x -> x.dequeue());
        while (queueForWashing.size() > 0) {
            queueForWashing.dequeue();
        }
        System.out.println();
/**Логика гаража машин*/
        VehicleStack stack = new VehicleStack(10);
       vehicle.forEach(stack::push);
       // vehicle.stream().peek(x->vehicle.forEach(stack::push)).forEach(x -> stack.pop());
        //Stream.of(stack).forEach(x -> x.pop());
        while (stack.size() > 0) {
            stack.pop();
        }
        System.out.println();

    /**ремонт всех машин*/
    //brokenVehicle1.stream().forEach(x->mechanicService.repair(x));
}
}
