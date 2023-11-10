package cocomo.restserver.auth.qr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import cocomo.restserver.define.Path;
import com.google.zxing.WriterException;

public class QrCodeImageView extends JFrame implements ActionListener {
	public static int fileList = 0;

	private Font font = new Font("210 모던굴림 Regular", Font.PLAIN, 14);
	private static JPanel mainPanel;
	private static JButton refreshBtn;
	private JLabel timerLb;
	private Timer timer;

	private Image qrImg = new ImageIcon("src/main/java/cocomo/qrcode/cocomoQR" + fileList + ".png").getImage();
	// └-> QRCode_CreateAndSave.class.getResource("../cocomoQR.png")는 안됨

	private static QRCodeData qrData = new QRCodeData();


	public QrCodeImageView() // 생성자
	{
		// 창 세팅
		setTitle("QrCode");
		setSize(400, 580);
		setBackground(Color.WHITE);
		setResizable(false); // 창 크기 변경 x
		setLocationRelativeTo(null); // 창이 중앙에 위치하게 나오도록
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 그냥 닫으면 프로그램 정상적으로 종료되지 x


		// 객체화
		mainPanel = new JPanel(); // 메인 패널
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLayout(null); // 절대 위치로 사용할 것

		timerLb = new JLabel(); // 타이머 라벨
		timerLb.setFont(font);
		timerLb.setBounds(10, 380, 400, 30); // x, y, xSize, ySize
		setTimer();

		refreshBtn = new JButton("새로고침"); // 새로고침 버튼
		refreshBtn.addActionListener(this);
		refreshBtn.setFont(font);
		refreshBtn.setBackground(new Color(170, 175, 190));
		refreshBtn.setBounds(150, 450, 100, 50);


		// 메인 패널에 출력
		mainPanel.add(timerLb);
		mainPanel.add(refreshBtn);


		// 메인 프레임에 메인패널을 붙여주고, 프레임을 보이게 설정
		add(mainPanel);
		setVisible(true);
	}


	public void setTimer()
	{
		// 타이머 설정
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			int i = 10;

			public void run()
			{
				timerLb.setText("남은 시간: " + i);
				i--;

				if (i < 0)
				{
					timer.cancel();

					// JLabel, JButton...는 html을 지원함!
					timerLb.setText("<html>시간이 만료되었습니다.<br/>새로고침 버튼을 눌러 QR코드를 재발급 받으세요.</html>");
				}
			}
		}, 0, 1000);
	}


	@Override
	public void paint(Graphics g) // GUI를 그림
	{
		qrImg = new ImageIcon(Path.PATH_QR + "/cocomoQR" + fileList + ".png").getImage();
		g.drawImage(qrImg, 0, 0, null); // background를 그려줌
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == refreshBtn)
		{
			try {
				if (fileList != 0) { fileList = 0; } // label은 0과 1만 사용 (파일 무한생성 방지)
				else { fileList++; }

				qrData.setTime(fileList, QRCodeService.Id); // QR코드 재생성
				timer.cancel();
				setTimer(); // 남은 시간 갱신
			} catch (WriterException | IOException e1) {
				e1.printStackTrace();
			}

			repaint();
		}
	}

}

