import java.util.ArrayList;
import java.util.Random;

public class Card {
	private static final double[] DIVIDEND_RATE = {
		0, 1.5, 2, 3, 5, 8, 10, 15, 20, 30, 50, 100, 1000
	};

	private static final int[] BORNUS_RATE = {
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
		3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
		3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
		3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
		4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
		4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
		4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
		4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
		8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
		8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
		9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
		10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
		20, 20, 20, 20, 20, 20,
		50, 50,
		100
	};

	private int[][] numbers;
	private int bet;
	private int bingo;
	private int hit;
	private int bornusMassX;
	private int bornusMassY;
	private int bornusRate;
	private boolean bornusBingo;
	private int bornusNumber;

	public Card() {
		Random random = new Random();

		numbers = new int[5][5];
		for (int j=0; j<5; j++) {
			int min = 15 * j + 1;
			int max = 15 * (j + 1);
			ArrayList<Integer> nums = new ArrayList<>();
			for (int i=min; i<=max; i++) {
				nums.add(i);
			}

			for (int i=0; i<5; i++) {
				Integer num = nums.get(random.nextInt(nums.size()));
				numbers[i][j] = num;
				nums.remove(num);
			}
		}

		numbers[2][2] = 0;

		bet = 0;
		bingo = 0;
		hit = 1;

		bornusMassX = random.nextInt(5);
		bornusMassY = random.nextInt(5);
		bornusRate = BORNUS_RATE[random.nextInt(BORNUS_RATE.length)];
		bornusBingo = false;
		bornusNumber= numbers[bornusMassY][bornusMassX];
	}

	public double computeExpectedRate(int[] appearedCount) {
		double max = 0;
		double[][] rate_table = new double[5][5];

		double lr_rate = 1;
		double rl_rate = 1;
		for (int i=0; i<5; i++) {
			double v_rate = 1;
			double h_rate = 1;
			for (int j=0; j<5; j++) {
				if (numbers[i][j] != 0) {
					v_rate *= (double)appearedCount[numbers[j][i]] / appearedCount[0];
					h_rate *= (double)appearedCount[numbers[i][j]] / appearedCount[0];
				}
			}
			lr_rate *= (double)appearedCount[numbers[i][i]] / appearedCount[0];
			lr_rate *= (double)appearedCount[numbers[i][4-i]] / appearedCount[0];

			max = max > v_rate ? max : v_rate;
			max = max > h_rate ? max : h_rate;
		}

		max = max > lr_rate ? max : lr_rate;
		max = max > rl_rate ? max : rl_rate;
		return max / 12;
	}

	public int getBornusRate() {
		return this.bornusRate;
	}

	public void checkNumber(int ball) {
		int column = ball / 15;
		if (ball % 15 == 0) {
			column--;
		}

		for (int i=0; i<5; i++) {
			if (numbers[i][column] == ball) {
				numbers[i][column] = 0;
				this.hit++;
				if (i == this.bornusMassY && column == this.bornusMassX) {
					this.bornusBingo = true;
				}
				updateBingo(column, i);
				break;
			}
		} 
	}

	public void updateBingo(int x, int y) {
		boolean vFlag = true;
		boolean hFlag = true;
		boolean lrFlag = (x == y);
		boolean rlFlag = (x+y == 4);
		for (int i=0; i<5; i++) {
			if (numbers[i][x] != 0) {
				vFlag = false;
			}
			if (numbers[y][i] != 0) {
				hFlag = false;
			}
			if (lrFlag && numbers[i][i] != 0) {
				lrFlag = false;
			}
			if (rlFlag && numbers[i][4-i] != 0) {
				rlFlag = false;
			}
		}

		if (vFlag) {
			bingo++;
		}

		if (hFlag) {
			bingo++;
		}

		if (rlFlag) {
			bingo++;
		}

		if (lrFlag) {
			bingo++;
		}
	}

	public int bet(int medal) {
		bet = medal;
		return bet;
	}

	public int dividend() {
		int dividend = (int)Math.floor(bet * DIVIDEND_RATE[bingo]);
		if (bornusBingo) {
			dividend *= bornusRate;
		}

		this.bet = 0;
		return dividend;
	}

	public boolean getBornusBingo() {
		return this.bornusBingo;
	}

	public String toString() {
		String line = "------------------";
		String str = line + "\n";
		for (int i=0; i<5; i++) {
			str += "|";
			for (int j=0; j<5; j++) {
				str += String.format("%3d", numbers[i][j]);
			}
			str += " |\n";
		}
		str += line + "\n";
		str += "ヒット数： " + String.format("%5d", this.hit) + "  \n";
		str += "ビンゴ数： " + String.format("%5d", this.bingo) + "  \n";
		str += "ベッド： " + String.format("%7d", bet) + "  \n";
		str += "ボーナス値： ";
		if (this.bornusBingo) {
			str += "*" + String.format("%2d", this.bornusNumber) + "  \n";
		} else {
			str += String.format("%3d", this.bornusNumber) + "  \n";
		}
		str += "ボーナス率： " + String.format("%3d", this.bornusRate) + "  \n";

		int dividend = (int)Math.floor(bet * DIVIDEND_RATE[bingo]);
		if (bornusBingo) {
			dividend *= bornusRate;
		}
		str += "配当： " + String.format("%9d", dividend) + "  \n";

		return str;
	}
}
