package vehicle.collections;

import vehicle.engine.AbstractEngine;
import vehicle.engine.DieselEngine;
import vehicle.engine.ElectricalEngine;
import vehicle.engine.GasolineEngine;
import vehicle.Color;
import vehicle.Rent;
import vehicle.Vehicle;
import vehicle.VehicleType;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VehicleCollection/* implements Comparable<vehicle.Vehicle>*/ {
    public List<VehicleType> vehicleTypeList = new ArrayList<>();
    public List<Vehicle> vehicleList = new ArrayList<>();

    public List<VehicleType> loadTypes(String inFile) throws IOException {
        File fileTypes = new File("src/main/resources/" + inFile + ".csv");
        FileReader fileReaderTypes = new FileReader(fileTypes);
        BufferedReader bufferedReaderTypes = new BufferedReader(fileReaderTypes);
        String line = null;
        while ((line = bufferedReaderTypes.readLine()) != null) {
            vehicleTypeList.add(createType(line));
        }
        bufferedReaderTypes.close();
        return vehicleTypeList;
    }

    public VehicleType createType(String crvString) {
        double taxCoefficient;
        String[] lines = crvString.split(",", 3);
        taxCoefficient = Double.parseDouble((lines[2].replace(',', '.')).replace('"', ' '));
        return new VehicleType(Integer.parseInt(lines[0]), lines[1], taxCoefficient);
    }

    public List<Rent> loadRents(String inFile) throws IOException {
        File fileRents = new File("src/main/resources/" + inFile + ".csv");
        FileReader fileReaderRents = new FileReader(fileRents);
        BufferedReader bufferedReaderRents = new BufferedReader(fileReaderRents);
        List<Rent> listRents = new ArrayList<>();
        String line = null;
        while ((line = bufferedReaderRents.readLine()) != null) {
            listRents.add(createRent(line));
        }
        bufferedReaderRents.close();
        return listRents;
    }

    private Rent createRent(String crvString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        String[] lines = crvString.split(",", 3);
        Date date = null;
        try {
            date = formatter.parse(lines[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double rentCost = Double.parseDouble((lines[2].replace(',', '.')).replace('"', ' '));
        return new Rent(date, rentCost);
    }

    public List<Vehicle> loadVehicles(String inFile) throws IOException {
        File fileVehicles = new File("src/main/resources/" + inFile + ".csv");
        FileReader fileReaderVehicles = new FileReader(fileVehicles);
        BufferedReader bufferedReaderVehicles = new BufferedReader(fileReaderVehicles);
        String line = null;
        while ((line = bufferedReaderVehicles.readLine()) != null) {
            vehicleList.add(createVehicle(line));
        }
        bufferedReaderVehicles.close();
        return vehicleList;
    }

    public Vehicle createVehicle(String crvString) throws IOException {
        String[] lines = crvString.split(",", 10);
        Vehicle vehicle = new Vehicle(vehicleTypeList.get(Integer.parseInt(lines[1]) - 1),
                lines[2], lines[3], Integer.parseInt(lines[4]), Integer.parseInt(lines[5]), Integer.parseInt(lines[6]),
                Color.valueOf(lines[7].toUpperCase()), createEngine(lines[8], lines[9]));
        vehicle.setId(Integer.parseInt(lines[0]));
        vehicle.setRents(searchRents("rents", Integer.parseInt(lines[0])));                                                                   /**переделать*/
        return vehicle;
    }

    private List<Rent> searchRents(String inFile, int idVehicle) throws IOException {
        File fileRents = new File("src/main/resources/" + inFile + ".csv");
        FileReader fileReaderRents = new FileReader(fileRents);
        BufferedReader bufferedReaderRents = new BufferedReader(fileReaderRents);
        List<Rent> listRentsVehicle = new ArrayList<>();
        String line = null;
        while ((line = bufferedReaderRents.readLine()) != null) {
            String[] lines = line.split(",", 2);
            if (Integer.parseInt(lines[0]) == idVehicle)
                listRentsVehicle.add(createRent(line));
        }
        bufferedReaderRents.close();
        return listRentsVehicle;
    }


    private AbstractEngine createEngine(String engine, String crvString) {
        if (engine.equals("Gasoline")) {
            int numberForTank = searchNumberForTank(crvString);
            String[] pars = createNumberForEngine(crvString.substring(0, crvString.length() - (numberForTank + 1)));
            return new GasolineEngine(Double.parseDouble(pars[0]), Double.parseDouble(pars[1]),
                    Double.parseDouble(crvString.substring(crvString.length() - numberForTank)));
        }
        if (engine.equals("Diesel")) {
            int numberForTank = searchNumberForTank(crvString);
            String[] pars = createNumberForEngine(crvString.substring(0, crvString.length() - (numberForTank + 1)));

            return new DieselEngine(Double.parseDouble(pars[0]), Double.parseDouble(pars[1]),
                    Double.parseDouble(crvString.substring(crvString.length() - numberForTank)));
        }
        if (engine.equals("Electrical")) {
            String[] pars = crvString.split(",");
            return new ElectricalEngine(Double.parseDouble(pars[0]), Double.parseDouble(pars[1]));
        }
        return new GasolineEngine(0, 0, 0); /** убрать эту строку*/
    }

    private String[] createNumberForEngine(String crvString) {
        String[] pars = crvString.split("\",\"|,\"|\",");
        String[] newPars = new String[pars.length];
        for (int i = 0; i < pars.length; i++) {
            char[] charsText = pars[i].toCharArray();
            for (int j = 0; j < charsText.length; j++) {
                if (charsText[j] == '\"') charsText[j] = ' ';
                if (charsText[j] == ',') charsText[j] = '.';
            }
            newPars[i] = new String(charsText);
        }
        return newPars;
    }

    private int searchNumberForTank(String crvString) {
        int numb = 0;
        char[] charsText = crvString.toCharArray();
        for (int i = charsText.length - 1; i > 0; i--)
            if (charsText[i] >= '0' && charsText[i] <= '9')
                numb++;
            else break;
        return numb;
    }

    public double sumTotalProfit() {
        double sumTotal = 0;
        for (Vehicle vehicle : vehicleList) {
            sumTotal += vehicle.getTotalProfit();
        }
        double scale = Math.pow(10, 2);//Окрушление
        return Math.ceil(sumTotal * scale) / scale;
    }

    public void insert(int index, Vehicle v) {
        try {
            vehicleList.add(index, v);
        } catch (Exception e) {
            vehicleList.add(v);
        }
    }

    public int delete(int index) {
        try {
            vehicleList.remove(index);
            return index;
        } catch (Exception e) {
            return -1;
        }
    }

    public void display() {
        System.out.println(
                "Id    Type     ModelName     Number      Weight(kg)   Year    Mileage    vehicle.Color    Income   Tax   Profit");
        for (Vehicle vehicle : vehicleList) {
            System.out.println(vehicle.getId() + " " + vehicle);
        }
        System.out.println("Total    " + sumTotalProfit());
    }

    public List<Vehicle> getVehicleList(){
        return vehicleList;
    }

/*
    public static void main(String[] args) throws IOException {
        VehicleCollection vehicleCollection = new VehicleCollection();
        vehicleCollection.loadRents("rents");
        vehicleCollection.loadTypes("types");
        vehicleCollection.loadVehicles("vehicles");
        // vehicleCollection.insert(5, new vehicle.Vehicle(new vehicle.VehicleType(2, "Car", 1), "Volkswagen Crafter", "6427 AA-7", 2500, 2014, 227010, vehicle.Color.WHITE, new GasolineEngine(2.18, 8.5, 75)));
        // System.out.println(vehicleCollection.delete(1));
        // vehicleCollection.display();
        //  vehicle.collections.VehicleComparator vehicleComparator = new vehicle.collections.VehicleComparator();
        // Collections.sort(vehicleCollection.vehicleList, vehicleComparator);
        vehicleCollection.display();
    }*/
}



