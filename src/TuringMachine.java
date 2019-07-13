import java.io.*;
import java.util.*;

public class TuringMachine {
    private List<String> listAlphabet;
    private List<String> listStatus;
    private DoublyLinkedListImpl<String> tape;
    private HashMap<Pair, String[]> hashMap;

    private void workWithFile(String inputName) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(new File(inputName)));

        String strAlphabet = br.readLine();
        String strStatus = br.readLine();
        if (!strStatus.matches("(.+,)*.+")) {
            throw new Exception("format error");
        }
        listAlphabet = Arrays.asList(strAlphabet.split(","));
        listStatus = Arrays.asList(strStatus.split(","));

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
            if (listStatus.indexOf(string[0]) == -1 && listStatus.indexOf(string[4]) == -1) {
                throw new Exception("This status is not in the list");
            }
            if (listAlphabet.indexOf(string[1]) == -1 && listAlphabet.indexOf(string[2]) == -1) {
                throw new Exception("This character is not in the list");
            }
            if (!string[3].equals("L") && !string[3].equals("R") && !string[3].equals("T")) {
                throw new Exception("format error");
            }

            hashMap.put(new Pair<>(string[0], string[1]), string);
            current = br.readLine();
        }
        br.close();
    }

    private TuringMachine(String pathName) throws Exception {
        this.listAlphabet = new ArrayList<>();
        this.listStatus = new ArrayList<>();
        this.tape = new DoublyLinkedListImpl<>();
        this.hashMap = new HashMap<>();

        workWithFile(pathName);
    }

    @Override
    public String toString() {
        String result = tape.toString();
        int count1 = 0;
        int count2 = 0;

        for (int i = result.length() - 1; i >= 0; i--) {
            if (result.charAt(i) == '_') {
                count2++;
            } else {
                break;
            }
        }

        for (int j = 0; j < result.length(); j++) {
            if (result.charAt(j) == '_') {
                count1++;
            } else {
                break;
            }
        }
        return result.substring(count1, result.length() - count2);
    }

    private void run() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("output.txt")));

        int pointer = 0;
        boolean stop = false;
        Pair<String, String> currentPair = new Pair<>(listStatus.get(0), tape.get(pointer));
        while (!stop) {
            String[] nextState = hashMap.get(currentPair);
            bw.write("command : " + Arrays.toString(nextState));
            bw.newLine();

            if (nextState == null) {
                stop = true;
            } else {
                stop = (nextState[4].equals("STOP"));
                tape.set(pointer, nextState[2]);
                switch (nextState[3]) {
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
            }
            bw.write(this.toString());
            bw.newLine();
            assert nextState != null;
            currentPair = new Pair<>(nextState[4], tape.get(pointer));
        }

        bw.write("RESULT : " + this.toString());
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
