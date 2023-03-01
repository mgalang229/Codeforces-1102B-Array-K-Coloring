import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.StringTokenizer;

/*

4 2
1 2 2 3

1 = 1, 2 (every color should contain distinct numbers)
2 = 2, 3

NO

---------------------

5 2
3 2 1 2 3
2 1 1 2 1

1 = 2, 1, 3
2 = 3, 2

YES

---------------------

5 2
2 1 1 2 1

frequency[num] <= k
maintain a prev[num] (increment as we encounter num)

---------------------

7 ?
2 3 4 3 6 1 6

1 1 1 2 1 1 2

---------------------

7 7
1 1 2 2 3 3 4
1 2 3 4 5 6 7

---------------------

12 7
1 1 2 2 3 3 4 2 2 2 2 2

1 2 3 4 5 6 7 1 2 5 6 7

---------------------

7 5
5 5 5 3 5 5 4

sort the sequence in non-decreasing order

 */

public class Main {
	
	public static void main(String[] args) {
		FastScanner fs = new FastScanner();
		PrintWriter out = new PrintWriter(System.out);
		int T = 1;
		//T = fs.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int n = fs.nextInt(), k = fs.nextInt();
			int max = 0;
			int[] a = new int[n];
			for (int i = 0; i < n; i++) {
				a[i] = fs.nextInt();
				max = Math.max(max, a[i]);
			}
			int[] freq = new int[max+1];
			Arrays.fill(freq, 0);
			boolean possible = true;
			for (int i = 0; i < n; i++) {
				freq[a[i]]++;
				if (freq[a[i]] > k) {
					possible = false;
					break;
				}
			}
			if (!possible) {
				System.out.println("NO");
				continue;
			}
			Pair[] p = new Pair[n];
			for (int i = 0; i < n; i++) {
				p[i] = new Pair(a[i], i);
			}
			Arrays.sort(p, new Comparator<Pair>() {
				@Override
				public int compare(Pair a, Pair b) {
					return Integer.compare(a.first, b.first);
				}
			});
			Info[] info = new Info[max+1];
			for (int i = 0; i < n; i++) {
				info[p[i].first] = new Info();
			}
			System.out.println("YES");
			int[] ans = new int[n];
			for (int i = 0; i < k; i++) {
				int lowest = Math.min(info[p[i].first].lowest, i + 1);
				int highest = Math.max(info[p[i].first].highest, i + 1);
				info[p[i].first] = new Info(lowest, highest);
//				System.out.print((i + 1) + " ");
				ans[p[i].second] = i + 1;
			}
			int[] newPrev = new int[max+1];
			Arrays.fill(newPrev, 0);
			for (int i = k; i < n; i++) {
				if (info[p[i].first].lowest <= newPrev[p[i].first] + 1 && newPrev[p[i].first] + 1 <= info[p[i].first].highest) {
					newPrev[p[i].first] = info[p[i].first].highest + 1;
				} else {
					newPrev[p[i].first]++;
				}
				ans[p[i].second] = newPrev[p[i].first];
//				System.out.print(newPrev[a[i]] + " ");
			}
			for (int x : ans) {
				System.out.print(x + " ");
			}
			System.out.println();
		}
		out.close();
	}
	
	static class Pair {
		int first, second;
		
		Pair(int first, int second) {
			this.first = first;
			this.second = second;
		}
		
		@Override
		public String toString() {
			return this.first + " " + this.second;
		}
	}
	
	static class Info {
		int lowest, highest;
		
		Info() {
			this.lowest = Integer.MAX_VALUE;
			this.highest = 0;
		}
		
		Info(int lowest, int highest) {
			this.lowest = lowest;
			this.highest = highest;
		}
	}
	
	static final Random rnd = new Random();
	static void shuffleSort(int[] a) { //change this
		int n = a.length;
		for (int i = 0; i < n; i++) {
			int newInd = rnd.nextInt(n);
			int temp = a[newInd]; //change this
			a[newInd] = a[i];
			a[i] = temp;
		}
		Arrays.sort(a);
	}
	
	static class FastScanner {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		
		String next() {
			while (!st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		
		int[] readArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++) {
				a[i] = nextInt();
			}
			return a;
		}
		
		long[] readLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < n; i++) {
				a[i] = nextLong();
			}
			return a;
		}
		
		double[] readDoubleArray(int n) {
			double[] a = new double[n];
			for (int i = 0; i < n; i++) {
				a[i] = nextDouble();
			}
			return a;
		}
		
		long nextLong() {
			return Long.parseLong(next());
		}
		
		double nextDouble() {
			return Double.parseDouble(next());
		}
		
		String nextLine() {
			String str = "";
			try {
				if (st.hasMoreTokens()) {
					str = st.nextToken("\n");
				} else {
					str = br.readLine();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
