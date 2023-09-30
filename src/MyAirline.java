import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MyAirline {
    public static void main(String[] args) {

        Map<String,Airplane> airplanes = AirlineData.getAirplaneData();
        Map<String,Flight> flights = AirlineData.getFlightData();
        Map<String,Set<Reservation>> reservations = AirlineData.getReservationData();
        boolean appFlag = true;

        while (appFlag) {

            Scanner input = new Scanner(System.in);
            Flight flight = null;
            String flightCode = "";

            printFlightInfo(flights);
            System.out.println("Press Q to quit, or any other key to start the reservation process.");

            if (input.nextLine().equalsIgnoreCase("q")) {
                appFlag = false;
                break;
            }

            while (flight == null) {
                System.out.println("Which flight would you like to book?");
                flightCode = input.nextLine();
                flight = flights.get(flightCode);
                if (flight == null) {
                    System.out.println("Incorrect flight code!");
                }
            }

            Set<Reservation> flightReservations = reservations.get(flight.getFlightCode());
            String airplaneCode = flight.getAirplaneCode();
            Airplane airplane = airplanes.get(airplaneCode);
            airplane.reserveSeats(flightReservations);
            airplane.printSeats();

            boolean flag = true;

            while (flag) {
                System.out.println("Which seat would you like to reserve?");
                String seatInput = input.nextLine();
                String[] seatInputArr = seatInput.split(",");
                try {
                    Reservation reservation = new Reservation(Integer.parseInt(seatInputArr[0]), seatInputArr[1]);
                    Airplane.Seat seat = airplane.findSeat(reservation);
                    if (seat == null) {
                        System.out.println("Incorrect input!");
                        continue;
                    }
                    if (seat.isReserved()) {
                        System.out.println("Seat already reserved!");
                        continue;
                    }
                    airplane.reserveSeat(seat);
                    saveReservation(flightCode, reservation);
                    flag = false;
                } catch (NumberFormatException ex) {
                    System.out.println("Incorrect input!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            airplane.printSeats();

        }
    }

    public static void printFlightInfo(Map<String, Flight> flights) {
        System.out.println(Flight.getFormatString().formatted("FLIGHT", "AIRPLANE", "DEPARTURE", "DESTINATION", "FREQUENCY"));
        flights.forEach((k,v) -> System.out.println(v));
    }

    public static void saveReservation(String flightCode, Reservation reservation) throws IOException {
        FileWriter fileWriter = new FileWriter(AirlineData.getReservationDataFile(), true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("%n%s;%d;%s", flightCode, reservation.getRow(), reservation.getSeat());
        printWriter.close();
    }
}