package br.ufscar.lcrergo;

import java.time.format.DateTimeParseException;
import java.util.List;

import br.ufscar.lcrergo.TADLParser.DatetimeContext;

public class TADLSemantic extends TADLBaseVisitor<Void> {
    private SymbolTable table; 
    private String schName;

    @Override
    public Void visitApplication(TADLParser.ApplicationContext ctx) {
        table = new SymbolTable();
        return super.visitApplication(ctx);
    }
    
    @Override
    public Void visitSchedule(TADLParser.ScheduleContext ctx) {
        if (ctx.RPAREN().getText().contains("missing")) {
            TADLSemanticUtils.addSemanticError(ctx.getStart(), "Parentesis not closed");
        }
        this.schName = ctx.SYMBOL().getText();

        return super.visitSchedule(ctx);
    } 

    @Override
    public Void visitTask(TADLParser.TaskContext ctx) {
        Task task;

        var name  = ctx.SYMBOL(0).getText();
        var description = ctx.STRING(0).getText().replaceAll("\"", "");
        var place = ctx.STRING(1).getText().replaceAll("\"", "");
        var date = ctx.datetime().getText();
        var category = TADLSemanticUtils.getCategoryType(ctx.SYMBOL(1).getText());

        try {
            ctx.RPAREN().getText();
        } catch (NullPointerException e) {
            TADLSemanticUtils.addSemanticError(ctx.getStart(), "Parentesis not closed");
        }

        if (category == Category.INVALID) {
            TADLSemanticUtils.addSemanticError(ctx.getStart(), "Invalid Category");
        }

        try {
            task = new Task(name, description, place, date, category);
            if (table.exists(task.getName())) {
                TADLSemanticUtils.addSemanticError(ctx.getStart(),
                        String.format("Task %s already declared", name));
            } else if (table.exists(task.getDate())) {
                TADLSemanticUtils.addSemanticError(ctx.getStart(),
                        String.format("Day %s already has a task", 
                        task.getDate().toString().replaceAll("'", "")));
            } else {
                table.insert(task);
            }
        } catch (DateTimeParseException | NullPointerException e) {
            TADLSemanticUtils.addSemanticError(ctx.getStart(), "Invalid Task");
        }

        return super.visitTask(ctx);
    }

    @Override
    public Void visitDatetime(DatetimeContext ctx) {
        var day = Integer.parseInt(ctx.MONTH_DAY(0).getText());
        var month = Integer.parseInt(ctx.MONTH_DAY(1).getText());
        var year = Integer.parseInt(ctx.YEAR().getText());

        if (!TADLSemanticUtils.verifyDaysPerMonth(day, month)) {
            TADLSemanticUtils.addSemanticError(ctx.getStart(), "Day out of range");
        }
        if (month <= 0 || month > 12) {
            TADLSemanticUtils.addSemanticError(ctx.getStart(), "Month out of range");
        }
        if (year <= 0) {
            TADLSemanticUtils.addSemanticError(ctx.getStart(), "Year invalid");
        }

        return super.visitDatetime(ctx);
    }

    public SymbolTable getTable() {
        return table;
    }

    public List<Task> getTaskList() {
        return table.getTaskList();
    }

    public String getSchName() {
        return schName;
    }
}
