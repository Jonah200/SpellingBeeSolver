package app;

public class App {
    public static void main(String[] args) throws Exception {
        Solver solver = new Solver();
        solver.addLetters("h", "gwedti");
        solver.solve();
        System.out.println(solver.getValidWords());
    }
}