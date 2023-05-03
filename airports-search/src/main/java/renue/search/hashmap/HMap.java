package renue.search.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import renue.search.Data;
import renue.search.Searchable;
import renue.utils.Pair;

public class HMap implements Searchable {

    private Map<String, List<HMapNode>> dataMap;

    public HMap() {
        dataMap = new HashMap<>();
    }

    @Override
    public void insert(Data data) {
        String firstLetter = data.getName().substring(0, 1);

        List<HMapNode> dataList = dataMap.computeIfAbsent(firstLetter, k -> new ArrayList<>());
        ;
        while (dataList.stream().anyMatch(d -> d.name.equals(data.getName()))) {
            data.setName(data.getName() + "*");
        }
        dataList.add(new HMapNode(data.getName(), new Pair<Integer, Integer>(data.getStartByte(), data.getEndByte())));
    }

    @Override
    public List<Pair<Integer, Integer>> search(String prefix) {
        if (prefix.isEmpty() || prefix == null) {
            return getAll();
        }

        List<Pair<Integer, Integer>> result = new ArrayList<>();

        String firstLetter = prefix.substring(0, 1);

        if (!dataMap.containsKey(firstLetter)) {
            return result;
        }

        List<HMapNode> letterList = dataMap.get(firstLetter);
        for (HMapNode airportData : letterList) {
            if (airportData.name.startsWith(prefix)) {
                result.add(airportData.bytes);
            }
        }

        return result;
    }

    private List<Pair<Integer, Integer>> getAll() {
        List<Pair<Integer, Integer>> result = new ArrayList<>();

        for (List<HMapNode> letterList : dataMap.values()) {
            for (HMapNode airportData : letterList) {
                result.add(airportData.bytes);
            }
        }

        return result;
    }
}
