package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.goal.Goal;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> personListView;
    @FXML
    private ListView<GoalCard> goalListView;

    public PersonListPanel(ObservableList<Person> personList, ObservableList<Goal> goalList) {
        super(FXML);
        setConnections(personList, goalList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Person> personList, ObservableList<Goal> goalList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
        personListView.setItems(mappedList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
        ObservableList<GoalCard> mappedGoalList = EasyBind.map(
                goalList, (goal) -> new GoalCard(goal, goalList.indexOf(goal) + 1));
        goalListView.setItems(mappedGoalList);
        goalListView.setCellFactory(listView -> new GoalListViewCell());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

    //@@author deborahlow97
    @Subscribe
    private void handleJumpToGoalListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollToGoal(event.targetIndex);
    }

    /**
     * Scrolls to the {@code GoalCard} at the {@code index} and selects it.
     */
    private void scrollToGoal(int index) {
        Platform.runLater(() -> {
            goalListView.scrollTo(index);
            goalListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code GoalCard}.
     */
    class GoalListViewCell extends ListCell<GoalCard> {

        @Override
        protected void updateItem(GoalCard goal, boolean empty) {
            super.updateItem(goal, empty);

            if (empty || goal == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(goal.getRoot());
            }
        }
    }
}
