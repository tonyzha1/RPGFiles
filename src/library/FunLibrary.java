package library;

public class FunLibrary {
	public static String htmlFormatString(String s)
	{
		return "<html>"+s.replaceAll("\n", "<br/>")+"</html>";
	}

}
