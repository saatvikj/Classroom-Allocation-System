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
import java.util.HashMap;
import java.util.Map;

import exceptions.NoResultFoundException;

public class Student extends User {

	private Map<Slot, Course> timetable;
	private transient ArrayList<Request> allRequests = new ArrayList<>();
	private transient ArrayList<Course> allCourses = new ArrayList<>();

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

	public void addToTimeTable(Course _course) {

		for (Map.Entry<Slot, ClassRoom> courseTimeTable : _course.getCourseTimeTable().entrySet()) {

			Slot slt = courseTimeTable.getKey();
			
			if (timetable != null) {
				if (slt.getPurpose().equals(Slot.TYPES[0])) {
					System.out.println(slt.getDay());
					timetable.put(slt, _course);
				}
			} else {
				timetable = new HashMap<Slot, Course>();
				if (slt.getPurpose().equals(Slot.TYPES[0])) {
					System.out.println(slt.getDay());
					timetable.put(slt, _course);
				}
			}
		}
	}

	public ArrayList<Course> giveRelevantCourses(String[] keywords, boolean audit)
			throws NoResultFoundException, ClassNotFoundException, FileNotFoundException, IOException {

		deserializeCourses();

		ArrayList<Course> relevantCourses = new ArrayList<>();
		for (int i = 0; i < allCourses.size(); i++) {

			boolean currCourseRelevancy = false;
			for (int j = 0; j < allCourses.get(i).getPostConditions().length; j++) {
				String postCon = allCourses.get(i).getPostConditions()[j].toLowerCase();
				for (int k = 0; k < keywords.length; k++) {
					if (postCon.contains(keywords[k].toLowerCase())) {
						currCourseRelevancy = true;
					}
				}
			}

			String name = allCourses.get(i).getCourseName();
			String splitName[] = name.split(" ");

			for (int l = 0; l < splitName.length; l++) {
				for (int k = 0; k < keywords.length; k++) {
					if (keywords[k].equalsIgnoreCase(splitName[l])) {
						currCourseRelevancy = true;
					}
				}
			}

			if (currCourseRelevancy) {
				relevantCourses.add(allCourses.get(i));
			}
		}

		if (relevantCourses.isEmpty()) {
			throw new NoResultFoundException("No courses match your search!");
		} else {
			return relevantCourses;
		}

	}

	public void viewTimeTable() {

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

							if (this.listOfNotifications != null && !(this.listOfNotifications.contains(
									"Your request for " + request.getPreferredRoom().getRoomNumber().toUpperCase()
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
							if (this.listOfNotifications != null && !(this.listOfNotifications.contains(
									"Your request for " + request.getPreferredRoom().getRoomNumber().toUpperCase()
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

	public void deserializeCourses() throws IOException, ClassNotFoundException, FileNotFoundException {

		/*
		 * Deserializes the list of registered users into the ArrayList so that
		 * checking can be done.
		 * 
		 */
		ObjectInputStream in = null;
		allCourses = new ArrayList<>();

		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/courses.txt"));
			Course course;

			try {

				while (true) {
					course = (Course) in.readObject();
					allCourses.add(course);
				}

			} catch (EOFException e) {

			}

		} catch (FileNotFoundException e) {

		} finally {

			if (in != null) {
				in.close();
			} else {
				allCourses = new ArrayList<Course>();
			}

		}

	}

}
