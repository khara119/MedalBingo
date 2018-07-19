import java.util.ArrayList;
import java.util.Random;
import java.util.Calendar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

public class Game {
	public static final int MAX_CARD_NUM = 9;
	public static final int SLEEP_TIME = 2500;
	public static final String LOG_FILE = 
		String.format("%04d", Calendar.getInstance().get(Calendar.YEAR)) +
		String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1) +
		String.format("%02d", Calendar.getInstance().get(Calendar.DATE)) +
		String.format("%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) +
		String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE)) +
		String.format("%02d", Calendar.getInstance().get(Calendar.SECOND)) + ".log";

	public static void main(String[] args) throws Exception {
		String[] names = Utils.getNames();
		ArrayList<Player> allPlayers = new ArrayList<>();
		ArrayList<Player> noMonitorPlayers = new ArrayList<>();
		for (String name : names) {
			Player player = new Player(name);
			allPlayers.add(player);
			noMonitorPlayers.add(player);
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Game.LOG_FILE), true), "UTF-8")));
		String separator = "";
		for (Player player : allPlayers) {
			pw.print(separator + player.getName());
			separator = ",";
		}
		pw.println();
		separator = "";
		for (Player player : allPlayers) {
			pw.print(separator + player.getMedal());
			separator = ",";
		}
		pw.println();
		pw.close();

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

				if (i <= 3) {
					System.out.println();
					System.out.println("...カードの選択中です...");
					System.out.println();
				}
	
				Thread.sleep(SLEEP_TIME);
	
				for (Player player : monitorPlayers) {
					player.checkNumber(ball);
					if (i==3) {
						player.bet();
					}

					if (i > 3) {
						System.out.println(player);
						Thread.sleep(SLEEP_TIME);
					}
				}

				for (Player player : noMonitorPlayers) {
					player.checkNumber(ball);
					if (i==3) {
						player.bet();
					}
				}
	
				balls.remove(ball);
			}

			int retire = 0;
			ArrayList<Player> retiredMonitorPlayer = new ArrayList<>();
			for (Player player : monitorPlayers) {
				player.dividend();
				System.out.println(player.summary());
				if (player.getMedal() <= 0) {
					retire++;
					System.out.println(player.getName() + "さんが脱落しました。");
					System.out.println();
					retiredMonitorPlayer.add(player);
				}
				Thread.sleep(SLEEP_TIME/2);
			}

			monitorPlayers.removeAll(retiredMonitorPlayer);
			System.out.println();

			ArrayList<Player> retiredNoMonitorPlayer = new ArrayList<>();
			for (Player player : noMonitorPlayers) {
				player.dividend();
				System.out.println(player.summary());
				if (player.getMedal() <= 0) {
					System.out.println(player.getName() + "さんが脱落しました。");
					System.out.println();
					retiredNoMonitorPlayer.add(player);
				}
				Thread.sleep(SLEEP_TIME/2);
			}

			System.out.println();
			noMonitorPlayers.removeAll(retiredNoMonitorPlayer);

			for (int i=0; i<retire; i++) {
				if (!noMonitorPlayers.isEmpty()) {
					int idx = random.nextInt(noMonitorPlayers.size());
					System.out.println(noMonitorPlayers.get(idx).getName() + "さんが入りました。");
					monitorPlayers.add(noMonitorPlayers.get(idx));
					noMonitorPlayers.remove(idx);
				}
			}

			int survive = monitorPlayers.size() + noMonitorPlayers.size();
			System.out.println("現在" + survive + "人が残っています。");
			Thread.sleep(SLEEP_TIME);

			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Game.LOG_FILE), true), "UTF-8")));
			separator = "";
			for (Player player : allPlayers) {
				pw.print(separator + player.getMedal());
				separator = ",";
			}
			pw.println();
			pw.close();
		}
	}
}
