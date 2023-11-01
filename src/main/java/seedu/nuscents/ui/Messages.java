package seedu.nuscents.ui;

public class Messages {
    public static final String LINE = "-------------------------------------------------------------------------------"
            + "--------------";
    public static final String LOGO = " __   __  __   __   ____   _____ _____ __   __ ________   ____\n"
            + "|  \\ |  |  |  |  |/     / /   __|     |  \\ |  |__    __|/     /\n"
            + "|   \\|  |  |  |  |\\   __\\|   /  |   __|   \\|  |  |  |   \\   __\\\n"
            + "|       |  |  |  | \\__   |  |   |   __|       |  |  |    \\__   |\n"
            + "|       |  |__|  |/      |   \\__|     |       |  |  |   /      |\n"
            + "|__|\\___|________|______/ \\_____|_____|__|\\___|  |__|   |_____/\n";
    public static final String MESSAGE_EXIT = "Thank you for using NUScents. Hope to see you again soon!";
    public static final String HELP_MENU =
            "- `list` :" + System.lineSeparator() +
                    "    List all entries." + System.lineSeparator() +
                    "- `allowance /amt AMT /date DATE /desc DESC [/note NOTE] [/cat CAT]` :" + System.lineSeparator() +
                    "    Add an allowance." + System.lineSeparator() +
                    "- `expense /amt AMT /date DATE /desc DESC [/note NOTE] [/cat CAT]` :" + System.lineSeparator() +
                    "    Add an expense." + System.lineSeparator() +
                    "- `delete INDEX` :" + System.lineSeparator() +
                    "    Delete an entry." + System.lineSeparator() +
                    "- `filter CATEGORY` :" + System.lineSeparator() +
                    "    Filter by category." + System.lineSeparator() +
                    "- `view INDEX` :" + System.lineSeparator() +
                    "    View entry detail." + System.lineSeparator() +
                    "- `exit` :" + System.lineSeparator() +
                    "    Exit program.";
    public static final String MESSAGE_UNKNOWN = "OOPS!!! I'm sorry, but I don't know what that means :-(";
    public static final String MESSAGE_EMPTY_ALLOWANCE = "OOPS!!! Invalid input format for adding an allowance.";
    public static final String MESSAGE_EMPTY_EXPENSE = "OOPS!!! Invalid input format for adding an expense.";
    public static final String MESSAGE_INVALID_AMOUNT = "OOPS!!! The amount entered needs to be a float!";
    public static final String MESSAGE_INVALID_DATE = "OOPS!!! The format of the date is invalid.";
    public static final String MESSAGE_INVALID_INDEX = "OOPS!!! This is an invalid transaction index.";
    public static final String MESSAGE_INVALID_INDEX_ARGUMENTS = "OOPS!!! The input should consist only of digits.";
    public static final String MESSAGE_EMPTY_LIST = "You have not made any transactions!";
    public static final String MESSAGE_EMPTY_KEYWORD = "OOPS!!! The keyword of a find command cannot be empty.";
    public static final String MESSAGE_EMPTY_BUDGET = "OOPS!!! The budget command requires a valid float value.";
    public static final String MESSAGE_INVALID_BUDGET = "OOPS!!! The budget amount has to be a valid float value.";
    public static final String MESSAGE_EMPTY_INDEX = "OOPS!!! The index of a delete/view command cannot be empty.";
    public static final String MESSAGE_FATAL_ERROR = "OOPS!!! Fatal error occurred...";
    public static final String MESSAGE_READ_ERROR = "OOPS!!! A problem occurred while reading the data file.";

    public static final String MESSAGE_FILE_TAMPERED_ERROR = "OOPS!!! The data file has been tampered with. "
            + "Exiting immediately...";
    public static final String MESSAGE_UNKNOWN_EXPENSE_CATEGORY = "OOPS!!! The category you provided is not a "
            + "valid category.\n"
            + "Valid categories are FOOD/ENTERTAINMENT/TRANSPORTATION/UTILITY/RENT/OTHERS.";
    public static final String MESSAGE_UNKNOWN_ALLOWANCE_CATEGORY = "OOPS!!! The category you provided is not a "
            + "valid category.\n "
            + "Valid categories are SALARY/ALLOWANCE/INVESTMENTS/GIFTS/OTHERS.";
    public static final String MESSAGE_UNKNOWN_FILTER_CATEGORY = "OOPS!!! The category you provided is not a "
            + "valid category.\n"
            + "Valid categories are "
            + "SALARY/ALLOWANCE/INVESTMENTS/GIFTS/FOOD/ENTERTAINMENT/TRANSPORTATION/UTILITY/RENT/OTHERS.";
}
