package seedu.innsync.logic.commands;

import seedu.innsync.logic.Emoticons;
import seedu.innsync.logic.commands.exceptions.CommandException;
import seedu.innsync.model.Model;

/**
 * Handles confirmation for commands that require user confirmation.
 */
public class CancelConfirmCommand extends Command {
    public static final String MESSAGE_CANCEL = "Command cancelled! " + Emoticons.PROUD;

    /**
     * Constructor for when the user cancels the operation.
     */
    public CancelConfirmCommand() { }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_CANCEL);
    }

    @Override
    public boolean requireConfirmation() {
        return false;
    }
}
