package renue.search.hashmap;

import renue.utils.Pair;

class HMapNode {
    String name;
    Pair<Integer, Integer> bytes;

    HMapNode(String name, Pair<Integer, Integer> bytes) {
        this.name = name;
        this.bytes = bytes;
    }
}
