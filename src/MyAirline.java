import java.util.Map;

public class MyAirline {
    public static void main(String[] args) {
        Airplane airplane = new Airplane("BE747", "Boeing 747", 32, 6);
        airplane.buySeat(3,"A");
        airplane.buySeat(32,"B");
        airplane.buySeat(32,"F");
        airplane.printSeats();

        Map<String,Airplane> airplanes = AirlineData.getAirplaneData();
        Map<String,Flight> flights = AirlineData.getFlightData();

        printFlightInfo(flights);
    }

    public static void printFlightInfo(Map<String, Flight> flights) {
        System.out.println(Flight.getFormatString().formatted("AIRPLANE", "DEPARTURE", "DESTINATION", "FREQUENCY"));
        flights.forEach((k,v) -> System.out.println(v));
    }
}