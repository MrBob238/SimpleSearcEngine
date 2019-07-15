package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class SelectionContext{

    private SearchStrategy strategy;

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public void searchByStrategy(ArrayList<String> dataArray, HashMap<String, LinkedHashSet<Integer>> databaseMap,
                                 String strForSearch) {
        this.strategy.search(dataArray, databaseMap, strForSearch);

    }
}

interface SearchStrategy{

    void search(ArrayList<String> dataArray, HashMap<String, LinkedHashSet<Integer>> databaseMap,
                String strForSearch);
}

class searchByAllStrategy implements SearchStrategy{

    @Override
    public void search(ArrayList<String> dataArray, HashMap<String, LinkedHashSet<Integer>> databaseMap,
                       String strForSearch) {
        String[] queryArray = strForSearch.split(" ");
        LinkedHashSet<Integer> resultOfSearchIndexes = new LinkedHashSet<>();
        LinkedHashSet<Integer> tempList = new LinkedHashSet<>(); //sum of lists with num of words from query.
        for (int i = 0; i < queryArray.length; i++) {
            if (databaseMap.containsKey(queryArray[i])) {
                tempList.addAll(databaseMap.get(queryArray[i]));
            }
        }
        for (Integer numFromTempList : tempList) {
            boolean toADD = true;
            for (int i = 0; i < queryArray.length; i++) {
                if (!databaseMap.get(queryArray[i]).contains(numFromTempList)) {
                    toADD = false;
                    break;
                }
            }
            if (toADD) {
                resultOfSearchIndexes.add(numFromTempList);
            }
        }
        int numOfMatching = resultOfSearchIndexes.size();
        String message = numOfMatching != 0 ? numOfMatching + " persons found: " : "No matching people found.";
        System.out.println(message);
        for (int num : resultOfSearchIndexes) {
            System.out.println(dataArray.get(num));
        }
    }
}

class SearchByANYStrategy implements SearchStrategy{

    @Override
    public void search(ArrayList<String> dataArray, HashMap<String, LinkedHashSet<Integer>> databaseMap,
                       String strForSearch) {
        String[] queryArray = strForSearch.split(" ");
        Set<Integer> resultOfSearchIndexes = new LinkedHashSet<>();
        for (Map.Entry<String, LinkedHashSet<Integer>> pair : databaseMap.entrySet()) {
            for (String word : queryArray) {
                if (pair.getKey().contains(word.toLowerCase().trim())) {
                    for (Integer number : pair.getValue()) {
                        resultOfSearchIndexes.add(number);
                    }
                }
            }
        }
        int numOfMatching = resultOfSearchIndexes.size();
        String message = numOfMatching != 0 ? numOfMatching + " persons found: " : "No matching people found.";
        System.out.println(message);
        for (int num : resultOfSearchIndexes) {
            System.out.println(dataArray.get(num));
        }
    }
}

class SearchByNONEStrategy implements SearchStrategy{

    @Override
    public void search(ArrayList<String> dataArray, HashMap<String, LinkedHashSet<Integer>> databaseMap,
                       String strForSearch) {
        String[] queryArray = strForSearch.split(" ");
        LinkedHashSet<Integer> tempList = new LinkedHashSet<>(); //sum of lists with num of words from query.
        for (int i = 0; i < queryArray.length; i++) {
            if (databaseMap.containsKey(queryArray[i])) {
                tempList.addAll(databaseMap.get(queryArray[i]));
            }
        }
        int numOfMatching = dataArray.size() - tempList.size();
        String message = numOfMatching != 0 ? numOfMatching + " persons found: " : "No matching people found.";
        System.out.println(message);
        for (int i = 0; i < dataArray.size(); i++) {
            if (tempList.contains(i)) {
                continue;
            } else {
                System.out.println(dataArray.get(i));
            }
        }
    }
}

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
    static HashMap<String, LinkedHashSet<Integer>> databaseMap;
    static ArrayList<String> dataArray;

    public static void createDatabaseMap(){
        //createDatabaseArray();
        LinkedHashSet<Integer> indexes;
        int numberOfLine = 0;
        databaseMap = new HashMap<>();
        for (String line : dataArray) {
            for (String word : line.toLowerCase().split(" ")) {
                indexes = databaseMap.get(word) == null ? new LinkedHashSet<>() : databaseMap.get(word);
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
    }

    /**
     * С уровня 5 (устаревший). Выводит строки, в которых есть совпадение с поисковым запросом (даже фрагмент слова).
     * Можно этот метод использовать как default в интерфейсе SearchStrategy.
     * @param dataArray
     * @param databaseMap
     * @param strForSearch
     */
    public static void search(ArrayList<String> dataArray, HashMap<String, LinkedHashSet<Integer>> databaseMap, String strForSearch) {
        Set<Integer> resultOfSearchIndexes = new LinkedHashSet<>();
        for (Map.Entry<String, LinkedHashSet<Integer>> pair : databaseMap.entrySet()) {
            if (pair.getKey().contains(strForSearch.toLowerCase().trim())) {
                for (Integer number : pair.getValue()) {
                    resultOfSearchIndexes.add(number);
                }
            }
        }
        int numOfMatching = resultOfSearchIndexes.size();
        String message = numOfMatching != 0 ? numOfMatching + " persons found: " : "No matching people found.";
        System.out.println(message);

        for (int num : resultOfSearchIndexes) {
            System.out.println(dataArray.get(num));
        }
    }

    public static void menu() {
        createDatabaseArray();
        createDatabaseMap();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\n === Menu === \n" +
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
                    // Начало уровня 6
                    SelectionContext searcher = new SelectionContext();
                    System.out.println("Select a matching strategy: ALL, ANY, NONE");
                    String strStrategy = scanner.nextLine();
                    switch (strStrategy) {
                        case "ANY" :
                            searcher.setStrategy(new SearchByANYStrategy());
                            break;
                        case "ALL" :
                            searcher.setStrategy(new searchByAllStrategy());
                            break;
                        case "NONE" :
                            searcher.setStrategy(new SearchByNONEStrategy());
                            break;
                        default:
                            throw new IllegalArgumentException("Неверная стратегия.");
                            //System.out.println("No such strategy, press correct strategy.");
                    }
                    //Конец уровня 6.
                    //search(dataArray, databaseMap, strForSearch); //Метод уровня 5.
                    System.out.println("Enter a name or email to search all suitable people.");
                    String strForSearch = scanner.nextLine();
                    searcher.searchByStrategy(dataArray, databaseMap, strForSearch);
                    break;
                case 2:
                    System.out.println();
                    System.out.println("=== List of people ===");
                    for (String line : dataArray) {
                        System.out.println(line);
                    }
                    break;
                case 0:
                    System.out.println("Bye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");

            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        filePath = args[1];
        //filePath = "C:\\Hyperskill\\dataset_stage5_tests.txt";
        menu();


    }


}
