package wallpaper;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

public class Generator {
	
	static HashMap<String, Integer> keyboard = createSpecialCharsMap();
	
	public static void main(String[] args) {
		promptUser();
	}
	
	private static void promptUser() {
		String msgToUsers = "Enter folder path here";
		String boxTitle = "Desktop Generator";
		Object result2 = JOptionPane.showInputDialog(null, msgToUsers, boxTitle, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (result2 != null) {
			selectPic((String) result2);
		}
	}
	
	private static HashMap<String, Integer> createSpecialCharsMap() {
		HashMap<String, Integer> specialChars = new HashMap<String, Integer>();
		specialChars.put("&", KeyEvent.VK_AMPERSAND);
		specialChars.put("@", KeyEvent.VK_AT);
		specialChars.put("{", KeyEvent.VK_BRACELEFT);
		specialChars.put("}", KeyEvent.VK_BRACERIGHT);
		specialChars.put("^", KeyEvent.VK_CIRCUMFLEX);
		specialChars.put("]", KeyEvent.VK_CLOSE_BRACKET);
		specialChars.put(",", KeyEvent.VK_COMMA);
		specialChars.put("$", KeyEvent.VK_DOLLAR);
		specialChars.put("!", KeyEvent.VK_EXCLAMATION_MARK);
		specialChars.put("(", KeyEvent.VK_LEFT_PARENTHESIS);
		specialChars.put("<", KeyEvent.VK_LESS);
		specialChars.put("-", KeyEvent.VK_MINUS);
		specialChars.put("#", KeyEvent.VK_NUMBER_SIGN);
		specialChars.put("[", KeyEvent.VK_OPEN_BRACKET);
		specialChars.put(".", KeyEvent.VK_PERIOD);
		specialChars.put("+", KeyEvent.VK_PLUS);
		specialChars.put("'", KeyEvent.VK_QUOTE);
		specialChars.put(")", KeyEvent.VK_RIGHT_PARENTHESIS);
		specialChars.put(";", KeyEvent.VK_SEMICOLON);
		specialChars.put("_", KeyEvent.VK_UNDERSCORE);
		return specialChars;
	}
	
	public static void selectPic(String folder) {
		File dir = new File(folder);
		if (!dir.exists()) {
			JOptionPane.showMessageDialog(null, "Folder does not exist", "Error", JOptionPane.ERROR_MESSAGE);
			promptUser();
		} else {
			File[] pics = dir.listFiles();
			String allFirsts = "";
			HashMap<String, Integer> starts = new HashMap<String, Integer>();
			for (File pic : pics) {
				String firstLetter = Character.toString(pic.getName().charAt(0)).toUpperCase();
				if (!starts.containsKey(firstLetter)) {
					starts.put(firstLetter, 1);
					allFirsts += firstLetter;
				} else {
					starts.put(firstLetter, starts.get(firstLetter) + 1);
				}
			}
			Random rand = new Random();
			String startSelected = Character.toString(allFirsts.charAt(rand.nextInt(allFirsts.length())));
			int numClicks = rand.nextInt(starts.get(startSelected)) + 1;
			Desktop dt = Desktop.getDesktop();

			try {
				dt.open(dir);
				Robot r = new Robot();
				r.delay(1000);
				int keyCmd = findKeyCmd(startSelected);
				for (int num = 0; num < numClicks; num += 1) {
					typeKey(r, keyCmd);
				}
				r.delay(500);
				typeKey(r, KeyEvent.VK_CONTEXT_MENU);
				for (int i = 0; i < 3; i += 1) {
					typeKey(r, KeyEvent.VK_DOWN);
				}	
				typeKey(r, KeyEvent.VK_ENTER);
				
			} catch (IOException | AWTException e) {
				System.out.println("error");
				e.printStackTrace();
			}
		}
	}
	
	private static void typeKey(Robot r, int keyCmd) {
		r.keyPress(keyCmd);
		r.keyRelease(keyCmd);
	}

	private static int findKeyCmd (String start) {
		if (keyboard.containsKey(start)) {
			return keyboard.get(start);
		}
		return (int) start.charAt(0);
	}
	
}