package renue.search.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import renue.search.Data;
import renue.search.Searchable;
import renue.utils.Pair;

public class HMap implements Searchable {

    private Map<String, Map<String, Pair<Integer, Integer>>> dataMap;

    public HMap() {
        dataMap = new HashMap<>();
    }

    @Override
    public void insert(Data data) {
        String firstLetter = data.getName().substring(0, 1);

        Map<String, Pair<Integer, Integer>> letterMap = dataMap.computeIfAbsent(firstLetter, k -> new HashMap<>());
        while (letterMap.containsKey(data.getName())) {
            data.setName(data.getName() + "*");
        }
        letterMap.put(data.getName(), new Pair<Integer,Integer>(data.getStartByte(), data.getEndByte()));
    }

    @Override
    public List<Pair<Integer, Integer>> search(String prefix) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();

        for (Map<String, Pair<Integer, Integer>> letterMap : dataMap.values()) {
            for (Map.Entry<String, Pair<Integer, Integer>> entry : letterMap.entrySet()) {
                String airportName = entry.getKey();
                if (airportName.startsWith(prefix)) {
                    result.add(entry.getValue());
                }
            }
        }

        return result;
    }
    
}
