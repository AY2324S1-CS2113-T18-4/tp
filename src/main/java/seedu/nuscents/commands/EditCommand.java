package seedu.nuscents.commands;

import seedu.nuscents.data.TransactionList;
import seedu.nuscents.data.transaction.Transaction;

public class EditCommand extends Command {
    private int taskIndex;
    public EditCommand(int taskIndex) { this.taskIndex = taskIndex; }
    @Override
    public void execute(TransactionList tasks) {
        tasks.editTransaction(taskIndex);
    }
}
