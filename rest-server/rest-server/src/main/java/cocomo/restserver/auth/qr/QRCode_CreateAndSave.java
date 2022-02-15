package cocomo.restserver.auth.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class QRCode_CreateAndSave
{

	private static QRCode_Data qrData = new QRCode_Data();
	private static int fileNumber = 0;
	private static LocalTime currentTime;
	private static String currentTimeStr;
	public static int Id;


	@SuppressWarnings("static-access")
	public static void main(String[] args, int userId) throws Exception
	{
	}

	public static void qrCreate(int userId) throws IOException, WriterException {
		Id = userId;
		qrData.setTime(fileNumber, userId); // time값 세팅 및 QR코드 생성
		// new QrCode_ImageView();
	}

	public static void createQRImage(File qrFile, String jsonData, int size, String fileType)
	{
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		BitMatrix byteMatrix = null;
		try {
			byteMatrix = qrCodeWriter.encode(jsonData, BarcodeFormat.QR_CODE, size, size, hintMap);
		} catch (WriterException e) {
			// qr 값 생성 안됨
			e.printStackTrace();
		}

		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);

		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();

		graphics.setColor(Color.WHITE); // 배경색
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		graphics.setColor(Color.BLACK); // QR코드색

		for (int i = 0; i < matrixWidth; i++)
		{
			for (int j = 0; j < matrixWidth; j++)
			{
				if (byteMatrix.get(i, j))
				{
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}

		try {
			ImageIO.write(image, fileType, qrFile);
		} catch (IOException e) {
			// qr 생성 x
			e.printStackTrace();
		}

		System.out.println("QR" + qrFile + "생성됨 (Data: " + currentTimeStr + ")");
	}
}
