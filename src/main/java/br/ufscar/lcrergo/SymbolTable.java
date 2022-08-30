package br.ufscar.lcrergo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Task> table;

    public SymbolTable() {
        table = new HashMap<>();
    }

    public void insert(Task t) {
        table.put(t.getName(), t);
    }

    public boolean exists(String key) {
        return table.containsKey(key);
    }

    public Category verify(String key) {
        return table.get(key).getCategory();
    }

    public Task getTask(String key) {
        return table.get(key);
    }

    public Map<String, Task> getTable() {
        return table;
    }

    public List<Task> getTaskList() {
        return new ArrayList<Task>(table.values());
    }

    public String toString() {
        String ret = "{"; 
        for (var entry : table.entrySet()) 
            ret += String.format("%n%s,", entry.getValue());

        ret += "\n}";

        return ret;
    }
}
