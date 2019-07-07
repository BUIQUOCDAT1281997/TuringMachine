import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TuringMachine {

    private List<String> indexA;
    private List<String> indexS;

    private DoublyLinkedListImpl<String> tape;


    private String[][] table;

    private void workWithFile(String inputName) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(new File(inputName)));

        String strAlphabet = br.readLine();
        String strStatus = br.readLine();
        if (!strStatus.matches("(.+,)*.+")) {
            throw new Exception("format error");
        }
        indexA = Arrays.asList(strAlphabet.split(","));
        indexS = Arrays.asList(strStatus.split(","));
        table = new String[indexA.size()][indexS.size()];

        String strTape = br.readLine();
        for (int i = 0; i < strTape.length(); i++) {
            tape.addLast(String.valueOf(strTape.charAt(i)));
        }

        String current = br.readLine();
        String[] string;
        while (current != null) {
            if (!current.matches(".+,.+,.+,.+,.+")) {
                throw new Exception("format error");
            }
            string = current.split(",");
            table[indexA.indexOf(string[1])][indexS.indexOf(string[0])] = string[2] + "," + string[3] + "," + string[4];
            current = br.readLine();
        }
        br.close();

    }

    private TuringMachine(String pathName) throws Exception {

        indexA = new ArrayList<>();
        indexS = new ArrayList<>();
        tape = new DoublyLinkedListImpl<>();

        workWithFile(pathName);

    }

    /*
      private void printTable(String[][] strings) {
        for (String[] string : strings) {
            System.out.println(Arrays.toString(string));
        }
    }
     */

    private String trimString(String string) {

        String result = string;

        for (int i = string.length() - 1; i >= 0; i--) {
            if (string.charAt(i) == '_') {
                result = string.substring(0, i);
            } else {
                break;
            }
        }

        for (int j = 0; j < string.length(); j++) {
            if (string.charAt(j) == '_') {
                result = string.substring(j);
            } else {
                break;
            }
        }
        return result;
    }

    private void run() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("output.txt")));
        int pointer = 0;

        int locationOfStatus = 0;
        int locationOfAlphabet;

        boolean stop = false;

        List<String> currentList;

        while (!stop) {
            locationOfAlphabet = indexA.indexOf(tape.get(pointer));

            String nextState = table[locationOfAlphabet][locationOfStatus];

            if (nextState == null) {
                stop = true;
            } else {
                currentList = Arrays.asList(nextState.split(","));

                stop = (currentList.get(2).equals("STOP"));

                tape.set(pointer, currentList.get(0));

                switch (currentList.get(1)) {
                    case "L": {
                        pointer--;
                        if (tape.get(pointer) == null) {
                            tape.addFirst("_");
                        }
                        break;
                    }
                    case "R": {
                        pointer++;
                        if (tape.get(pointer) == null) {
                            tape.addLast("_");
                        }
                        break;
                    }
                }
                locationOfStatus = indexS.indexOf(currentList.get(2));
            }

        }

        String result = "";
        Iterator<String> iterator = tape.iterator();
        while (iterator.hasNext()) {
            result = result.concat(iterator.next());
        }
        bw.write(trimString(result));
        bw.close();
    }

    public static void main(String[] args) {
        try {
            TuringMachine turingMachine = new TuringMachine("input.txt");
            turingMachine.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
