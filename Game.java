import java.util.ArrayList;
import java.util.Random;

public class Game {
	public static final int MAX_CARD_NUM = 4;
	public static final int SLEEP_TIME = 3500;

	public static void main(String[] args) throws Exception {
		String[] names = Utils.getNames();
		ArrayList<Player> noMonitorPlayers = new ArrayList<>();
		for (String name : names) {
			noMonitorPlayers.add(new Player(name));
		}

		ArrayList<Player> monitorPlayers = new ArrayList<>();
		for (int i=0; i<5; i++) {
			int idx = new Random().nextInt(noMonitorPlayers.size());
			System.out.println(noMonitorPlayers.get(idx).getName() + "さんが入りました。");
			monitorPlayers.add(noMonitorPlayers.get(idx));
			noMonitorPlayers.remove(idx);
		}
		Thread.sleep(SLEEP_TIME);

		while (!monitorPlayers.isEmpty()) {
			System.out.println("次のゲームを開始します。");
			System.out.println();

			for (Player player : monitorPlayers) {
				Card[] cards = new Card[MAX_CARD_NUM];
				for (int j=0; j<MAX_CARD_NUM; j++) {
					cards[j] = new Card();
				}

				player.setCards(cards);

				System.out.println(player);

				Thread.sleep(SLEEP_TIME);
			}

			for (Player player : noMonitorPlayers) {
				Card[] cards = new Card[MAX_CARD_NUM];
				for (int j=0; j<MAX_CARD_NUM; j++) {
					cards[j] = new Card();
				}

				player.setCards(cards);
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
				System.out.println();
	
				Thread.sleep(SLEEP_TIME);
	
				for (Player player : monitorPlayers) {
					player.checkNumber(ball);
					if (i==3) {
						player.bet();
					}
					System.out.println(player);
					Thread.sleep(SLEEP_TIME);
				}

				for (Player player : noMonitorPlayers) {
					player.checkNumber(ball);
					if (i==3) {
						player.bet();
					}
				}
	
				balls.remove(ball);
			}
	
			for (Player player : monitorPlayers) {
				player.dividend();
				System.out.println(player);
				if (player.getMedal() <= 0) {
					System.out.println(player.getName() + "さんが下りました。");
					monitorPlayers.remove(player);

					if (!noMonitorPlayers.isEmpty()) {
						int idx = random.nextInt(noMonitorPlayers.size());
						System.out.println(noMonitorPlayers.get(idx).getName() + "さんが入りました。");
						monitorPlayers.add(noMonitorPlayers.get(idx));
						noMonitorPlayers.remove(idx);
					}
				}
				System.out.println();
				Thread.sleep(SLEEP_TIME);
			}

			int survive = monitorPlayers.size() + noMonitorPlayers.size();
			System.out.println("現在" + survive + "人が残っています。");
			Thread.sleep(SLEEP_TIME);
		}
	}
}
