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
import java.util.Iterator;
import java.util.Map;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String name;
	protected String emailID;
	protected String encryptedPassword;
	protected Map<Slot, ClassRoom> bookedRooms;
	protected String typeOfUser;
	public ArrayList<String> listOfNotifications;
	private ArrayList<ClassRoom> allRooms = new ArrayList<ClassRoom>();

	public User() {

	}

	public User(String name, String emailID, String encryptedPassword, String typeOfUser) {
		super();
		this.name = name;
		this.emailID = emailID;
		this.encryptedPassword = encryptedPassword;
		this.typeOfUser = typeOfUser;
	}

	public String getTypeOfUser() {
		return typeOfUser;
	}

	public void setTypeOfUser(String typeOfUser) {
		this.typeOfUser = typeOfUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public Map<Slot, ClassRoom> getBookedRooms() {
		return bookedRooms;
	}

	public void setBookedRooms(Map<Slot, ClassRoom> bookedRooms) {
		this.bookedRooms = bookedRooms;
	}

	public ArrayList<String> getListOfNotifications() {
		return listOfNotifications;
	}

	public void setListOfNotifications(ArrayList<String> listOfNotifications) {
		this.listOfNotifications = listOfNotifications;
	}

	public void viewRoomBooked() {
		/*
		 * Iterates through the hashmap and prints all the rooms booked by that
		 * User, else prints a fancy message of no bookings.
		 */
	}

	public ArrayList<ClassRoom> checkRoomAvailability(ClassRoom reqRoom, Slot reqSlot, int reqCap)
			throws ClassNotFoundException, IOException {
		/*
		 * It directly calls the method checkIfEmptyInSlot of the reqRoom object
		 * with reqSlot as the parameter.
		 */
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

	public boolean checkIfValidSlot(Slot slot1, Slot slot2) {
		if (slot1.equals(slot2)) {
			if ((slot1.getDate().equals(new Date(0000, 00, 00)))) {
				return false;
			} else {
				return true;
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

	public void makeBooking(ClassRoom reqRoom, Slot reqSlot, int reqCapacity)
			throws ClassNotFoundException, IOException {
		/*
		 * It calls the method makeBooking of the reqRoom object with reqSlot as
		 * the first parameter rest is handled by inheritance (comment).
		 */

		deserializeRooms();
		for (int i = 0; i < allRooms.size(); i++) {

			if (allRooms.get(i).getRoomNumber().equals(reqRoom.getRoomNumber())) {

				allRooms.get(i).getBookedSlots().get(reqSlot.getDay()).put(reqSlot, this);
				System.out.println("Booking confirmed");
			}

		}
		
		serializeRooms();

	}

	public void cancelBooking(ClassRoom bookedRoom, Slot bookedSlot) {
		/*
		 * It calls the method cancelBooking from bookedRoom object with
		 * bookedSlot as parameter and then makes this entry in its own
		 * hashmap(bookedRooms) as null.
		 */
	}

	public void populateNotifications() {
		/*
		 * It goes through the required things to give notification about and
		 * refreshes the listOfNotifications attribute. (New requests for admin,
		 * request status for student etc etc)
		 */
	}

	public void deserialize() {

	}

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

	public ClassRoom getCorrespondingRoom(String venue) throws ClassNotFoundException, IOException {

		deserializeRooms();
		for (int i = 0; i < allRooms.size(); i++) {
			if (allRooms.get(i).getRoomNumber().equals(venue)) {
				return allRooms.get(i);
			}
		}
		return null;
	}

}
