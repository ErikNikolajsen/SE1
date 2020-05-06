package planner.app;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		

		Calendar calendar = Calendar.getInstance();
		calendar.set(2020, 4, 11);
		
		Date d1 = calendar.getTime(); 
		Date d2 = new Date();
		System.out.println(d1);
		System.out.println(d2);
		
		System.out.println(d2.before(d1));
		
		
		
		//Get Monday date of year and week number
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.YEAR, 2020);
		cld.set(Calendar.WEEK_OF_YEAR, 19);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date result = cld.getTime();
		System.out.println(result);
		
		//Get Sunday date of year and week number
		Calendar cld2 = Calendar.getInstance();
		cld2.set(Calendar.YEAR, 2020);
		cld2.set(Calendar.WEEK_OF_YEAR, 19);
		cld2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date result2 = cld2.getTime();
		System.out.println(result2);
		
		
		System.out.println();
		LocalDate t = LocalDate.now();
		LocalDate t2 = LocalDate.of(2020,05,06);
		System.out.println(t);
		System.out.println(t2);
		
		
		Login.displayLogin();
	}
}
