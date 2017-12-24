package JDBCExamples;

public class Student {
	private String name;
	public Student (String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void func(String s, String city) {
		s = "hello";
		city  = "Moscow";
	}
}
