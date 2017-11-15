package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Saatvik Jain & Meghna Gupta
 *
 */
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

	/**
	 * Getter function to return the Map courseTimeTable
	 * 
	 * @return courseTimeTable
	 */
	public Map<Slot, List<ClassRoom>> getCourseTimeTable() {
		return courseTimeTable;
	}

	/**
	 * Setter function that sets the value of courseTimeTable
	 * 
	 * @param courseTimeTable
	 */
	public void setCourseTimeTable(Map<Slot, List<ClassRoom>> courseTimeTable) {
		this.courseTimeTable = courseTimeTable;
	}

	/**
	 * Default constructor of the class Course
	 */
	public Course() {

	}

	/**
	 * Getter function that returns the course name
	 * 
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Setter function that sets the value of courseName
	 * 
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Getter function to return the pre-requisites of that particular course
	 * 
	 * @return preReqs
	 */
	public String[] getPreReqs() {
		return preReqs;
	}

	/**
	 * Setter function that sets the value of pre-requisites
	 * 
	 * @param preReqs
	 */
	public void setPreReqs(String[] preReqs) {
		this.preReqs = preReqs;
	}

	/**
	 * Getter function that returns the postconditions of that particular course
	 * 
	 * @return postConditions
	 */
	public String[] getPostConditions() {
		return postConditions;
	}

	/**
	 * Setter function that sets the postCondition of a course
	 * 
	 * @param postConditions
	 */
	public void setPostConditions(String[] postConditions) {
		this.postConditions = postConditions;
	}

	/**
	 * Getter function that returns the acronym of a course
	 * 
	 * @return acronym
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * Setter function that sets the value of acronym of a course
	 * 
	 * @param acronym
	 */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	/**
	 * Getter function that returns the courseType-credit/audit
	 * 
	 * @return courseType
	 */
	public int getCourseType() {
		return courseType;
	}

	/**
	 * Setter function that sets the courseType-credit/audit
	 * 
	 * @param courseType
	 */
	public void setCourseType(int courseType) {
		this.courseType = courseType;
	}

	/**
	 * Getter function that returns the name of the instructor
	 * 
	 * @return instructor
	 */
	public String getInstructor() {
		return instructor;
	}

	/**
	 * Setter function to set the name of the instructor
	 * 
	 * @param instructor
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	/**
	 * Getter function that return the code of a Course
	 * 
	 * @return courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * Setter function to set the code of a course
	 * 
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * Getter function to return the credits of a course
	 * 
	 * @return credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Setter function to set the number of credits of a course
	 * 
	 * @param credits
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}

	/**
	 * toString method for the class Course which displays all the attributes of
	 * a course object.
	 * 
	 * @return string
	 */
	@Override
	public String toString() {
		return "Course [preReqs=" + Arrays.toString(preReqs) + ", postConditions=" + Arrays.toString(postConditions)
				+ ", acronym=" + acronym + ", courseType=" + courseType + ", instructor=" + instructor + ", courseCode="
				+ courseCode + ", credits=" + credits + ", courseName=" + courseName + ", courseTimeTable="
				+ courseTimeTable + ", allRooms=" + allRooms + "]";
	}

}
