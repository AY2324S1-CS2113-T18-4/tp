package seedu.nuscents.ui;

import seedu.nuscents.data.Task;
import seedu.nuscents.data.TaskList;

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

    public static void showTaskCount( ) {
        System.out.println("Now you have " + Task.getTaskCount() + " tasks in the list.");
    }

    public static void showTaskAddedMessage(Task task) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.getDetails());
        showTaskCount();
        System.out.println(LINE);
    }

    public static void showTaskRemovedMessage(Task task) {
        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task.getDetails());
        showTaskCount();
        System.out.println(LINE);
    }

    public static void showTaskList(TaskList taskList) {
        System.out.println(LINE);
        if (taskList.getTasks().isEmpty()) {
            System.out.println(MESSAGE_EMPTY_LIST);
            System.out.println(LINE);
            return;
        }
        System.out.println("Here are the tasks in your list:");
        ArrayList<Task> tasks = taskList.getTasks();
        for (Task task : tasks) {
            int index = tasks.indexOf(task) + 1;
            System.out.println(index + ". " + task.getDetails());
        }
        System.out.println(LINE);
    }

    public static void showReadDataError() {
        System.out.println(LINE);
        System.out.println("No previous data found /:");
        System.out.println(LINE);
    }

    public static void showHelpMenu() {
        System.out.println(LINE);
        System.out.println("- `list` :\n    Show a list of all entries.\n"
                + "- `allowance /amt AMOUNT /date DATE /desc DESCRIPTION "
                + "[/note ADDITIONAL_INFORMATION]` :\n    Add an allowance.\n"
                + "- `expense /amt AMOUNT /date DATE /desc DESCRIPTION "
                + "[/note ADDITIONAL_INFORMATION]` :\n    Add an expense.\n"
                + "- `delete ENTRY_NUMBER` :\n    Delete an entry.\n"
                + "- `view ENTRY_NUMBER` :\n    View an entry detail.\n"
                + "- `exit` :\n    Exit the program.");
        System.out.println(LINE);
    }

}
