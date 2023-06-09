package cinema;

import java.util.Objects;
import java.util.Scanner;

public class Cinema {
    private final String[][] cinemaGrid;
    private final int rows;
    private final int seats;
    private int cinemaSize;
    private int maxIncome;
    private int[][] priceGrid;
    private int ticketsSold = 0;
    private int currentIncome = 0;


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = Integer.parseInt(input.nextLine());
        System.out.println("Enter the number of seats in each row:");
        int seats = Integer.parseInt(input.nextLine());

        Cinema cinema = new Cinema(seats, rows);

        while (true) {
            System.out.println();
            System.out.print("""
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit
                    """);

            switch (input.nextLine()) {
                case "0":
                    return;
                case "1":
                    System.out.println();
                    cinema.printGrid();
                    break;
                case "2":
                    System.out.println();
                    int wantedSeat = 0;
                    int wantedRow = 0;
                    while (!cinema.buyApproval(wantedSeat, wantedRow)) {
                        System.out.println("Enter a row number:");
                        wantedRow = Integer.parseInt(input.nextLine());
                        System.out.println("Enter a seat number in that row:");
                        wantedSeat = Integer.parseInt(input.nextLine());
                    }
                    cinema.buySeat(wantedSeat, wantedRow);
                    break;
                case "3":
                    System.out.println("\nNumber of purchased tickets: " + cinema.ticketsSold);
                    double ratioSold = (double) cinema.ticketsSold / cinema.cinemaSize;
                    System.out.printf("Percentage : %.2f%%\n", ratioSold * 100);
                    System.out.println("Current income: $" + cinema.currentIncome);
                    System.out.println("Total income: $" + cinema.maxIncome);
                    break;
                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    public Cinema (int seats, int rows) {
        this.rows = rows;
        this.seats = seats;
        this.cinemaGrid = new String[seats + 1][rows + 1];
        this.cinemaGrid[0][0] = " ";
        this.cinemaSize = seats * rows;

        makePriceGrid(seats, rows);

        //Make headers for seats (x-axis) and rows (y-axis)
        for (int x = 1; x <= this.seats; x++) {
            this.cinemaGrid[x][0] = Integer.toString(x);
        }
        for (int y = 1; y <= this.rows; y++) {
            this.cinemaGrid[0][y] = Integer.toString(y);
        }
    }

    public void printGrid() {
        System.out.println("Cinema:");
        for (int y = 0; y <= rows; y++) {
            for (int x = 0; x <= seats; x++) {
                String currValue = cinemaGrid[x][y];
                if (currValue == null) {
                    currValue = "S";
                }
                if (x == seats) {
                    System.out.println(currValue);
                } else {
                    System.out.print(currValue + " ");
                }
            }
        }
    }

    public void makePriceGrid(int seats, int rows) {
        int frontHalf = rows / 2;
        //int backHalf = rows - frontHalf;
        cinemaSize = seats * rows;
        priceGrid = new int[seats + 1][rows + 1];

        if (cinemaSize <= 60) {
            for (int y = 1; y <= rows; y++) {
                for (int x = 1; x <= seats; x++) {
                    priceGrid[x][y] = 10;
                }
            }
            maxIncome = seats * rows * 10;
        } else {
            maxIncome = 0;
            for (int y = 1; y <= rows; y++) {
                for (int x = 1; x <= seats; x++) {
                    if (y <= frontHalf) {
                        priceGrid[x][y] = 10;
                        maxIncome += 10;
                    } else {
                        priceGrid[x][y] = 8;
                        maxIncome += 8;
                    }
                }
            }
        }
    }
    private void buySeat(int wantedSeat, int wantedRow) {
        System.out.println("\nTicket price: $" + priceGrid[wantedSeat][wantedRow]);
        cinemaGrid[wantedSeat][wantedRow] = "B";
        ticketsSold += 1;
        currentIncome += priceGrid[wantedSeat][wantedRow];
    }

    private boolean buyApproval (int wantedSeat, int wantedRow) {
        if (wantedSeat == 0 && wantedRow == 0) {
            return false;
        } else if (wantedSeat > seats || wantedSeat < 1 || wantedRow > rows || wantedRow < 1) {
            System.out.println("Wrong input!\n");
            return false;
        } else if (Objects.equals(cinemaGrid[wantedSeat][wantedRow], "B")) {
            System.out.println("That ticket has already been purchased!\n");
            return false;
        }
        return true;
    }
}