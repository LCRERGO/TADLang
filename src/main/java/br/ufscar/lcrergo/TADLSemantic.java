package br.ufscar.lcrergo;

import java.time.format.DateTimeParseException;

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
        schName = ctx.SYMBOL().getText();
        return super.visitSchedule(ctx);
    } 

    @Override
    public Void visitTask(TADLParser.TaskContext ctx) {
        var category = TADLSemanticUtils.getCategoryType(ctx.CATEGORY().getText());
        Task task;

        var name  = ctx.SYMBOL().getText();
        var description = ctx.STRING(0).getText().replaceAll("\"", "");
        var place = ctx.STRING(1).getText().replaceAll("\"", "");
        var date = ctx.datetime().getText();

        if (category == Category.INVALID) 
            TADLSemanticUtils.addSemanticError(ctx.getStart(), "Invalid Category");

        try {
            task = new Task(name, description, place, date, category);
            table.insert(task);
        } catch (DateTimeParseException | NullPointerException e) {
            TADLSemanticUtils.addSemanticError(ctx.getStart(), "Invalid Task");
        }

        return super.visitTask(ctx);
    }

    public SymbolTable getTable() {
        return table;
    }

    public String getSchName() {
        return schName;
    }
}
