package application.vehicle.parser.impl;

import application.infrastructure.core.annotations.Autowired;
import application.vehicle.Color;
import application.vehicle.Rent;
import application.vehicle.Vehicle;
import application.vehicle.VehicleType;
import application.vehicle.engine.AbstractEngine;
import application.vehicle.engine.DieselEngine;
import application.vehicle.engine.ElectricalEngine;
import application.vehicle.engine.GasolineEngine;
import application.vehicle.parser.ParserVehicleInterface;
import application.vehicle.technical.TechnicalSpecialist;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParserVehicleFromFile implements ParserVehicleInterface {
    @Autowired
    TechnicalSpecialist technicalSpecialist;// = new TechnicalSpecialist();

    public ParserVehicleFromFile() {
    }

    public List<VehicleType> loadTypes() {
        List<VehicleType> vehicleTypeList = new ArrayList<>();
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
        return new Rent(Integer.parseInt(lines[0]), date, rentCost);
    }

    public List<Vehicle> loadVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        File fileVehicles = new File("src/main/resources/" + "vehicles" + ".csv");
        List<VehicleType> vehicleTypes = loadTypes();
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
        vehicle.setRents(loadRentsVehicle(Integer.parseInt(lines[0])));                                      /**переделать*/
        return vehicle;
    }

    public List<Rent> loadRents() {
        File fileRents = new File("src/main/resources/" + "rents" + ".csv");
        FileReader fileReaderRents = null;
        List<Rent> listRents = null;
        try {
            fileReaderRents = new FileReader(fileRents);
            BufferedReader bufferedReaderRents = new BufferedReader(fileReaderRents);
            listRents = new ArrayList<>();
            String line = null;
            while ((line = bufferedReaderRents.readLine()) != null) {
                listRents.add(createRent(line));
            }
            bufferedReaderRents.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listRents;
    }

    private List<Rent> loadRentsVehicle(int idVehicle) {
        List<Rent> listRentsVehicle = new ArrayList<>();
        List<Rent> listRents = loadRents();
        for (Rent rent : listRents) {
            if (rent.getId() == idVehicle)
                listRentsVehicle.add(rent);
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

    public void save(Vehicle vehicle) {
        String fileVehicles = "src/main/resources/" + "vehicles" + ".csv";
        String vehicles = vehicle.getString();
        try {
            Files.write(Paths.get(fileVehicles), vehicles.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(fileVehicles), "\n".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(VehicleType vehicleType) {
        String fileVehicleType = "src/main/resources/" + "types" + ".csv";
        String types = vehicleType.getString();
        try {
            Files.write(Paths.get(fileVehicleType), types.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(fileVehicleType), "\n".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void save(Rent rent) {
        String fileRents = "src/main/resources/" + "rents" + ".csv";
        String rents = rent.getString();
        try {
            Files.write(Paths.get(fileRents), rents.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(fileRents), "\n".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFromDBToFileOrFromFileToDB() {
      /*  ParserVehicleInterface parserVehicleInterface = new ParserVehicleFromDB();
        for (Rent loadRent : parserVehicleInterface.loadRents()) {
            save(loadRent);
        }
        for (VehicleType loadType : parserVehicleInterface.loadTypes()) {
            save(loadType);
        }

        for (Vehicle loadVehicle : parserVehicleInterface.loadVehicles()) {
            save(loadVehicle);
        }
        System.out.println("Метод saveFromDBToFileOrFromFileToDB() в ParserVehicleFromFile отработал " + this);*/
        System.out.println("Метод saveFromDBToFileOrFromFileToDB() в ParserVehicleFromFile отключена запсь в файл " + this);

    }

}

