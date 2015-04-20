package aufgabe1;

import javax.swing.JFrame;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MyFrame() {
		this.setTitle("Ein Titel");
		this.setSize(640, 480);
	}

	public static void main(String[] arguments) {
		MyFrame myFrame = new MyFrame();

		myFrame.setVisible(true);
	}

}