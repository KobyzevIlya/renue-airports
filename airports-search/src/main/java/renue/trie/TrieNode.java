package renue.trie;

import java.util.HashMap;
import java.util.Map;

import renue.utils.Pair;

class TrieNode {
    Map<Character, TrieNode> children;
    Pair<Integer, Integer> bytes;

    TrieNode() {
        children = new HashMap<>();
        bytes = null;
    }
}
