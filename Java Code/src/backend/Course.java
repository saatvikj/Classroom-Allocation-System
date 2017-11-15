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
	 * 
	 * @return courseTimeTable
	 */
	public Map<Slot, List<ClassRoom>> getCourseTimeTable() {
		return courseTimeTable;
	}

	/**
	 * 
	 * @param courseTimeTable
	 */
	public void setCourseTimeTable(Map<Slot, List<ClassRoom>> courseTimeTable) {
		this.courseTimeTable = courseTimeTable;
	}

	/**
	 * 
	 */
	public Course() {

	}

	/**
	 * 
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * 
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * 
	 * @return preReqs
	 */
	public String[] getPreReqs() {
		return preReqs;
	}

	/**
	 * 
	 * @param preReqs
	 */
	public void setPreReqs(String[] preReqs) {
		this.preReqs = preReqs;
	}

	/**
	 * 
	 * @return postConditions
	 */
	public String[] getPostConditions() {
		return postConditions;
	}

	/**
	 * 
	 * @param postConditions
	 */
	public void setPostConditions(String[] postConditions) {
		this.postConditions = postConditions;
	}

	/**
	 * 
	 * @return acronym
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * 
	 * @param acronym
	 */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	/**
	 * 
	 * @return courseType
	 */
	public int getCourseType() {
		return courseType;
	}

	/**
	 * 
	 * @param courseType
	 */
	public void setCourseType(int courseType) {
		this.courseType = courseType;
	}

	/**
	 * 
	 * @return instructor
	 */
	public String getInstructor() {
		return instructor;
	}

	/**
	 * 
	 * @param instructor
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	/**
	 * 
	 * @return courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * 
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * 
	 * @return credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * 
	 * @param credits
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}

	/**
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
