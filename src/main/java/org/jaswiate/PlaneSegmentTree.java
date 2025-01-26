package org.jaswiate;

import java.util.ArrayList;

/*
    Klasa implementująca strukturę danych drzewa przedziałowego.
    Zastosowanie tej struktury usprawnia liczenie sumy danego przedziału zmniejszając
    pesymistyczną złożoność tej operacji w porównaniu do skorzystania ze zwykłej tablicy z O(n) na O(logn),
    gdzie n to długość listy samolotów.
*/
public class PlaneSegmentTree {
    public Integer[] tree;
    private final ArrayList<Integer> planeRoutes;
    private final int n;

    public PlaneSegmentTree(ArrayList<Integer> planeRoutes) {
        this.planeRoutes = planeRoutes;
        this.n = planeRoutes.size();
        this.tree = new Integer[4 * n];
        build(0, 0, n - 1);
    }

    public PlaneSegmentTree(PlaneSegmentTree planeSegmentTree) {
        this.planeRoutes = null;
        this.n = planeSegmentTree.n;
        this.tree = planeSegmentTree.tree.clone();
    }

    public void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = planeRoutes.get(start);
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;

            build(leftChild, start, mid);
            build(rightChild, mid + 1, end);
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }

    public Integer queryCapacity(int left, int right) {
        return query(0,0, this.n - 1, left, right);
    }

    public Integer query(int node, int start, int end, int left, int right) {
        if (right < start || end < left) {
            return 0;
        }
        if (left <= start && end <= right) {
            return tree[node];
        }

        int mid = (start + end) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        Integer leftSum = query(leftChild, start, mid, left, right);
        Integer rightSum = query(rightChild, mid + 1, end, left, right);
        return leftSum + rightSum;
    }

    public void updateCapacity(int index, int value) {
        update(0, 0, this.n - 1, index, value);
    }

    public void update(int node, int start, int end, int index, int value) {
        if (start == end) {
            tree[node] = value;
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node + 1;
            int rightChild = 2 * node + 2;

            if (index <= mid) {
                update(leftChild, start, mid, index, value);
            } else {
                update(rightChild, mid + 1, end, index, value);
            }
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }

}
