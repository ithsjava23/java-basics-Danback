package org.example;
//Daniel
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
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

            switch (userSelect) {
                case '1' -> {
                    System.out.print("INMATNING\n");
                    optionInmatning(el_pris, scanner);
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

    public static void optionInmatning(int[] el_pris, Scanner scanner) {
        try {
            System.out.print("Inmatning av elpriser (hela ören per kW/h):\n");
            if (scanner.hasNext()) {
                for (int i = 0; i < el_pris.length; i++) {
                    System.out.print(i + "-" + (i + 1) + ": \n");
                    el_pris[i] = Integer.parseInt(scanner.next());

                }

            }

            System.out.print("Inmatning av elpriserna har registrerats. \n");
            System.out.print(Arrays.toString(el_pris) + "\n");
        } catch (Exception e) {
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

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        double medelpris = (double) sum / elpriser.length;
        String formattedMedelpris = decimalFormat.format(medelpris);

        if (minHour < 10) {
            System.out.print("Lägsta pris: " + "0" + minHour + "-" + "0" + (minHour + 1) + ", " + min + " öre/kWh" + "\n");
            System.out.print("Högsta pris: " + "0" + maxHour + "-" + "0" + (maxHour + 1) + ", " + max + " öre/kWh" + "\n");
            System.out.print("Medelpris: " + formattedMedelpris + " öre/kWh" + "\n");
        } else {
            System.out.print("Lägsta pris: " + minHour + "-" + (minHour + 1) + ", " + min + " öre/kWh" + "\n");
            System.out.print("Högsta pris: " + maxHour + "-" + (maxHour + 1) + ", " + max + " öre/kWh" + "\n");
            System.out.print("Medelpris: " + formattedMedelpris + " öre/kWh" + "\n");
        }

    }


    public static void printPricesSorted(int[] el_priser) {
        int[][] tidOchPris = new int[24][2];
        for (int i = 0; i < el_priser.length; i++) {
            tidOchPris[i][0] = i;
            tidOchPris[i][1] = el_priser[i];
        }

        Arrays.sort(tidOchPris, (a, b) -> Integer.compare(b[1], a[1]));


        for (int[] ochPris : tidOchPris) {
            int hour = ochPris[0];
            int price = ochPris[1];

            String formattedHour = String.format("%02d", hour);

            System.out.print(formattedHour + "-" + String.format("%02d", hour + 1) + " " + price + " öre\n");
        }
    }


    public static void optimalLaddningsTid(int[] elpriser) {
        int bestStartingHour = 0;
        int sumOfBest = elpriser[0] + elpriser[1] + elpriser[2] + elpriser[3];

        for (int startHour = 1; startHour <= elpriser.length - 4; startHour++) {
            int summa = elpriser[startHour] + elpriser[startHour + 1] + elpriser[startHour + 2] + elpriser[startHour + 3];

            if (summa < sumOfBest) {
                sumOfBest = summa;
                bestStartingHour = startHour;
            }
        }

        double medelpris = (double) sumOfBest / 4;

        DecimalFormatSymbols swedishSymbols = new DecimalFormatSymbols(new Locale("sv", "SE"));
        swedishSymbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("#.##", swedishSymbols);

        String formattedMedelpris = decimalFormat.format(medelpris);

        System.out.print("Påbörja laddning klockan " + bestStartingHour + "\n");
        System.out.print("Medelpris 4h: " + formattedMedelpris + " öre/kWh\n");
    }
}

