import java.util.ArrayList;
import java.util.List;

public class Airplane {

    class Seat {
        private String seat;
        private boolean reserved;
        private boolean available;

        public Seat(int row, String seat) {
            String rowStr = String.valueOf(row);
            while(rowStr.length() < 2) {
                rowStr = 0 + rowStr;
            }
            this.seat = rowStr + seat;
            this.reserved = false;
            this.available = true;
        }

        @Override
        public String toString() {
            return seat;
        }
    }

    private String code;
    private String name;
    private int rows;
    private int seatsInRow;
    List<Seat> seats = new ArrayList<>();

    public Airplane(String code, String name, int rows, int seatsInRow) {
        this.code = code;
        this.name = name;
        this.rows = rows;
        this.seatsInRow = seatsInRow;

        for (int row = 1; row <= rows; row++) {
            for (int seat = 'A'; seat < (int) 'A' + seatsInRow; seat++) {
                this.seats.add(new Seat(row, String.valueOf((char) seat)));
            }
        }
    }

    public void reserveSeat(int row, String seatLetter) {
        Seat seat = findSeat(new Seat(row, seatLetter));
        seat.reserved = true;
    }

    public void buySeat(int row, String seatLetter) {
        Seat seat = findSeat(new Seat(row, seatLetter));
        seat.available = false;
    }

    public Seat findSeat(Seat seat) {
        for (var s : seats) {
            if (s.seat.equals(seat.seat)) return s;
        }
        return null;
    }

    public void printSeats() {
        int counter1 = 0;
        int counter2 = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsInRow; j++) {
                System.out.print(seats.get(counter1) + " ");
                counter1++;
            }
            System.out.println();
            for (int j = 0; j < seatsInRow; j++) {
                System.out.print(seats.get(counter2).available ? " O  " : " X  ");
                counter2++;
            }
            System.out.println();
        }
    }
}
