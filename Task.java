import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task {

	public static void main(String[] args) throws Exception {

		String[] eingabe = new String[864];
		Set<Integer> duplikatenIndex = new HashSet<Integer>();

		System.out.println("");
		
		 try {
			eingabe = readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 //Berechnung der 864 X 864 Matrix 
		duplikatenIndex = findDuplicatePosition(doBlocksAndNewMatrix(similarityLoop(eingabe), 864), 2, 864);
		for(Integer i : duplikatenIndex){
			System.out.print(i + ", ");
		}
		System.out.println("\n");
		
		String duplikatenNamen = findDuplicateName(eingabe, duplikatenIndex);
		System.out.println(duplikatenNamen);
	}
	

	public static String findDuplicateName(String[] eingabe, Set<Integer> index){
		Set<String> set = new HashSet<String>();
		
		for(int i : index){
			set.add(eingabe[i-1]);
		}
		System.out.println("Anzahl der Duplikate: " + set.size());
		return set.toString();
	}
	
	public static Set<Integer> findDuplicatePosition(int[][] matrix, int treshold, int blockSize){
		Set<Integer> set = new HashSet<Integer>();
		int counterSpalte = blockSize;
		int counterZeile = counterSpalte;
		
		for(int i = 1; i <= 864; i++){
			for(int j = i + 1; j <= 864; j++){
				if(matrix[i][j] <= treshold && counterZeile > 0){
					set.add(j);
					set.add(i);
				}
				counterZeile--;
			}
			counterSpalte--;
			if(counterSpalte == 0)
				counterSpalte = blockSize;
			
			counterZeile = counterSpalte;
		}
		return set;
	}
	
	public static int[][] doBlocksAndNewMatrix(int[][] inputMatrix, int blockSize) throws Exception{
		
		if(blockSize > 864)
			throw new Exception("Blockgröße viel zu groß!");
		
		int[][] result = new int[865][865];
		
		for (int i = 0; i < 865; i++)
			result[i][0] = i;

		for (int j = 0; j < 865; j++)
			result[0][j] = j;
		
		int counterSpalte = blockSize;
		int counterZeile = counterSpalte;
		
		for(int i = 1; i <= 864; i++){
			for(int j = i + 1; j <= 864; j++){
				if(counterZeile > 0){
					result[i][j] = inputMatrix[i][j];
					counterZeile--;
				}
			}
			counterSpalte--;
			if(counterSpalte == 0)
				counterSpalte = blockSize;
			
			counterZeile = counterSpalte;
		}
		return result;
	}
	
	
	// Methode für das Einlesen einer .txt File
	public static String[] readFile() throws IOException{
		FileReader fr = null;
		
		try {
			fr = new FileReader(
			  "C:\\Users\\ilker\\Desktop\\RestaurantNames.txt");
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  
		  BufferedReader br = new BufferedReader(fr);
		  List<String> words = new ArrayList<String>();
		  String token = null;
		 
		  while ((token = br.readLine()) != null) {
			  words.add(token);
		  }
		  
		  try {
			  br.close();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
		  
		  return words.toArray(new String[words.size()]);
	}

	
	// Printen eines 2D Arrays
	public static void print2DArray(int[][] array){
		for(int[] field : array)
			System.out.println(Arrays.toString(field));
	}
	
	
	public static int minFor3Values(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}

	// MEthode für 864 X 864 Matrix --> Inhalten der Felder sind Scores
	public static int[][] similarityLoop(String[] input){
		int[][] result = new int[input.length + 1][input.length + 1];
	
		
		for (int i = 0; i <= input.length; i++)
			result[i][0] = i;

		for (int j = 0; j <= input.length; j++)
			result[0][j] = j;
		
		
		for(int i = 1; i <= input.length; i++){
			for(int j = i; j <= input.length; j++){
				result[i][j] = editDistanceOnlyScore(input[i-1], input[j-1]);
			}
		}
		return result;
	}
	
	public static int editDistanceOnlyScore(String source, String target) {
		int[][] result = new int[source.length() + 1][target.length() + 1];

		for (int i = 0; i <= source.length(); i++)
			result[i][0] = i;

		for (int j = 0; j <= target.length(); j++)
			result[0][j] = j;

		for (int x = 1; x <= source.length(); x++) {
			for (int y = 1; y <= target.length(); y++) {
				char c1 = source.charAt(x-1);
				char c2 = target.charAt(y-1);

				if(c1 == c2)
					result[x][y] = result[x - 1][y - 1];
				else
					result[x][y] = 1 + minFor3Values(result[x][y - 1], // einfügen
													result[x - 1][y], // entfernen
													result[x - 1][y - 1]); // ersetzen
			}
		}
		int wert = result[source.length()][target.length()];
		return wert;
	}
	
	
	public static int[][] editDistance(String source, String target) {
		int[][] result = new int[source.length() + 1][target.length() + 1];

		for (int i = 0; i <= source.length(); i++)
			result[i][0] = i;

		for (int j = 0; j <= target.length(); j++)
			result[0][j] = j;

		for (int x = 1; x <= source.length(); x++) {
			for (int y = 1; y <= target.length(); y++) {
				char c1 = source.charAt(x-1);
				char c2 = target.charAt(y-1);

				if(c1 == c2)
					result[x][y] = result[x - 1][y - 1];
				else
					result[x][y] = 1 + minFor3Values(result[x][y - 1],	 // einfügen
													result[x - 1][y], 	//  entfernen
													result[x - 1][y - 1]); // ersetzen
			}
		}
		return result;
	}
}
