package sorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        SortingTool sortingTool = new SortingTool(args);
        sortingTool.sort();
    }

}

class SortingTool {

    private Scanner scanner;
    private String[] commandLineArgs;
    private String dataType = "word";
    private String sortingType = "natural";
    private String inputFileName = "";
    private String outFileName = "";
    private boolean isNatural = false;
    private long total = 0;

    final Set<String> sortTypes = Set.of("natural", "bycount");
    final Set<String> dataTypes = Set.of("word", "long", "line");
    private SortedMap<Object, Integer> values = new TreeMap<>();

    public SortingTool(String[] args) {
        this.commandLineArgs = args;
    }


    private void commandLinePurser() {
        for (int i = 0; i < commandLineArgs.length; i++) {
            if ("-sortingType".equals(commandLineArgs[i])) {
                if (i + 1 >= commandLineArgs.length || !sortTypes.contains(commandLineArgs[i + 1].toLowerCase())) {
                    System.out.println("No sorting type defined!");
                    return;
                }
                sortingType = commandLineArgs[++i];

            } else if ("-dataType".equals(commandLineArgs[i])) {
                if (i + 1 >= commandLineArgs.length || !dataTypes.contains(commandLineArgs[i + 1].toLowerCase())) {
                    System.out.println("No data type defined!");
                    return;
                }
                dataType = commandLineArgs[++i];

            } else if ("-inputFile".equals(commandLineArgs[i])) {
                inputFileName = commandLineArgs[++i];

            } else if ("-outputFile".equals(commandLineArgs[i])) {
                outFileName = commandLineArgs[++i];

            } else {
                System.out.println("\"" + commandLineArgs[i] + "\" isn't a valid parameter. It's skipped.");
            }
        }

        isNatural = "natural".equals(sortingType);
    }


    private void sortingStrategy() throws FileNotFoundException {
        commandLinePurser();

        switch (dataType) {
            case "long":
                sortLongs();
                break;
            case "line":
                sortLines();
                break;
            case "word":
                sortWords();
                break;
        }
    }

    public void sort() throws FileNotFoundException {
        sortingStrategy();
    }

    private void sortWords() throws FileNotFoundException {
        checkForInFile();

        while (scanner.hasNext()) {
            String word = scanner.next();
            values.put(word, values.getOrDefault(word, 0) + 1);
            total++;
        }

        printOrSaveValues(dataOutputFormatter(total, values));

    }

    private void sortLongs() throws FileNotFoundException {
        checkForInFile();

        while (scanner.hasNext()) {
            String myLong = scanner.next();
            if (!myLong.matches("-?\\d+")) {
                System.out.println("\"" + myLong + "\" isn't a long. It's skipped.");
            } else {
                long num = Long.parseLong(myLong);
                values.put(num, values.getOrDefault(num, 0) + 1);
                total += 1;
            }
        }

        printOrSaveValues(dataOutputFormatter(total, values));
    }

    private void sortLines() throws FileNotFoundException {
        checkForInFile();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            values.put(line, values.getOrDefault(line, 0) + 1);
            total += 1;
        }

        printOrSaveValues(dataOutputFormatter(total, values));
    }


    private List<String> dataOutputFormatter(long total, SortedMap<Object, Integer> values) {
        List<String> result = new ArrayList<>();
        boolean isLine = "line".equals(dataType);

        result.add(isNatural ? String.format("Total %s: %s.%n", dataType, total) : String.format("Total words: %s.%n", total));
        if (isNatural) {
            if (isLine) {
                result.add(String.format("Sorted data: %n"));
            } else {
                result.add("Sorted data: ");
            }
        }

        if (isNatural) {
            for (var entry : values.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    String output = entry.getKey() + " ";
                    if (isLine) {
                        output += "\n";
                    }
                    result.add(output);
                }
            }
        } else {
            values.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> result.add(String.format("%s: %d time(s), %d%%%n", entry.getKey(), entry.getValue(), Math.round(entry.getValue() * 100.0 / total))));
        }

        return result;
    }


    private void printOrSaveValues(List<String> values) {
        if (outFileName.isEmpty()) {
            print(values);
        } else {
            saveToFile(values);
        }
        scanner.close();
    }


    private void saveToFile(List<String> values) {
        File file = new File(outFileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String str : values) {
                printWriter.print(str);
            }
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }


    private void print(List<String> values) {
        values.forEach(System.out::print);
    }


    private void checkForInFile() throws FileNotFoundException {
        if (inputFileName.isEmpty()) {
            scanner = new Scanner(System.in);
        } else {
            final File file = new File(inputFileName);
            if (file.isFile()) {
                scanner = new Scanner(file);
            } else {
                System.out.println("File not found!");
            }
        }
    }
}
