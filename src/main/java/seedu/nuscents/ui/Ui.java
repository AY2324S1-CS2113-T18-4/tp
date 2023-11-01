package seedu.nuscents.ui;

import seedu.nuscents.data.TransactionList;
import seedu.nuscents.data.transaction.Allowance;
import seedu.nuscents.data.transaction.AllowanceCategory;
import seedu.nuscents.data.transaction.Expense;
import seedu.nuscents.data.transaction.ExpenseCategory;
import seedu.nuscents.data.transaction.Transaction;
import seedu.nuscents.data.transaction.TransactionCategory;

import java.util.ArrayList;
import java.util.Scanner;

import static seedu.nuscents.ui.Messages.MESSAGE_EXIT;
import static seedu.nuscents.ui.Messages.LINE;
import static seedu.nuscents.ui.Messages.LOGO;
import static seedu.nuscents.ui.Messages.MESSAGE_EMPTY_LIST;

public class Ui {
    private final Scanner input;

    public Ui() {
        this.input = new Scanner(System.in);
    }

    public String getUserCommand() {
        return input.nextLine();
    }

    public static void showLine() {
        System.out.println(LINE);
    }

    public static void showWelcomeMessage() {
        System.out.println("Hello from\n" + LOGO);
        System.out.println(LINE);
        System.out.println("What can I do for you?");
        System.out.println("Hint: To view a list of all possible commands, please enter 'help'.");
        System.out.println(LINE);
    }

    public static void showGoodbyeMessage() {
        System.out.println(LINE);
        System.out.println(MESSAGE_EXIT);
        System.out.println(LINE);
    }

    public void showException(Exception e) {
        System.out.println(LINE);
        System.out.println(e.getMessage());
        System.out.println(LINE);
    }

    public static void showTransactionCount( ) {
        System.out.println("Now you have " + Transaction.getTransactionCount() + " transactions in the list.");
    }

    public static void showTransactionAddedMessage(Transaction transaction) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this transaction:");
        System.out.println("  " + transaction.getDescription());
        showTransactionCount();
        System.out.println(LINE);
    }

    public static void showTransactionRemovedMessage(Transaction transaction) {
        System.out.println(LINE);
        System.out.println("Noted. I've removed this transaction:");
        System.out.println("  " + transaction.getDescription());
        showTransactionCount();
        System.out.println(LINE);
    }

    public static void showTransactionList(TransactionList transactionList) {
        System.out.println(LINE);
        if (transactionList.getTransactions().isEmpty()) {
            System.out.println(MESSAGE_EMPTY_LIST);
            System.out.println(LINE);
            return;
        }
        assert transactionList.getTransactions() != null;
        System.out.println("Here are the transactions in your list:");
        System.out.println(LINE);
        System.out.printf("%-5s  %-10s  %-7s  %-18s  %-15s  %-10s  %-5s %n",
                "S/N", "TYPE", "AMOUNT", "DATE", "DESCRIPTION", "NOTE", "CATEGORY");
        System.out.println(LINE);
        ArrayList<Transaction> transactions = transactionList.getTransactions();
        for (Transaction transaction : transactions) {
            int index = transactions.indexOf(transaction) + 1;
            String additionalInfo = transaction.getAdditionalInfo();
            String note;
            if (additionalInfo.isEmpty()) {
                note = "-";
            } else {
                note = additionalInfo;
            }
            if (transaction instanceof Allowance) {
                TransactionCategory allowanceCategory = transaction.getCategory();
                String category;
                if (allowanceCategory == AllowanceCategory.NO_ALLOWANCE_CATEGORY) {
                    category = "-";
                } else {
                    category = allowanceCategory.toString();
                }
                System.out.printf("%-5s  %-10s  %-7s  %-18s  %-15s  %-10s %-5s %n", index, "Allowance",
                        "$" + String.format("%.2f", transaction.getAmount()), transaction.getFormattedDate(),
                        transaction.getDescription(), note, category);
            } else if (transaction instanceof Expense) {
                TransactionCategory expenseCategory = transaction.getCategory();
                String category;
                if (expenseCategory == ExpenseCategory.NO_EXPENSE_CATEGORY) {
                    category = "-";
                } else {
                    category = expenseCategory.toString();
                }
                System.out.printf("%-5s  %-10s  %-7s  %-18s  %-15s  %-10s  %-5s %n", index, "Expense",
                        "$" + String.format("%.2f", transaction.getAmount()), transaction.getFormattedDate(),
                        transaction.getDescription(), note, category);
            }
        }
        System.out.println(LINE);
        System.out.println("Net Balance = $" + String.format("%.2f", transactionList.getNetBalance()));
        System.out.println("Budget  = $" + String.format("%.2f", transactionList.getBudget()));
        System.out.println(LINE);
    }

    public static void showReadDataError() {
        System.out.println(LINE);
        System.out.println("No previous data found /:");
        System.out.println(LINE);
    }

    public static void showTransactionViewMessage(Transaction transaction) {
        System.out.println(LINE);
        System.out.println("Following are details of the transaction:");
        if (transaction instanceof Allowance) {
            System.out.println("TYPE: ALLOWANCE");
        } else if (transaction instanceof Expense) {
            System.out.println("TYPE: EXPENSE");
        }
        System.out.println("DATE: " + transaction.getFormattedDate());
        System.out.println("AMOUNT: " + transaction.getAmount());
        System.out.println("DESCRIPTION: " + transaction.getDescription());
        System.out.println("NOTE: " + transaction.getAdditionalInfo());
        System.out.println(LINE);
    }

    public static void showFilterMessage(ArrayList<Transaction> filteredTransactions, TransactionCategory category) {
        System.out.println(LINE);
        System.out.println("Filtered transactions in the category " + category.toString() + ":");
        System.out.println(LINE);
        System.out.printf("%-5s  %-10s  %-7s  %-18s  %-15s  %-10s  %-5s %n",
                "S/N", "TYPE", "AMOUNT", "DATE", "DESCRIPTION", "NOTE", "CATEGORY");
        System.out.println(LINE);
        float totalAmount = 0;
        for (Transaction transaction : filteredTransactions) {
            int index = filteredTransactions.indexOf(transaction) + 1;
            String additionalInfo = transaction.getAdditionalInfo();
            String note;
            if (additionalInfo.isEmpty()) {
                note = "-";
            } else {
                note = additionalInfo;
            }
            String type = "";
            if (transaction instanceof Allowance) {
                type = "Allowance";
            } else if (transaction instanceof Expense) {
                type = "Expense";
            }
            System.out.printf("%-5s  %-10s  %-7s  %-18s  %-15s  %-10s  %-5s %n", index, type,
                    "$" + String.format("%.2f", transaction.getAmount()), transaction.getFormattedDate(),
                    transaction.getDescription(), note, category);
            if (transaction instanceof Allowance) {
                totalAmount += transaction.getAmount();
            } else if (transaction instanceof Expense) {
                totalAmount -= transaction.getAmount();
            }
        }
        System.out.println(LINE);
        System.out.println("Total amount for " + category + " = " + String.format("%.2f", totalAmount));
        System.out.println(LINE);
    }

    public static void showFilterNotFoundMessage(TransactionCategory category) {
        System.out.println(LINE);
        System.out.println("No transactions found in the category " + category.toString());
        System.out.println(LINE);
    }

    public static void showBudgetSet(TransactionList transactionList) {
        System.out.println(LINE);
        System.out.println("Budget set to:");
        System.out.println("  $" + transactionList.getBudget());
        System.out.println(LINE);
    }

    public static void showHelpMenu() {
        System.out.println(LINE);
        System.out.println(Messages.HELP_MENU);
        System.out.println(LINE);
    }

    public static void showFileTamperedMessage() {
        System.out.println(LINE);
        System.out.println(Messages.MESSAGE_FILE_TAMPERED_ERROR);
        System.out.println(LINE);
    }

}
