package backend;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import exceptions.NoResultFoundException;

public class Student extends User {

	private Map<Slot, Course> timetable;
	private transient ArrayList<Request> allRequests = new ArrayList<>();

	public Student(String name, String emailID, String encryptedPassword, String typeOfUser) {
		super(name, emailID, encryptedPassword, typeOfUser);
		// TODO Auto-generated constructor stub
	}

	public Map<Slot, Course> getTimetable() {
		return timetable;
	}

	public void setTimetable(Map<Slot, Course> timetable) {
		this.timetable = timetable;
	}

	public ArrayList<Request> getAllRequests() {
		return allRequests;
	}

	public void setAllRequests(ArrayList<Request> allRequests) {
		this.allRequests = allRequests;
	}

	@Override
	public void makeBooking(ClassRoom reqRoom, Slot reqSlot, int reqCapacity)
			throws ClassNotFoundException, IOException {

		System.out.println("Inside make booking function");
		deserializeRequests();
		Request studentRequest = new Request();
		studentRequest.setCurrentStatus(0);
		studentRequest.setPreferredRoom(reqRoom);
		studentRequest.setPurpose(reqSlot.getPurpose());
		studentRequest.setRequiredCapacity(reqCapacity);
		studentRequest.setSourceStudent(this);
		studentRequest.setTimeSlot(reqSlot);
		studentRequest.setTimeStamp(new Date());
		allRequests.add(studentRequest);
		serializeRequests();

	}

	public void addToTimeTable(Slot _slot, Course _course) {

	}

	public ArrayList<Course> giveRelevantCourses(String[] keywords, boolean audit) throws NoResultFoundException {
		return null;
	}

	public void viewTimeTable() {

	}

	public void cancelRequest(Slot timeSlot) {

	}

	public ArrayList<String> populateNotifications() {
		return super.populateNotifications();
	}

	public void deserializeRequests() throws ClassNotFoundException, IOException {

		ObjectInputStream in = null;
		allRequests = new ArrayList<Request>();
		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/requests.txt"));
			Request request;

			try {

				while (true) {
					request = (Request) in.readObject();
					System.out.println(request.getPreferredRoom().getRoomNumber() + " " + request.getCurrentStatus());
					if (request.getCurrentStatus() == 0) {
						allRequests.add(request);
					} else if (request.getCurrentStatus() == 1) {
						if (request.getSourceStudent().getEmailID().equals(this.getEmailID())) {

							Map<Slot, ClassRoom> bookedMap = this.getBookedRooms();

							boolean flag = false;

							for (Map.Entry<Slot, ClassRoom> entry : bookedMap.entrySet()) {
								Slot key = entry.getKey();
								ClassRoom value = entry.getValue();
								if (key.displayFormattedDate().equals(request.getTimeSlot().displayFormattedDate())
										&& value.getRoomNumber().equals(request.getPreferredRoom().getRoomNumber())) {
									flag = true;
								}
							}

							if (!flag) {
								bookedMap.put(request.getTimeSlot(), request.getPreferredRoom());
							}

							if (this.listOfNotifications != null && !(this.listOfNotifications.contains("Your request for " + request.getPreferredRoom().getRoomNumber().toUpperCase()
												+ " has been accepted!"))) {
								this.listOfNotifications.add(
										"Your request for " + request.getPreferredRoom().getRoomNumber().toUpperCase()
												+ " has been accepted!");
							} else {
								this.listOfNotifications = new ArrayList<>();
								this.listOfNotifications.add(
										"Your request for " + request.getPreferredRoom().getRoomNumber().toUpperCase()
												+ " has been accepted!");
							}
						}
					} else {

						if (request.getSourceStudent().getEmailID().equals(this.getEmailID())) {
							if (this.listOfNotifications != null && !(this.listOfNotifications.contains("Your request for " + request.getPreferredRoom().getRoomNumber().toUpperCase()
									+ " has been rejected!"))) {
								this.listOfNotifications.add(
										"Your request for " + request.getPreferredRoom().getRoomNumber().toUpperCase()
												+ " has been rejected!");
							} else {
								this.listOfNotifications = new ArrayList<>();
								this.listOfNotifications.add(
										"Your request for " + request.getPreferredRoom().getRoomNumber().toUpperCase()
												+ " has been rejected!");
							}
						}
					}

				}

			} catch (EOFException e) {

			}

		} catch (FileNotFoundException e) {

		} finally {

			if (in != null) {
				in.close();
			} else {
				allRequests = new ArrayList<Request>();
			}
		}

	}

	public void serializeRequests() throws FileNotFoundException, IOException {

		ObjectOutputStream out = null;

		try {

			out = new ObjectOutputStream(new FileOutputStream("./src/res/requests.txt"));

			for (int i = 0; i < allRequests.size(); i++) {
				Request request = allRequests.get(i);
				if (!request.checkExpiry()) {
					out.writeObject(request);
				}
			}

		} finally {

			out.close();

		}

	}

}
