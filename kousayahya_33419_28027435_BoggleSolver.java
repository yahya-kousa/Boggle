import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BoggleSolver
{
	Set<String> dict = new HashSet<>();
	Set<String> words = new HashSet<>();

	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A - Z.)
	public BoggleSolver(String dictionaryName)
	{
		try {
			Scanner scan = new Scanner(new File(dictionaryName));
			dict = new HashSet<>();
			while(scan.hasNextLine())
				dict.add(scan.nextLine());
		}catch(FileNotFoundException e) { System.out.println("Incorrect file pt. 2"); }
	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable object
	public Iterable<String> getAllValidWords(BoggleBoard board)
	{
		int rows = board.rows();
		int cols = board.cols();
		boolean[][] visited = new boolean[rows][cols];

		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				String s = "";
				wordCheck(s, board, i, j, visited);
			}
		}
		return words;
	}

	public void wordCheck(String s, BoggleBoard b, int i, int j, boolean[][] visited)
	{
		if (s.length() == 16)
		{
			visited[i][j] = false;
			return;
		}

		String l = b.getLetter(i, j) + "";
		s += l;
		if (b.getLetter(i, j) == 'Q')
		{
			s += "U";
		}

		visited[i][j] = true;

		if(isValidPos(i + 1, j - 1, b) && !visited[i + 1][j - 1])
		{
			wordCheck(s, b, i + 1, j - 1, visited);
		}
		if(isValidPos(i + 1, j , b) && !visited[i+1][j])
		{
			wordCheck(s, b, i + 1, j, visited);
		}
		if(isValidPos(i + 1, j + 1, b) && !visited[i+1][j+1])
		{
			wordCheck(s, b, i + 1, j + 1, visited);
		}
		if(isValidPos(i , j + 1, b) && !visited[i][j+1])
		{
			wordCheck(s, b, i, j + 1, visited);
		}
		if(isValidPos(i, j - 1, b) && !visited[i][j-1])
		{
			wordCheck(s, b, i, j - 1, visited);
		}
		if(isValidPos(i - 1, j - 1, b) && !visited[ i-1][j-1])
		{
			wordCheck(s, b,  i - 1, j - 1, visited);
		}
		if(isValidPos(i - 1, j + 1, b) && !visited[ i-1][j+1])
		{
			wordCheck(s, b,i - 1, j + 1, visited);
		}
		if(isValidPos(i - 1, j , b) && !visited[ i-1][j])
		{
			wordCheck(s, b, i- 1, j, visited);
		}

		visited[i][j] = false;

		if (s.length() >= 3 && dict.contains(s.toUpperCase()))
		{
			words.add(s);
			dict.remove(s);
		}
	}

	public boolean isValidPos(int i, int j, BoggleBoard b)
	{
		return !(i == b.rows() || i < 0 || j == b.cols() || j < 0);
	}


	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A - Z.)
	public int scoreOf(String word)
	{
		if (word.length() <= 2)
		{
			return 0;
		}
		else if (word.length() == 3 || word.length() == 4)
		{
			return 1;
		}
		else if (word.length() == 5)
		{
			return 2;
		}
		else if (word.length() == 6)
		{
			return 3;
		}
		else if (word.length() == 7)
		{
			return 5;
		}
		else
		{
			return 11;
		}
	}

	public static void main(String[] args) {
		System.out.println("WORKING");

		final String PATH = "./data/";
		BoggleBoard board = new BoggleBoard(PATH + "board-q.txt");
		BoggleSolver solver = new BoggleSolver(PATH + "dictionary-yawl.txt");

		int totalPoints = 0;

		for (String s : solver.getAllValidWords(board)) {
			System.out.println(s + ", points = " + solver.scoreOf(s));
			totalPoints += solver.scoreOf(s);
		}

		System.out.println("Score = " + totalPoints); //should print 84

		new BoggleGame(4, 4);
	}
}
