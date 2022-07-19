
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.swing.*;


public class GameFrame extends JFrame{
	
	
	  
	Thread snow;
	SnowPanel sp;
	
	
	private Player player = new Player();
	private int score = 0;
	
	private LoginPanel loginPanel = new LoginPanel();
	private ScorePanel scorePanel  =new ScorePanel();
	private GamePanel gamePanel = new GamePanel();
	
	private Music loginBGM = new Music("jingleBell.mp3",true);
	
	public GameFrame() {
		setTitle("타이핑 게임 ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setContentPane(loginPanel);	//LoginPanel을 컨텐트 팬으로 설정
		
		loginBGM.start(); // 로그인페이지 배경음악
		
		setLocationRelativeTo(null);	//컴퓨터 화면 중앙에 프레임 생성하도록
		setVisible(true);

	}
	

	


	 class SnowPanel extends JPanel implements Runnable{
	        Vector<Point> v =new Vector<Point>();//눈덩이 50개의 위치를 저장
	        SnowPanel(){
	            this.setLayout(null);
	            for(int i=0; i<50; i++){
	                int xS=(int)(Math.random()*this.getWidth());
	                int yS=(int)(Math.random()*this.getHeight());
	                v.add(new Point(xS,yS));
	            }
	        }
	        
	        public void paintComponent(Graphics g){
	        	System.out.println("눈");
	            super.paintComponent(g);
	            ImageIcon icon=new ImageIcon("back.jpg");
	            Image img=icon.getImage();
	            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);//배경
	            g.setColor(Color.WHITE);
	            for(int i=0; i<v.size(); i++){//10x10 눈덩이를 그린다.
	                Point p=v.get(i);
	                g.fillOval((int)p.getX(), (int)p.getY(), 10, 10);
	            }
	        }
	        
	        public void changeSnowPoaition(){
	            for(int i=0; i<v.size(); i++){
	                Point p=v.get(i);
	                p.x+=(int)(Math.random()*20);
	                p.y+=(int)(Math.random()*5);
	                //눈덩이가 프레임 밖으로 나가게 되면 나간 좌표를 0으로 초기화
	                if(p.x>this.getWidth())
	                    p.x=0;
	                if(p.y>this.getHeight())
	                    p.y=0;
	                v.set(i, p);
	            }
	        }
	        
	        public void run(){
	        	
	            while(true){
	                try{
	                    Thread.sleep(50);
	                }
	                catch(Exception e){
	                    return;
	                }
	                changeSnowPoaition();
	                repaint();//계속 업데이트
	            }
	        }
	    }


	
	
	
	
	
	public class LoginPanel extends JPanel{
		
	
		//LoginPage
		//그룹을 만들어 한번에 부착하도록 하기 위함
		private Box langBox = Box.createHorizontalBox();
		private Box levelBox = Box.createHorizontalBox();
		private Box nameBox = Box.createHorizontalBox();
		
		private ImageIcon title = new ImageIcon("title.png");
		private Image titleImgResize = title.getImage().getScaledInstance(700,100,Image.SCALE_SMOOTH);
		private ImageIcon titleIcon = new ImageIcon(titleImgResize);
		
		private JLabel mainTitle = new JLabel (title);
		private JLabel langLabel = new JLabel("언어   ");
		private JLabel lvLabel = new JLabel("레벨    ");
		private String [] level = {"EASY","NORMAL","HARD"};
		private JComboBox<String> lvCombo = new JComboBox<String>(level);	
			//레벨 고르는 콤보박스
		private JRadioButton [] radio = new JRadioButton [2];
			//언어 선택하는 JRadioButton
		private ButtonGroup g = new ButtonGroup();
		private String [] langType = {"한 	","영	"};
		private JLabel name = new JLabel ("이름    ");
		private JTextField inputName = new JTextField(30); //플레이어 이름 입력 칸
		private JButton gameStartBtn=new JButton(new ImageIcon("gameStart.png"));
		
		private JButton rankViewBtn = new JButton(new ImageIcon("viewRanking.png"));
		private JButton editKoWordBtn = new JButton(new ImageIcon("addE.png"));
		private JButton editEnWordBtn = new JButton(new ImageIcon("addK.png"));
		
		//Ranking Page
		private JLabel rankTitle = new JLabel("Top 10");
		private ImageIcon home = new ImageIcon ("homeIcon.png");
		private Image homeImage = home.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
		private ImageIcon homeIcon = new ImageIcon(homeImage);
		
		
		private JButton goHome = new JButton(homeIcon);
		private JLabel modeTitle = new JLabel();

		private String line;
		private String []splitLine = new String[2];
		private String []rankText = new String[10];
		private JLabel []rankLabel = new JLabel[10];
		private JLabel []scoreText = new JLabel[10];
		
		@Override
		public void paintComponent(Graphics g) {//배경 이미지 설정 
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("longinbackbround.jpg");
			g.drawImage(icon.getImage(),0, 0, this.getWidth(), this.getHeight(), this);
			
			
			setOpaque(false);
		}

		
		public LoginPanel() {
			this.setLayout(null); // 원하는 좌표에 컴포넌트 부착 위함
			
			
			
			mainTitle.setFont(new Font("Goudy Stout",1,40));
			mainTitle.setBounds(50, 20, 700, 100);
			mainTitle.setForeground(Color.WHITE);
			
			langLabel.setFont(new Font("함초롬돋움",1,20));
			langBox.add(langLabel);
			langBox.setBounds(280, 150, 200, 30);
			langLabel.setForeground(Color.WHITE);

			lvLabel.setFont(new Font("함초롬돋움",1,20));
			lvLabel.setForeground(Color.WHITE);
			lvCombo.setFont(new Font("함초롬돋움",1,20));
			lvCombo.setForeground(Color.BLACK);
			levelBox.add(lvLabel);
			levelBox.add(lvCombo);
			levelBox.setBounds(280, 200, 200, 30);
			
			for (int i=0; i<radio.length; i++) {
				radio[i] = new JRadioButton(langType[i]);
				g.add(radio[i]);
				langBox.add(radio[i]);
				radio[i].setFont(new Font("함초롬돋움",1,30));
				radio[i].setForeground(Color.WHITE);
			}
			
			radio[0].setSelected(true);

			
			name.setFont(new Font("함초롬돋움",1,20));
			name.setForeground(Color.WHITE);
			inputName.setFont(new Font("함초롬돋움",1,15));
			nameBox.add(name);
			nameBox.add(inputName);
			nameBox.setBounds(280, 250, 200, 30);
		
			//gameStartBtn.setFont(new Font("함초롬돋움",1,15));
			gameStartBtn.setBounds(250, 290, 280, 80);
			//gameStartBtn.setBackground(Color.RED);
			gameStartBtn.setOpaque(false);
			//gameStartBtn.setForeground(Color.WHITE);
			gameStartBtn.setBorderPainted(false);	
			

			rankViewBtn.setBounds(250, 370, 280, 80);
			rankViewBtn.setOpaque(false);
			rankViewBtn.setBorderPainted(false);
			
			
			editKoWordBtn.setBounds(70, 450, 280, 100);
			
			editKoWordBtn.setOpaque(false);
			editKoWordBtn.setBorderPainted(false);
			
			editEnWordBtn.setOpaque(false);
			editEnWordBtn.setBounds(450, 450, 280, 100);			
			editEnWordBtn.setBorderPainted(false);
			
			add(mainTitle);
			add(langBox);
			add(levelBox);
			add(nameBox);
			add(gameStartBtn);
			add(rankViewBtn);
			add(editKoWordBtn);
			add(editEnWordBtn);
			// 게임 시작 버튼
						gameStartBtn.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// 버튼 클릭시 효과음 재생 false면 한번만 재생한다.
								
								
								// 선택한 radioButton의 index저장
								int selectedIndex;
								if(radio[0].isSelected()) selectedIndex = 0;
								else selectedIndex = 1;
								
								// Player객체 설정
								player = new Player(inputName.getText(),
										lvCombo.getSelectedIndex()+1, score, radio[selectedIndex].getText());
								player.setName(inputName.getText());
								player.setLevel(lvCombo.getSelectedIndex()+1);
								player.setLanguage(langType[selectedIndex]);
								
								// gamePanel생성
								gamePanel = new GamePanel(scorePanel, player);
								
								// LoginPanel의 모든 요소를 안보이도록 설정
								setLoginPageHidden();
								
								// 부착할 패널의 레이아웃 설정
								getContentPane().setLayout(new BorderLayout());
								splitPane(); // JsplitPane을 생성하여 ContentPane의 CENTER에 부착
								makeInfoPanel(player);
								setResizable(false); // 사이즈 조정 못하도록
							
								repaint();
								loginBGM.close(); // loginBGM 종료
								gamePanel.gameStart(player);
							}
						}); // end of ActionListener
						
						rankViewBtn.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// 버튼 클릭시 효과음 재생
								
								setLoginPageHidden();
								
								int selectedIndex;
								if(radio[0].isSelected()) selectedIndex = 0;
								else selectedIndex = 1;
								
								// 필요한 Player정보 저장
								player = new Player(inputName.getText(),
										lvCombo.getSelectedIndex()+1, score, radio[selectedIndex].getText());					
								player.setLevel(lvCombo.getSelectedIndex()+1);
								player.setLanguage(langType[selectedIndex]);
								
								rankTitle.setFont(new Font("Goudy Stout",1,40));
								rankTitle.setBounds(270, 60, 800, 40);
								rankTitle.setForeground(Color.WHITE);
								
								setModeTitle(player);
								modeTitle.setFont(new Font("Goudy Stout",1,20));
								modeTitle.setBounds(260, 120, 400, 20);
								modeTitle.setForeground(Color.WHITE);
								
								goHome.setBounds(380,100, home.getIconWidth(), home.getIconHeight());
								
								// 이미지만 보이게
								goHome.setBorderPainted(false);
								goHome.setFocusPainted(false);
								goHome.setContentAreaFilled(false);
								goHome.setVisible(true);
								//add(goHome);
								// 내림차순으로 Sorting한 기록파일 불러옴
								String fileName = "sorted" + player.getLanguage()
								+ player.getLevel()+".txt";
								
								try {
									BufferedReader in = new BufferedReader(new InputStreamReader(
											new FileInputStream(fileName), "MS949"));
									
									int i=0;
									while (i<10) {
										line = in.readLine();
										if(line == null) break; // 랭킹이 10위까지 있지 않을 때
										splitLine = line.trim().split(",");
										// data[0]은 name, data[1]은 score
										rankText[i] = Integer.toString(i+1) + "     " + splitLine[0];
										rankLabel[i] = new JLabel(rankText[i]);
										rankLabel[i].setFont(new Font("함초롬돋움",1,15));
										rankLabel[i].setBounds(300, 150+i*22, 700, 20);
										
										scoreText[i] = new JLabel(splitLine[1]);
										scoreText[i].setFont(new Font("함초롬돋움",1,15));
										scoreText[i].setForeground(Color.WHITE);
										scoreText[i].setBounds(500, 150+i*22, 700, 20);
										add(rankLabel[i]);
										add(scoreText[i]);
										
										i++;
									}
										
								} catch (IOException e1) {
									System.out.println("해당 랭킹파일 없음");
								} finally {
									add(rankTitle);
									add(modeTitle);
									add(goHome);
									
									// 다시 LoginPanel
									goHome.addActionListener(new ActionListener() { 
										@Override
										public void actionPerformed(ActionEvent e) {
											// 버튼 클릭시 효과음 재생
											
											GameFrame f = new GameFrame();
											//setRankPageHidden();							
											//setLoginPageVisible();
											//repaint();
										
										}
									});
								}
							}
						}); // end of ActionListener
						
						editEnWordBtn.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								editWord("en.txt");
							}
							
						});
						
						editKoWordBtn.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								editWord("ko.txt");
							}
							
						});
						
					
				} // end of LoginPanel()
	
		
		public void setLoginPageHidden() {
			mainTitle.setVisible(false);
			langBox.setVisible(false);
			levelBox.setVisible(false);
			nameBox.setVisible(false);
			gameStartBtn.setVisible(false);
			rankViewBtn.setVisible(false);
			editKoWordBtn.setVisible(false);
			editEnWordBtn.setVisible(false);
		}
		
		public void setLoginPageVisible() {
			mainTitle.setVisible(true);
			langBox.setVisible(true);
			levelBox.setVisible(true);
			nameBox.setVisible(true);
			gameStartBtn.setVisible(true);
			rankViewBtn.setVisible(true);
			editKoWordBtn.setVisible(true);
			editEnWordBtn.setVisible(true);
		}
		
		public void setRankPageHidden() {
			rankTitle.setVisible(false);
			modeTitle.setVisible(false);
			goHome.setVisible(false);
			
			for(int i=0; i < rankLabel.length; i++) {
				rankLabel[i].setVisible(false);
				scoreText[i].setVisible(false);
			}
		}
			
		public void setModeTitle(Player player) {
			modeTitle = new JLabel(player.getLanguage() + " Mode Lv." + player.getLevel());
		}
	
	protected void editWord(String fileName) {
		JOptionPane edit = new JOptionPane();
		String str = edit.showInputDialog("추가할 단어를 입력하세요!");
		String word = str.trim(); // 혹시모를 공백 제거
		try {
			FileWriter out = new FileWriter(fileName,true);
			out.write("\n" + word);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}

	private void splitPane() {
		JSplitPane hPane = new JSplitPane();
		getContentPane().add(hPane, BorderLayout.CENTER); // CENTER에 부착
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); // 수평으로 쪼갬
		hPane.setDividerLocation(600);
		hPane.setEnabled(false); // 활성화 막아버림(못움직이게)
		hPane.setLeftComponent(gamePanel);
		hPane.setRightComponent(scorePanel);
	}
	
	public void makeInfoPanel(Player player) {
		
		getContentPane().add(new UserInfoPanel(player), BorderLayout.NORTH);
	}
	
	public class UserInfoPanel extends JPanel{
		// 게임 플레이 중 상단에 플레이어 정보를 표시
		public UserInfoPanel(Player player) {
			int level;
			String userName;
			String lang;
			level = player.getLevel();
			userName = player.getName();
			lang = player.getLanguage();
			
			this.setLayout(new FlowLayout());
		
			JLabel name = new JLabel("플레이어:");
			JLabel userNameInfo = new JLabel("");
			userNameInfo.setText(userName + "  / ");
			JLabel levelInfo = new JLabel("");
			levelInfo.setText("Lv." + Integer.toString(level));
			JLabel langInfo = new JLabel("");
			langInfo.setText(" / " + lang);
			
			name.setFont(new Font("함초롬돋움",Font.BOLD,12));
			userNameInfo.setFont(new Font("함초롬돋움",Font.BOLD,12));
			levelInfo.setFont(new Font("함초롬돋움",Font.BOLD,12));
			langInfo.setFont(new Font("함초롬돋움",Font.BOLD,12));
			
			add(name); 
			add(userNameInfo);
			add(levelInfo);
			add(langInfo);
		}
	
	}
	}
}