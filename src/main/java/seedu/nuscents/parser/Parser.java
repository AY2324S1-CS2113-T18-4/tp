package seedu.nuscents.parser;

import seedu.nuscents.commands.Command;
import seedu.nuscents.commands.ExitCommand;
import seedu.nuscents.commands.ListCommand;
import seedu.nuscents.commands.AddCommand;
import seedu.nuscents.commands.DeleteCommand;
import seedu.nuscents.commands.FindCommand;
import seedu.nuscents.commands.HelpCommand;
import seedu.nuscents.commands.InvalidCommand;
import seedu.nuscents.commands.ViewCommand;

import seedu.nuscents.data.transaction.Transaction;
import seedu.nuscents.data.transaction.Allowance;
import seedu.nuscents.data.transaction.Expense;
import seedu.nuscents.data.transaction.ExpenseCategory;
import seedu.nuscents.data.transaction.AllowanceCategory;
import seedu.nuscents.data.exception.NuscentsException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static seedu.nuscents.commands.ListOfCommands.COMMAND_EXIT;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_LIST;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_ALLOWANCE;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_EXPENSE;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_DELETE;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_FIND;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_HELP;
import static seedu.nuscents.commands.ListOfCommands.COMMAND_VIEW;
import static seedu.nuscents.ui.Messages.MESSAGE_UNKNOWN_CATEGORY;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_DATE;
import static seedu.nuscents.ui.Messages.MESSAGE_EMPTY_ALLOWANCE;
import static seedu.nuscents.ui.Messages.MESSAGE_EMPTY_EXPENSE;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_AMOUNT;
import static seedu.nuscents.ui.Messages.MESSAGE_EMPTY_INDEX;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_INDEX;
import static seedu.nuscents.ui.Messages.MESSAGE_INVALID_INDEX_ARGUMENTS;
import static seedu.nuscents.ui.Messages.MESSAGE_FATAL_ERROR;
import static seedu.nuscents.ui.Messages.MESSAGE_EMPTY_KEYWORD;

public class Parser {
    private static final String DATE_PATTERN1 = "\\d{1,2}-\\d{1,2}-\\d{4}"; // dd-mm-yyyy
    private static final String DATE_PATTERN2 = "\\d{4}-\\d{1,2}-\\d{1,2}"; // yyyy-mm-dd
    private static final String AMT_PATTERN = "/amt ([^/]+)";
    private static final String DATE_PATTERN = "/date ([^/]+)";
    private static final String DESC_PATTERN = "/desc ([^/]+)";
    private static final String NOTE_PATTERN = "/note ([^/]+)";
    private static final String CATEGORY_PATTERN = "/cat ([^/]+)";

    public static <TaskList> Command parseCommand(String text, TaskList tasks) throws NuscentsException,
            ParseException {
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
            case COMMAND_FIND:
                return new FindCommand(parseFind(arguments));
            case COMMAND_VIEW:
                return new ViewCommand(parseTaskIndex(arguments));
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
        if (format.contains("-")) {
            separator = "-";
        } else {
            separator = "/";
        }
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
            float amount = 0;
            try {
                amount = Float.parseFloat(extractValue("allowance", arguments, AMT_PATTERN, false));
            } catch (NumberFormatException e) {
                throw new NuscentsException(MESSAGE_INVALID_AMOUNT);
            }
            String date = extractValue("allowance", arguments, DATE_PATTERN, false);
            String description = extractValue("allowance", arguments, DESC_PATTERN, false);
            String additionalInformation = extractValue("allowance", arguments, NOTE_PATTERN, true);
            String category = extractValue("allowance", arguments, CATEGORY_PATTERN, true);
            AllowanceCategory allowanceCategory = parseAllowanceCategory(category);
            String format = datePatternValidation(date);
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date formattedDate = parseDate(date, format, formatter);
            return new Allowance(amount, formattedDate, description, additionalInformation, allowanceCategory);
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
            float amount = 0;
            try {
                amount = Float.parseFloat(extractValue("expense", arguments, AMT_PATTERN, false));
            } catch (NumberFormatException e) {
                throw new NuscentsException(MESSAGE_INVALID_AMOUNT);
            }
            String date = extractValue("expense", arguments, DATE_PATTERN, false);
            String description = extractValue("expense", arguments, DESC_PATTERN, false);
            String additionalInformation = extractValue("expense", arguments, NOTE_PATTERN, true);
            String category = extractValue("expense", arguments, CATEGORY_PATTERN, true);
            ExpenseCategory expenseCategory = parseExpenseCategory(category);
            String format = datePatternValidation(date);
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date formattedDate = parseDate(date, format, formatter);
            return new Expense(amount, formattedDate, description, additionalInformation, expenseCategory);
        }
    }

    public static ExpenseCategory parseExpenseCategory(String expenseCategory) throws NuscentsException {
        String expenseCategoryLowercase = expenseCategory.toLowerCase();
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
        case "others":
            category = ExpenseCategory.OTHERS;
            break;
        case "":
        case "no_expense_category":
            category = ExpenseCategory.NO_EXPENSE_CATEGORY;
            break;
        default:
            throw new NuscentsException(MESSAGE_UNKNOWN_CATEGORY);
        }
        return category;
    }

    public static AllowanceCategory parseAllowanceCategory(String allowanceCategory) throws NuscentsException {
        String allowanceCategoryLowerCase = allowanceCategory.toLowerCase();
        AllowanceCategory category = null;
        switch (allowanceCategoryLowerCase) {
        case "work":
            break;
        case "":
        case "no_allowance_category":
            category = AllowanceCategory.NO_ALLOWANCE_CATEGORY;
            break;
        default:
            throw new NuscentsException(MESSAGE_UNKNOWN_CATEGORY);
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
            if (taskIndex > Transaction.getTransactionCount() || taskIndex <= 0) {
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
