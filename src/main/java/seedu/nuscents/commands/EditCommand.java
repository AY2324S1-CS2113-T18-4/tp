package seedu.nuscents.commands;

import seedu.nuscents.data.TransactionList;

public class EditCommand extends Command {
    private int taskIndex;
    public EditCommand(int taskIndex) { this.taskIndex = taskIndex; }
    @Override
    public void execute(TransactionList tasks) {
        tasks.editTransaction(taskIndex);
    }
}
