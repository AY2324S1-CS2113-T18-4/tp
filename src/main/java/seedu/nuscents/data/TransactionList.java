package seedu.nuscents.data;

import seedu.nuscents.data.transaction.Allowance;
import seedu.nuscents.data.transaction.AllowanceCategory;
import seedu.nuscents.data.transaction.Expense;
import seedu.nuscents.data.transaction.ExpenseCategory;
import seedu.nuscents.data.transaction.Transaction;
import seedu.nuscents.data.transaction.TransactionCategory;
import seedu.nuscents.ui.Ui;

import java.util.ArrayList;


public class TransactionList {
    private ArrayList<Transaction> transactions;
    private float budget;

    public TransactionList() {
        transactions = new ArrayList<Transaction>();
    }

    public TransactionList(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        Ui.showTransactionAddedMessage(transaction);
    }

    public void deleteTransaction(int transactionIndex) {
        Transaction transaction = transactions.get(transactionIndex-1);
        transactions.remove(transaction);
        Transaction.decreaseTransactionCountByOne();
        Ui.showTransactionRemovedMessage(transaction);
    }

    public void viewTransaction(int transactionIndex) {
        Transaction transaction = transactions.get(transactionIndex-1);
        Ui.showTransactionViewMessage(transaction);
    }

    public float getNetBalance(){
        float netBalance = 0;
        ArrayList<Transaction> transactions = getTransactions();
        for (Transaction transaction : transactions) {
            int index = transactions.indexOf(transaction) + 1;
            if (transaction instanceof Allowance) {
                netBalance += transaction.getAmount();
            } else if (transaction instanceof Expense) {
                netBalance -= transaction.getAmount();
            }
        }
        return netBalance;
    }

    public void filterTransaction(TransactionCategory category) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        boolean isFound = false;

        for (Transaction transaction : transactions) {
            if (transaction.getCategory() == category) {
                filteredTransactions.add(transaction);
                isFound = true;
            }

            // hacky fix
            if ((transaction.getCategory() == AllowanceCategory.OTHERS) && (category == ExpenseCategory.OTHERS)) {
                filteredTransactions.add(transaction);
                isFound = true;
            }
        }

        if (isFound) {
            Ui.showFilterMessage(filteredTransactions, category);
        } else {
            Ui.showFilterNotFoundMessage(category);
        }
    }

    public void setBudget(float budget) {
        this.budget = budget;
        Ui.showBudgetSet(this);
    }

    public float getBudget() {
        return this.budget;
    }

}

