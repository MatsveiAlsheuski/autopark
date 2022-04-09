package application.vehicle.parser.impl;

import application.vehicle.Order;
import application.vehicle.Vehicle;
import application.vehicle.parser.ParserBreakingsInterface;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserBreakingsFromFile implements ParserBreakingsInterface {

    public ParserBreakingsFromFile() {
    }

    public Map<String, Integer> detectBreaking(Vehicle vehicle, String[] details) {
        String fileOrders = "src/main/resources/orders.csv";
        Map<String, Integer> map = new HashMap<String, Integer>();
        int count = ((int) (Math.random() * 5));
        if (count != 0) {
            String detail = details[(int) (Math.random() * 8)];
            String breaking = vehicle.getId() + ", " + detail + ", " + count;
            try {
                Files.write(Paths.get(fileOrders), breaking.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(fileOrders), "\n".getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put(detail, count);
        }
        return map;
    }

    public void repair(Vehicle vehicle) {
        File fileOrders = new File("src/main/resources/orders.csv");
        List<Order> listOrders = getListOrders();
        for (int i = listOrders.size() - 1; i >= 0; i--) {
            if (vehicle.getId() == listOrders.get(i).getIdVehicle()) {
                listOrders.remove(i);
                //            System.out.println(appliction.vehicle);                        /** вывод на экран что машина испавлена*/
            }
        }
        try {
            FileWriter fileWriterOrders = new FileWriter(fileOrders, false);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriterOrders);
            for (Order listOrder : listOrders) {
                bufferWriter.write(String.valueOf(listOrder));
                bufferWriter.write("\n");
            }
            bufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getListOrders(){
        List<String> list = readOrders();
        List<Order> listOrders = new ArrayList<>();
        for (String string : list) {
            String[] lines = string.split(", ", 3);
            listOrders.add(new Order(Integer.parseInt(lines[0]),lines[1],Integer.parseInt(lines[2])));
        }
        return listOrders;
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

}
