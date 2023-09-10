package org.example;


import java.util.Scanner;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] el_pris = new int[24];
        char userSelect;

        do {
            printMainMenu();
            userSelect = (scanner.next().charAt(0));
            scanner.nextLine();

            switch (userSelect) {
                case '1' -> {
                    System.out.print("INMATNING\n");
                    optionInmatning(el_pris);
                }
                case '2' -> {
                    System.out.print("MIN, MAX & MEDEL\n");
                    MinMaxMedel(el_pris);
                }
                case '3' -> {
                    System.out.print("SORTERA\n");
                    printPricesSorted(el_pris);
                }
                case '4' -> {
                    System.out.print("BÄSTA LADDNINGSTID(4h).\n");
                    optimalLaddningsTid(el_pris);
                }
                case 'E', 'e' -> System.out.print("");
                default -> System.out.print("OGILTLIGT ALTERNATIV, FÖRSÖK IGEN\n");
            }
        } while (userSelect != 'e' && userSelect != 'E');
        // nödvändigt?

    }

    public static void printMainMenu() {
        System.out.print("Elpriser\n");
        System.out.print("========\n");
        System.out.print("1. Inmatning\n");
        System.out.print("2. Min, Max och Medel\n");
        System.out.print("3. Sortera\n");
        System.out.print("4. Bästa Laddningstid (4h)\n");
        System.out.print("5. Visualisering\n");
        System.out.print("e. Avsluta\n");

    }

    public static void optionInmatning(int[] el_pris) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Inmatning av elpriser (hela ören per kW/h):\n");
            for (int i = 0; i < el_pris.length; i++) {
                System.out.print("Timmen " + i + "- " + (i + 1) + ": \n");
                el_pris[i] = scanner.nextInt();

            }
            System.out.print("Inmatning av elpriserna har registrerats. \n");
            System.out.print(Arrays.toString(el_pris) + "\n");
        }
        catch(Exception e) {
            System.out.print(e + "\n");
        }

    }

    public static void MinMaxMedel(int[] elpriser) {
        int min = elpriser[0];
        int max = elpriser[0];
        int minHour = 0;
        int maxHour = 0;
        int sum = elpriser[0];

        for (int i = 1; i < elpriser.length; i++) {
            int price = elpriser[i];
            sum += price;

            if (price < min) {
                min = price;
                minHour = i;
            }

            if (price > max) {
                max = price;
                maxHour = i;
            }

        }

        //double average = (double) sum / elpriser.length;

        System.out.print("Lägsta priset är: " + min + " öre (klockan " + minHour + " - " + (minHour + 1) + ")\n");
        System.out.print("Högsta priset är: " + max + " öre (klockan " + maxHour + " - " + (maxHour + 1) + ")\n");
        System.out.print("Medelpriset är: " + sum / elpriser.length + "\n");
    }

    public static void printPricesSorted(int[] elpriser) {
        int[] kopieradePriser = Arrays.copyOf(elpriser, elpriser.length);
        Arrays.sort(kopieradePriser);

        System.out.print("Här är alla elpriser sorterade i ordning, dyrast överst:\n");
        for (int i = elpriser.length - 1; i >= 0; i--) {
            System.out.print("kl. " + i + " - " + (i + 1) + ": " + kopieradePriser[i] + " öre\n");

        }
    }

    public static void optimalLaddningsTid(int[] elpriser) {
        int bestStartingHour = 0;
        int bestEndingHour = 3;
        int sumOfBest = elpriser[0] + elpriser[1] + elpriser[2] + elpriser[3];


        for (int startHour = 1; startHour <= elpriser.length - 4; startHour++) {
            int stopHour = startHour + 3;
            int summa = elpriser[startHour] + elpriser[startHour + 1] + elpriser[startHour + 2] + elpriser[startHour + 3];

            if (summa < sumOfBest) {
                sumOfBest = summa;
                bestStartingHour = startHour;
                bestEndingHour = stopHour;
            }

        }
        int medelpris = sumOfBest / 4;

        System.out.print("Bästa laddningstid är (4h): kl " + bestStartingHour + " - " + bestEndingHour + "\n");
        System.out.print("Medelpris under dessa timmar är: " + medelpris + " öre per kW/h\n");

    }

}

