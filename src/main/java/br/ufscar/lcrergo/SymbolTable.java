package br.ufscar.lcrergo;

import java.time.format.DateTimeFormatter;
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

    public boolean exists(DateTimeFormatter date) {
        for (var task : table.values()) {
            var d1 = date.toString().replaceAll("'", "").trim();
            var d2 = task.getDate().toString().replaceAll("'", "").trim();
            if (strEquals(d1, d2)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean strEquals(String str1, String str2) {
        if (str1.length() != str1.length()) {
            return false;
        }

        for (int i = 0; i < str1.length(); i++) {
            if(str1.charAt(i) != str2.charAt(i)) {
                //System.out.printf("Failed at (%c, %c)%n",str1.charAt(i),str2.charAt(i));
                return false;
            }
        }

        return true;

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
