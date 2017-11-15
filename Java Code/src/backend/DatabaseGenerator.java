package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The class SatabaseGenerator reads the csv file and then serializes all the
 * data into the respective txt files. This class is run only once, and then the
 * text files are later used in all the functionalities.
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class DatabaseGenerator {

	private ArrayList<Course> allCourses = new ArrayList<Course>();
	private ArrayList<ClassRoom> allRooms = new ArrayList<ClassRoom>();
	private String fileName = "./src/res/MainTT.csv";
	private String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
	private ArrayList<String> exemptedWords = new ArrayList<>();
	private Map<String, Integer> frequency = new HashMap<>();
	private ArrayList<String> autoCompleteText = new ArrayList<>();

	/**
	 * This function reads the CSV file and populates all the rooms present in
	 * the CSV, and then serializes the object of the class ClassRoom into the
	 * rooms.txt file.
	 */
	public void populateRooms() {
		BufferedReader br = null;
		String line = "";
		String splitby = ",";

		try {
			br = new BufferedReader(new FileReader(this.fileName));
			int count = 0;
			while ((line = br.readLine()) != null) {
				count = count + 1;
				if (count != 1) {
					String[] courseDetails = line.split(splitby);
					checkAndMakeRoom(courseDetails);
				}

			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Splits the line of the CSV file to extract the room information, and then
	 * calls the makeRoom function to make the new ClassRoom.
	 * 
	 * @param details
	 * @throws IOException
	 */
	public void checkAndMakeRoom(String[] details) throws IOException {

		for (int i = 6; i <= 10; i++) {

			String[] subdetails = details[i].split("\\~");
			for (int j = 0; j < 3; j++) {
				makeRoom(subdetails[j]);
			}
		}

	}

	/**
	 * This function asks the user to enter the capacity of each room. It then
	 * sets the vslue of room capacity into the object of ClassRoom and adds the
	 * room to allRooms arraylist.
	 * 
	 * @param bookingDetails
	 * @throws IOException
	 */
	public void makeRoom(String bookingDetails) throws IOException {

		if (!(bookingDetails.equals("-"))) {
			String[] details = bookingDetails.split("\\$");
			for (int i = 1; i < details.length; i++) {
				String roomName = details[i];
				if (checkNewRoom(roomName)) {
					ClassRoom rm = new ClassRoom();
					rm.setRoomNumber(roomName);

					InputStreamReader isr = new InputStreamReader(System.in);
					BufferedReader br = new BufferedReader(isr);

					System.out.println("Capacity of " + roomName + ":");

					String cap = br.readLine();
					int capacity = Integer.parseInt(cap);

					rm.setCapacity(capacity);

					allRooms.add(rm);
				}

			}

		}

	}

	/**
	 * This function checks whether a room already exists or not. It returns
	 * true if the room doesn't exist and false if it does.
	 * 
	 * @param roomName
	 * @return boolean
	 */
	public boolean checkNewRoom(String roomName) {
		for (int i = 0; i < allRooms.size(); i++) {
			if (allRooms.get(i).getRoomNumber().equals(roomName)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This function reads the CSV file and then populates the Course objects
	 * into the allCourses.txt file and then serializes it.
	 */
	public void populateCourses() {

		BufferedReader br = null;
		String line = "";
		String splitby = ",";

		try {
			br = new BufferedReader(new FileReader(this.fileName));
			int count = 0;
			while ((line = br.readLine()) != null) {
				count = count + 1;
				if (count != 1) {
					String[] courseDetails = line.split(splitby);

					Course c = new Course();

					c.setCourseType(getType(courseDetails[0]));
					c.setCourseName(courseDetails[1]);
					c.setCourseCode(courseDetails[2]);
					c.setInstructor(courseDetails[3]);
					c.setCredits(Integer.parseInt(courseDetails[4]));
					c.setAcronym(courseDetails[5]);
					c.setPreReqs(courseDetails[11].split("\\\\"));

					String postCon = courseDetails[12];
					postCon = postCon.replaceAll("\\|", ",");
					generateKeywords(postCon, c.getCourseName());

					c.setPostConditions(postCon.split("\\\\"));

					for (int i = 6; i <= 10; i++) {

						String[] courseSubDetails = courseDetails[i].split("\\~");

						for (int j = 0; j < 3; j++) {

							if (!(courseSubDetails[j].equals("-"))) {

								String[] splitDateVenue = courseSubDetails[j].split("\\$");
								Date dt = new Date(0000, 00, 00);
								String startTime = splitDateVenue[0].split("\\-")[0];
								int startTimeHour = Integer.parseInt(startTime.split("\\:")[0]);
								int startTimeMinute = Integer.parseInt(startTime.split("\\:")[1]);
								Time sTime = new Time(startTimeHour, startTimeMinute, 0);
								String endTime = splitDateVenue[0].split("\\-")[1];
								int endTimeHour = Integer.parseInt(endTime.split("\\:")[0]);
								int endTimeMinute = Integer.parseInt(endTime.split("\\:")[1]);
								Time eTime = new Time(endTimeHour, endTimeMinute, 0);
								String purpose = Slot.TYPES[j];
								Slot slt = new Slot(dt, this.daysOfWeek[i - 6], purpose, sTime, eTime);

								for (int k = 1; k < splitDateVenue.length; k++) {

									ClassRoom slotRoom = getCorrespondingRoom(splitDateVenue[k]);
									Map<Slot, List<ClassRoom>> newTT = c.getCourseTimeTable();

									if (newTT.containsKey(slt)) {
										List<ClassRoom> rooms = newTT.get(slt);
										rooms.add(slotRoom);
										newTT.put(slt, rooms);
									} else {
										List<ClassRoom> rooms = new ArrayList<ClassRoom>();
										rooms.add(slotRoom);
										newTT.put(slt, rooms);
									}

									c.setCourseTimeTable(newTT);
								}

							}

						}

					}

					allCourses.add(c);
				}

			}

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * This function slpits the CSV file to extract the bookedslots of a
	 * praticular room and then books those slot.
	 */
	public void bookSlots() {
		BufferedReader br = null;
		String line = "";
		String splitby = ",";

		try {
			br = new BufferedReader(new FileReader(this.fileName));
			int count = 0;
			while ((line = br.readLine()) != null) {
				count = count + 1;
				if (count != 1) {
					String[] courseDetails = line.split(splitby);
					for (int i = 6; i <= 10; i++) {

						String[] courseSubDetails = courseDetails[i].split("\\~");

						for (int j = 0; j < 3; j++) {

							if (!(courseSubDetails[j].equals("-"))) {

								String[] splitDateVenue = courseSubDetails[j].split("\\$");
								Date dt = new Date(0000, 00, 00);
								String startTime = splitDateVenue[0].split("\\-")[0];
								int startTimeHour = Integer.parseInt(startTime.split("\\:")[0]);
								int startTimeMinute = Integer.parseInt(startTime.split("\\:")[1]);
								Time sTime = new Time(startTimeHour, startTimeMinute, 0);
								String endTime = splitDateVenue[0].split("\\-")[1];
								int endTimeHour = Integer.parseInt(endTime.split("\\:")[0]);
								int endTimeMinute = Integer.parseInt(endTime.split("\\:")[1]);
								Time eTime = new Time(endTimeHour, endTimeMinute, 0);
								String purpose = Slot.TYPES[j];
								Slot slt = new Slot(dt, this.daysOfWeek[i - 6], purpose, sTime, eTime);

								for (int k = 1; k < splitDateVenue.length; k++) {
									String roomName = splitDateVenue[k];
									ClassRoom c = getCorrespondingRoom(roomName);
									Course crs = getCorrespondingCourse(courseDetails[1]);

									Map<String, Map<Slot, Object>> newMap = c.getBookedSlots();

									if (newMap.containsKey(this.daysOfWeek[i - 6])) {

										newMap.get(this.daysOfWeek[i - 6]).put(slt, crs);

									} else {

										Map<Slot, Object> map = new HashMap<Slot, Object>();
										map.put(slt, crs);
										newMap.put(this.daysOfWeek[i - 6], map);

									}

									c.setBookedSlots(newMap);

								}

							}

						}

					}

				}
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This function returns the ClassRoom object according to the RoomNumber
	 * provided.
	 * 
	 * @param venue
	 * @return ClassRoom
	 */
	public ClassRoom getCorrespondingRoom(String venue) {
		for (int i = 0; i < allRooms.size(); i++) {
			if (allRooms.get(i).getRoomNumber().equals(venue)) {
				return allRooms.get(i);
			}
		}
		return null;
	}

	/**
	 * This function returns the Course object according to the courseName input
	 * by the user.
	 * 
	 * @param name
	 * @return Course
	 */
	public Course getCorrespondingCourse(String name) {
		for (int i = 0; i < allCourses.size(); i++) {
			if (allCourses.get(i).getCourseName().equals(name)) {
				return allCourses.get(i);
			}
		}
		return null;
	}

	/**
	 * This function checks if the word contains only alphabet or not
	 * 
	 * @param word
	 * @return String
	 */
	public String checkAlpha(String word) {
		char[] charWord = word.toCharArray();
		String newString = "";
		for (int i = 0; i < charWord.length; i++) {
			if (Character.isLetter(charWord[i])) {
				newString = newString + charWord[i];

			}
		}
		return newString;
	}

	/**
	 * Returns +1 and -1 based on mandatory/elective course
	 * 
	 * @param type
	 * @return int
	 */
	public int getType(String type) {

		if (type.equals("Mandatory")) {
			return 1;
		} else {
			return -1;
		}

	}

	/**
	 * This function serializes the allRooms arraylist into rooms.txt file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void serializeRooms() throws FileNotFoundException, IOException {

		ObjectOutputStream out = null;

		try {

			out = new ObjectOutputStream(new FileOutputStream("./src/res/rooms.txt"));

			for (int i = 0; i < allRooms.size(); i++) {
				ClassRoom classroom = allRooms.get(i);
				out.writeObject(classroom);
			}

		} finally {

			out.close();

		}

	}

	/**
	 * This function serializes the allCourses array list into courses.txt file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void serializeCourses() throws FileNotFoundException, IOException {

		ObjectOutputStream out = null;

		try {

			out = new ObjectOutputStream(new FileOutputStream("./src/res/courses.txt"));

			for (int i = 0; i < allCourses.size(); i++) {
				Course course = allCourses.get(i);
				out.writeObject(course);
			}

		} finally {

			out.close();

		}

	}

	/**
	 * This function serializes the autoCompleteText array list into
	 * autocomplete.txt file.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void serializeAutoCompleteText() throws FileNotFoundException, IOException {

		ObjectOutputStream out = null;

		try {

			out = new ObjectOutputStream(new FileOutputStream("./src/res/autocomplete.txt"));

			for (int i = 0; i < autoCompleteText.size(); i++) {
				String word = autoCompleteText.get(i);
				out.writeObject(word);
			}

		} finally {

			out.close();

		}

	}

	/**
	 * This function generates the keywords according to the postconditions.
	 * 
	 * @param postConditions
	 * @param name
	 */
	public void generateKeywords(String postConditions, String name) {

		String[] postCons = postConditions.split("\\\\");

		for (int i = 0; i < postCons.length; i++) {

			String[] allWords = postCons[i].split(" ");

			for (int j = 0; j < allWords.length; j++) {

				allWords[j] = checkAlpha(allWords[j]).toLowerCase();
				allWords[j] = allWords[j].replaceAll("�", "");
				if (!exemptedWords.contains(allWords[j])) {

					if (frequency.containsKey(allWords[j])) {
						int count = frequency.get(allWords[j]);
						frequency.replace(allWords[j], count + 1);
					} else {
						frequency.put(allWords[j], 1);
					}

				}
			}

		}

		String[] courseName = name.split(" ");
		for (int k = 0; k < courseName.length; k++) {

			if (!exemptedWords.contains(courseName[k])) {
				courseName[k] = checkAlpha(courseName[k]).toLowerCase();
				courseName[k] = courseName[k].replaceAll("�", "");
				if (frequency.containsKey(courseName[k])) {
					int count = frequency.get(courseName[k]);
					frequency.replace(courseName[k], count + 1);
				} else {
					frequency.put(courseName[k], 1);
				}
			}

		}

	}

	/**
	 * This function sorts the map in decreasing order of the values i.e. is the
	 * frequency
	 * 
	 * @param map
	 * @return sortedEntries
	 */
	static <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});

		return sortedEntries;
	}

	/**
	 * Main method of the Database Generator
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		DatabaseGenerator ob = new DatabaseGenerator();

		for (int i = 0; i < Constants.words.length; i++) {

			ob.exemptedWords.add(Constants.words[i].toLowerCase());
		}

		ob.populateRooms();

		ob.populateCourses();

		ob.bookSlots();

		ob.serializeRooms();

		ob.serializeCourses();

		List<Entry<String, Integer>> ebt = entriesSortedByValues(ob.frequency);

		for (int i = 0; i < ebt.size(); i++) {
			ob.autoCompleteText.add(ebt.get(i).getKey());
		}

		ob.serializeAutoCompleteText();

	}

}
