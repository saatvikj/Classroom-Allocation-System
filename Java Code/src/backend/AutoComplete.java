package backend;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AutoComplete extends TextField {

	private final SortedSet<String> entries;
	private ContextMenu entriesPopup;

	public AutoComplete() {
		super();
		this.entries = new TreeSet<>();
		this.entriesPopup = new ContextMenu();

		setListner();
	}

	/**
	 * wrapper for default constructor with setting of
	 * "TextFieldWithLengthLimit" LengthLimit
	 *
	 * @param lengthLimit
	 */

	/**
	 * "Suggestion" specific listners
	 */
	private void setListner() {
		textProperty().addListener((observable, oldValue, newValue) -> {
			String enteredText = getText();
			if (enteredText == null || enteredText.isEmpty()) {
				entriesPopup.hide();
			} else {
				List<String> filteredEntries = entries.stream()
						.filter(e -> e.toLowerCase().contains(enteredText.toLowerCase())).collect(Collectors.toList());
				if (!filteredEntries.isEmpty()) {
					populatePopup(filteredEntries, enteredText);
					if (!entriesPopup.isShowing()) {
						entriesPopup.show(AutoComplete.this, Side.BOTTOM, 0, 0); 
																								
																								
					}
				} else {
					entriesPopup.hide();
				}
			}
		});

		focusedProperty().addListener((observableValue, oldValue, newValue) -> {
			entriesPopup.hide();
		});
	}

	/**
	 * Populate the entry set with the given search results. Display is limited
	 * to 10 entries, for performance.
	 *
	 * @param searchResult
	 *            The set of matching strings.
	 */
	private void populatePopup(List<String> searchResult, String searchReauest) {
		List<CustomMenuItem> menuItems = new LinkedList<>();
		int maxEntries = 7;
		int count = Math.min(searchResult.size(), maxEntries);
		for (int i = 0; i < count; i++) {
			final String result = searchResult.get(i);
			Label entryLabel = new Label();
			entryLabel.setGraphic(buildTextFlow(result, searchReauest));
			entryLabel.setPrefHeight(10);								
			entryLabel.setPrefWidth(300);
			CustomMenuItem item = new CustomMenuItem(entryLabel, true);
			menuItems.add(item);

			item.setOnAction(actionEvent -> {
				setText(result);
				positionCaret(result.length());
				entriesPopup.hide();
			});
		}

		entriesPopup.getItems().clear();
		entriesPopup.getItems().addAll(menuItems);
	}

	/**
	 * Get the existing set of autocomplete entries.
	 *
	 * @return The existing autocomplete entries.
	 */
	public SortedSet<String> getEntries() {
		return entries;
	}

	public static TextFlow buildTextFlow(String text, String filter) {
		int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
		Text textBefore = new Text(text.substring(0, filterIndex));
		Text textAfter = new Text(text.substring(filterIndex + filter.length()));
		Text textFilter = new Text(text.substring(filterIndex, filterIndex + filter.length()));
		textFilter.setFill(Color.GREEN);
		textFilter.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
		return new TextFlow(textBefore, textFilter, textAfter);
	}

}