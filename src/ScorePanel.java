
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class ScorePanel extends JPanel {
	private Player player = new Player();
	private GamePanel gamePanel = new GamePanel();
	private static int score = 0;
	private int life = 5; // 생명
	private JLabel textLabel = new JLabel("점수");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private JLabel [] lifeLabel = new JLabel[life];
	private JLabel warningLabel = new JLabel("<html>하트가 모두 없어지면<br>※※ Game Over! ※※</html>");
	private Color skyBlue = new Color(153, 214, 255);
	public JLabel comboScore = new JLabel ();
	public JLabel comboImage = new JLabel ();
	
	private ImageIcon comboImg = new ImageIcon("comboplus.png");
	private Image comboImgResize = comboImg.getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private ImageIcon successCombo = new ImageIcon(comboImgResize);
	
	private ImageIcon comboImg2 = new ImageIcon("combofailed.png");
	private Image comboImgResize2 = comboImg2.getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private ImageIcon failedCombo = new ImageIcon(comboImgResize2);
	
	private ImageIcon comboImg5 = new ImageIcon("combo5.png");
	private Image comboImgResize5 = comboImg5.getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private ImageIcon combo5 = new ImageIcon(comboImgResize5);
	
	private ImageIcon comboImg10 = new ImageIcon("combo10.png");
	private Image comboImgResize10 = comboImg10.getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private ImageIcon combo10 = new ImageIcon(comboImgResize10);
	
	private ImageIcon comboImg100 = new ImageIcon("combo100.png");
	private Image comboImgResize100 = comboImg100.getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private ImageIcon combo100 = new ImageIcon(comboImgResize100);
	
	private ImageIcon comboImg444 = new ImageIcon("bomb.png");
	private Image comboImgResize444 = comboImg444.getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private ImageIcon combo444 = new ImageIcon(comboImgResize444);
	
	private ImageIcon comboImg777 = new ImageIcon("double.png");
	private Image comboImgResize777 = comboImg777.getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private ImageIcon combo777 = new ImageIcon(comboImgResize777);
	
	private ImageIcon [] numbers = {
			new ImageIcon("number/number0.png"),
			new ImageIcon("number/number1.png"),
			new ImageIcon("number/number2.png"),
			new ImageIcon("number/number3.png"),
			new ImageIcon("number/number4.png"),
			new ImageIcon("number/number5.png"),
			new ImageIcon("number/number6.png"),
			new ImageIcon("number/number7.png"),
			new ImageIcon("number/number8.png"),
			new ImageIcon("number/number9.png")	
	};
	private Image number0 = numbers[0].getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private Image number1 = numbers[1].getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private Image number2 = numbers[2].getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private Image number3 = numbers[3].getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private Image number4 = numbers[4].getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private Image number5 = numbers[5].getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private Image number6 = numbers[6].getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private Image number7 = numbers[7].getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private Image number8 = numbers[8].getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
	private Image number9 = numbers[9].getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);

	private ImageIcon [] numberIcon = {
		new ImageIcon(number0),
		new ImageIcon(number1),
		new ImageIcon(number2),
		new ImageIcon(number3),
		new ImageIcon(number4),
		new ImageIcon(number5),
		new ImageIcon(number6),
		new ImageIcon(number7),
		new ImageIcon(number8),
		new ImageIcon(number9)
	};

	
	
	public void changeComboImage(int iscombo) {
		
		
		if (iscombo== 0) {
			comboImage.setIcon(failedCombo);
			comboImage.getParent().repaint();
		}
		else if (iscombo ==100) {
			comboImage.setIcon(combo100);
			comboImage.getParent().repaint();
		}
		else if (iscombo ==444) {
			comboImage.setIcon(combo444);
			comboImage.getParent().repaint();
		}
		else if (iscombo >9000) {
			int num = iscombo%90;
			comboImage.setIcon(numberIcon[num]);
			comboImage.getParent().repaint();
		}
		else if (iscombo <5) {
			comboImage.setIcon(successCombo);
			comboImage.getParent().repaint();
		}
		else if (iscombo <10) {
			comboImage.setIcon(combo5);
			comboImage.getParent().repaint();
		}	
		else  {
			comboImage.setIcon(combo10);
			comboImage.getParent().repaint();
		}
		
	}
	
	public ScorePanel() {		
		
		setBackground(skyBlue);
		setLayout(null);
		
		textLabel.setFont(new Font("함초롬돋움",1,15));
		textLabel.setSize(50,20);
		textLabel.setLocation(20,200);
		add(textLabel);
		
		scoreLabel.setFont(new Font("함초롬돋움",1,15));
		scoreLabel.setSize(100,20);
		scoreLabel.setLocation(100,200);
		add(scoreLabel);
		

		
		comboImage.setSize(130,130);
		comboImage.setIcon(successCombo);
		comboImage.setLocation(30,350);
		add(comboImage);
		
		comboScore.setFont(new Font("Gothic",Font.ITALIC,20));
		comboScore.setSize(130,30);
		comboScore.setLocation(30,300);
		comboScore.setText(" COMBO! : 0");
		comboScore.setOpaque(true);
		comboScore.setBackground(Color.WHITE);
		comboScore.setForeground(Color.MAGENTA);
		add(comboScore);
		ImageIcon heart = new ImageIcon("heart.png");

		for (int i=0; i<life; i++) {
			lifeLabel[i] = new JLabel(heart);
			lifeLabel[i].setSize(heart.getIconWidth(),heart.getIconHeight());
			lifeLabel[i].setLocation(30*i+20,50);
			add(lifeLabel[i]);
		}
		
		warningLabel.setFont(new Font("함초롬돋움",1,15));
		warningLabel.setSize(200,50);
		warningLabel.setLocation(20,70);
		add(warningLabel);

	}
	
	synchronized void increase(Player player, int n) {
		score += n ;

		System.out.println("점수 "+ score + "로 증가  ");
		player.setScore(score);
		scoreLabel.setText(Integer.toString(score));
		System.out.println("점수" + score + "로 고침");
		scoreLabel.getParent().repaint();
	}

	synchronized void decrease(Player player) {
		score -= 10;
		
		System.out.println("점수 "+ score + "로 감소  ");
		player.setScore(score);
		scoreLabel.setText(Integer.toString(score));
		scoreLabel.getParent().repaint();
	}
	
	public void repaintScore() {
		scoreLabel.getParent().repaint();
	}
	public void repaintCombo() {
		comboScore.getParent().repaint();
	}
	
	public void initPlayerInfo(String name, int level, int score, String language) {
		player = new Player(name, level, score, language);

	}
	
	synchronized boolean decreaseLife(Player player) {
		life--;
		boolean isTrue = false;
		
		switch(life) {
		case 4: // ♥ ♥ ♥ ♥ ♡ 
			lifeLabel[4].setVisible(false);
			break;
		case 3: // ♡ ♥ ♥ ♥ ♡ 
			lifeLabel[0].setVisible(false);
			break;
		case 2: // ♡ ♥ ♥ ♡ ♡ 
			lifeLabel[3].setVisible(false);

			break;
		case 1: // ♡ ♡ ♥ ♡ ♡ 
			lifeLabel[1].setVisible(false);
			break;
		case 0: // ♡ ♡ ♡ ♡ ♡ 
			lifeLabel[2].setVisible(false);
			// 현재 Panel안보이게
			warningLabel.setText("GAME OVER");
			warningLabel.setLocation(70,70);
		
			
			// 게임 종료 후 정보 저장
			Player p = new Player(player.getName(), player.getLevel(),
					player.getScore(), player.getLanguage());
			p.storeInfo();
			
			// 종료할것인지 물어보는 JOptionPane
			String [] answer = {"예", "다시시작"};
			int choice = JOptionPane.showOptionDialog(gamePanel, player.getName() + "은(는) " + player.getScore() + "점 입니다.\n게임을 종료하시겠습니까?",
					"게임 종료", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, answer, null);
			
			if(choice == 0) { // "예" 선택. 창 닫는다
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
			else if(choice == 1) { // "다시시작" 선택. 현재 프레임 닫고 새 프레임 연다
				// 스레드 종료하고  다시 시작...
				GameFrame f = new GameFrame();
				isTrue = true;
			}
			
			break;
		}
		return isTrue;
	}
	
	
	synchronized boolean increaseLife(Player player) {
		if(life==5) 
			return false;
		life++;
		boolean isTrue = false;
		
		switch(life) {
		case 5: // ♥ ♥ ♥ ♥ ♡ 
			lifeLabel[4].setVisible(true);
			break;
		case 4: // ♡ ♥ ♥ ♥ ♡ 
			lifeLabel[0].setVisible(true);
			break;
		case 3: // ♡ ♥ ♥ ♡ ♡ 
			lifeLabel[3].setVisible(true);

			break;
		case 2: // ♡ ♡ ♥ ♡ ♡ 
			lifeLabel[1].setVisible(true);
			break;
		case 1: // ♡ ♡ ♡ ♡ ♡ 
			lifeLabel[2].setVisible(true);
			// 현재 Panel안보이게
			warningLabel.setText("GAME OVER");
			warningLabel.setLocation(70,70);
		
			
			// 게임 종료 후 정보 저장
			Player p = new Player(player.getName(), player.getLevel(),
					player.getScore(), player.getLanguage());
			p.storeInfo();
			
			// 종료할것인지 물어보는 JOptionPane
			String [] answer = {"예", "다시시작"};
			int choice = JOptionPane.showOptionDialog(gamePanel, player.getName() + "은(는) " + player.getScore() + "점 입니다.\n게임을 종료하시겠습니까?",
					"게임 종료", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, answer, null);
			
			if(choice == 0) { // "예" 선택. 창 닫는다
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
			else if(choice == 1) { // "다시시작" 선택. 현재 프레임 닫고 새 프레임 연다
				// 스레드 종료하고  다시 시작...
				GameFrame f = new GameFrame();
				isTrue = true;
			}
			
			break;
		}
		return isTrue;
	}
	
}