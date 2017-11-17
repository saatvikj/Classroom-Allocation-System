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
import java.util.List;
import java.util.Map;

import exceptions.NoResultFoundException;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
public class Student extends User {

	private Map<Slot, Course> timetable;
	private transient ArrayList<Request> allRequests = new ArrayList<>();
	private transient ArrayList<Course> allCourses = new ArrayList<>();
	private Map<Course, Integer> tutPref = new HashMap<>();
	private Map<Course, Integer> labPref = new HashMap<>();

	/**
	 * Parameterized constructor for a Student class
	 * 
	 * @param name
	 * @param emailID
	 * @param encryptedPassword
	 * @param typeOfUser
	 * @param securityQuestion
	 * @param securityAnswer
	 */
	public Student(String name, String emailID, String encryptedPassword, String typeOfUser, String securityQuestion, String securityAnswer) {
		super(name, emailID, encryptedPassword, typeOfUser, securityQuestion, securityAnswer);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Getter method that returns the timetable of a student
	 * 
	 * @return timetable
	 */
	public Map<Slot, Course> getTimetable() {
		return timetable;
	}

	/**
	 * Setter method that sets the timetable of a student
	 * 
	 * @param timetable
	 */
	public void setTimetable(Map<Slot, Course> timetable) {
		this.timetable = timetable;
	}

	/**
	 * Getter method that returns the allRequests array list
	 * 
	 * @return allRequests
	 */
	public ArrayList<Request> getAllRequests() {
		return allRequests;
	}

	/**
	 * Setter method that sets the value of allRequests
	 * 
	 * @param allRequests
	 */
	public void setAllRequests(ArrayList<Request> allRequests) {
		this.allRequests = allRequests;
	}

	/**
	 * Getter method that returns tutPref of a student
	 * 
	 * @return tutPref
	 */
	public Map<Course, Integer> getTutPref() {
		return tutPref;
	}

	/**
	 * Setter method that sets the value of tutPref of a student
	 * 
	 * @param tutPref
	 */
	public void setTutPref(Map<Course, Integer> tutPref) {
		this.tutPref = tutPref;
	}

	/**
	 * Getter method that returns labPref of a student
	 * 
	 * @return labPref
	 */
	public Map<Course, Integer> getLabPref() {
		return labPref;
	}

	/**
	 * Setter method that sets the value of labPref of a student
	 * 
	 * @param labPref
	 */
	public void setLabPref(Map<Course, Integer> labPref) {
		this.labPref = labPref;
	}

	/**
	 * The function that makes a request on behalf of a student and then
	 * serializes the allRequests arraylist
	 */
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

	/**
	 * This function adds a course to the timetable of a student
	 * 
	 * @param _course
	 */
	public void addToTimeTable(Course _course) {

		for (Map.Entry<Slot, List<ClassRoom>> courseTimeTable : _course.getCourseTimeTable().entrySet()) {

			Slot slt = courseTimeTable.getKey();

			if (timetable != null) {
				if (slt.getPurpose().equals(Slot.TYPES[0])) {

					timetable.put(slt, _course);
				}
			} else {
				timetable = new HashMap<Slot, Course>();
				if (slt.getPurpose().equals(Slot.TYPES[0])) {

					timetable.put(slt, _course);
				}
			}
		}
	}

	/**
	 * This function adds labs according to the slot provided in the time table
	 * of a student
	 * 
	 * @param _slot
	 * @param _course
	 */
	public void addToTimeTableLabs(Slot _slot, Course _course) {

		for (Map.Entry<Slot, List<ClassRoom>> courseTimeTable : _course.getCourseTimeTable().entrySet()) {

			Slot slt = courseTimeTable.getKey();

			if (timetable != null) {
				if (slt.getPurpose().equals(Slot.TYPES[1])) {

					timetable.put(_slot, _course);
				}
			} else {
				timetable = new HashMap<Slot, Course>();
				if (slt.getPurpose().equals(Slot.TYPES[1])) {

					timetable.put(_slot, _course);
				}
			}
		}
	}

	/**
	 * This function adds tutorials according to the slot provided in the time
	 * table of a student
	 * 
	 * @param _slot
	 * @param _course
	 */
	public void addToTimeTableTutorials(Slot _slot, Course _course) {

		for (Map.Entry<Slot, List<ClassRoom>> courseTimeTable : _course.getCourseTimeTable().entrySet()) {

			Slot slt = courseTimeTable.getKey();

			if (timetable != null) {
				if (slt.getPurpose().equals(Slot.TYPES[2])) {

					timetable.put(_slot, _course);
				}
			} else {
				timetable = new HashMap<Slot, Course>();
				if (slt.getPurpose().equals(Slot.TYPES[2])) {

					timetable.put(_slot, _course);
				}
			}
		}
	}

	/**
	 * 
	 * This function gives the list of relevant courses as per the keywords
	 * entered by a user
	 * 
	 * @param keywords
	 * @param audit
	 * @return relevantCourses
	 * @throws NoResultFoundException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
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

	/**
	 * This function populates the notifications of a student
	 * 
	 * @return notifs
	 */
	public ArrayList<String> populateNotifications() {

		ArrayList<String> notifs = super.populateNotifications();
		try {
			deserializeRequests();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return notifs;
	}

	/**
	 * This function deserializes the requests from a requests.txt
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void deserializeRequests() throws ClassNotFoundException, IOException {

		ObjectInputStream in = null;
		allRequests = new ArrayList<Request>();
		try {

			in = new ObjectInputStream(new FileInputStream("./src/res/requests.txt"));
			Request request;

			try {

				while (true) {
					request = (Request) in.readObject();
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

	/**
	 * This function serializes the requests into a requests.txt file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
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

	/**
	 * This function deserializes the courses.txt file into an arraylist
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
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
