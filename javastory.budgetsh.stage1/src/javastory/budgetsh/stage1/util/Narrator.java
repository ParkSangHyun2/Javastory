package javastory.budgetsh.stage1.util;

public class Narrator {
	//
	private String playerClass;
	private TalkingAt position;
	private boolean silentMode;
	private boolean formatted;

	public Narrator(Object playerClass, TalkingAt position) {
		//
		this.playerClass = playerClass.getClass().getSimpleName();
		this.position = position;
	}

	public void setFormatted(boolean formatted) {
		//
		this.formatted = formatted;
	}

	public void keepSilent() {
		//
		silentMode = false;
	}

	public void keepTalktive() {
		//
		silentMode = true;
	}

	public void say(String line) {
		//
		printOut(appendTabs().append(formatMessage(line)));
	}

	public void sayln(String line) {
		//
		printOut(appendTabs().append(formatMessage(line)).append("\n"));
	}

	public void sayNewLine() {
		//
		System.out.print("\n");
	}

	public void printOut(StringBuffer message) {
		//
		if (!silentMode) {
			System.out.print(message.toString());
		} else {
			System.out.print("*");
		}
	}

	private String formatMessage(String message) {
		//
		if (formatted) {
			return String.format("<:%s> %s", playerClass, message);
		} else {
			return message;
		}
	}

	private StringBuffer appendTabs() {
		//
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < this.position.tabCount(); i++) {
			buffer.append("\t");
		}

		return buffer;
	}

	public void sleep(int seconds) {
		//
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
