package application.vehicle;

import application.infrastructure.orm.annotations.Column;

public class Order {
    private int idVehicle;
    private String details;
    private int numberOfBreakdowns;

    public Order() {
    }

    public Order(int idVehicle, String details, int numberOfBreakdowns) {
        this.idVehicle = idVehicle;
        this.details = details;
        this.numberOfBreakdowns = numberOfBreakdowns;
    }

    public int getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getNumberOfBreakdowns() {
        return numberOfBreakdowns;
    }

    public void setNumberOfBreakdowns(int numberOfBreakdowns) {
        this.numberOfBreakdowns = numberOfBreakdowns;
    }

    @Override
    public String toString() {
        return  idVehicle + "," + details + "," + numberOfBreakdowns;
    }
}
