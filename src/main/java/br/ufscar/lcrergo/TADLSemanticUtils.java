package br.ufscar.lcrergo;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class TADLSemanticUtils {
    private static List<String> semanticErrors = new ArrayList<>();

    // Add a semantic error
    public static void addSemanticError(Token t, String msg) {
        var line = t.getLine();
        semanticErrors.add(String.format("Line %d: %s", line, msg));
    }

    public static void printSemanticErrors() {
        for (var err : semanticErrors) {
            System.err.println(err);
        }
    }

    public static List<String> getSemanticErrors() {
        return semanticErrors;
    }

    public static Category getCategoryType(String category) {
        Category cat;

        switch(category) {
            case "house-chores":
                cat = Category.HOUSE_CHORES;
                break;
            case "test":
                cat = Category.TEST;
                break;
            case "work":
                cat = Category.WORK;
                break;
            case "homework":
                cat = Category.HOMEWORK;
                break;
            case "event":
                cat = Category.EVENT;
                break;
            case "leisure":
                cat = Category.LEISURE;
                break;
            case "others":
                cat = Category.OTHERS;
                break;

            default:
                cat = Category.INVALID;
        }

        return cat;
    }
}
