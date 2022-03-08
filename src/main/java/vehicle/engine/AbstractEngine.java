package vehicle.engine;

public abstract class AbstractEngine implements Startable {

    String nameEngine;
    double taxPerMonth;

    public AbstractEngine(String nameEngine, double taxPerMonth) {
        this.nameEngine = nameEngine;
        this.taxPerMonth = taxPerMonth;
    }

    @Override
    public double getTaxPerMonth() {
        return taxPerMonth;
    }


    @Override
    public abstract double getMaxKilometers();

    public String getNameEngine() {
        return nameEngine;
    }

    public void setNameEngine(String nameEngine) {
        this.nameEngine = nameEngine;
    }

    public void setTaxPerMonth(int taxPerMonth) {
        this.taxPerMonth = taxPerMonth;
    }

    @Override
    public String toString() {
        return nameEngine + ", " + taxPerMonth;
    }
}
