package org.jaswiate;

import java.util.ArrayList;

/*
    Klasa przetwarzająca listę komend i przetwarzająca wyniki za pomocą kontrolera.
*/
public class Solver {
    public final ArrayList<String> commands;
    public final ArrayList<Integer> planeRoutes;
    public final PlanesController planesController;

    public Solver(ArrayList<String> commands, ArrayList<Integer> planeRoutes) {
        this.commands = commands;
        this.planeRoutes = planeRoutes;
        this.planesController = new PlanesController(planeRoutes);
    }

    public void solve() {
        int i; int j; int p; int t;

        for (String command : commands) {
            String[] commandParts = command.split(" ");

            switch (commandParts[0]) {
                case "P":
                    i = Integer.parseInt(commandParts[1]) - 1;
                    p = Integer.parseInt(commandParts[2]);
                    t = Integer.parseInt(commandParts[3]);
                    planesController.P(i, p, t);
                    break;
                case "C":
                    i = Integer.parseInt(commandParts[1]) - 1;
                    t = Integer.parseInt(commandParts[2]);
                    planesController.C(i, t);
                    break;
                case "A":
                    i = Integer.parseInt(commandParts[1]) - 1;
                    p = Integer.parseInt(commandParts[2]);
                    t = Integer.parseInt(commandParts[3]);
                    planesController.A(i, p, t);
                    break;
                case "Q":
                    i = Integer.parseInt(commandParts[1]) - 1;
                    j = Integer.parseInt(commandParts[2]) - 1;
                    t = Integer.parseInt(commandParts[3]);
                    System.out.println(planesController.Q(i, j, t));
                    break;
                default:
                    System.err.println("Unknown command: " + command);
            }
        }
    }
}
