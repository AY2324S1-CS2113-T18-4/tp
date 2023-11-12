package seedu.nuscents.parser;

import seedu.nuscents.commands.Command;
import seedu.nuscents.commands.ExitCommand;
import seedu.nuscents.commands.ListCommand;
import seedu.nuscents.commands.AddCommand;
import seedu.nuscents.commands.DeleteCommand;
import seedu.nuscents.commands.HelpCommand;
import seedu.nuscents.commands.InvalidCommand;
import seedu.nuscents.commands.ViewCommand;
import seedu.nuscents.commands.FilterCommand;
import seedu.nuscents.commands.BudgetCommand;
import seedu.nuscents.commands.EditCommand;

import seedu.nuscents.data.TransactionList;
import seedu.nuscents.data.transaction.Allowance;
import seedu.nuscents.data.transaction.Expense;
import seedu.nuscents.data.transaction.ExpenseCategory;
import seedu.nuscents.data.transaction.AllowanceCategory;
import seedu.nuscents.data.transaction.TransactionCategory;
import seedu.nuscents.data.exception.NuscentsException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static seedu.nuscents.commands.ListOfCommands.COMMAND_EXIT;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_LIST;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_ALLOWANCE;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_EXPENSE;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_DELETE;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_HELP;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_VIEW;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_FILTER;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_BUDGET;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_EDIT;
import static seedu.nuscents.ui.Messages.MESSAGE_EMPTY_ALLOWANCE;
import static seedu.nuscents.ui.Messages.MESSAGE_EMPTY_EXPENSE;
import static seedu.nuscents.ui.Messages.MESSAGE_EMPTY_INDEX;
import static seedu.nuscents.ui.Messages.MESSAGE_EMPTY_KEYWORD;
import static seedu.nuscents.ui.Messages.MESSAGE_EMPTY_BUDGET;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_BUDGET;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_BUDGET_FLOAT_DP;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_BUDGET_SIZE;
import static seedu.nuscents.ui.Messages.MESSAGE_FATAL_ERROR;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_AMOUNT;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_DATE;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_INDEX;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_INDEX_ARGUMENTS;
import static seedu.nuscents.ui.Messages.MESSAGE_UNKNOWN_ALLOWANCE_CATEGORY;
import static seedu.nuscents.ui.Messages.MESSAGE_UNKNOWN_EXPENSE_CATEGORY;
import static seedu.nuscents.ui.Messages.MESSAGE_UNKNOWN_FILTER_CATEGORY;

public class Parser {
    private static final String DATE_PATTERN1 = "\\d{1,2}-\\d{1,2}-\\d{4}"; // dd-mm-yyyy
    private static final String DATE_PATTERN2 = "\\d{4}-\\d{1,2}-\\d{1,2}"; // yyyy-mm-dd
    private static final String AMT_PATTERN = "/amt ([^/]+)";
    private static final String DATE_PATTERN = "/date ([^/]+)";
    private static final String DESC_PATTERN = "/desc ([^/]+)";
    private static final String NOTE_PATTERN = "/note ([^/]+)";
    private static final String CATEGORY_PATTERN = "/cat ([^/]+)";

    public static <TransactionList> Command parseCommand(String text, TransactionList transactions)
            throws NuscentsException, ParseException {
        String[] commandTypeAndArgs = text.split(" ", 2);
        String commandType = commandTypeAndArgs[0];
        String arguments;
        if (commandTypeAndArgs.length > 1) {
            arguments = commandTypeAndArgs[1];
        } else {
            arguments = null;
        }
        try {
            switch (commandType) {
            case COMMAND_EXIT:
                return new ExitCommand();
            case COMMAND_LIST:
                return new ListCommand();
            case COMMAND_ALLOWANCE:
                return new AddCommand(parseAllowance(arguments));
            case COMMAND_EXPENSE:
                return new AddCommand(parseExpense(arguments));
            case COMMAND_DELETE:
                return new DeleteCommand(parseTaskIndex(arguments));
            case COMMAND_VIEW:
                return new ViewCommand(parseTaskIndex(arguments));
            case COMMAND_FILTER:
                return new FilterCommand(parseFilterCategory(arguments));
            case COMMAND_BUDGET:
                return new BudgetCommand(parseBudget(arguments));
            case COMMAND_EDIT:
                return parseEdit(arguments);
            case COMMAND_HELP:
                if (arguments != null) {
                    throw new NuscentsException("OOPS!!! The correct format is 'help' alone.");
                }
                return new HelpCommand();
            default:
                return new InvalidCommand();
            }
        } catch (NuscentsException | ParseException e) {
            throw e;
        }
    }

    private static String datePatternValidation(String date) throws NuscentsException {
        if (date.matches(DATE_PATTERN1)) {
            return "d-M-yyyy";
        } else if (date.matches(DATE_PATTERN2)) {
            return "yyyy-M-d";
        } else {
            throw new NuscentsException(MESSAGE_INVALID_DATE);
        }
    }

    public static Date parseDate(String date, String format, SimpleDateFormat formatter)
            throws NuscentsException, ParseException {
        String separator;
        separator = "-";
        String[] dateMonthYear = date.split(separator);
        if (Integer.parseInt(dateMonthYear[1]) > 12) {
            throw new NuscentsException(MESSAGE_INVALID_DATE);
        }
        return formatter.parse(date);
    }

    /**
     * Parsers arguments in the context of adding an allowance.
     *
     * @param arguments full command argument string
     * @return a {@link Allowance} object
     * @throws NuscentsException If the description of the allowance is empty.
     */
    public static Allowance parseAllowance(String arguments) throws NuscentsException, ParseException {
        if (arguments == null) {
            throw new NuscentsException(MESSAGE_EMPTY_ALLOWANCE);
        } else {
            try {
                float amount = Float.parseFloat(
                        extractValue("allowance", arguments, AMT_PATTERN, false));
                if (amount > 0 && amount < 100000 && Math.abs(amount * 100 - Math.floor(amount * 100)) < 1e-6) {
                    String date = extractValue("allowance", arguments, DATE_PATTERN, false);
                    String description = extractValue("allowance", arguments, DESC_PATTERN, false);
                    String additionalInfo = extractValue("allowance", arguments, NOTE_PATTERN, true);
                    String category = extractValue("allowance", arguments, CATEGORY_PATTERN, true);
                    AllowanceCategory allowanceCategory = parseAllowanceCategory(category);
                    String format = datePatternValidation(date);
                    SimpleDateFormat formatter = new SimpleDateFormat(format);
                    Date formattedDate = parseDate(date, format, formatter);
                    assert formattedDate != null;
                    assert description != null;
                    assert additionalInfo != null;
                    assert category != null;
                    return new Allowance(amount, formattedDate, description, additionalInfo, allowanceCategory);
                } else {
                    throw new NuscentsException(MESSAGE_INVALID_AMOUNT);
                }
            } catch (NumberFormatException e) {
                throw new NuscentsException(MESSAGE_INVALID_AMOUNT);
            }
        }
    }

    /**
     * Parsers arguments in the context of adding an expense.
     *
     * @param arguments full command argument string
     * @return a {@link Expense} object
     * @throws NuscentsException If the description of the expense is empty.
     */
    public static Expense parseExpense(String arguments) throws NuscentsException, ParseException {
        if (arguments == null) {
            throw new NuscentsException(MESSAGE_EMPTY_EXPENSE);
        } else {
            try {
                float amount = Float.parseFloat(
                            extractValue("expense", arguments, AMT_PATTERN, false));
                if (amount > 0 && amount < 100000 && Math.abs(amount * 100 - Math.floor(amount * 100)) < 1e-6) {
                    String date = extractValue("expense", arguments, DATE_PATTERN, false);
                    String description = extractValue("expense", arguments, DESC_PATTERN, false);
                    String additionalInfo = extractValue("expense", arguments, NOTE_PATTERN, true);
                    String category = extractValue("expense", arguments, CATEGORY_PATTERN, true);
                    ExpenseCategory expenseCategory = parseExpenseCategory(category);
                    String format = datePatternValidation(date);
                    SimpleDateFormat formatter = new SimpleDateFormat(format);
                    Date formattedDate = parseDate(date, format, formatter);

                    // Ensure all necessary fields are present before creating the Expense object
                    assert formattedDate != null;
                    assert description != null;
                    assert additionalInfo != null;
                    assert category != null;

                    return new Expense(amount, formattedDate, description, additionalInfo, expenseCategory);
                } else {
                    throw new NuscentsException(MESSAGE_INVALID_AMOUNT);
                }
            } catch (NumberFormatException e) {
                throw new NuscentsException(MESSAGE_INVALID_AMOUNT);
            }
        }
    }

    public static ExpenseCategory parseExpenseCategory(String expenseCategory) throws NuscentsException {
        String expenseCategoryLowercase = expenseCategory.toLowerCase().trim();
        ExpenseCategory category = null;
        switch (expenseCategoryLowercase) {
        case "food":
            category = ExpenseCategory.FOOD;
            break;
        case "entertainment":
            category = ExpenseCategory.ENTERTAINMENT;
            break;
        case "transportation":
            category = ExpenseCategory.TRANSPORTATION;
            break;
        case "utility":
            category = ExpenseCategory.UTILITY;
            break;
        case "rent":
            category = ExpenseCategory.RENT;
            break;
        case "other_expense":
            category = ExpenseCategory.OTHER_EXPENSE;
            break;
        case "":
            // fallthrough
        case "no_expense_category":
            category = ExpenseCategory.NO_EXPENSE_CATEGORY;
            break;
        default:
            throw new NuscentsException(MESSAGE_UNKNOWN_EXPENSE_CATEGORY);
        }
        return category;
    }

    public static AllowanceCategory parseAllowanceCategory(String allowanceCategory) throws NuscentsException {
        String allowanceCategoryLowerCase = allowanceCategory.toLowerCase().trim();
        AllowanceCategory category = null;
        switch (allowanceCategoryLowerCase) {
        case "salary":
            category = AllowanceCategory.SALARY;
            break;
        case "allowance":
            category = AllowanceCategory.ALLOWANCE;
            break;
        case "investments":
            category = AllowanceCategory.INVESTMENTS;
            break;
        case "gifts":
            category = AllowanceCategory.GIFTS;
            break;
        case "other_allowance":
            category = AllowanceCategory.OTHER_ALLOWANCE;
            break;
        case "":
            // fallthrough
        case "no_allowance_category":
            category = AllowanceCategory.NO_ALLOWANCE_CATEGORY;
            break;
        default:
            throw new NuscentsException(MESSAGE_UNKNOWN_ALLOWANCE_CATEGORY);
        }
        return category;
    }

    public static TransactionCategory parseFilterCategory(String transactionCategory) throws NuscentsException {
        if (transactionCategory == null) {
            throw new NuscentsException(MESSAGE_UNKNOWN_FILTER_CATEGORY);
        }
        String allowanceCategoryLowerCase = transactionCategory.toLowerCase().trim();
        TransactionCategory category = null;
        switch (allowanceCategoryLowerCase) {
        case "salary":
            category = AllowanceCategory.SALARY;
            break;
        case "allowance":
            category = AllowanceCategory.ALLOWANCE;
            break;
        case "investments":
            category = AllowanceCategory.INVESTMENTS;
            break;
        case "gifts":
            category = AllowanceCategory.GIFTS;
            break;
        case "food":
            category = ExpenseCategory.FOOD;
            break;
        case "entertainment":
            category = ExpenseCategory.ENTERTAINMENT;
            break;
        case "transportation":
            category = ExpenseCategory.TRANSPORTATION;
            break;
        case "utility":
            category = ExpenseCategory.UTILITY;
            break;
        case "rent":
            category = ExpenseCategory.RENT;
            break;
        case "other_allowance":
            category = AllowanceCategory.OTHER_ALLOWANCE;
            break;
        case "other_expense":
            category = ExpenseCategory.OTHER_EXPENSE;
            break;
        default:
            throw new NuscentsException(MESSAGE_UNKNOWN_FILTER_CATEGORY);
        }
        return category;
    }

    public static int parseTaskIndex(String arguments) throws IndexOutOfBoundsException,
            NuscentsException {
        if (arguments == null) {
            throw new NuscentsException(MESSAGE_EMPTY_INDEX);
        }
        try {
            int taskIndex = Integer.parseInt(arguments);
            if (taskIndex > TransactionList.getTransactionCount() || taskIndex <= 0) {
                throw new IndexOutOfBoundsException(MESSAGE_INVALID_INDEX);
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new NuscentsException(MESSAGE_INVALID_INDEX_ARGUMENTS);
        }
    }

    public static String parseFind(String arguments) throws NuscentsException {
        if (arguments == null) {
            throw new NuscentsException(MESSAGE_EMPTY_KEYWORD);
        } else {
            return arguments;
        }
    }

    public static float parseBudget(String arguments) throws NuscentsException {
        if (arguments == null) {
            throw new NuscentsException(MESSAGE_EMPTY_BUDGET);
        }
        try {
            float budget = Float.parseFloat(arguments);
            if (Math.abs(budget * 100 - Math.floor(budget * 100)) < 1e-6) {
                if (budget < 100000) {
                    if (budget > 0) {
                        return budget;
                    } else {
                        throw new NuscentsException(MESSAGE_INVALID_BUDGET);
                    }
                } else {
                    throw new NuscentsException(MESSAGE_INVALID_BUDGET_SIZE);
                }
            } else {
                throw new NuscentsException(MESSAGE_INVALID_BUDGET_FLOAT_DP);
            }
        } catch (NumberFormatException e) {
            throw new NuscentsException(MESSAGE_INVALID_BUDGET);
        }
    }
    
    public static EditCommand parseEdit(String arguments) throws NuscentsException, ParseException {
        if (arguments == null) {
            throw new NuscentsException(MESSAGE_EMPTY_INDEX);
        } else {
            String[] editDetails = arguments.split(" ", 3);
            if (editDetails.length < 3) {
                throw new NuscentsException(MESSAGE_EMPTY_INDEX);
            }
            int index = parseTaskIndex(editDetails[0]);
            String type = editDetails[1];
            if (type.equals("expense")){
                return new EditCommand(parseExpense(editDetails[2]), index);
            } else if (type.equals("allowance")) {
                return new EditCommand(parseAllowance(editDetails[2]), index);
            } else{
                throw new NuscentsException(MESSAGE_FATAL_ERROR);
            }
        }
    }

    private static String extractValue(String command, String input, String pattern, boolean isOptional)
            throws NuscentsException {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(input);

        if (m.find()) {
            return m.group(1).trim();
        } else if (!isOptional) {
            switch (command) {
            case "allowance":
                throw new NuscentsException(MESSAGE_EMPTY_ALLOWANCE);
            case "expense":
                throw new NuscentsException(MESSAGE_EMPTY_EXPENSE);
            default:
                throw new NuscentsException(MESSAGE_FATAL_ERROR);
            }
        }
        return "";
    }
}
