import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class fileOp {
	public static ArrayList<String> keys() throws IOException 
	{
		File dir = new File("sport");
	    File[] files = dir.listFiles();
	    String line2; String[] stopw;
	    ArrayList <String> al = new ArrayList<>();
	    ArrayList <String> al2 = new ArrayList<>();
	    
	    FileReader stopwordreader = new FileReader("stop_words_en.txt");
		BufferedReader sp = new BufferedReader(stopwordreader);
		
		while ((line2 = sp.readLine()) != null)
		{ //loop for reading stopwords
			
			line2 = line2.replaceAll("[^a-zA-Z ]", "").toLowerCase(Locale.ENGLISH);
			stopw = line2.split(" ");
			for (int i = 0; i < stopw.length; i++)
			{
				if (line2.compareTo("") != 0) 
				{
					al2.add(stopw[i]);
				}
			}
		}
		sp.close();
		
		al2.add(" ");
		al2.add("");
		int filecounter = 1;
	    for (File file : files) {
	        if(file.isFile()) {
	            BufferedReader inputStream = null;
	            String line;
	            String[] line3;
	            
	            try {
	                inputStream = new BufferedReader(new FileReader(file));
	                while ((line = inputStream.readLine()) != null) {
	                	line = line.replaceAll("[^a-zA-Z ]", "").toLowerCase(Locale.ENGLISH);
	        			line3 = line.split(" ");
	        			for (int i = 0; i < line3.length; i++)
	        			{
	        				al.add(line3[i]);
	        			}
	                }
	                al.add(filecounter + ".txt");
	                filecounter += 1;
	            }catch(IOException e) {
	            	System.out.println(e);
	            }
	            finally {
	                if (inputStream != null) 
	                {
	                    inputStream.close();
	                }
	            }
	        }
	    
	}
	    al.removeAll(al2);
		return al;
	}
	
	public static ArrayList<String> searchtxt() throws IOException
	{
		ArrayList <String> arr = new ArrayList<>();
		FileReader searchreader = new FileReader("search.txt");
		BufferedReader bf = new BufferedReader(searchreader);
		String line;
		
		while ((line = bf.readLine()) != null)
		{ //loop for reading stopwords
			
			line = line.replaceAll("[^a-zA-Z ]", "").toLowerCase(Locale.ENGLISH);
			arr.add(line);
		}
		bf.close();
		
		return arr;
	}
	
}

