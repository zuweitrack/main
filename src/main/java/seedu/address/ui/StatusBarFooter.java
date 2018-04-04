package seedu.address.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.goal.Goal;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

    public static final String PERCENTAGE_GOAL_COMPLETED = "Goal %d % complete.";
    private static final int PERCENTAGE_KEY_NUMBER = 100;
    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;
    @FXML
    private StatusBar goalCompletionStatus;

    public StatusBarFooter(String saveLocation, int goalCompletion) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        setGoalCompletion(goalCompletion);
        registerAsAnEventHandler(this);
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    private void setSaveLocation(String location) {
        Platform.runLater(() -> this.saveLocationStatus.setText(location));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> this.syncStatus.setText(status));
    }

    //@@author deborahlow97
    private void setGoalCompletion(int goalCompletion) {
        //        Platform.runLater(() -> this.goalCompletionStatus.setText(String.format(PERCENTAGE_GOAL_COMPLETED,
        //                goalCompletion)));
        Platform.runLater(() -> this.goalCompletionStatus.setText("Goal " + goalCompletion + "% completed."));
    }

    private int getGoalCompletion(ObservableList<Goal> goalList) {
        int totalGoal = goalList.size();
        int totalGoalCompleted = 0;
        String completionStatus;
        for (int i = 0; i < totalGoal; i++) {
            completionStatus = goalList.get(i).getCompletion().value;
            totalGoalCompleted += isCompletedGoal(completionStatus);
        }
        int percentageGoalCompletion = (int) (((float) totalGoalCompleted / totalGoal) * PERCENTAGE_KEY_NUMBER);
        return percentageGoalCompletion;
    }

    /**
     * @param completionStatus gives a String that should be either "true" or "false", indicating if the goal is
     *                         completed.
     * @return true or false
     */
    private int isCompletedGoal(String completionStatus) {
        int valueToAdd;
        if (completionStatus.equals("true")) {
            valueToAdd = 1;
        } else {
            valueToAdd = 0;
        }
        return valueToAdd;
    }
    //@@author
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        setGoalCompletion(getGoalCompletion(abce.data.getGoalList()));
    }
}
