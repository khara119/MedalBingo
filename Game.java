import java.util.ArrayList;
import java.util.Random;

public class Game {
	public static final int MAX_CARD_NUM = 4;
	public static final int SLEEP_TIME = 3500;

	public static void main(String[] args) throws Exception{
		ArrayList<Player> players = new ArrayList<>();
		for (int i=0; i<5; i++) {
			players.add(new Player());
		}

		while (!players.isEmpty()) {
			System.out.println("次のゲームを開始します。");
			System.out.println();

			for (Player player : players) {
				Card[] cards = new Card[MAX_CARD_NUM];
				for (int j=0; j<MAX_CARD_NUM; j++) {
					cards[j] = new Card();
				}

				player.setCards(cards);

				System.out.println(player);

				Thread.sleep(SLEEP_TIME);
			}

			ArrayList<Integer> balls = new ArrayList<>();
			for (int i=1; i<=75; i++) {
				balls.add(i);
			}
	
			Random random = new Random();
			for (int i=1; i<=35; i++) {
				Integer ball = balls.get(random.nextInt(balls.size()));
	
				System.out.println(i + "回目の抽選です");
				System.out.println("次の番号は " + ball + " 番です。");
	
				Thread.sleep(SLEEP_TIME);
	
				for (Player player : players) {
					player.checkNumber(ball);
					if (i==3) {
						player.bet();
						
					}
					System.out.println(player);
					Thread.sleep(SLEEP_TIME);
				}
	
				balls.remove(ball);
			}
	
			for (Player player : players) {
				player.dividend();
				System.out.println(player);
				if (player.getMedal() <= 0) {
					System.out.println(player.getName() + "さんが下りました。");
					players.remove(player);
				}
				System.out.println();
				Thread.sleep(SLEEP_TIME);
			}
		}
	}
}
