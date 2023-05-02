package renue.trie;

import java.util.ArrayList;
import java.util.List;

import renue.utils.Pair;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(Data data) {
        TrieNode current = root;

        for (char c : data.getName().toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }

        if (current.bytes != null) {
            while (current.children.containsKey('\0')) {
                current = current.children.get('\0');
            }
            current.children.put('\0', new TrieNode());
            current = current.children.get('\0');
        }

        current.bytes = new Pair<Integer,Integer>(data.getStartByte(), data.getEndByte());
    }

    public List<Pair<Integer, Integer>> search(String prefix) {
        TrieNode current = root;
        List<Pair<Integer, Integer>> result = new ArrayList<>();

        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return result;
            }
            current = current.children.get(c);
        }

        collect(current, result);

        return result;
    }

    private void collect(TrieNode node, List<Pair<Integer, Integer>> result) {
        if (node.bytes != null) {
            result.add(node.bytes);
        }

        for (TrieNode child : node.children.values()) {
            collect(child, result);
        }
    }
}
