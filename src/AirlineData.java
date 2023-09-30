import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AirlineData {
    private static final File airplaneDataFile = new File("airplanes.txt");
    private static final File flightDataFile = new File("flights.txt");
    private static final File reservationDataFile = new File("reservations.txt");
    private static final Scanner airplaneData;

    static {
        try {
            airplaneData = new Scanner(airplaneDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Scanner flightData;

    static {
        try {
            flightData = new Scanner(flightDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Scanner reservationData;

    static {
        try {
            reservationData = new Scanner(reservationDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String,Airplane> getAirplaneData() {

        Map<String,Airplane> airplanes = new HashMap<>();

        while (airplaneData.hasNextLine()) {
            String data = airplaneData.nextLine();
            String[] dataArr = data.split(";");
            airplanes.put(dataArr[0], new Airplane(dataArr[0], dataArr[1], Integer.parseInt(dataArr[2]), Integer.parseInt(dataArr[3])));
        }

        return airplanes;
    }

    public static Map<String,Flight> getFlightData() {

        Map<String,Flight> flights = new HashMap<>();

        while (flightData.hasNextLine()) {
            String data = flightData.nextLine();
            String[] dataArr = data.split(";");
            flights.put(dataArr[0], new Flight(dataArr[0], dataArr[1], dataArr[2], dataArr[3], dataArr[4]));
        }

        return flights;
    }

    public static Map<String, Set<Reservation>> getReservationData() {

        Map<String,Set<Reservation>> reservations = new HashMap<>();

        while (reservationData.hasNextLine()) {
            String data = reservationData.nextLine();
            String[] dataArr = data.split(";");
            reservations.compute(dataArr[0], (k,v) -> {
                if (v == null) v = new HashSet<>();
                v.add(new Reservation(Integer.parseInt(dataArr[1]), dataArr[2]));
                return v;
            });
        }

        return reservations;
    }
}
