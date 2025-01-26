package org.jaswiate;

import java.util.*;

/*
    Klasa kontrolera, który odpowiada za logikę rozwiązania. Przyjmuje listę tras i tworzy
    drzewo przedziałowe, które trzyma aktualny stan floty, oraz uporządkowana hash-mapa (TreeMap<Integer, PlaneSegmentTree>),
    która przechowuje poprzednie zmiany w stanie samolotów na trasach.
 */
public class PlanesController {
    public PlaneSegmentTree actualPlaneRoutes;
    public TreeMap<Integer, PlaneSegmentTree> historyPlaneRoutes = new TreeMap<>();

    public PlanesController(ArrayList<Integer> planeRoutes) {
        this.actualPlaneRoutes = new PlaneSegmentTree(planeRoutes);
        this.historyPlaneRoutes.put(0, new PlaneSegmentTree(actualPlaneRoutes));
    }

    public void P(Integer i, Integer p, Integer t) {
        /*
            Metoda zmieniająca pojemność samolotu i na p od chwili t.
            Metoda działa tylko w momencie, gdy samolot i jest aktywny.
            Po wywołaniu, dodajemy wpis do historii zmian floty jeśli nie ma tam jeszcze wpisu z kluczem t.
            Złożoność czasowa jednego wywołania: O(2logn + n) = O(n) - czas stworzenia nowego drzewa przedziałowego
            w historii zmian floty, gdzie n to ilość samolotów.
        */
        if (this.actualPlaneRoutes.queryCapacity(i, i) == 0) {
            throw new IllegalArgumentException("Plane not assigned to route");
        }
        this.actualPlaneRoutes.updateCapacity(i, p);

        if (historyPlaneRoutes.containsKey(t)) {
            historyPlaneRoutes.get(t).updateCapacity(i, p);
        } else {
            this.historyPlaneRoutes.put(t, new PlaneSegmentTree(actualPlaneRoutes));
        }

    }

    public void C(Integer i, Integer t) {
        /*
            Metoda wycofywująca samolot o numerze i z floty od chwili t.
            Zakładamy, że raz wycofany samolot nie jest już brany pod uwagę w zapytaniach Q.
            Złożoność czasowa jednego wywołania: O(logn) - czas aktualizacji drzewa przedziałowego.
        */
        this.actualPlaneRoutes.updateCapacity(i, 0);
        for (Map.Entry<Integer, PlaneSegmentTree> entry : this.historyPlaneRoutes.entrySet()) {
            entry.getValue().updateCapacity(i, 0);
        }
    }

    public void A(Integer i, Integer p, Integer t) {
        /*
            Metoda przypisująca samolot i do trasy o pojemności p od chwili t.
            Metoda działa tylko w momencie, gdy trasa i nie posiada przypisanego samolotu (został wcześniej wycofany).
            Po wywołaniu, dodajemy wpis do historii zmian floty jeśli nie ma tam jeszcze wpisu z kluczem t.
            Złożoność czasowa jednego wywołania: O(2logn + n) = O(n) - czas stworzenia nowego drzewa przedziałowego
            w historii zmian floty, gdzie n to ilość samolotów.
        */
        if (this.actualPlaneRoutes.queryCapacity(i, i) != 0) {
            throw new IllegalArgumentException("Plane already assigned to route");
        }
        this.actualPlaneRoutes.updateCapacity(i, p);

        if (historyPlaneRoutes.containsKey(t)) {
            historyPlaneRoutes.get(t).updateCapacity(i, p);
        } else {
            this.historyPlaneRoutes.put(t, new PlaneSegmentTree(actualPlaneRoutes));
        }
    }

    public Integer Q(Integer i, Integer j, Integer t) {
        /*
            Metoda służąca do obliczenia sumy pojemności samolotów na trasach od i do j do chwili t:
            iteruje po historii zmian we flocie, zwraca sumę pojemności z odpowiedniego przedziału
            korzystajać z drzewa przedziałowego mnożąc wynik razy ilość dni, przez który utrzymywał się dany stan floty.
            Pesymistyczna złożoność czasowa jednego wywołania: O(q * logn), gdzie q to ilość wywołań P i A,
            a n to ilość samolotów. O(logn) to złożoność jednego query w drzewie przedziałowym.
            Optymistyczna to O(logn), kiedy musimy policzyć tylko jedno query.
        */

        Integer totalCapacity = 0;
        Integer lastT = 0;
        Integer lastCapacity = 0;

        for (Map.Entry<Integer, PlaneSegmentTree> entry : this.historyPlaneRoutes.entrySet()) {
            Integer currentT = entry.getKey();
            Integer currentCapacity = entry.getValue().queryCapacity(i, j);

            if (currentT > t) {
                break;
            }

            totalCapacity += (currentT - lastT) * lastCapacity;
            lastT = currentT;
            lastCapacity = currentCapacity;
        }
        totalCapacity += (t - lastT) * lastCapacity;
        return totalCapacity;
    }

}
