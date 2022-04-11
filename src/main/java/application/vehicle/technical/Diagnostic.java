package application.vehicle.technical;

import application.infrastructure.core.Context;
import application.infrastructure.threads.annotations.Schedule;
import application.vehicle.collections.VehicleCollection;

import java.util.List;

public class Diagnostic {

    public Diagnostic() {
    }

    @Schedule(delta = 10000)
    public void makeDiagnostic(Context context) {
        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);
        Workroom workroom = context.getObject(Workroom.class);
        workroom.checkAllVehicle(vehicleCollection.getVehicleList());

    }
}
