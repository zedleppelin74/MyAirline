import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

enum DaysOfWeek {MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY;

    public static DaysOfWeek getDayOfWeek(String day) {
        return switch(day.toUpperCase().substring(0,3)) {
            case "MON" -> DaysOfWeek.MONDAY;
            case "TUE" -> DaysOfWeek.TUESDAY;
            case "WED" -> DaysOfWeek.WEDNESDAY;
            case "THU" -> DaysOfWeek.THURSDAY;
            case "FRI" -> DaysOfWeek.FRIDAY;
            case "SAT" -> DaysOfWeek.SATURDAY;
            case "SUN" -> DaysOfWeek.SUNDAY;
            default -> null;
        };
    }
}
public class Flight {

    private String airplaneCode;
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    Set<DaysOfWeek> frequency = new TreeSet<>();

    public Flight(String airplaneCode, String departure, String destination, String frequency) {
        this.airplaneCode = airplaneCode;
        this.departure = departure;
        this.destination = destination;

        String[] frequencyArr = frequency.split(",");
        Arrays.asList(frequencyArr).forEach(d -> {
            String trimmed = d.trim();
            this.frequency.add(DaysOfWeek.getDayOfWeek(trimmed));
        });
    }

    @Override
    public String toString() {
        return getFormatString().formatted(airplaneCode, departure, destination, frequency);
    }

    public static String getFormatString() {
        return "%-10s %-10s %-15s %s";
    }
}
