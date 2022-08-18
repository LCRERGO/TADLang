package br.ufscar.lcrergo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Generator {
    private PrintWriter out;
    private SymbolTable table;
    private String schName;

    public Generator(String fname, SymbolTable table, String schName) throws FileNotFoundException {
        this.table = table;
        this.schName = schName;
        this.out = new PrintWriter(fname);
    }

    public Generator(PrintWriter pw, SymbolTable table, String schName) {
        this.table = table;
        this.schName = schName;
        this.out = pw;
    }

    public void generate() {
        var prefixTag = "<html>";
        var sufixTag = "</html>";

        out.append("<!DOCTYPE html>");
        out.append(prefixTag);
        generateHead();

        generateBody();

        out.append(sufixTag);
        out.close();
    }


    private void generateHead() {
        var prefixTag = "<head>";
        var sufixTag = "</head>";
        String[] heads = {
                "<meta charset='UTF-8'>",
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>",
                "<title>" + schName + "</title>",
        };

        out.append(prefixTag);
        for (var head : heads) {
            out.append(head);
        }
        generateDefaultStyles();
        out.append(sufixTag);
    }


    private void generateDefaultStyles() {
        var prefixTag = "<style>";
        var sufixTag = "</style>";
        String[] styles = {
                "@import url('https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700&family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700&display=swap');",
                "* {",
                "box-sizing: border-box;",
                "font-family: 'Roboto', sans-serif;",
                "}",

                ".schedule-name {",
                "text-align: center;",
                "font-weight: 500;",
                "}",

                ".task-list {",
                "display: flex;",
                "flex-direction: row;",
                "flex-wrap: wrap;",
                "justify-content: space-around;",
                "}",

                ".task {",
                "margin: 2rem;",
                "padding: 1.5rem 2rem;",
                "box-shadow: 0 1rem 2rem 0 rgba(0,0,0,0.2);",
                "border-radius: 0.5rem;",
                "}",

                ".task>h2 {",
                "font-family: 'Lato', sans-serif;",
                "font-weight: 500;",
                "font-size: 2rem;",
                "}",

                ".task > dl > dt {",
                "font-family: 'Lato', sans-serif;",
                "font-weight: 400;",
                "font-size: 1.5rem;",
                "padding: 0;",
                "margin: 0;",
                "}",

                ".task > dl > dd {",
                "font-size: 1rem;",
                "font-weight: 300;",
                "padding: 0;",
                "margin: 0;",
                "}",
        };

        out.append(prefixTag);
        for (var style : styles) {
            out.append(style);
        }
        out.append(sufixTag);
    }

    private void generateBody() {
        var prefixTag = "<body>";
        var sufixTag = "</body>";

        out.append(prefixTag);
        out.append("<h1 class='schedule-name'>" + capitalize(schName) + "</h1>");
        generateTasks();

        out.append(sufixTag);
    }

    private void generateTasks() {
        var prefixTag = "<div class='task-list'>";
        var sufixTag = "</div>";

        out.append(prefixTag);
        for (var entry : table.getTable().entrySet()) {
            Task task = entry.getValue();
            String[] taskStrs = {
                    "<div class='task'>",
                    "<h2>" + capitalize(task.getName().replaceAll("-", " ")) + "</h2>",
                    "<dl>",

                    "<dt>Description</dt>",
                    "<dd>" + task.getDescription() + "</dd>",

                    "<dt>Place</dt>",
                    "<dd>" + task.getPlace() + "</dd>",

                    "<dt>Date</dt>",
                    "<dd>" + task.getDate().toString().replaceAll("'", "") + "</dd>",

                    "<dt>Category</dt>",
                    "<dd>" + capitalize(task.getCategory().toString().toLowerCase().replaceAll("_", " ")) + "</dd>",

                    "</dl>",
                    "</div>",
            };

            for (var taskstr : taskStrs) {
                out.append(taskstr);
            }
        }

        out.append(sufixTag);
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}