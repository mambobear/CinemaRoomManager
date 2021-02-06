package cinema;


import java.util.Scanner;

public class Cinema {

    private static char[][] seats;
    private static int numberOfPurchasedTickets = 0;
    private static int rows;
    private static int cols;
    private static int currentIncome = 0;
    private static int totalIncome;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        cols = scanner.nextInt();

        seats = initSeats(rows, cols);
        totalIncome = calculateProfit();

        while(true) {
            int choice = showMenu(scanner);
            switch (choice) {
                case 1:
                    showLayout();
                    break;
                case 2:
                    if (numberOfPurchasedTickets < rows * cols) {
                        buyTicket(scanner);
                    } else {
                        System.out.println("All tickets sold out");
                    }
                    break;
                case 3:
                    showStatistics();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void showStatistics() {
        System.out.printf("\nNumber of purchased tickets: %d", numberOfPurchasedTickets);

        double percentage = ((double )numberOfPurchasedTickets / (rows * cols)) * 100.0;
        System.out.printf("\nPercentage: %.2f%%", percentage);

        System.out.printf("\nCurrent income: $%d", currentIncome);

        System.out.printf("\nTotal income: $%d\n", totalIncome);
    }

    private static void buyTicket(Scanner scanner) {

        while(true) {
            System.out.println("\nEnter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int col = scanner.nextInt();

            if (row < 0 || row > rows || col < 0 || col > cols) {
                System.out.println("\nWrong input!");
            } else if (isRowPurchased(row, col)) {
                System.out.println("\nThat ticket has already been purchased!");
            } else {
                currentIncome += getPrice(row);
                numberOfPurchasedTickets++;

                System.out.println("\nTicket price: $" + getPrice(row));
                reserveSeat(row, col);
                return;
            }
        }
    }

    private static boolean isRowPurchased(int row, int col) {
        return seats[row - 1][col - 1] == 'B';
    }

    private static int showMenu(Scanner scanner) {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        return scanner.nextInt();
    }

    private static void reserveSeat(int row, int col) {
        seats[row - 1][col - 1] = 'B';
    }

    private static char[][] initSeats(int rows, int cols) {
        char[][] seats = new char[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                seats[row][col] = 'S';
            }
        }
        return  seats;
    }

    public static void showLayout() {
        System.out.println("\nCinema:");
        System.out.print(" ");
        for (int c = 1; c <= cols; c++) {
            System.out.print(" " + c);
        }
        System.out.print("\n");
        for (int row = 0; row < rows; row++) {
            System.out.print(row + 1);
            for (int col = 0; col < cols; col++) System.out.print(" " + seats[row][col]);
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private static int calculateProfit() {

        int profit;
        int capacity = rows * cols;
        if (capacity <= 60) {
            profit = 10 * capacity;
        } else {
            profit = (rows / 2) * cols * 2 + capacity * 8;
        }
        return profit;
//        System.out.println("Total income:\n$" + profit);
    }

    private static int getPrice(int row) {

        int capacity = rows * cols;
        int price;
        if (capacity <= 60) price = 10;
        else price = (row <= rows / 2) ? 10 : 8;
        return price;
    }
}