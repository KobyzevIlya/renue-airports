package renue.search;

import java.util.List;

import renue.utils.Pair;

public interface Searchable {
    public void insert(Data data);
    public List<Pair<Integer, Integer>> search(String prefix);
}
