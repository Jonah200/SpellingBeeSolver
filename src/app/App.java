package app;

public class App {
    public static void main(String[] args) throws Exception {
        Solver solver = new Solver();
        solver.addLetters("b", "nlapoe");
        solver.solve();
        System.out.println("Valid Words: " + solver.getValidWords());
    }
}