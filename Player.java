import java.util.Random;

public class Player {
	private String name;
	private int medal;
	private Card[] cards;
	private int[] appearedCount;
	private int initialMedal;

	public Player() {
		this.name = Utils.generateName();
		this.medal = new Random().nextInt(9900) + 100;
		this.initialMedal = this.medal;
		this.appearedCount = new int[76];
		for (int i=0; i<76; i++) {
			this.appearedCount[i] = 0;
		}

		Random random = new Random();
		for (int i=0; i<100; i++) {
			this.appearedCount[0]++;
			for (int j=0; j<40; j++) {
				this.appearedCount[random.nextInt(75)+1]++;
			}
		}
	}

	public void setCards(Card[] cards) {
		this.cards = cards;
	}

	public void checkNumber(int ball) {
		for (Card card : this.cards) {
			card.checkNumber(ball);
		}
	}

	public void bet() {
		for (Card card : this.cards) {
			double expectedRate = card.computeExpectedRate(appearedCount);
			this.medal -= card.bet((int)Math.ceil(this.medal * expectedRate));
		}
	}

	public void dividend() {
		for (Card card : this.cards) {
			this.medal += card.dividend();
		}
	}

	public int getMedal() {
		return this.medal;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		String str = this.name + "（" + this.medal + "[" +
			(this.medal - this.initialMedal) + "]）\n";
		String[][] cards_str = new String[cards.length][10];
		for (int i=0; i<cards.length; i++) {
			String[] split_str = cards[i].toString().split("\n");
			for (int j=0; j<split_str.length; j++) {
				cards_str[i][j] = split_str[j];
			}
		}

		for (int i=0; i<cards_str[0].length; i++) {
			for (int j=0; j<cards_str.length; j++) {
				str += cards_str[j][i];
			}
			str += "\n";
		}

		return str;
	}
}
