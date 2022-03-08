package vehicle;

public class VehicleType {

    private int id;
    private String typeName;
    private double taxCoefficient;

    public VehicleType() {
    }

    public VehicleType(int id, String typeName, double taxCoefficient) {
        this.id = id;
        this.typeName = typeName;
        this.taxCoefficient = taxCoefficient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setTaxCoefficient(double taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }

    public String getTypeName() {
        return typeName;
    }

    public double getTaxCoefficient() {
        return taxCoefficient;
    }

    public String display() {
        return "typeName = " + typeName + ",\ntaxCoefficient = " + taxCoefficient;
    }

    public String getString() {
        return typeName + ", " + taxCoefficient;
    }
}
