package vehicle.technical;

import exception.DefectedVehicleException;
import vehicle.Rent;
import vehicle.Vehicle;
import vehicle.collections.VehicleCollection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MechanicService implements Fixer {
    String[] details = {"Фильтр", "Втулка", "Вал", "Ось", "Свеча", "Масло", "ГРМ", "ШРУС"};

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        //  File fileOrders = new File("src/main/resources/orders.csv");
        String fileOrders = "src/main/resources/orders.csv";
        Map<String, Integer> map = new HashMap<String, Integer>();
        int count = ((int) (Math.random() * 5));
        if (count != 0) {
            String detail = details[(int) (Math.random() * 8)];
            String breaking = vehicle.getId() + ", " + detail + ", " + count;
            try {
                Files.write(Paths.get(fileOrders), breaking.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(fileOrders), "\n".getBytes(), StandardOpenOption.APPEND);
               /* FileWriter fileWriterOrders = new FileWriter(fileOrders, false);
                BufferedWriter bufferWriter = new BufferedWriter(fileWriterOrders);
                bufferWriter.write(breaking);
                bufferWriter.close();*/
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put(detail, count);
        }
        return map;
    }

    @Override
    public void repair(Vehicle vehicle) {
        File fileOrders = new File("src/main/resources/orders.csv");
        List<String> listOrders = readOrders();
        for (int i = listOrders.size() - 1; i >= 0; i--) {
            String[] lines = listOrders.get(i).split(",", 3);
            if (vehicle.getId() == Integer.parseInt(lines[0])) {
                listOrders.remove(i);
                //            System.out.println(vehicle);                        /** вывод на экран что машина испавлена*/
            }
        }
        try {
            FileWriter fileWriterOrders = new FileWriter(fileOrders, false);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriterOrders);
            for (String listOrder : listOrders) {
                bufferWriter.write(listOrder);
                bufferWriter.write("\n");
            }
            bufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isBroken(Vehicle vehicle) {
        List<String> listOrders = readOrders();
        for (String listOrder : listOrders) {
            String[] lines = listOrder.split(", ", 3);
            if (vehicle.getId() == Integer.parseInt(lines[0]))
                return true;
        }
        return false;
    }

    private List<String> readOrders() {
        File fileOrders = new File("src/main/resources/orders.csv");
        List<String> listOrders = new ArrayList<>();
        try {
            FileReader fileReaderOrders = new FileReader(fileOrders);
            BufferedReader bufferedReaderOrders = new BufferedReader(fileReaderOrders);

            String line = null;
            while (true) {
                if (!((line = bufferedReaderOrders.readLine()) != null)) break;
                listOrders.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOrders;
    }

    public Vehicle getMaxBrokeCar(ArrayList<Vehicle> vehicle) {
        Map<Integer, Integer> breakingCar = new HashMap(getMapSumBrokeCar());
        int max = 0;
        for (Map.Entry<Integer, Integer> entry : breakingCar.entrySet()) {
            if (entry.getValue() != null && max < entry.getValue()) max = entry.getValue();
        }
        for (Map.Entry<Integer, Integer> entry : breakingCar.entrySet()) {
            if (entry.getValue() != null && max == entry.getValue()) {
                for (Vehicle vehicle1 : vehicle) {
                    if (entry.getKey() == vehicle1.getId())
                        return vehicle1;
                }
            }
        }
        return null;
    }

    public Map<Integer, Integer> getMapSumBrokeCar() {
        Map<Integer, Integer> breakingCar = new HashMap();
        List<String> listOrders = readOrders();
        for (String st : listOrders) {
            String[] lines = st.split(", ", 3);
            if (breakingCar.get(Integer.parseInt(lines[0])) == null)
                breakingCar.put(Integer.parseInt(lines[0]), Integer.parseInt(lines[2]));
            breakingCar.put(Integer.parseInt(lines[0]), breakingCar.get(Integer.parseInt(lines[0])) + Integer.parseInt(lines[2]));
        }
        return breakingCar;
    }


    public static void main(String[] args) throws IOException, DefectedVehicleException {
        VehicleCollection vehicleCollection = new VehicleCollection();
        vehicleCollection.loadRents("rents");
        vehicleCollection.loadTypes("types");
        vehicleCollection.loadVehicles("vehicles");
        ArrayList<Vehicle> vehicle = (ArrayList<Vehicle>) vehicleCollection.getVehicleList();

        Fixer fixer = new MechanicService();
        MechanicService mechanicService = new MechanicService();
        /**поиск неисправностей и запись их в файл*/
        /*for (Vehicle vehicle1 : vehicle) {
            System.out.println(fixer.detectBreaking(vehicle1));
        }*/
        /**поиск машины с самым большим числом неисправностей*/
        System.out.println(mechanicService.getMaxBrokeCar(vehicle));

        /**ремонт всех неисправных машин*/
        for (Vehicle vehicle1 : vehicle) {
            if (fixer.isBroken(vehicle1)) System.out.println(vehicle1);
            fixer.repair(vehicle1);
        }
        /**ремонт одной машины машины ести она неисправна*/
        fixer.repair(vehicle.get(1));
        System.out.println(mechanicService.arenda(vehicle.get(1)));

    }

    public Vehicle arenda(Vehicle vehicle) throws DefectedVehicleException {
        if (isBroken(vehicle)) throw new DefectedVehicleException("it Brock Car");
        return vehicle;
    }

}

