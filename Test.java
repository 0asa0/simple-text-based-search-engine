import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Test {
	
	public static void main(String[] args) throws IOException {
		
		HashedDictionary2<String, String> database = new HashedDictionary2<String, String>();
		
		ArrayList<String> keys = new ArrayList<String>(); 
		ArrayList<Integer> values = new ArrayList<Integer>(); 
		ArrayList<String> search = new ArrayList<String>(); 
		keys = fileOp.keys();// arraylist for keys reading from txts.
		search = fileOp.searchtxt(); //arraylist for searchkeys from txt.
		
		int filecounter = 1;
		int arrfileindex;
		int oldarrfileindex = 0;
		String lastindex = keys.get(keys.size() - 1);
		lastindex = lastindex.replaceAll(".txt", "");
		int lastindexof = Integer.parseInt(lastindex); ; //this code block finds from which txt the keys in the ArrayList come from and assigns the number of that txt to the ArrayList named values

		while (filecounter != lastindexof + 1) {
			arrfileindex = keys.indexOf(filecounter + ".txt");
			for (int i = 0; i < arrfileindex - oldarrfileindex; i++) {
				values.add(filecounter);
			}
			filecounter += 1;
			oldarrfileindex = keys.indexOf((filecounter - 1) + ".txt");
			keys.remove(arrfileindex);
		}
		
		 
		long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < keys.size(); i++) {
			database.add(keys.get(i), Integer.toString(values.get(i))); // Adding keys and values to the hashtable.
		}
		
		
		long endTime = System.currentTimeMillis();
		long estimatedTime = endTime - startTime;
		//System.out.println(estimatedTime);

		
		String result1 = null; String word1 = null;
		String result2 = null; String word2 = null;
		String result3 = null; String word3 = null;
		//european believes sport
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter first word for query");
		word1= scan.next();
		System.out.println("Please enter second word for query");
		word2= scan.next();
		System.out.println("Please enter third word for query");
		word3= scan.next();
		
		
		
		if (database.contains(word1)) {
			result1 = database.getValue(word1); // query from hashtable
			int[] arr1 = new int[result1.split(" ").length];
			for (int i = 0; i < result1.split(" ").length; i++) 
			{
				arr1[i] = Integer.parseInt(result1.split(" ")[i]); // assigning txt names(values) to array
			}
			System.out.println(word1);
			countFreq(arr1, arr1.length); //The function that finds how many times that word is repeated in which txt. 
			System.out.println(" ");
		}
		else {
			System.out.println("word cannot found");
		}
		if (database.contains(word2)) {
			result2 = database.getValue(word2);
			int[] arr2 = new int[result2.split(" ").length];
			for (int i = 0; i < result2.split(" ").length; i++)
			{
				arr2[i] = Integer.parseInt(result2.split(" ")[i]); 
			}
			System.out.println(word2);
			countFreq(arr2, arr2.length);
			System.out.println(" ");
		}
		else {
			System.out.println("word cannot found");
		}
		if (database.contains(word3)) {
 			result3 = database.getValue(word3);
 			int[] arr3 = new int[result3.split(" ").length];
 			for (int i = 0; i < result3.split(" ").length; i++)
			{
				arr3[i] = Integer.parseInt(result3.split(" ")[i]);
			}
 			System.out.println(word3);
 			countFreq(arr3, arr3.length);
 			System.out.println(" "); 
		}
		else {
			System.out.println("word cannot found");
		}
		
		
		
		
	}
	public static void countFreq(int arr[], int n)
	{
	    boolean visited[] = new boolean[n];
	     
	    Arrays.fill(visited, false);
	 
	    for (int i = 0; i < n; i++) {
	 
	        if (visited[i] == true)
	            continue;
	 
	        int count = 1;
	        for (int j = i + 1; j < n; j++) {
	            if (arr[i] == arr[j]) {
	                visited[j] = true;
	                count++;
	            }
	        }
	        System.out.println("This word occurs " + count+ " times in " +arr[i]+ ".txt");
	    }
	}
}
