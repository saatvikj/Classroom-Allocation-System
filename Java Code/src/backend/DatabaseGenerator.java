package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseGenerator {
	
	private ArrayList<Course> allCourses;
	private ArrayList<ClassRoom> allRooms;
	private String fileName = "sample.txt";

	public void populateRooms()
	{
		BufferedReader br = null;
		String line = "";
		String splitby = ",";
		
		try {
			br = new BufferedReader(new FileReader(this.fileName));
			int count = 0;
			while((line = br.readLine()) != null)
			{
				count = count + 1;
				if(count != 1)
				{
					String[] courseDetails = line.split(splitby);
					checkAndMakeRoom(courseDetails);
				}
				
			}
		}
		catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null)
			{
				try{
					br.close();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}
	
	public void checkAndMakeRoom(String[] details)
	{
		for(int i = 6; i<= 10; i++)
		{
			if(!(details[i].equals("-")))
			{
				String[] splitDateVenue = details[i].split("&");
				String roomName = splitDateVenue[1];
				if(checkNewRoom(roomName))
				{
					ClassRoom rm = new ClassRoom();
					rm.setRoomNumber(roomName);
					rm.setCapacity(100);
					allRooms.add(rm);	
				}
				
			}
		}
	}
	
	public boolean checkNewRoom(String roomName)
	{
		for(int i = 0; i < allRooms.size(); i++)
		{
			if(allRooms.get(i).getRoomNumber().equals(roomName))
			{
				return false;
			}
		}
		return true;
	}
	
	public void populateCourses()
	{
		
	}
	
	public void serialize()
	{
		
	}
	
	public static void main(String[] args)
	{
		DatabaseGenerator ob = new DatabaseGenerator();
		//ob.populateCourses();
		ob.populateRooms();
		for(int i = 0; i < ob.allRooms.size();i++)
		{
			System.out.println(ob.allRooms.get(i).getRoomNumber());
		}
	}
	
	

}
