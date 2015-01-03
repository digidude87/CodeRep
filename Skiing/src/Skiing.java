import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Progress {
	private static int temp[][];

	public static int[][] getTemp() {
		return temp;
	}

	public static void setTemp(int[][] temp) {
		Progress.temp = temp;
	}

	static class Peak {
		private int value;
		private int x;
		private int y;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

	}

	private static int maxLength(List<Peak> peaks, int[][] grid, int count) {
		int r = 0;
		int c = 0;
		int m = 0;
		int max = -1;
		if (peaks.size() == 0) {
			temp[r][c] = 1;
		}
		List<Peak> nodes = new ArrayList<Progress.Peak>();
		for (Peak p : peaks) {
			r = p.getX();
			c = p.getY();
			if (temp[r][c] != -1 && max < temp[r][c])
				max = temp[r][c];
			else if (temp[r][c] == -1) {
				nodes = findReachablePeaks(p, grid);
				if (nodes.size() > 0) {
					temp[r][c] = 1 + (maxLength(nodes, grid, count + 1));
				}else{
					temp[r][c] = 1;
				}
				if (max < temp[r][c]) {
					max = temp[r][c];
				}
			}

		}
		return max;
	}

	public static List<Peak> findReachablePeaks(Peak p, int[][] grid) {
		int row = p.getX();
		int col = p.getY();
		int val = p.getValue();
		int maxy = grid[0].length;
		int maxx = grid.length;
		Peak temp = new Peak();
		List<Peak> accessiblePeaks = new ArrayList<Peak>();
		if (!(row - 1 < 0)) {
			if (grid[row - 1][col] < val) {
				temp = new Peak();
				temp.setX(row - 1);
				temp.setY(col);
				temp.setValue(grid[row - 1][col]);
				accessiblePeaks.add(temp);
			}
		}
		if (!(row + 1 == maxx)) {
			if (grid[row + 1][col] < val) {
				temp = new Peak();
				temp.setX(row + 1);
				temp.setY(col);
				temp.setValue(grid[row + 1][col]);
				accessiblePeaks.add(temp);
			}
		}
		if (!(col - 1 < 0)) {
			if (grid[row][col - 1] < val) {
				temp = new Peak();
				temp.setX(row);
				temp.setY(col - 1);
				temp.setValue(grid[row][col - 1]);
				accessiblePeaks.add(temp);
			}
		}
		if (!(col + 1 == maxy)) {
			if (grid[row][col + 1] < val) {
				temp = new Peak();
				temp.setX(row);
				temp.setY(col + 1);
				temp.setValue(grid[row][col + 1]);
				accessiblePeaks.add(temp);
			}
		}
		return accessiblePeaks;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(
					"G:/java_pr/input.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int rows = 0;
			int cols = 0;
			String peakName = null;
			strLine = br.readLine();
			int noOfInputs = Integer.parseInt(strLine);
			int count = 0;
			for (int i = 0; i < noOfInputs; i++) {
				strLine = br.readLine();
				if (count == 0) {
					String[] str = strLine.split(" ");
					peakName = str[0];
					rows = Integer.parseInt(str[1]);
					cols = Integer.parseInt(str[2]);
				}
				int[][] grid = new int[rows][cols];
				while (count < rows) {
					strLine = br.readLine();
					String[] rowData = strLine.split(" ");
					for (int j = 0; j < rowData.length; j++) {
						grid[count][j] = Integer.parseInt(rowData[j]);
					}
					count++;
				}
				temp = new int[rows][cols];
				Peak p;
				List<Peak> peaks = new ArrayList<Progress.Peak>();
				for (int t = 0; t < rows; t++)
					Arrays.fill(temp[t], -1);
				for (int k = 0; k < rows; k++) {
					for (int l = 0; l < cols; l++) {
						p = new Peak();
						p.setX(k);
						p.setY(l);
						p.setValue(grid[k][l]);
						peaks = findReachablePeaks(p, grid);
						if (peaks.size() > 0)
							temp[k][l] = 1 + maxLength(peaks, grid, 1);
						else
							temp[k][l] = 1;
					}
				}
				System.out.print(peakName + " : ");
				int output = 0;
				for (int k = 0; k < rows; k++) {
					for (int l = 0; l < cols; l++)
						if(output<temp[k][l]){
							output = temp[k][l];
						}
					
				}
				System.out.print(output);
				System.out.println();
				count = 0;
			}
		} catch (Exception e) {// Catch exception if any
			// System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
