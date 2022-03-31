package application.vehicle.collections;

import application.infrastructure.core.annotations.Autowired;
import application.vehicle.Color;
import application.vehicle.Rent;
import application.vehicle.Vehicle;
import application.vehicle.VehicleType;
import application.vehicle.engine.AbstractEngine;
import application.vehicle.engine.DieselEngine;
import application.vehicle.engine.ElectricalEngine;
import application.vehicle.engine.GasolineEngine;
import application.vehicle.technical.TechnicalSpecialist;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParserVehicleFromFile {
    @Autowired
    TechnicalSpecialist technicalSpecialist;// = new TechnicalSpecialist();

    public ParserVehicleFromFile() {
    }


    public List<VehicleType> createVehicleTypes(List<VehicleType> vehicleTypeList) {
        return loadTypes(vehicleTypeList);
    }

    public List<Vehicle> createVehicles(List<Vehicle> vehicleList, List<VehicleType> vehicleTypeList) {
        return loadVehicles(vehicleList, vehicleTypeList);
    }

    private List<VehicleType> loadTypes(List<VehicleType> vehicleTypeList) {
        File fileTypes = new File("src/main/resources/" + "types" + ".csv");
        FileReader fileReaderTypes = null;
        try {
            fileReaderTypes = new FileReader(fileTypes);

            BufferedReader bufferedReaderTypes = new BufferedReader(fileReaderTypes);
            String line = null;
            while ((line = bufferedReaderTypes.readLine()) != null) {
                vehicleTypeList.add(createType(line));
            }
            bufferedReaderTypes.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicleTypeList;
    }

    private VehicleType createType(String crvString) {
        double taxCoefficient;
        String[] lines = crvString.split(",", 3);
        taxCoefficient = Double.parseDouble((lines[2].replace(',', '.')).replace('"', ' '));
        return new VehicleType(Integer.parseInt(lines[0]), lines[1], taxCoefficient);
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

    private List<Vehicle> loadVehicles(List<Vehicle> vehicleList, List<VehicleType> vehicleTypeList) {
        File fileVehicles = new File("src/main/resources/" + "vehicles" + ".csv");
        List<VehicleType> vehicleTypes = vehicleTypeList;
        FileReader fileReaderVehicles = null;
        try {
            fileReaderVehicles = new FileReader(fileVehicles);
            BufferedReader bufferedReaderVehicles = new BufferedReader(fileReaderVehicles);
            String line = null;
            while ((line = bufferedReaderVehicles.readLine()) != null) {
                vehicleList.add(createVehicle(line, vehicleTypes));
            }
            bufferedReaderVehicles.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }

    private Vehicle createVehicle(String crvString, List<VehicleType> vehicleTypeList) {
        String[] lines = crvString.split(",", 10);
        Vehicle vehicle = new Vehicle(vehicleTypeList.get(Integer.parseInt(lines[1]) - 1),
                lines[2], lines[3], Integer.parseInt(lines[4]), Integer.parseInt(lines[5]), Integer.parseInt(lines[6]),
                Color.valueOf(lines[7].toUpperCase()), createEngine(lines[8], lines[9]));
        vehicle.setId(Integer.parseInt(lines[0]));
        vehicle.setRents(searchRents(Integer.parseInt(lines[0])));                                      /**переделать*/
        return vehicle;
    }

    private List<Rent> searchRents(int idVehicle) {
        File fileRents = new File("src/main/resources/" + "rents" + ".csv");
        FileReader fileReaderRents = null;
        List<Rent> listRentsVehicle = null;
        try {
            fileReaderRents = new FileReader(fileRents);
            BufferedReader bufferedReaderRents = new BufferedReader(fileReaderRents);
            listRentsVehicle = new ArrayList<>();
            String line = null;
            while ((line = bufferedReaderRents.readLine()) != null) {
                String[] lines = line.split(",", 2);
                if (Integer.parseInt(lines[0]) == idVehicle)
                    listRentsVehicle.add(createRent(line));
            }
            bufferedReaderRents.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return null;//new GasolineEngine(0, 0, 0); /** убрать эту строку*/
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
}
