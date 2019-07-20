public class Main {
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
