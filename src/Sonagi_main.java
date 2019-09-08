import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.sampled.*;
import java.io.*;

// words.txt 파일을 읽고 벡터에 저장하고 벡터로부터 랜덤하게 단어를 추출하는 클래스
class Words {
   private Vector<String> wordVector = new Vector<String>();//파일에서 단어를 읽어 저장할 벡터
   public Words(String fileName) {
      try {
         Scanner scanner = new Scanner(new FileReader(fileName));
         while(scanner.hasNext()) { // 파일 끝까지 읽음
            String word = scanner.nextLine(); // 한 라인을 읽고 '\n'을 버린 나머지 문자열만 리턴
            wordVector.add(word); // 문자열을 벡터에 저장
         }
         scanner.close();
      }
      catch(FileNotFoundException e) {//파일을 읽을 수 없는 경우
         System.out.println("file not found error");
         System.exit(0);
      }
   }
   
   public String getRandomWord() {//랜덤하게 단어를 추출해서 반환하는 함수
      final int WORDMAX = wordVector.size(); // 총 단어의 개수
      int index = (int)(Math.random()*WORDMAX);//인덱스를 랜덤하게 추출
      return wordVector.get(index);//인덱스에 해당하는 단어 반환
   }   
}
class Background extends JPanel {//닉네임입력 화면,게임화면 안쪽 배경 이미지가 그려진 패널
   private ImageIcon icon = new ImageIcon("images/rainning.png");//이미지로딩
   private Image img = icon.getImage();//이미지 객체
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img, 0, 0, getWidth(), getHeight(), this);//이미지를 패널 크기로 조절하여 그린다
   }
}

class Side_BG extends JPanel {//게임화면 바깥 배경 이미지가 그려진 패널
   private ImageIcon icon2 = new ImageIcon("images/durty2.png");//이미지로딩
   private Image img2 = icon2.getImage();//이미지 객체
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img2, 0, 0, getWidth(), getHeight(), this);//이미지를 패널 크기로 조절하여 그린다
   }
}

class Sonagi_Nick extends JFrame implements ActionListener,KeyListener{//닉네임 입력화면 프레임
      private JTextField textField;// 닉네임 입력창
      private  JLabel lblNewLabel;  // 라벨 
      public  String Nick_Name; // 닉네임 변수 
      Sonagi_main main; // Main
            
      public Sonagi_Nick(){
         // 화면구성 
         setSize(500,600);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setTitle("산성비 게임");
         getContentPane().setLayout(null);//컨텐트팬의 배치관리자 삭제
         
         Background back = new Background();//닉네임 입력화면 이미지를 설정하기 위해 Background 객체 생성
         back.setLayout(null);//패널의 배치관리자 삭제
         back.setBounds(0,0,this.getWidth(),this.getHeight());//패널의 위치,크기 설정
         getContentPane().add(back);//컨텐트팬에 back 패널 부착
         
         lblNewLabel = new JLabel("산성비 게임"); // 라벨 
         lblNewLabel.setFont(new Font("굴림", Font.BOLD, 27));
         lblNewLabel.setBounds(160, 32, 381, 31);
         back.add(lblNewLabel);//back 패널에 라벨 부착
         
         //마우스 커서 변경
         Toolkit tk = Toolkit.getDefaultToolkit();
         Image img = tk.getImage("images/cloud.png");
         Cursor cursor = tk.createCustomCursor(img,new Point(10,10),"cloud");
         setCursor(cursor);

         textField = new JTextField(); //닉네임 입력창 
         textField.setFont(new Font("굴림", Font.BOLD, 24));
         textField.setBounds(24, 261, 238, 66);
         textField.setColumns(10);//최대 입력개수 설정
         back.add(textField);//back 패널에 닉네임 입력창 부착
         
         JButton btnNewButton = new JButton("확인"); //확인버튼 
         btnNewButton.setFont(new Font("굴림", Font.BOLD, 22));
         btnNewButton.setBounds(290, 261, 156, 66);
         back.add(btnNewButton);//back 패널에 확인버튼 부착
         btnNewButton.addActionListener(this);//확인버튼에 이벤트리스너 달기
         
         JLabel lblNewLabel_1 = new JLabel("닉네임"); // 닉네임 라벨 
         lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
         lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 19));
         lblNewLabel_1.setBounds(43, 202, 188, 47);
         back.add(lblNewLabel_1);//back 패널에 닉네임 라벨 부착
         
         setVisible(true);
        
         textField.addKeyListener(this);//닉네임 입력창에 이벤트리스너 달기 
      }
      
      // 버튼  이벤트 
      @Override
      public void actionPerformed(ActionEvent e){
         if(!textField.getText().equals("")){
            // 텍스트 입력창에 값이 있다면 닉네임 변수에 저장 후 게임화면 띄우기 
            Nick_Name = textField.getText().toString();
            main.showGameView(this, Nick_Name);
         }else {
            // 텍스트 입력창에 값이 없다면 라벨텍스트 변경 
            lblNewLabel.setText("닉네임 입력 바람");
         }
      }
      
      //Main 등록 
      public void setSonagi_main(Sonagi_main main) {
         this.main =main;
      }
      
      //엔터키 이벤트 
      @Override
      public void keyPressed(KeyEvent e) {
         //엔터키 이벤트 
         if (e.getKeyCode() == KeyEvent.VK_ENTER){
            // 텍스트 이력창에 값이 있다면 닉네임 변수에 저장 후 게임화면 띄우기 
            if(!textField.getText().equals("")){
               Nick_Name = textField.getText().toString();
               System.out.println(Nick_Name);
               main.showGameView(this, Nick_Name);
            }
            else {
            // 텍스트 입력창에 값이 없다면 라벨텍스트 변경 
            lblNewLabel.setText("올바르지 않은 닉네임 입니다.");
            }
         }
      }

      @Override
      public void keyReleased(KeyEvent e) {}

      @Override
      public void keyTyped(KeyEvent e) {}      
   }

class Sonagi_GameView extends JFrame implements ActionListener, KeyListener, ListSelectionListener {//게임화면 프레임
     private Clip clip;
      private JTextField textField_1;//단어 입력 창
      private JButton btn_gamestart;//게임시작 버튼
      private JButton btn_gameend;//게임종료 버튼
      private JButton btn_gamefinish;//게임 나가기(강제종료) 버튼
      private String[] game_LV = { "level 1", "level 2", "level 3", "level 4", "level 5", "level 6", "level 7", "level 8",
            "level 9", "level 10" };//게임레벨 리스트 배열
      private JPanel panel_Screen;//게임 메인스크린 패널
      private JLabel label[] = new JLabel[10000];//불러온 파일에 있는 단어를 저장할 레이블 배열
      private JLabel score;//게임점수 레이블
      private JLabel level_list;//레벨리스트 제목 레이블
      private int scorenum = 0;//게임점수 변수
      private int i = 0;
      private JList list;//게임레벨 리스트
      private int spd = 3500;//게임스피드 변수
      private JPanel panel_life1;//게임생명1을 나타낼 패널
      private JPanel panel_life2;//게임생명2을 나타낼 패널
      private JPanel panel_life3;//게임생명3을 나타낼 패널
      private int life = 0;//게임생명 변수 0으로 초기화
      private JLabel level;//게임진행시 현재 레벨 레이블
      private JPanel panel_over;//게임종료시 결과를 나타낼 패널
      private JLabel lblNewLabel;//게임종료화면에서 게임종료를 나타낼 레이블
      private JLabel lblScore;//최종점수 출력 레이블
      private JLabel Label_nickname;//닉네임 출력 레이블
      private JLabel lblNick;//게임종료화면에서 닉네임 출력 레이블
      private String NickName;//닉네임을 저장할 변수
      Sonagi_main main;

      public Sonagi_GameView(String Nick) {
         NickName = Nick;// 닉네임 변수 저장
         // 화면구성
         setTitle("산성비 게임");
         setSize(1200, 800);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         getContentPane().setLayout(null);//컨탠트팬의 배치관리자 삭제

         //마우스 커서 변경
         Toolkit tk = Toolkit.getDefaultToolkit();
         Image img = tk.getImage("images/cloud.png");
         Cursor cursor = tk.createCustomCursor(img,new Point(10,10),"cloud");
         setCursor(cursor);
         
         Side_BG sideBG = new Side_BG();//게임화면 바깥이미지가 그려진 Side_BG 객체 생성
         sideBG.setBounds(0,0,this.getWidth(),this.getHeight());
         sideBG.setLayout(null);
         getContentPane().add(sideBG);//컨텐트팬에 sideBG패널 부착
         
         textField_1 = new JTextField();// 단어입력창
         textField_1.setBounds(115, 650, 267, 38);
         sideBG.add(textField_1);//sideBG에 단어입력창 부착
         textField_1.setColumns(10);//최대 입력개수 설정

         level_list = new JLabel("레벨 선택");// 레벨리스트 제목 레이블
         level_list.setHorizontalAlignment(SwingConstants.CENTER);
         level_list.setFont(new Font("굴림",Font.BOLD,25));
         level_list.setBounds(986,20,149,45);
         sideBG.add(level_list);//sideBG에 레벨리스트 제목레이블 부착
         
         list = new JList(game_LV);// 레벨 리스트 보여주기
         list.setSelectedIndex(0);//리스트의 첫번째 요소인 level1이 초기에 선택됨
         list.setFont(new Font("굴림", Font.BOLD, 22));
         list.setBounds(986, 70, 162, 280);
         sideBG.add(list);//sideBG에 레벨리스트 부착

         btn_gamestart = new JButton("시작");// 시작 버튼
         btn_gamestart.setFont(new Font("굴림", Font.BOLD, 23));
         btn_gamestart.setBounds(497, 650, 118, 38);
         sideBG.add(btn_gamestart);//sideBG에 시작버튼 부착

         btn_gameend = new JButton("종료");// 종료 버튼
         btn_gameend.setFont(new Font("굴림", Font.BOLD, 23));
         btn_gameend.setBounds(620, 650, 118, 38);
         sideBG.add(btn_gameend);//sideBG에 종료버튼 부착
         
         btn_gamefinish = new JButton("나가기");// 나가기 버튼
         btn_gamefinish.setFont(new Font("굴림", Font.BOLD, 23));
         btn_gamefinish.setBounds(743, 650, 118, 38);
         sideBG.add(btn_gamefinish);//sideBG에 나가기버튼 부착
         
         panel_Screen = new Background();// 게임 메인스크린 패널
         panel_Screen.setBounds(43, 29, 740, 571);
         sideBG.add(panel_Screen);//sideBG에 게임 메인스크린 패널 부착
         panel_Screen.setLayout(null);
         panel_Screen.setVisible(false);//게임 메인스크린 패널이 안보이게 설정

         JPanel panel_1 = new JPanel(); // 게임화면에서 빨간색 선(데드라인)
         panel_1.setBackground(Color.RED);
         panel_1.setBounds(0, 520, 740, 10);
         panel_Screen.add(panel_1);//게임 메인스크린에 데드라인 부착

         score = new JLabel("0점");// 게임점수를 출력할 레이블
         score.setHorizontalAlignment(SwingConstants.CENTER);
         score.setFont(new Font("굴림", Font.BOLD, 25));
         score.setBounds(823, 241, 149, 45);
         sideBG.add(score);//sideBG에 게임점수 레이블 부착

         Label_nickname = new JLabel(Nick+" 님"); //닉네임 출력 레이블
         Label_nickname.setHorizontalAlignment(SwingConstants.CENTER);
         Label_nickname.setFont(new Font("굴림", Font.BOLD, 25));
         Label_nickname.setBounds(823, 71, 149, 27);
         sideBG.add(Label_nickname);//sideBG에 닉네임 레이블 부착

         level = new JLabel("level 1"); //현재 게임레벨을 출력하는 레이블(level1로 초기화 설정)
         level.setHorizontalAlignment(SwingConstants.CENTER);
         level.setFont(new Font("굴림", Font.BOLD, 24));
         level.setBounds(846, 142, 99, 42);
         sideBG.add(level);//sideBG에 게임레벨 레이블 부착

         panel_life1 = new JPanel(); // 생명1을 나타내는 패널
         panel_life1.setBackground(Color.RED);
         panel_life1.setBounds(824, 319, 26, 27);
         sideBG.add(panel_life1);//sideBG에 생명1 부착

         panel_life2 = new JPanel();// 생명2를 나타내는 패널
         panel_life2.setBackground(Color.RED);
         panel_life2.setBounds(864, 319, 26, 27);
         sideBG.add(panel_life2);//sideBG에 생명2 부착

         panel_life3 = new JPanel(); // 생명3을 나타내는 패널
         panel_life3.setBackground(Color.RED);
         panel_life3.setBounds(904, 319, 26, 27);
         sideBG.add(panel_life3);//sideBG에 생명3 부착

         // 게임결과화면
         panel_over = new JPanel();// 게임종료시 결과를 표시할 패널
         panel_over.setBackground(Color.PINK);
         panel_over.setBounds(14, 142, 794, 286);
         panel_over.setVisible(false);//게임결과 패널을 안보이게 설정
         panel_over.setLayout(null);
         sideBG.add(panel_over);//sideBG에 게임결과 패널 부착

         lblNewLabel = new JLabel("Game Over"); // 게임 종료를 표시할 레이블 
         lblNewLabel.setBounds(0, 36, 794, 63);
         lblNewLabel.setForeground(Color.RED);
         lblNewLabel.setFont(new Font("굴림", Font.BOLD, 54));
         lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
         panel_over.add(lblNewLabel);//게임결과 패널에 부착
         
         lblNick = new JLabel("Nick");//게임종료시 닉네임을 표시할 레이블
         lblNick.setBounds(0, 115, 794, 45);
         lblNick.setHorizontalAlignment(SwingConstants.CENTER);
         lblNick.setForeground(Color.RED);
         lblNick.setFont(new Font("굴림", Font.BOLD, 54));
         panel_over.add(lblNick);//게임결과 패널에 부착
         
         lblScore = new JLabel("Score"); // 게임결과 점수를 표시할 레이블
         lblScore.setBounds(0, 232, 794, 42);
         lblScore.setHorizontalAlignment(SwingConstants.CENTER);
         lblScore.setForeground(Color.RED);
         lblScore.setFont(new Font("굴림", Font.BOLD, 54));
         panel_over.add(lblScore);//게임결과 패널에 부착

         setVisible(true);
         
         // 버튼,리스트,키 이벤트설정및 텍스트 필드 포커스 설정
         btn_gamestart.addActionListener(this); // 버튼 이벤트
         btn_gameend.addActionListener(this);
         btn_gamefinish.addActionListener(this);
         
         textField_1.addKeyListener(this); // 키 이벤트
         list.addListSelectionListener(this); // 리스트 이벤트
         textField_1.requestFocus(); // 텍스트 포커스

      }
      
      public void playSound(String file_url) {//음악 재생
          try{
             clip=AudioSystem.getClip();
             File audioFile=new File(file_url);
             AudioInputStream audioStream=AudioSystem.getAudioInputStream(audioFile);
             clip.open(audioStream);
          }
          catch(LineUnavailableException e){ e.printStackTrace(); }
          catch(UnsupportedAudioFileException e){ e.printStackTrace(); }
          catch(IOException e){ e.printStackTrace(); }
      }
      
      @Override
      public void actionPerformed(ActionEvent e) {
         // 게임시작 버튼 이벤트
         if (e.getSource().equals(btn_gamestart)) {//시작버튼을 선택할 경우
           playSound("music/bgm.wav"); //음악파일 불러오기
           clip.start();//음악 재생
            btn_gamestart.setVisible(false);//시작버튼 안보이게 설정
            life = 0;//라이프 0 = 생명이 모두 있는것
            score.setText("0점"); // 게임시작시 점수판 0점
            scorenum = 0; // 게임점수 변수 =0
            btn_gameend.setVisible(true);
            // 만약에 게임결과화면이 띄어져 있으면 보이지 않게
            if (panel_over.isVisible()) {
               panel_over.setVisible(false);// 결과화면 안보이게
            }

            // 단어가 만들어져 있는지 확인
            if (label[0] != null) {
               for (int i = 0; i < label.length; i++) {
                  // 쓰레드가 돌아가고 있으면 중지
                  if (!new Sonagi_Thead().isAlive() || !new Sonagi_Move().isAlive()) {
                     new Sonagi_Thead().interrupt();// 단어 쓰레드 중지
                     new Sonagi_Move().interrupt();// 내려가는 쓰레드 중지
                  }
                  // 단어가 만들어져 있다면 안보이게
                  if (label[i] != null) {
                     label[i].setVisible(false);
                  }
               }
            }
            // 게임시작시 텍스트필드 포커스 주기
            textField_1.requestFocus();
            // 단어를 뿌려주는 쓰레드 실행
            new Sonagi_Thead().start();
         }
         else if(e.getSource().equals(btn_gameend)) {//종료버튼을 선택할 경우
            clip.stop();//먼저 재생된 음악 종료
             panel_Screen.setVisible(false);//게임메인 스크린 안보이게 설정
              panel_over.setVisible(true);//게임 종료화면 보이게 설정
              btn_gamestart.setVisible(true);//시작버튼 보이게 설정
              btn_gamestart.setText("재시작");//시작버튼 이름을 '재시작'으로 변경
              btn_gameend.setVisible(false);//종료버튼 안보이게 설정
              playSound("music/박수.wav");//음악파일 불러오기
              clip.start();//음악 재생
              lblScore.setText(scorenum+"점 입니다");
              lblNick.setText(NickName+"님의 최종점수는 ");
              new Sonagi_Move().stop();
              new Sonagi_Thead().stop();
         }
         else if(e.getSource().equals(btn_gamefinish))//나가기버튼을 선택할 경우
            System.exit(0);//게임 강제종료
      }

      // 단어를 입력하여 동일단어 있는지 확인하는 키이벤트
      @Override
      public void keyPressed(KeyEvent e) {
         // 엔터키 이벤트
         if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // 텍스트 필드에 값이 있는지 없는지 확인
            if (!textField_1.getText().equals("")) {
                  String work_answer = textField_1.getText().toString(); // String 변수에 텍스트 값 주기
                  for (int i = 0; i <= label.length; i++) { // 동일 단어 검사 for문
                     try {
                        if (work_answer.equals(label[i].getText())) {// 동일단어 검사
                           if (label[i].isVisible()) { // 동일단어가 화면에 보여져 있는지 검사
                              scorenum = scorenum += 5; // 점수 +5
                              score.setText(scorenum + "점"); // 점수판 점수 변경
                              label[i].setVisible(false);// 단어 화면에서 안보이게
                              textField_1.setText("");// 텍스트 값 없애기
                           }
                        }
                        else {
                           textField_1.setText("");
                        }
                     }
                     catch (Exception e2) {
                        e2.getMessage();
                     }
                  }
            }
         }
      }
      @Override
      public void keyReleased(KeyEvent e) {}
      @Override
      public void keyTyped(KeyEvent e) {}
   
      // 단어를 하나씩 뿌려주는 쓰레드
      class Sonagi_Thead extends Thread {
         public void run() {
            panel_Screen.setVisible(true);// 게임화면 등장
            // 단어를 레벨에 따른 속도로 게임화면에 x값 랜덤으로생성
            for (i = 0; i <= 10000; i++) {
               try {
                  Random random = new Random();// 랜덤 객체 생성
                  Words word = new Words("word.txt");//단어가 저장된 파일 불러오기
                  label[i] = new JLabel(word.getRandomWord());// 단어 랜덤으로 가지고옴
                  label[i].setBounds(0, 0, 80, 20);// 단어 초기 위치 높이,폭 설정
                  panel_Screen.add(label[i]);// 단어 추가
                  label[i].setLocation(random.nextInt(678), 2);// x값 랜덤으로 보여주기
                  new Sonagi_Move().start();// 단어를 움직이는 쓰레드 실행
                  Thread.sleep(spd);// 게임레벨에 따른 단어속도
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
         }
      }

      // 단어 내려가고,게임오버 확인하는 쓰레드
      class Sonagi_Move extends Thread {
         @Override
         public void run() {
            // 단어 생성 갯수 만큼 for 돌려 각 단어마다 y값 변경
            for (int a = 0; a <= i; a++) {
               if (label[a].isVisible()) {
                  int sp = label[a].getY();
                  int xp = label[a].getX();
                  label[a].setLocation(xp, sp + 10);
               }
               // 단어가 보여져있는 상태롤 빨간선을 넘었는지 확인
               if (label[a].isVisible() && label[a].getY() > 512) {
                  label[a].setVisible(false);// 단어가 선을 넘으면 단어 사라짐
                  life++;// 라이프 1 = 생명 1없어짐
               }
            }
            // 라이프 확인
            switch (life) {
            case 0:// 생명 모두 있음
               panel_life1.setBackground(Color.RED);
               panel_life2.setBackground(Color.RED);
               panel_life3.setBackground(Color.RED);
               break;

            case 1: // 생명 1개 없어짐
               panel_life1.setBackground(Color.BLUE);
               panel_life2.setBackground(Color.RED);
               panel_life3.setBackground(Color.RED);
               break;

            case 2:// 생명 2 없어짐
               panel_life1.setBackground(Color.BLUE);
               panel_life2.setBackground(Color.BLUE);
               panel_life3.setBackground(Color.RED);
               break;
            case 3:// 생명 3개 없어짐 게임오버
               clip.stop();//음악 중지
               panel_life1.setBackground(Color.BLUE);
               panel_life2.setBackground(Color.BLUE);
               panel_life3.setBackground(Color.BLUE);
               
               // 게임 결과화면 띄우기
               playSound("music/박수.wav");
               clip.start();
               panel_Screen.setVisible(false);
               panel_over.setVisible(true);
               // 닉네임과 최종점수를 표시
               lblScore.setText(score.getText()+"입니다");
               lblNick.setText(NickName+"의 최종 스코어는");
             
               btn_gameend.setVisible(false);
                btn_gamestart.setVisible(true);//시작버튼 보이게 설정
                btn_gamestart.setText("재시작");//시작버튼 이름을 '재시작'으로 변경
      
               // 쓰레드 중지
               new Sonagi_Thead().interrupt();
               new Sonagi_Move().interrupt();
               break;
            default:
               break;
            }
         }
      }
      //Main 등록 
      public void setSonagi_main(Sonagi_main main) {
         this.main = main;
      }

      // 레벨리스트 이벤트 처리
      @Override
      public void valueChanged(ListSelectionEvent arg0) {
         String str;//선택한 레벨을 나타낼 문자열
         if (arg0.getSource() == list) {
            switch (list.getSelectedIndex()) {
            case 0://level1
               spd = 3500;// 게임속도
               str = (String) list.getSelectedValue();// 레벨리스트에서 선택한 레벨을 문자열로 저장
               level.setText(str); // 현재 레벨 레이블에 보여주기
               break;
            case 1://level2
               spd = 2900;// 게임속도
               str = (String) list.getSelectedValue();// 레벨리스트에서 선택한 레벨을 문자열로 저장
               level.setText(str);//  현재 레벨 레이블에 보여주기
               break;
            case 2://level3
               spd = 2600;// 게임속도
               str = (String) list.getSelectedValue();// 레벨리스트에서 선택한 레벨을 문자열로 저장
               level.setText(str);//  현재 레벨 레이블에 보여주기
               break;
            case 3://level4
               spd = 2300;// 게임속도
               str = (String) list.getSelectedValue();// 레벨리스트에서 선택한 레벨을 문자열로 저장
               level.setText(str);//  현재 레벨 레이블에 보여주기
               break;
            case 4://level5
               spd = 1900;// 게임속도
               str = (String) list.getSelectedValue();// 레벨리스트에서 선택한 레벨을 문자열로 저장
               level.setText(str);//  현재 레벨 레이블에 보여주기
               break;
            case 5://level6
               spd = 1600;// 게임속도
               str = (String) list.getSelectedValue();// 레벨리스트에서 선택한 레벨을 문자열로 저장
               level.setText(str);//  현재 레벨 레이블에 보여주기
               break;
            case 6://level7
               spd = 1300;// 게임속도
               str = (String) list.getSelectedValue();// 레벨리스트에서 선택한 레벨을 문자열로 저장
               level.setText(str);// 현재 레벨 레이블에 보여주기
               break;
            case 7://level8
               spd = 1000;// 게임속도
               str = (String) list.getSelectedValue();// 레벨리스트에서 선택한 레벨을 문자열로 저장
               level.setText(str);//현재 레벨 레이블에 보여주기
               break;
            case 8://level9
               spd = 800;// 게임속도
               str = (String) list.getSelectedValue();// 레벨리스트에서 선택한 레벨을 문자열로 저장
               level.setText(str);//현재 레벨 레이블에 보여주기
               break;
            case 9://level10
               spd = 400;// 게임속도
               str = (String) list.getSelectedValue();// 레벨리스트에서 선택한 레벨을 문자열로 저장
               level.setText(str);//현재 레벨 레이블에 보여주기
               break;
            default:
               break;
            }
         }
      }
   }

public class Sonagi_main {
   
   public static void main(String[] args) { // Main 시작점 
      Sonagi_main main = new Sonagi_main(); 
      Sonagi_Nick gui = new Sonagi_Nick();  //닉네임 설정 화면 띄우기 
      
      gui.setSonagi_main(main); // Main 위치 등록 ;
   }
   
   // 닉네임 클래스 , 닉네임 가지고 showGameView 메소드 실행 
   public void showGameView(Sonagi_Nick gui ,String nick){
      gui.dispose();//닉네임 화면 없애기
      Sonagi_GameView game = new Sonagi_GameView(nick);// 닉네임 가지고 게임화면 띄우기 
   }
}