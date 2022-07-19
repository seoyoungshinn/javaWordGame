
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.TimerTask;
import java.util.Vector;

public class GamePanel extends JPanel {
	

	private Player player = new Player();
	private static int comboscore=0;
	private static boolean isdrop = true;
	private static boolean bonusTime = false;
	private static int isbonus = 0;
	
	private JTextField input = new JTextField(30);
	private Vector<JLabel>targetVector = new Vector<JLabel>(); // targetLabel을 담는 targetVector
	private Vector<JLabel>targetDAN = new Vector<JLabel>(); // targetLabel을 담는 targetVector
	private Vector<JLabel>targetJIBANG = new Vector<JLabel>(); // targetLabel을 담는 targetVector
	// 색
	public Color skyBlue = new Color(219, 239, 255);
	public Color lightBlue = new Color(94, 177, 255);
	
	private ScorePanel scorePanel = null;
	private GameGroundPanel gameGroundPanel = new GameGroundPanel();
	private InputPanel inputPanel = new InputPanel();
	
	private TextSource textSource = new TextSource(); // 단어 벡터 생성

	
	// 단어를 생성하는 스레드
	private GenerateTANThread generateTANThread = new GenerateTANThread(targetVector, player);
	// 단어를 떨어뜨리는 스레드
	private DropTANThread dropTANThread = new DropTANThread(targetVector,player);
	// 땅에 닿은 단어 감지하는 스레드
	private DetectTANThread detectTANThread = new DetectTANThread(targetVector);
	
	// 단어를 생성하는 스레드
	private GenerateDANThread generateDANThread = new GenerateDANThread(targetDAN, player);
	// 단어를 떨어뜨리는 스레드
	private DropDANThread dropDANThread = new DropDANThread(targetDAN,player);
	// 땅에 닿은 단어 감지하는 스레드
	private DetectDANThread detectDANThread = new DetectDANThread(targetDAN);
	
	
	// 단어를 생성하는 스레드
	private GenerateJIBANGThread generateJIBANGThread = new GenerateJIBANGThread(targetJIBANG, player);
	// 단어를 떨어뜨리는 스레드
	private DropJIBANGThread dropJIBANGThread = new DropJIBANGThread(targetJIBANG,player);
	// 땅에 닿은 단어 감지하는 스레드
	private DetectJIBANGThread detectJIBANGThread = new DetectJIBANGThread(targetJIBANG);
	
	//private upTANThread upTANThread = new upTANThread(targetVector,player);
	
	// 레벨에 따른 난이도 조절

	private int [] generateTANSpeed= {4000,2000,1500};
	private int [] generateDANSpeed= {7000,6000,3000};
	private int [] dropTANSpeed= {1000,800,500};
	private int [] dropDANSpeed= {500,250,200};

	
	public GamePanel() {
	}
	
	public GamePanel(ScorePanel scorePanel, Player player) {
		// 기억해둔다.
		this.scorePanel = scorePanel;
		this.player = player;
		
		// 스레드 생성자 부르기
		generateTANThread = new GenerateTANThread(targetVector, player);
		dropTANThread = new DropTANThread(targetVector,player);
		//upTANThread = new upTANThread(targetVector,player);
		textSource = new TextSource(player.getLanguage()); // 단어 벡터 생성
		
		generateDANThread = new GenerateDANThread(targetDAN, player);
		dropDANThread = new DropDANThread(targetDAN,player);

		generateJIBANGThread = new GenerateJIBANGThread(targetJIBANG, player);
		dropJIBANGThread = new DropJIBANGThread(targetJIBANG,player);
		
		//레이아웃 설정
		setLayout(new BorderLayout());
		add(gameGroundPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
		
		// 입력란
		input.setHorizontalAlignment(JTextField.CENTER); // input JTextField 가운데정렬
		input.setFont(new Font("Aharoni", Font.PLAIN, 20));
		
		
		// textfield에서 enter 누르면 실행됨
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					JTextField t = (JTextField)(e.getSource());
					String inWord = t.getText(); // 사용자가 입력한 단어
					int flag =0;
					
			
					
					for (int i=0; i<targetVector.size(); i++) {
						String text = targetVector.get(i).getText();
						if (text.equals(inWord)) {
							t.setText(null);
							System.out.println(inWord + " 탄수화물 맞춤"); // 콘솔에서 확인 위함
							// 점수 증가
							scorePanel.increase(player,5);
							if(bonusTime == true)
								scorePanel.increase(player,5);
							// scorePaenl repaint
							scorePanel.repaintScore();
							gameGroundPanel.remove(targetVector.get(i)); // 패널에서 라벨 떼기
					
							targetVector.remove(i); // targetVector에서 삭제
							comboscore ++;
							System.out.println("COMBO! :"+comboscore);
							if (isbonus == 0)
								scorePanel.changeComboImage(comboscore);
							
							flag =1;
						}
							
					}
					
					for (int i=0; i<targetDAN.size(); i++) {
						String DANtext = targetDAN.get(i).getText();
						if(DANtext.equals(inWord))  { // 단어맞추기 성공	
							t.setText(null);
							System.out.println(inWord + " 단백질  맞춤"); // 콘솔에서 확인 위함
							// 점수 증가
							scorePanel.increase(player,7);
							if(bonusTime == true)
								scorePanel.increase(player,7);
							// scorePaenl repaint
							scorePanel.repaintScore();
							gameGroundPanel.remove(targetDAN.get(i)); // 패널에서 라벨 떼기
					
							targetDAN.remove(i); // targetVector에서 삭제
							comboscore ++;
							System.out.println("COMBO! :"+comboscore);
							if (isbonus == 0)
								scorePanel.changeComboImage(comboscore);
							flag = 1;
			
						}	
					}
			
					for (int i=0; i<targetJIBANG.size(); i++) {
						String JIBANGtext = targetJIBANG.get(i).getText();
						if(JIBANGtext.equals(inWord))  { // 단어맞추기 성공	
							t.setText(null);
							System.out.println(inWord + " 지방  맞춤"); // 콘솔에서 확인 위함
							// 점수 증가
							scorePanel.increase(player,20);
							if(bonusTime == true)
								scorePanel.increase(player,20);
							// scorePaenl repaint
							scorePanel.repaintScore();
							gameGroundPanel.remove(targetJIBANG.get(i)); // 패널에서 라벨 떼기
					
							targetJIBANG.remove(i); // targetVector에서 삭제
							comboscore ++;
							System.out.println("COMBO! :"+comboscore);
							if (isbonus == 0)
								scorePanel.changeComboImage(comboscore);
							flag = 1;
							//bombItem(); //주변에 음식을 뭉텅이로 먹어줌
							//setUpTimeTrue();
							//setBonusTimeTrue();
							//scorePanel.increaseLife(player);
							iTemSelct();
						}	
						
					}
					
				
					t.setText(null); // input 비우기
							
						// 벡터 마지막원소에서도 일치하는 단어 못찾음
					if( flag == 0 ){
							System.out.println(inWord + " 	틀림");
							// 점수 감소
							scorePanel.decrease(player);
							scorePanel.repaintScore();
							t.setText(null);
							comboscore = 0;
							System.out.println("COMBO! :"+comboscore);
							scorePanel.changeComboImage(comboscore);
					}
					
					if ((comboscore%10 == 0) && (comboscore !=0)) {
						scorePanel.increase(player,50);
						if (isbonus == 0)
							scorePanel.changeComboImage(100);
						
						
					}
				

					t.requestFocus(); // 엔터 친 후에도 textField에 focus유지
					scorePanel.comboScore.setText("COMBO! :"+comboscore);
					scorePanel.repaintCombo();
					t.setText(null);
				
				
					System.out.println(bonusTime);
		
			} // end of actionPerformed()
		});
	}
	
	public void iTemSelct() {
		int select = (int)(Math.random() * 10);
		switch(select) {
		case 0:		//확률 10%
			scorePanel.increaseLife(player); 
			break;
					//확률 40%
		case 1:
		case 2:
		case 3:
		case 4:
			bombItem();
			break;
			
					//확률 20%
		case 5:
		case 6:
			setUpTimeTrue();
			break;
			
					//확률 30%
		case 7:
		case 8:
		case 9:
			setBonusTimeTrue();
			break;
		}
	}
	
	
	public class BonusTimeThread extends Thread{
		public void run() {
			System.out.println("보너스타임시작 ");
			bonusTime = true;
			try {
				isbonus = 9999;
				for(int i=9;i>=0;i--) {
				scorePanel.changeComboImage(isbonus--);
				Thread.sleep(1000);
				}
			}catch(InterruptedException e) {
				return;
			}
			bonusTime = false;
			isbonus = 0;
			System.out.println("보너스타임 끗 ");
		}
	}
	
	public void setUpTimeTrue() {
		new UpWordThread ().start();
	}
	
	public class UpWordThread extends Thread{
		
		public void run() {
			System.out.println("UpWord시작 ");
			isdrop = false;
			try {
				Thread.sleep(5000);
			}catch(InterruptedException e) {
				return;
			}
			isdrop = true;
			System.out.println("UpWord 끗 ");
		}
	}
	
	public void setBonusTimeTrue() {
		scorePanel.changeComboImage(777);
		new BonusTimeThread ().start();
	}
	
	public void bombItem() {
		
		scorePanel.changeComboImage(444);
		
		for (int i=0; i<targetVector.size(); i++) {		
			scorePanel.increase(player,5);
			scorePanel.repaintScore();
			gameGroundPanel.remove(targetVector.get(i)); // 패널에서 라벨 떼기
			targetVector.remove(i); // targetVector에서 삭제	
		}
		for (int i=0; i<targetJIBANG.size(); i++) {	
			scorePanel.increase(player,20);
			scorePanel.repaintScore();
			gameGroundPanel.remove(targetJIBANG.get(i)); // 패널에서 라벨 떼기
			targetJIBANG.remove(i); // targetJIBANG에서 삭제	
		}
		for (int i=0; i<targetDAN.size(); i++) {		
			scorePanel.increase(player,7);
			scorePanel.repaintScore();
			gameGroundPanel.remove(targetDAN.get(i)); // 패널에서 라벨 떼기
			targetDAN.remove(i); // targetDAN에서 삭제	
		}
	}

	
	
	

	class GameGroundPanel extends JPanel{ // 단어내려오는곳 배경이미지
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("table.png");
			g.drawImage(icon.getImage(), 0, 0, gameGroundPanel.getWidth(),
					gameGroundPanel.getHeight(), gameGroundPanel);
			setOpaque(false);
		}
		public GameGroundPanel() {
			this.setBackground(skyBlue);
			// 단어가 마구잡이로 내려와야함.
			setLayout(null);
		}
	}
	
	class InputPanel extends JPanel{ // 단어 입력하는곳
		public InputPanel() {
			setLayout(new FlowLayout());
			this.setBackground(lightBlue);
			add(input);
		}
	}
	
	public void gameStart(Player player) {
		this.player = player;

		
		// 단어생성 시작
		generateTANThread.start();
		// 단어 떨어뜨리기 시작
		dropTANThread.start();
		// 땅에 닿은 단어 감지 시작
		detectTANThread.start();
		
		
		// 단어생성 시작
		generateDANThread.start();
				// 단어 떨어뜨리기 시작
		dropDANThread.start();
				// 땅에 닿은 단어 감지 시작
		detectDANThread.start();
		

		// 단어생성 시작
		generateJIBANGThread.start();
				// 단어 떨어뜨리기 시작
		dropJIBANGThread.start();
				// 땅에 닿은 단어 감지 시작
		detectJIBANGThread.start();
		
		//upTANThread.start();
		
	}
	
	public void gameOver() { // 게임종료
		// 단어생성 중단
		generateTANThread.interrupt();
		// 단어 떨어뜨리기 중단
		dropTANThread.interrupt();
		// 땅에 닿은 단어 감지 중단
		detectTANThread.interrupt();
	}
	


	
	
	// 단어 생성하는 스레드
	public class GenerateTANThread extends Thread{
		
		private Vector<JLabel>targetVector = null;
		private Player player = null;
		
		// 단어 가져와 Label설정, 부착하는 메소드
		synchronized void generateWord(Player player) {
			JLabel targetLabel = new JLabel("");
			// 단어 한 개 선택
			String newTAN = textSource.get(player.getLanguage());
			targetLabel.setText(newTAN);
			Image hambergerImage = new ImageIcon ("hambergerIcon.png").getImage().getScaledInstance(70,70,Image.SCALE_SMOOTH);
			ImageIcon hambergerIcon = new ImageIcon(hambergerImage);
			
			targetLabel.setIcon(hambergerIcon);
			
			// targetLabel 모양
			targetLabel.setHorizontalAlignment(JLabel.CENTER); // JLabel 가운데정렬
			targetLabel.setSize(200, 40);
			if(player.getLanguage()=="ko") {
				targetLabel.setFont(new Font("함초롬돋움",1,21));
			}
			else targetLabel.setFont(new Font("Dialog", 1, 21));
			targetLabel.setForeground(Color.BLUE);
			
			
			// x좌표 랜덤 설정
			int startX = (int) (Math.random()*gameGroundPanel.getWidth());
			while(true) {
				if ((startX + targetLabel.getWidth()) > gameGroundPanel.getWidth()) 
					startX = (int) (Math.random()*gameGroundPanel.getWidth());
				else
					break;
			}
			
			targetLabel.setLocation(startX,0);
			
			targetLabel.setOpaque(false); // 배경 투명하게
			targetVector.add(targetLabel); // targetVector에 생성한 newWord 추가
			gameGroundPanel.add(targetLabel);
		}
		
		public GenerateTANThread(Vector<JLabel>targetVector, Player player) {
			this.targetVector = targetVector;
			this.player = player;
		}
		
		@Override
		public void run() {
			while(true) {
				int generateTANtime = generateTANSpeed[player.getLevel()-1];
				generateWord(player);
				gameGroundPanel.repaint();
				try {
					sleep(generateTANtime);
				} catch (InterruptedException e) {
					return;
				}
			} // end of while
		} // end of run()
	} // end of GenerateWordThread
	
	// 단어 아래로 내리는 스레드
	public class DropTANThread extends Thread{
		
		private Vector<JLabel>targetVector = null;
		private Player player = null;
		
		public DropTANThread(Vector<JLabel>targetVector, Player player) {
			this.targetVector = targetVector;
			this.player = player;
		}
		
	
		
		// y좌표 증가해 단어 밑으로 내림
		synchronized void dropTAN(Player player) {
			for (int i=0; i<targetVector.size(); i++) {
				int x = targetVector.get(i).getX();
				int y = targetVector.get(i).getY();
				targetVector.get(i).setLocation(x, y+5);
				gameGroundPanel.repaint();
				
			} // end of for
		}
		
		synchronized void upTAN(Player player) {
			
			for (int i=0; i<targetVector.size(); i++) {
				int x = targetVector.get(i).getX();
				int y = targetVector.get(i).getY();
				if (y>0) {
				targetVector.get(i).setLocation(x, y-10);}				
				gameGroundPanel.repaint();				
			} // end of for
		}
		
		// targetVector에 들어있는 모든 JLabel들의 y좌표 증가
		@Override
	public void run() {
			 while (true){
				 int dropTANTime = dropTANSpeed[player.getLevel()-1];
	
				 try {
					 sleep(dropTANTime);
					 dropTAN(player);
					 if (isdrop == false) {
						 upTAN(player);
					 }
					 gameGroundPanel.repaint();
					} catch (InterruptedException e) {
						
						return;
				 }
			} // end of while
		} // end of run()
	} // end of DropWordThread
	

	
	public class DetectTANThread extends Thread {
		
		private Vector<JLabel>targetVector = null;
		
		public DetectTANThread(Vector<JLabel>targetVector) {
			this.targetVector = targetVector;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					sleep(1);
					for(int i=0; i<targetVector.size(); i++) {
						// 바닥에 닿은 단어 구하기 위함
						int y = ((JLabel)targetVector.get(i)).getY();
						if (y > gameGroundPanel.getHeight()-20) {
							System.out.println(targetVector.get(i).getText() + " 탄수화물  떨어짐");
							
							// true값이 반환되면 게임을 종료한다.
							boolean isGameOver =scorePanel.decreaseLife(player);
							if(isGameOver == true) { // 모든스레드 종료
								gameOver();
							}
							
							// 게임이 종료되지 않을 경우 패널에서 라벨 제거 게임 계속됨
							gameGroundPanel.remove(targetVector.get(i)); // 패널에서 라벨 떼기
							targetVector.remove(i); // targetVector에서 삭제
						}
					}
				} catch (InterruptedException e) {
					return;
				}
			} // end of while
		} // end of run()
	}// end of Thread
		
	











// 단백질
public class GenerateDANThread extends Thread{
	
	private Vector<JLabel>targetDAN = null;
	private Player player = null;
	
	// 단어 가져와 Label설정, 부착하는 메소드
	synchronized void generateWord(Player player) {
		JLabel targetDANLabel = new JLabel("");
		// 단어 한 개 선택
		String newDAN = textSource.get(player.getLanguage());
		targetDANLabel.setText(newDAN);
		
		Image cakeImage = new ImageIcon ("cake.png").getImage().getScaledInstance(60,50,Image.SCALE_SMOOTH);
		ImageIcon cakeIcon = new ImageIcon(cakeImage);
		
		targetDANLabel.setIcon(cakeIcon);		
		
		// targetLabel 모양
		targetDANLabel.setHorizontalAlignment(JLabel.CENTER); // JLabel 가운데정렬
		targetDANLabel.setSize(200, 40);
		if(player.getLanguage()=="ko") {
			targetDANLabel.setFont(new Font("함초롬돋움",1,21));
		}
		else targetDANLabel.setFont(new Font("Dialog", 1, 21));
		targetDANLabel.setForeground(Color.GREEN);
		
		
		// x좌표 랜덤 설정
		int startX = (int) (Math.random()*gameGroundPanel.getWidth());
		while(true) {
			if ((startX + targetDANLabel.getWidth()) > gameGroundPanel.getWidth()) 
				startX = (int) (Math.random()*gameGroundPanel.getWidth());
			else
				break;
		}
		
		targetDANLabel.setLocation(startX,0);
		
		targetDANLabel.setOpaque(false); // 배경 투명하게
		targetDAN.add(targetDANLabel); // targetVector에 생성한 newWord 추가
		gameGroundPanel.add(targetDANLabel);
	}
	
	public GenerateDANThread(Vector<JLabel>targetDAN, Player player) {
		this.targetDAN = targetDAN;
		this.player = player;
	}
	
	@Override
	public void run() {
		while(true) {
			
			int generateDANtime = generateDANSpeed[player.getLevel()-1];;
			generateWord(player);
			gameGroundPanel.repaint();
			try {
				sleep(generateDANtime);
			} catch (InterruptedException e) {
				return;
			}
			
			
		} // end of while
	} // end of run()
} // end of GenerateWordThread

// 단어 아래로 내리는 스레드
public class DropDANThread extends Thread{
	
	private Vector<JLabel>targetDAN = null;
	private Player player = null;
	
	public DropDANThread(Vector<JLabel>targetDAN, Player player) {
		this.targetDAN = targetDAN;
		this.player = player;
	}
	
	// y좌표 증가해 단어 밑으로 내림
	synchronized void dropDAN(Player player) {
		for (int i=0; i<targetDAN.size(); i++) {
			int x = targetDAN.get(i).getX();
			int y = targetDAN.get(i).getY();
			targetDAN.get(i).setLocation(x, y+5);
			gameGroundPanel.repaint();
		} // end of for
	}
	
	synchronized void upDAN(Player player) {
		for (int i=0; i<targetDAN.size(); i++) {
			int x = targetDAN.get(i).getX();
			int y = targetDAN.get(i).getY();
			if (y>0) {
				targetDAN.get(i).setLocation(x, y-10);}
				gameGroundPanel.repaint();	
		} // end of for
		
	}

	
	
	// targetVector에 들어있는 모든 JLabel들의 y좌표 증가
	@Override
	public void run() {
		 while (true){
			 int dropDAN = dropDANSpeed[player.getLevel()-1];;
			 dropDAN(player);
			 gameGroundPanel.repaint();
			 try {
				 sleep(dropDAN);
				 if (isdrop == false) {
					 upDAN(player);
				 }
				} catch (InterruptedException e) {
					return;
				} 
			 
			 
		} // end of while
	} // end of run()
} // end of DropWordThread

public class DetectDANThread extends Thread {
	
	private Vector<JLabel>targetDAN = null;
	
	public DetectDANThread(Vector<JLabel>targetDAN) {
		this.targetDAN = targetDAN;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				sleep(1);
				for(int i=0; i<targetDAN.size(); i++) {
					// 바닥에 닿은 단어 구하기 위함
					int y = ((JLabel)targetDAN.get(i)).getY();
					if (y > gameGroundPanel.getHeight()-20) {
						System.out.println(targetDAN.get(i).getText() + " 단백질 떨어짐");
						
						// true값이 반환되면 게임을 종료한다.
						boolean isGameOver =scorePanel.decreaseLife(player);
						if(isGameOver == true) { // 모든스레드 종료
							gameOver();
						}
						
						// 게임이 종료되지 않을 경우 패널에서 라벨 제거 게임 계속됨
						gameGroundPanel.remove(targetDAN.get(i)); // 패널에서 라벨 떼기
						targetDAN.remove(i); // targetVector에서 삭제
					}
				}
			} catch (InterruptedException e) {
				return;
			}
		} // end of while
	} // end of run()
}// end of Thread
	







//단백질
public class GenerateJIBANGThread extends Thread{
	
	private Vector<JLabel>targetJIBANG = null;
	private Player player = null;
	
	// 단어 가져와 Label설정, 부착하는 메소드
	synchronized void generateWord(Player player) {
		JLabel targetJIBANGLabel = new JLabel("");
		// 단어 한 개 선택
		String newJIBANG = textSource.get(player.getLanguage());
		targetJIBANGLabel.setText(newJIBANG);
		
		Image beerImage = new ImageIcon ("beer.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
		ImageIcon beerIcon = new ImageIcon(beerImage);
		
		targetJIBANGLabel.setIcon(beerIcon);	
		
		// targetLabel 모양
		targetJIBANGLabel.setHorizontalAlignment(JLabel.CENTER); // JLabel 가운데정렬
		targetJIBANGLabel.setSize(200, 40);
		if(player.getLanguage()=="ko") {
			targetJIBANGLabel.setFont(new Font("함초롬돋움",1,21));
		}
		else targetJIBANGLabel.setFont(new Font("Dialog", 1, 21));
		targetJIBANGLabel.setForeground(Color.RED);
		
		
		// x좌표 랜덤 설정
		int startX = (int) (Math.random()*gameGroundPanel.getWidth());
		while(true) {
			if ((startX + targetJIBANGLabel.getWidth()) > gameGroundPanel.getWidth()) 
				startX = (int) (Math.random()*gameGroundPanel.getWidth());
			else
				break;
		}
		
		targetJIBANGLabel.setLocation(startX,0);
		
		targetJIBANGLabel.setOpaque(false); // 배경 투명하게
		targetJIBANG.addElement(targetJIBANGLabel); // targetVector에 생성한 newWord 추가
		gameGroundPanel.add(targetJIBANGLabel);
	}
	
	public GenerateJIBANGThread(Vector<JLabel>targetJIBANG, Player player) {
		this.targetJIBANG = targetJIBANG;
		this.player = player;
	}
	
	@Override
	public void run() {
		while(true) {
			int generateJIBANGtime = 20000;

			
			try {
				sleep(generateJIBANGtime);
				
			} catch (InterruptedException e) {
				return;
			}
			generateWord(player);
			gameGroundPanel.repaint();
			
		} // end of while
	} // end of run()
} // end of GenerateWordThread

//단어 아래로 내리는 스레드
public class DropJIBANGThread extends Thread{
	
	private Vector<JLabel>targetJIBANG = null;
	private Player player = null;
	
	public DropJIBANGThread(Vector<JLabel>targetJIBANG, Player player) {
		this.targetJIBANG = targetJIBANG;
		this.player = player;
	}
	
	// y좌표 증가해 단어 밑으로 내림
	synchronized void dropJIBANG(Player player) {
		for (int i=0; i<targetJIBANG.size(); i++) {
			int x = targetJIBANG.get(i).getX();
			int y = targetJIBANG.get(i).getY();
			targetJIBANG.get(i).setLocation(x, y+5);
			gameGroundPanel.repaint();
		} // end of for
	}
	
	// targetVector에 들어있는 모든 JLabel들의 y좌표 증가
	@Override
	public void run() {
		 while (true){
			 int dropJIBANG = 50;
			 dropJIBANG(player);
			 gameGroundPanel.repaint();
	
			 try {
				 sleep(dropJIBANG);
				} catch (InterruptedException e) {

					return;
				}
		} // end of while
	} // end of run()
} // end of DropWordThread

public class DetectJIBANGThread extends Thread {
	
	private Vector<JLabel>targetJIBANG = null;
	
	public DetectJIBANGThread(Vector<JLabel>targetJIBANG) {
		this.targetJIBANG = targetJIBANG;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				sleep(1);
				for(int i=0; i<targetJIBANG.size(); i++) {
					// 바닥에 닿은 단어 구하기 위함
					int y = ((JLabel)targetJIBANG.get(i)).getY();
					if (y > gameGroundPanel.getHeight()-20) {
						System.out.println(targetJIBANG.get(i).getText() + " 지방  떨어짐");
						
						// true값이 반환되면 게임을 종료한다.
						boolean isGameOver =scorePanel.decreaseLife(player);
						if(isGameOver == true) { // 모든스레드 종료
							gameOver();
						}
						
						// 게임이 종료되지 않을 경우 패널에서 라벨 제거 게임 계속됨
						gameGroundPanel.remove(targetJIBANG.get(i)); // 패널에서 라벨 떼기
						targetJIBANG.remove(i); // targetVector에서 삭제
					}
				}
			} catch (InterruptedException e) {
				return;
			}
		} // end of while
	} // end of run()
}// end of Thread

}