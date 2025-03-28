package seedu.innsync.logic.commands;

import static seedu.innsync.logic.commands.CommandTestUtil.VALID_BOOKINGTAG_BEACHHOUSE;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_BOOKINGTAG_HOTEL;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.innsync.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.innsync.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.innsync.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.innsync.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.innsync.commons.core.index.Index;
import seedu.innsync.logic.Messages;
import seedu.innsync.model.AddressBook;
import seedu.innsync.model.Model;
import seedu.innsync.model.ModelManager;
import seedu.innsync.model.UserPrefs;
import seedu.innsync.model.person.Person;
import seedu.innsync.testutil.PersonBuilder;

public class UntagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeValidBookingTag_success() {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Person taggedPerson = new PersonBuilder().withBookingTags(VALID_BOOKINGTAG_BEACHHOUSE).build();
        model.addPerson(taggedPerson);
        Index index = Index.fromZeroBased(model.getPersonList().indexOf(taggedPerson));

        Person editedPerson = new PersonBuilder(taggedPerson).withBookingTags().build();
        expectedModel.addPerson(editedPerson);

        UntagCommand untagCommand = new UntagCommand(index, "", VALID_BOOKINGTAG_BEACHHOUSE);
        String expectedMessage = String.format(UntagCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        assertCommandSuccess(untagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeValidTag_success() {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Person taggedPerson = new PersonBuilder().withTags(VALID_TAG_FRIEND).build();
        model.addPerson(taggedPerson);
        Index index = Index.fromZeroBased(model.getPersonList().indexOf(taggedPerson));

        Person editedPerson = new PersonBuilder(taggedPerson).withTags().build();
        expectedModel.addPerson(editedPerson);

        UntagCommand untagCommand = new UntagCommand(index, VALID_TAG_FRIEND, "");
        String expectedMessage = String.format(UntagCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        assertCommandSuccess(untagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeInvalidBookingTag_failure() {
        Index index = Index.fromZeroBased(INDEX_FIRST_PERSON.getZeroBased());

        UntagCommand untagCommand = new UntagCommand(index, "", VALID_BOOKINGTAG_HOTEL);
        String expectedMessage = String.format(UntagCommand.MESSAGE_FAILURE_BOOKINGTAG, VALID_BOOKINGTAG_HOTEL);

        assertCommandFailure(untagCommand, model, expectedMessage);
    }

    @Test
    public void execute_removeInvalidTag_failure() {
        Index index = Index.fromZeroBased(INDEX_FIRST_PERSON.getZeroBased());

        UntagCommand untagCommand = new UntagCommand(index, VALID_TAG_HUSBAND, "");
        String expectedMessage = String.format(UntagCommand.MESSAGE_FAILURE_TAG, VALID_TAG_HUSBAND);

        assertCommandFailure(untagCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getPersonList().size() + 1);
        UntagCommand untagCommand = new UntagCommand(outOfBoundIndex, VALID_TAG_HUSBAND, "");
        assertCommandFailure(untagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
