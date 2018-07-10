import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

public class Player {
	private String name;
	private int cardSelectType;
	private int minCardSelectNum;
	private int maxCardSelectNum;
	private double expectedBetRate;
	private int expectedBornusRate;
	private int betType;
	private double minBetRate;
	private double maxBetRate;
	private int medal;
	private Card[] cards;
	private int[] appearedCount;
	private int initialMedal;

	public Player() {
		this(Utils.generateName());
	}

	public Player(String name) {
		this.name = name;

		Random random = new Random();
		cardSelectType = random.nextInt(4);
		switch(cardSelectType) {
			case 0:
				this.minCardSelectNum = 1;
				this.maxCardSelectNum = 1;
				this.expectedBetRate = 0;
				this.expectedBornusRate = 1;
				break;
			case 1:
				this.minCardSelectNum = 2;
				this.maxCardSelectNum = 4;
				this.expectedBetRate = 0;
				this.expectedBornusRate = 1;
				break;
			case 2:
				this.minCardSelectNum = 0;
				this.maxCardSelectNum = 4;
				this.expectedBetRate = random.nextDouble() * 0.5;
				this.expectedBornusRate = 1;
				break;
			case 3:
				this.minCardSelectNum = 0;
				this.maxCardSelectNum = 4;
				this.expectedBetRate = 0;
				this.expectedBornusRate = random.nextInt(10) + 1;
				break;
		
		}

		betType = random.nextInt(3);
		switch(betType) {
			case 0:
				this.minBetRate = 0.1;
				this.maxBetRate = 0.5;
				break;
			case 1:
				this.minBetRate = 0.5;
				this.maxBetRate = 1;
				break;
			case 2:
				this.minBetRate = 0.1;
				this.maxBetRate = 1;
				break;
		}

		this.medal = new Random().nextInt(9900) + 100;
		this.initialMedal = this.medal;
		this.appearedCount = new int[76];
		for (int i=0; i<76; i++) {
			this.appearedCount[i] = 0;
		}

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
		if (cardSelectType == 3) {
			Arrays.sort(this.cards, (a, b) -> b.getBornusRate() - a.getBornusRate());
		} else {
			Arrays.sort(this.cards, (a, b) -> (int)(b.computeExpectedRate(appearedCount) - a.computeExpectedRate(appearedCount)));
		}

		int cardSelectNum = this.maxCardSelectNum;
		Random random = new Random();
		if (this.cardSelectType == 1) {
			cardSelectNum = random.nextInt(this.maxCardSelectNum - this.minCardSelectNum) + this.minCardSelectNum;
		}

		ArrayList<Card> selectCard = new ArrayList<>();
		for (int i=0; i<cardSelectNum; i++) {
			if ((this.cardSelectType == 2 && this.expectedBetRate < this.cards[i].computeExpectedRate(appearedCount)) ||
				(this.cardSelectType == 3 && this.expectedBornusRate < this.cards[i].getBornusRate())) {
				break;
			}

			selectCard.add(this.cards[i]);
		}

		this.cards = selectCard.toArray(new Card[selectCard.size()]);

		for (Card card : this.cards) {
			if (this.medal <= 0) {
				break;
			}

			int minBet = (int)Math.ceil(medal * this.minBetRate / 4);
			int maxBet = (int)Math.ceil(medal * this.maxBetRate / 4);

			int bet = random.nextInt(maxBet - minBet) + minBet;
			if (bet < 0) {
				bet = 1;
			} else if (bet > this.medal) {
				bet = this.medal;
			}

			card.bet(bet);
			this.medal -= bet;
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
			(this.medal - this.initialMedal) + "]） ";
		if (this.cardSelectType == 0) {
			str += "単発型";
		} else if (this.cardSelectType == 1) {
			str += "複数型";
		} else if (this.cardSelectType == 2) {
			str += "確率型";
		} else {
			str += "ボーナス型";
		}

		if (this.betType == 0) {
			str += " 慎重派\n";
		} else if (this.betType == 1) {
			str += " 一攫千金派\n";
		} else {
			str += " テンション派\n";
		}

		if (this.cards.length > 0) {
			String[][] cards_str = new String[cards.length][13];
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
		} else {
			str += "今回は参加しません\n";
		}

		return str;
	}
}
