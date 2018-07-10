import java.util.Random;

public class UtilsSample {
	public static String generateName() {
		String[] names = {
			"NAMES",
			"ARE",
			"HERE"
		};

		return names[new Random().nextInt(names.length)];
	}

	public static String[] getNames() {
		String[] names = {
			"NAMES",
			"ARE",
			"HERE"
		};

		return names;
	}
}
