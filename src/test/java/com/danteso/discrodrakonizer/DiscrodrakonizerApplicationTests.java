package com.danteso.discrodrakonizer;

import com.danteso.discrodrakonizer.bot.DiscordBot;
import com.danteso.discrodrakonizer.bot.actions.SendPngAction;
import com.danteso.discrodrakonizer.browser.PikabuParser;
import com.danteso.discrodrakonizer.browser.RandomQuery;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
@SpringBootTest
class DiscrodrakonizerApplicationTests {

	@Test
	void contextLoads() throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader("src/main/resources/templates/messageTemplate.html"));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
		}
		String htmlTemplate = contentBuilder.toString();
		//String htmlTemplate = "<html><body><div>User: $user</div><div>Date: $date</div><div>Content: $content</div></body></html>";
		System.out.println(htmlTemplate);
		// Populate parameters
		Map<String, String> parameters = new HashMap<>();
		parameters.put("user", "Drakonizer");
		parameters.put("date", "Сегодня, в 17:03");
		parameters.put("content", "Pong!");

		String html = htmlTemplate;
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			html = html.replace("$" + entry.getKey(), entry.getValue());
		}

		HtmlImageGenerator imageGenerator = new HtmlImageGenerator() {
			protected JEditorPane createJEditorPane() {
				JEditorPane editor = super.createJEditorPane();
				editor.setOpaque(false); // The solution
				return editor;
			}
		};

		imageGenerator.loadHtml(html);
		imageGenerator.saveAsImage("testing.png");
	}


	@Test
	public void version3() throws IOException {

	}


	@Autowired
	PikabuParser parser;


	@Autowired
	DiscordBot bot;


	@Test
	public void test() throws IOException {
		RandomQuery randomQuery = new RandomQuery();
		SendPngAction sendPngAction = new SendPngAction(null, randomQuery);
		sendPngAction.findImageLink();
	}

//	@Test
//	public void main() throws IOException, FontFormatException {
//		createDiscordMessageImage("src/main/resources/templates/user_icon.png", "UserNickname", "Hello there! This is a Discord-like message.", "May 4, 2024", "output.png");
//	}
//
//
//	public void createDiscordMessageImage(String iconPath, String nickname, String content, String date, String outputPath) throws IOException, FontFormatException {
//		int width = 400;
//		int height = 160;
//
//		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D graphics = image.createGraphics();
//
//        // Set Background Color (Darker)
//        graphics.setColor(new Color(47, 49, 54));
//		graphics.fillRect(0, 0, width, height);
//
//		// Load and draw icon
//		try {
//            Image icon = ImageIO.read(new File(iconPath));
//			graphics.drawImage(icon, 10, 10, 100, 100, null);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		// Set Font and Color for Username
//		graphics.setColor(Color.WHITE);
//		Font usernameFont = Font.createFont(Font.TRUETYPE_FONT, new File("gg sans Regular.ttf")).deriveFont(24f);
//        graphics.setFont(usernameFont);
//        graphics.drawString(nickname, 120, 30);
//
//        // Set Font and Color for Message Date
//        graphics.setColor(Color.WHITE);
//            Font dateFont = Font.createFont(Font.TRUETYPE_FONT, new File("gg sans Regular.ttf")).deriveFont(12f);
//        graphics.setFont(dateFont);
//        graphics.drawString(date, 120, 50);
//
//		// Set Font and Color for Message Content
//		graphics.setColor(Color.WHITE);
//            Font contentFont = Font.createFont(Font.TRUETYPE_FONT, new File("gg sans Regular.ttf")).deriveFont(24f);
//        graphics.setFont(contentFont);
//        drawWrappedString(graphics, content, 120, 70, width - 130, 20);
//
//		graphics.dispose();
//
//		// Save the image to a file
//		try {
//			ImageIO.write(image, "PNG", new File(outputPath));
//			System.out.println("Image saved successfully.");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void drawWrappedString(Graphics2D g, String text, int x, int y, int width, int lineHeight) {
//		FontMetrics fm = g.getFontMetrics();
//		int yPosition = y;
//		for (String line : text.split("\n")) {
//			while (fm.stringWidth(line) > width) {
//				int spaceIndex = line.lastIndexOf(' ', (int) (width / fm.charWidth(' ')));
//				if (spaceIndex == -1) {
//					break;
//				}
//				g.drawString(line.substring(0, spaceIndex), x, yPosition);
//				yPosition += lineHeight;
//				line = line.substring(spaceIndex + 1);
//			}
//			g.drawString(line, x, yPosition);
//			yPosition += lineHeight;
//		}
//	}

}
