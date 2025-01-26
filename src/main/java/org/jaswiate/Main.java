package org.jaswiate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
    By Jakub Świątek
    Rozwiązanie zadania rekrutacyjnego Smart4Aviation.

    Metoda rozwiązania opiera się na zastosowaniu drzewa przedziałowego oraz uporządkowanej hash-mapy.
    Drzewo przedziałowe umożliwia sprawne obliczanie wartości na danym przedziale, natomiast hash-mapa służy do przechowywania
    poprzednich zmian floty samolotów w celu obliczania np. poprzednich ilości miejsc przed operacją P.

    Analiza złożoności:
    * Złożoność pamięciowa rozwiązania to w pesymistycznej wersji O(q * n) -> dla samych wywołań operacji P lub A,
      które tworzą nowe drzewa w mapie. Wtedy możemy mieć q drzew o długości 4n = O(n).

    * Analiza złożoności czasowej nie jest oczywista z racji na zależności między wykonywanymi operacjami. Złożoności
      pojedynczych wywołan poszczególnych operacji są opisane przy ich definicjach, natomiast końcowa złożoność zależy od
      tego jakie operacje i w jakich ilościach były wykonywane.

      Same wywołania Q i C dałyby złożoność O(q * logn), natomiast wywołania P i A to O(q * n), ponieważ stworzenie
      drzewa przedziałowego odbywa się w czasie liniowym.

      Według mojej analizy, najgorszym scenariuszem jest połowa wywołań operacji P i połowa wywołań Q, ponieważ
      funkcja f(x) = -x(x-1) osiąga maksymalną wartość w x = 1/2.
      Złożoność wynosi wtedy O((1/2 * q * n) + (1/2 * q * 1/2 * q * logn)) = O(1/4 * q^2 * logn).
*/

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Provide task input:");

        int n = scanner.nextInt();
        int q = scanner.nextInt();

        ArrayList<Integer> planeRoutes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            planeRoutes.add(scanner.nextInt());
        }

        scanner.nextLine();

        ArrayList<String> commands = new ArrayList<>();
        for (int query = 0; query < q; query++) {
            commands.add(scanner.nextLine());
        }

        Solver solver = new Solver(commands, planeRoutes);
        solver.solve();
    }
}