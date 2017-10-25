package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DatabaseGenerator {

	private ArrayList<Course> allCourses = new ArrayList<Course>();
	private ArrayList<ClassRoom> allRooms = new ArrayList<ClassRoom>();
	private String fileName = "./src/res/MainTT.csv";
	private String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

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

	public void checkAndMakeRoom(String[] details) {

		for (int i = 6; i <= 10; i++) {

			if (!(details[i].equals("-"))) {
				String[] splitDateVenue = details[i].split("\\$");
				String roomName = splitDateVenue[1];
				if (checkNewRoom(roomName)) {
					ClassRoom rm = new ClassRoom();
					rm.setRoomNumber(roomName);
					rm.setCapacity(100);
					allRooms.add(rm);
				}

			}
		}

	}

	public boolean checkNewRoom(String roomName) {
		for (int i = 0; i < allRooms.size(); i++) {
			if (allRooms.get(i).getRoomNumber().equals(roomName)) {
				return false;
			}
		}
		return true;
	}

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
					c.setPostConditions(postCon.split("\\\\"));

					for (int i = 6; i <= 10; i++) {

						if (!(courseDetails[i].equals("-"))) {

							String[] splitDateVenue = courseDetails[i].split("\\$");
							String roomName = splitDateVenue[1];
							Date dt = new Date(0000, 00, 00);
							String startTime = splitDateVenue[0].split("\\-")[0];

							int startTimeHour = Integer.parseInt(startTime.split("\\:")[0]);
							int startTimeMinute = Integer.parseInt(startTime.split("\\:")[1]);
							Time sTime = new Time(startTimeHour, startTimeMinute, 0);
							String endTime = splitDateVenue[0].split("\\-")[1];
							int endTimeHour = Integer.parseInt(endTime.split("\\:")[0]);
							int endTimeMinute = Integer.parseInt(endTime.split("\\:")[1]);
							Time eTime = new Time(endTimeHour, endTimeMinute, 0);
							Slot slt = new Slot(dt, this.daysOfWeek[i - 6], sTime, eTime);
							ClassRoom slotRoom = getCorrespondingRoom(splitDateVenue[1]);
							Map<Slot, ClassRoom> newTT = c.getCourseTimeTable();
							newTT.put(slt, slotRoom);
							c.setCourseTimeTable(newTT);

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

						if (!(courseDetails[i].equals("-"))) {
							String[] splitDateVenue = courseDetails[i].split("\\$");
							String roomName = splitDateVenue[1];

							Date dt = new Date(0000, 00, 00);
							String startTime = splitDateVenue[0].split("\\-")[0];
							int startTimeHour = Integer.parseInt(startTime.split("\\:")[0]);
							int startTimeMinute = Integer.parseInt(startTime.split("\\:")[1]);
							Time sTime = new Time(startTimeHour, startTimeMinute, 0);
							String endTime = splitDateVenue[0].split("\\-")[1];
							int endTimeHour = Integer.parseInt(endTime.split("\\:")[0]);
							int endTimeMinute = Integer.parseInt(endTime.split("\\:")[1]);
							Time eTime = new Time(endTimeHour, endTimeMinute, 0);
							Slot slt = new Slot(dt, this.daysOfWeek[i - 6], sTime, eTime);
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

	public ClassRoom getCorrespondingRoom(String venue) {
		for (int i = 0; i < allRooms.size(); i++) {
			if (allRooms.get(i).getRoomNumber().equals(venue)) {
				return allRooms.get(i);
			}
		}
		return null;
	}

	public Course getCorrespondingCourse(String name) {
		for (int i = 0; i < allCourses.size(); i++) {
			if (allCourses.get(i).getCourseName().equals(name)) {
				return allCourses.get(i);
			}
		}
		return null;
	}

	public int getType(String type) {

		if (type.equals("Mandatory")) {
			return 1;
		} else {
			return -1;
		}

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

	public static void main(String[] args) throws FileNotFoundException, IOException {
		DatabaseGenerator ob = new DatabaseGenerator();

		ob.populateRooms();

		ob.populateCourses();

		ob.bookSlots();

		ob.serializeRooms();

		ob.serializeCourses();

	}

}