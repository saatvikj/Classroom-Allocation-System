package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	private String[] preReqs;
	private String[] postConditions;
	private String acronym;
	private int courseType; // -1 for elective, 1 for mandatory
	private String instructor;
	private String courseCode;
	private int credits;
	private String courseName;
	private Map<Slot, List<ClassRoom>> courseTimeTable = new HashMap<Slot, List<ClassRoom>>();
	private transient ArrayList<ClassRoom> allRooms;

	public Map<Slot, List<ClassRoom>> getCourseTimeTable() {
		return courseTimeTable;
	}

	public void setCourseTimeTable(Map<Slot, List<ClassRoom>> courseTimeTable) {
		this.courseTimeTable = courseTimeTable;
	}

	public Course() {

	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String[] getPreReqs() {
		return preReqs;
	}

	public void setPreReqs(String[] preReqs) {
		this.preReqs = preReqs;
	}

	public String[] getPostConditions() {
		return postConditions;
	}

	public void setPostConditions(String[] postConditions) {
		this.postConditions = postConditions;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public int getCourseType() {
		return courseType;
	}

	public void setCourseType(int courseType) {
		this.courseType = courseType;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public void bookRooms() {
		/*
		 * Iterate over the the Time Table, and then call the function
		 * makeBooking of the ClassRoom object as ClassRoom.makeBooking(Slot,
		 * this) and then makes the same changes in the object in the arraylist
		 * as well and at the end serializes the entire arraylist. If two slots
		 * have same start and end time and same day, we will add two separate
		 * key value pairs in the hashmap corresponding to that day which will
		 * be differentiated on the basis of Date.
		 */
	}

	public void deserialize() {
		/*
		 * It deserializes the entire text file and stores it in the arraylist.
		 */
	}

	public void serialize() {
		/*
		 * It serializes the arraylist allRooms into its text file.
		 */
	}

	@Override
	public String toString() {
		return "Course [preReqs=" + Arrays.toString(preReqs) + ", postConditions=" + Arrays.toString(postConditions)
				+ ", acronym=" + acronym + ", courseType=" + courseType + ", instructor=" + instructor + ", courseCode="
				+ courseCode + ", credits=" + credits + ", courseName=" + courseName + ", courseTimeTable="
				+ courseTimeTable + ", allRooms=" + allRooms + "]";
	}

}
