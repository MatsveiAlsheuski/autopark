package application.vehicle;

import java.util.Date;

public class Rent {
    private Date rentDate;
    private double rentCost;

    public Rent() {
    }

    public Rent(Date rentDate, double rentCost) {
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
}
