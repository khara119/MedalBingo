import java.util.ArrayList;
import javafx.scene.control.CheckBox;

public class Data {
	private String name;
	private ArrayList<Integer> medalsPerTurn;
	private CheckBox showBox;

	public Data(String n) {
		name = n;
		medalsPerTurn = new ArrayList<>();
		showBox = new CheckBox(n);
	}

	public void add(int m) {
		medalsPerTurn.add(m);
	}

	public CheckBox getShowBox() {
		return showBox;
	}

	public void setSelected(boolean b) {
		showBox.setSelected(b);
	}

	public String getName() {
		return name;
	}

	public int getMedal(int idx) {
		return medalsPerTurn.get(idx);
	}

	public int getCurrentMedal() {
		return medalsPerTurn.get(medalsPerTurn.size()-1);
	}

	public boolean isShow() {
		return showBox.isSelected();
	}
}
