package seedu.nuscents.data;

import seedu.nuscents.data.transaction.AllowanceCategory;
import seedu.nuscents.data.transaction.ExpenseCategory;
import seedu.nuscents.data.transaction.Transaction;
import seedu.nuscents.data.transaction.TransactionCategory;
import seedu.nuscents.ui.Ui;

import javax.sound.sampled.Line;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class TransactionList {
    private ArrayList<Transaction> transactions;

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

    public void editTransaction(int transactionIndex) {
        Transaction transaction = transactions.get(transactionIndex-1);
        Ui.showTransactionViewMessage(transaction);
        Scanner input = new Scanner(System.in);
        System.out.println("What would you like to edit?");

        String userInput = input.nextLine().trim();
        String[] inputs = userInput.split(" /");

        if (inputs.length < 5) {
            System.out.println("Invalid number of arguments provided. Edit aborted.");
            return;
        }

        // Conversion for each parameter:

        // 1. Convert amount to integer:
        int amount = Integer.parseInt(inputs[0]);

        // 2. Convert date string to java.util.Date object:
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Assuming date format is like "24-03-2000"
        Date date = null;
        try {
            date = sdf.parse(inputs[1]);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Edit aborted.");
            return;
        }

        // 3. Description:
        String description = inputs[2];

        // 4. Additional Info:
        String additionalInfo = inputs[3];

        // 5. Convert string to TransactionCategory:
        TransactionCategory category = null;

        try {
            category = AllowanceCategory.valueOf(inputs[4].toUpperCase());
        } catch (IllegalArgumentException e1) {
            try {
                category = ExpenseCategory.valueOf(inputs[4].toUpperCase());
            } catch (IllegalArgumentException e2) {
                System.out.println("Invalid category provided. Edit aborted.");
                return;
            }
        }

        transaction.setTransaction(amount, date, description, additionalInfo, category);
        System.out.println("Transaction updated successfully.");
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

}

