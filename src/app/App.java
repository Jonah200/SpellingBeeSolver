package app;

public class App {
    public static void main(String[] args) throws Exception {
        Solver solver = new Solver();
        solver.addLetters("i", "cteovn");
        solver.solve();
        System.out.println(solver.getValidWords());
    }
}