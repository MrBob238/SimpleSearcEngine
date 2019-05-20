package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static final String NAMES =
            "Kristofer Galley\n" +
                    "Fernando Marbury fernando_marbury@gmail.com\n" +
                    "Kristyn Nix nix-kris@gmail.com\n" +
                    "Regenia Enderle\n" +
                    "Malena Gray\n" +
                    "Colette Mattei\n" +
                    "Wendolyn Mcphillips\n" +
                    "Jim Gray\n" +
                    "Coreen Beckham\n" +
                    "Bob Yeh bobyeah@gmail.com\n" +
                    "Shannan Bob stropeshah@gmail.com\n" +
                    "Yer Fillion\n" +
                    "Margene Resendez marres@gmail.com\n" +
                    "Blossom Ambler\n" +
                    "Teri Ledet teri_ledet@gmail.com\n" +
                    "Dana Baron baron@gmail.com\n" +
                    "Abram Goldsberry\n" +
                    "Yer Leopold\n" +
                    "Stefania Trunzo\n" +
                    "Alexis Leopold\n" +
                    "Carlene Bob\n" +
                    "Oliver Dacruz\n" +
                    "Jonie Richter\n" +
                    "Pasquale Gallien gallien@evilcorp.com\n" +
                    "Verdie Gentle\n" +
                    "Gerardo Strouth gallien@evilcorp.com\n" +
                    "Agripina Bob\n" +
                    "Latricia Niebuhr\n" +
                    "Malena Schommer\n" +
                    "Drema Leopold\n" +
                    "Heide Payeur\n" +
                    "Ranae Digiovanni\n" +
                    "Simona Pereira\n" +
                    "Nick Digiovanni\n" +
                    "Angelita Wigington gallien@evilcorp.com\n" +
                    "Elin Gray\n" +
                    "Dwain Trunzo\n" +
                    "Boris Beiler\n" +
                    "Remi Malek fsociefy@gmail.com\n" +
                    "Demetria Hostetler gallien@evilcorp.com\n" +
                    "Nydia Mcduffie\n" +
                    "Florencio Defibaugh\n" +
                    "Warner Giblin\n" +
                    "Bob Mans\n" +
                    "Shu Gray\n" +
                    "Kaycee Gray\n" +
                    "Victorina Froehlich victory@gmail.com\n" +
                    "Roseanne Gray\n" +
                    "Erica Radford hisam@gmail.com\n" +
                    "Elyse Pauling";
    static String filePath;
    static HashMap<String, ArrayList<Integer>> databaseMap;
    static ArrayList<String> dataArray;

    public static void createDatabaseMap(){
        //createDatabaseArray();
        ArrayList<Integer> indexes;
        int numberOfLine = 0;
        databaseMap = new HashMap<>();
        for (String line : dataArray) {
            for (String word : line.toLowerCase().split(" ")) {
                indexes = databaseMap.get(word) == null ? new ArrayList<>() : databaseMap.get(word);
                indexes.add(numberOfLine);
                databaseMap.put(word,indexes);
            }
            numberOfLine++;
        }
    }

    public static void createDatabaseArray() {
        File file = new File(filePath);
        dataArray = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                dataArray.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        /*
        //Начало неправильного варианта.
        for (String line : NAMES.split("\n")) {
            dataArray.add(line);
        }
        //Конец неправильного варианта.

         */


    }
    public static void search(ArrayList<String> dataArray, String strForSearch) {
        for (String line : dataArray) {
            if (line.toLowerCase().contains(strForSearch.toLowerCase().trim())) {
                System.out.println(line);
            }
        }
        System.out.println();
    }
    public static void search(ArrayList<String> dataArray, HashMap<String, ArrayList<Integer>> databaseMap, String strForSearch) {
        int numOfMatching = 0;
        for (Map.Entry<String, ArrayList<Integer>> pair : databaseMap.entrySet()) {
            if (pair.getKey().contains(strForSearch.toLowerCase().trim())) {
                numOfMatching += pair.getValue().size();
            }
        }
        String message = numOfMatching != 0 ? numOfMatching + " persons found: " : "No matching people found.";
        System.out.println(message);
        for (Map.Entry<String, ArrayList<Integer>> pair : databaseMap.entrySet()) {
            if (pair.getKey().contains(strForSearch.toLowerCase().trim())) {
                for (Integer number : pair.getValue()) {
                    System.out.println(dataArray.get(number));
                }
            }
        }
        System.out.println();
    }

    public static void menu() {
        createDatabaseArray();
        createDatabaseMap();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Menu === \n" +
                    "1. Find a person \n" +
                    "2. Print all people \n" +
                    "0. Exit");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("The number of menu item must be number!");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println();
                    System.out.println("Enter a name or email to search all suitable people.");
                    String strForSearch = scanner.nextLine();
                    search(dataArray, databaseMap, strForSearch);
                    break;
                case 2:
                    System.out.println();
                    System.out.println("=== List of people ===");
                    for (String line : dataArray) {
                        System.out.println(line);
                    }
                    //System.out.println();
                    break;
                case 0:
                    System.out.println("Bye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");

            }
        }
    }

    public static void main(String[] args) {
        filePath = args[1];
        //System.out.println(NAMES);
        menu();


    }


}
