public class Main {
    public static void main(String[] args) {
        try {

            // 1. Циклический сдвиг влево на заданное число разрядов (оба аргумента в двоичном коде)

            /* TuringMachine turingMachine = new TuringMachine("command.txt",
           //         "input.txt", "alphabet.txt");
           turingMachine.run();*/



            // 2. даны два числа (А и В) в двоичной системе счисления, разделенные знаком "-". Вычислить разность (головка находиться в последной клетки- значить 7)
            TuringMachine tM = new TuringMachine("command2.txt",
                    "input2.txt", "alphabet2.txt");
            tM.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
