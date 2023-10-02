import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    public static File getReservationDataFile() {
        return reservationDataFile;
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

        Map<String,Flight> flights = new TreeMap<>();

        while (flightData.hasNextLine()) {

            String data = flightData.nextLine();
            String[] dataArr = data.split(";");

            List<DayOfWeek> frequency = new ArrayList<>();
            for (var day : dataArr[5].split(",")) {
                frequency.add(DayOfWeek.valueOf(day));
            }

            List<LocalDateTime> dates = getFlightDepartures(frequency, Integer.parseInt(dataArr[6]));
            for (var date : dates) {
                String flightCode = "MA" + dataArr[0] + date.getDayOfWeek().ordinal();
                String[] duration = dataArr[4].split(":");
                flights.put(flightCode, new Flight(flightCode, dataArr[1], dataArr[2], dataArr[3],
                        date, date.plusHours(Integer.parseInt(duration[0])).plusMinutes(Integer.parseInt(duration[1]))));
            }
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

    public static List<LocalDateTime> getFlightDepartures(List<DayOfWeek> frequency, int hour) {

        LocalDateTime today = LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0);
        int todayDayOfWeek = today.getDayOfWeek().ordinal();
        List<LocalDateTime> departures = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            for (var day : frequency) {
                if (day == DayOfWeek.values()[i]) {
                    if (i < todayDayOfWeek) {
                        departures.add(today.plusDays(7 - i).truncatedTo(ChronoUnit.MINUTES));
                    } else {
                        if (today.plusDays(i).isBefore(LocalDateTime.now())) {
                            departures.add(today.plusDays(i + 7).truncatedTo(ChronoUnit.MINUTES));
                        } else {
                            departures.add(today.plusDays(i).truncatedTo(ChronoUnit.MINUTES));
                        }
                    }
                }
            }
        }
        Collections.sort(departures);
        return departures;
    }
}
