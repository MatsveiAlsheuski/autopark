package application.vehicle;

import java.util.Date;

public class Rent {
    private int id;
    private Date rentDate;
    private double rentCost;

    public Rent() {
    }

    public Rent(int id, Date rentDate, double rentCost) {
        this.id = id;
        this.rentDate = rentDate;
        this.rentCost = rentCost;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public double getRentCost() {
        return rentCost;
    }

    public void setRentCost(double rentCost) {
        this.rentCost = rentCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getString() {

        if (rentCost % 1 == 0)
            return id + "," + rentDate + "," + rentCost;
        return id + "," + rentDate + ",\"" + rentCost + "\"";
    }
}
