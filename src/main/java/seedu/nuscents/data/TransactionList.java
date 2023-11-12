package seedu.nuscents.data;

import seedu.nuscents.data.transaction.Allowance;
import seedu.nuscents.data.transaction.Expense;
import seedu.nuscents.data.transaction.Transaction;
import seedu.nuscents.data.transaction.TransactionCategory;
import seedu.nuscents.ui.Ui;

import java.util.ArrayList;


public class TransactionList {
    private static int count = 0;
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

    public static int getTransactionCount() {
        return count;
    }

    public static void increaseTransactionCount() {
        count++;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        count++;
        Ui.showTransactionAddedMessage(transaction);
    }

    public void editTransaction(int transactionIndex, Transaction transaction) {
        transactions.set(transactionIndex-1, transaction);
    }
    public void deleteTransaction(int transactionIndex) {
        Transaction transaction = transactions.get(transactionIndex-1);
        transactions.remove(transaction);
        count--;
        Ui.showTransactionRemovedMessage(transaction);
    }

    public void viewTransaction(int transactionIndex) {
        Transaction transaction = transactions.get(transactionIndex-1);
        Ui.showTransactionViewMessage(transaction);
    }

    public void clearTransactionList() {
        transactions.clear();
        count = 0;
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
        }

        if (isFound) {
            Ui.showFilterMessage(filteredTransactions, category);
        } else {
            Ui.showFilterNotFoundMessage(category);
        }
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getBudget() {
        return this.budget;
    }

    public float getTotalExpense() {
        float totalExpense = 0;
        ArrayList<Transaction> transactions = getTransactions();
        for (Transaction transaction : transactions) {
            int index = transactions.indexOf(transaction) + 1;
            if (transaction instanceof Expense) {
                totalExpense += transaction.getAmount();
            }
        }
        return totalExpense;
    }

    public boolean isWithinBudget() {
        boolean isWithinBudget;
        float budget = getBudget();
        float expense = getTotalExpense();
        if (expense > budget) {
            isWithinBudget = false;
        } else {
            isWithinBudget = true;
        }
        return isWithinBudget;
    }
}
