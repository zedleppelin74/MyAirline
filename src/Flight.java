import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Flight {

    private String flightCode;
    private String airplaneCode;
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    Set<DayOfWeek> frequency = new TreeSet<>();
    Set<Reservation> reservations = new HashSet<>();

    public Flight(String flightCode, String airplaneCode, String departure, String destination,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.flightCode = flightCode;
        this.airplaneCode = airplaneCode;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getAirplaneCode() {
        return airplaneCode;
    }

    public void addReservations(Set<Reservation> reservations) {
        this.reservations.addAll(reservations);
    }

    @Override
    public String toString() {
        return getFormatString().formatted(flightCode, airplaneCode, departure, destination, departureTime, arrivalTime);
    }

    public static String getFormatString() {
        return "%-10s %-10s %-10s %-15s %-20s %s";
    }
}
