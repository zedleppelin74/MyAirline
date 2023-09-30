public class Reservation {

//    private String flight;
    private int row;
    private String seat;

    public Reservation(int row, String seat) {
        this.row = row;
        this.seat = seat;
    }

    public int getRow() {
        return row;
    }

    public String getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Row: %-5d, Seat: %s".formatted(row, seat);
    }
}
