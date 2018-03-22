package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
    private static final String LEVEL_OF_FRIENDSHIP_FIELD_ID = "#levelOfFriendship";
    private static final String UNIT_NUMBER_FIELD_ID = "#unitNumber";
    private static final String CCAS_FIELD_ID = "#ccas";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String MEET_FIELD_ID = "#meetDate";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label birthdayLabel;
    private final Label levelOfFriendshipLabel;
    private final Label unitNumberLabel;
    private final Label ccaLabel;
    private final Label meetLabel;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.levelOfFriendshipLabel = getChildNode(LEVEL_OF_FRIENDSHIP_FIELD_ID);
        this.unitNumberLabel = getChildNode(UNIT_NUMBER_FIELD_ID);
        this.ccaLabel = getChildNode(CCAS_FIELD_ID);
        this.meetLabel = getChildNode(MEET_FIELD_ID);
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getBirthday() {
        return birthdayLabel.getText();
    }

    public String getLevelOfFriendship() {
        return levelOfFriendshipLabel.getText();
    }

    public String getUnitNumber() {
        return unitNumberLabel.getText();
    }

    public String getCcas() {
        return ccaLabel.getText().trim();
    }
    /**
     * Takes in @param value representing the level of friendship value
     * @return a number of hearts string.
     */
    public String changeLevelOfFriendshipToHeart(String value) {
        int intValue = Integer.parseInt(value);
        String heartString = "";
        for (int i = 0; i < intValue; i++) {
            heartString = heartString + '\u2665' + " ";
        }
        return heartString;
    }

    public String getMeetDate() {
        return meetLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }
}
