import java.io.*;
import java.util.*;

public class TuringMachine {
    private List<String> listAlphabet;
    private List<String> listStatus;
    private DoublyLinkedListImpl<String> tape;
    private HashMap<Pair, String[]> hashMap;
    private String initialState;
    private int pointer;

    private void loadDescription(String inputName) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(new File(inputName)));

        String strAlphabet = br.readLine();
        if (!strAlphabet.matches("(.,)*.")) {
            throw new Exception("format error");
        }
        String strStatus = br.readLine();
        if (!strStatus.matches("(.+,)*.+")) {
            throw new Exception("format error");
        }
        listAlphabet = Arrays.asList(strAlphabet.split(","));
        listStatus = Arrays.asList(strStatus.split(","));

        br.close();
    }

    private void loadCommand(String inputName) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(new File(inputName)));

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

    private void loadTape(String inputName) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(new File(inputName)));

        this.pointer = Integer.parseInt(br.readLine());
        this.initialState = br.readLine();
        String strTape = br.readLine();

        if (listStatus.indexOf(initialState) == -1) {
            throw new Exception("This status is not in the list");
        }

        for (int i = 0; i < strTape.length(); i++) {
            tape.addLast(String.valueOf(strTape.charAt(i)));
        }
        /*
        if (position < 0 || position >= strTape.length()) {
            throw new Exception("cursor position error");
        }
         */
        if (pointer < 0) {
            int count = -1;
            while (count != pointer) {
                tape.addFirst("_");
                count--;
            }
            tape.addFirst("_");
        } else if (pointer >= strTape.length()) {
            int count = strTape.length();
            while (count != pointer) {
                tape.addLast("_");
                count++;
            }
            tape.addLast("_");
        }

        br.close();
    }

    public TuringMachine(String command, String input, String alphabet) throws Exception {
        this.listAlphabet = new ArrayList<>();
        this.listStatus = new ArrayList<>();
        this.tape = new DoublyLinkedListImpl<>();
        this.hashMap = new HashMap<>();

        loadDescription(alphabet);
        loadCommand(command);
        loadTape(input);
    }

    @Override
    public String toString() {
        String x = tape.get(pointer);
        tape.set(pointer, "[" + x + "]");
        String result = tape.toString();
        tape.set(pointer, x);
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

    public void run() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("process.txt")));
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File("result.txt")));

        boolean stop = false;
        Pair<String, String> currentPair = new Pair<>(initialState, tape.get(pointer));
        while (!stop) {
            String[] nextState = hashMap.get(currentPair);
            bw.write("command : " + Arrays.toString(nextState));
            bw.newLine();

            if (nextState == null) {
                stop = true;
                System.out.println("khong du trang thai");
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

        bw2.write("RESULT : " + this.toString());
        bw.close();
        bw2.close();
    }

    public static void main(String[] args) {
        try {
            TuringMachine turingMachine = new TuringMachine("command.txt",
                    "input.txt", "alphabet.txt");
            turingMachine.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
