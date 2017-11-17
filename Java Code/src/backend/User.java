package backend;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String name;
	protected String emailID;
	protected String encryptedPassword;
	protected Map<Slot, ClassRoom> bookedRooms = new HashMap<Slot, ClassRoom>();
	protected String typeOfUser;
	protected String securityQuestion;
	protected String securityAnswer;
	public transient ArrayList<String> listOfNotifications = new ArrayList<String>();
	protected transient ArrayList<ClassRoom> allRooms = new ArrayList<ClassRoom>();

	/**
	 * Default constructor of User class
	 */
	public User() {

	}

	/**
	 * Parameterized constructor of User class
	 * 
	 * @param name
	 * @param emailID
	 * @param encryptedPassword
	 * @param typeOfUser
	 */
	public User(String name, String emailID, String encryptedPassword, String typeOfUser, String securityQuestion, String securityAnswer) {
		super();
		this.name = name;
		this.emailID = emailID;
		this.encryptedPassword = encryptedPassword;
		this.typeOfUser = typeOfUser;
		this.securityQuestion = securityQuestion;
		this.securityAnswer = securityAnswer;
	}

	/**
	 * Getter method that returns the typeOfUser
	 * 
	 * @return typeOfUser
	 */
	public String getTypeOfUser() {
		return typeOfUser;
	}

	/**
	 * Setter method that sets the typeOfUser
	 * 
	 * @param typeOfUser
	 */
	public void setTypeOfUser(String typeOfUser) {
		this.typeOfUser = typeOfUser;
	}

	/**
	 * Getter method that returns the name of the user
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method that sets the name of the user
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method that returns the email id of a user
	 * 
	 * @return emailID
	 */
	public String getEmailID() {
		return emailID;
	}

	/**
	 * Setter method that sets the email id of a user
	 * 
	 * @param emailID
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	/**
	 * Getter method that returns the encrypted password of a user
	 * 
	 * @return encryptedPassword
	 */
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	/**
	 * Setter method that sets the encrypted password of a user
	 * 
	 * @param encryptedPassword
	 */
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	/**
	 * Getter method that returns the security question
	 * 
	 * @return securityQuestion
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * Setter method that sets the security question
	 * 
	 * @param securityQuestion
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	/**
	 * Getter method that returns the security answer
	 * 
	 * @return securityAnswer
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	/**
	 * Setter method that sets the value of securityAnswer
	 * 
	 * @param securityAnswer
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/**
	 * Getter method that returns the bookedRooms of a user
	 * 
	 * @return bookedRooms
	 */
	public Map<Slot, ClassRoom> getBookedRooms() {
		return bookedRooms;
	}

	/**
	 * Setter method that sets the value of booked rooms of a user
	 * 
	 * @param bookedRooms
	 */
	public void setBookedRooms(Map<Slot, ClassRoom> bookedRooms) {
		this.bookedRooms = bookedRooms;
	}

	/**
	 * Getter method that returns the list of notifications of a user
	 * 
	 * @return listOfNotifications
	 */
	public ArrayList<String> getListOfNotifications() {
		return listOfNotifications;
	}

	/**
	 * Setter method that sets the value of listOfBotifications
	 * 
	 * @param listOfNotifications
	 */
	public void setListOfNotifications(ArrayList<String> listOfNotifications) {
		this.listOfNotifications = listOfNotifications;
	}

	/**
	 * It directly calls the method checkIfValidSlot with reqSlot as the
	 * parameter.
	 * 
	 * @param reqRoom
	 * @param reqSlot
	 * @param reqCap
	 * @return eligibleRooms
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public ArrayList<ClassRoom> checkRoomAvailability(ClassRoom reqRoom, Slot reqSlot, int reqCap)
			throws ClassNotFoundException, IOException {

		deserializeRooms();

		ArrayList<ClassRoom> eligibleRooms = new ArrayList<ClassRoom>();

		for (int i = 0; i < allRooms.size(); i++) {
			ClassRoom cr = allRooms.get(i);
			String day = reqSlot.getDay();
			if (allRooms.get(i).getBookedSlots().containsKey(day) && reqCap <= cr.getCapacity()) {
				Map<Slot, Object> dayMap = allRooms.get(i).getBookedSlots().get(day);
				Iterator it = dayMap.entrySet().iterator();
				boolean res = true;
				while (it.hasNext()) {
					Map.Entry<Slot, Object> pair = (Map.Entry<Slot, Object>) it.next();
					Slot slt = pair.getKey();
					res = res && checkIfValidSlot(slt, reqSlot);
				}

				if (res) {
					eligibleRooms.add(cr);
				}

			} else if (!(allRooms.get(i).getBookedSlots().containsKey(day)) && reqCap <= cr.getCapacity()) {
				eligibleRooms.add(cr);
			}
		}

		return eligibleRooms;
	}

	/**
	 * Checks if both the slots passed as parameter clash or not
	 * 
	 * @param slot1
	 * @param slot2
	 * @return boolean
	 */
	public boolean checkIfValidSlot(Slot slot1, Slot slot2) {
		if (slot1.equals(slot2)) {
			if ((slot1.getDate().equals(new Date(0000, 00, 00)))) {
				return false;
			} else {

				if (slot1.getDate().equals(slot2.getDate())) {
					return false;
				} else {
					return true;
				}

			}
		} else {

			if ((slot1.getStartTime().getHours() < slot2.getStartTime().getHours())
					|| ((slot1.getStartTime().getHours() == slot2.getStartTime().getHours())
							&& ((slot1.getStartTime().getMinutes() < slot2.getStartTime().getMinutes())))) {
				if (slot1.getEndTime().getHours() <= slot2.getStartTime().getHours()) {
					return true;
				}
			} else if ((slot1.getStartTime().getHours() > slot2.getStartTime().getHours())
					|| ((slot1.getStartTime().getHours() == slot2.getStartTime().getHours())
							&& ((slot1.getStartTime().getMinutes() > slot2.getStartTime().getMinutes())))) {
				if (slot2.getEndTime().getHours() <= slot1.getStartTime().getHours()) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * 
	 * It calls the method makeBooking of the reqRoom object with reqSlot as the
	 * first parameter rest is handled by inheritance (comment).
	 * 
	 * @param reqRoom
	 * @param reqSlot
	 * @param reqCapacity
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void makeBooking(ClassRoom reqRoom, Slot reqSlot, int reqCapacity)
			throws ClassNotFoundException, IOException {

		deserializeRooms();
		for (int i = 0; i < allRooms.size(); i++) {

			if (allRooms.get(i).getRoomNumber().equalsIgnoreCase(reqRoom.getRoomNumber())) {

				if (allRooms.get(i).getBookedSlots().containsKey(reqSlot.getDay())) {
					allRooms.get(i).getBookedSlots().get(reqSlot.getDay()).put(reqSlot, this);

				} else {
					Map<Slot, Object> newmap = new HashMap<Slot, Object>();
					newmap.put(reqSlot, this);
					allRooms.get(i).getBookedSlots().put(reqSlot.getDay(), newmap);
				}
			}

		}

		if (bookedRooms == null) {
			bookedRooms = new HashMap<Slot, ClassRoom>();
			bookedRooms.put(reqSlot, reqRoom);
		} else {
			bookedRooms.put(reqSlot, reqRoom);
		}

		serializeRooms();

	}

	/**
	 * 
	 * It calls the method cancelBooking from bookedRoom object with bookedSlot
	 * as parameter and then makes this entry in its own hashmap(bookedRooms) as
	 * null.
	 * 
	 * @param bookedRoom
	 * @param bookedSlot
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void cancelBooking(ClassRoom bookedRoom, Slot bookedSlot)
			throws FileNotFoundException, IOException, ClassNotFoundException {

		deserializeRooms();
		for (int i = 0; i < allRooms.size(); i++) {

			if (allRooms.get(i).getRoomNumber().equalsIgnoreCase(bookedRoom.getRoomNumber())) {

				if (allRooms.get(i).getBookedSlots().containsKey(bookedSlot.getDay())) {

					for (Map.Entry<Slot, Object> map : allRooms.get(i).getBookedSlots().get(bookedSlot.getDay())
							.entrySet()) {
						Slot key = map.getKey();
						Object value = map.getValue();
						if (key.getDate().getDate() == bookedSlot.getDate().getDate()
								&& key.getDate().getMonth() == bookedSlot.getDate().getMonth()
								&& key.getDate().getYear() == bookedSlot.getDate().getYear()
								&& key.getStartTime().equals(bookedSlot.getStartTime())
								&& key.getEndTime().equals(bookedSlot.getEndTime())) {
							allRooms.get(i).getBookedSlots().get(bookedSlot.getDay()).remove(key);
							break;
						}
					}

				}

			}
		}
		serializeRooms();

	}

	/**
	 * It goes through the required things to give notification about and
	 * refreshes the listOfNotifications attribute. (New requests for admin,
	 * request status for student etc etc)
	 * 
	 * @return listOfNotifications
	 */
	public ArrayList<String> populateNotifications() {

		listOfNotifications = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		for (Map.Entry<Slot, ClassRoom> map : bookedRooms.entrySet()) {
			Slot key = map.getKey();
			ClassRoom value = map.getValue();

			if (key.getDate().getDay() == day - 1) {
				String notification = "You have " + value.getRoomNumber().toUpperCase() + " booked today at "
						+ key.getStartTime();
				listOfNotifications.add(notification);
			}
		}

		return this.listOfNotifications;

	}

	/**
	 * This function serializes the rooms in rooms.txt file
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
	 * This method deserializes the rooms.txt file
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void deserializeRooms() throws IOException, ClassNotFoundException {
		/*
		 * Deserialize the file listofcourses.txt into the arraylist
		 */

		ObjectInputStream in = null;
		allRooms = new ArrayList<ClassRoom>();
		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/rooms.txt"));
			ClassRoom room;

			try {

				while (true) {
					room = (ClassRoom) in.readObject();
					allRooms.add(room);
				}

			} catch (EOFException e) {

			}

		} finally {

			in.close();

		}

	}

	/**
	 * This function returns the room object according to the roomNumber
	 * entered.
	 * 
	 * @param venue
	 * @return ClassRoom
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public ClassRoom getCorrespondingRoom(String venue) throws ClassNotFoundException, IOException {

		deserializeRooms();
		for (int i = 0; i < allRooms.size(); i++) {
			if (allRooms.get(i).getRoomNumber().equalsIgnoreCase(venue)) {
				return allRooms.get(i);
			}
		}
		return null;
	}

}
