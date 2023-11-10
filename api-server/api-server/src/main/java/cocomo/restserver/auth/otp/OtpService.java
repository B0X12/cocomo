package cocomo.restserver.auth.otp;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cocomo.restserver.auth.AuthUser;
import org.apache.commons.codec.binary.Base32;

public class OtpService {

    private static AuthUser authUser = new AuthUser();

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
        // TODO Auto-generated method stub
    }

    public static String createOtp(String otpKey) throws InvalidKeyException, NoSuchAlgorithmException
    {
        String otpCode = generateOtp(otpKey);
        // generateOtp : KEY를 인자로 받아 OTP CODE를 생성함

        return otpCode;
    }

    public static String generateKey(String name)
    {
        byte[] buffer = new byte[5 + 5 * 5];
        new Random().nextBytes(buffer);
        Base32 codec = new Base32();
        byte[] secretKey = Arrays.copyOf(buffer, 10);
        byte[] bEncodedKey = codec.encode(secretKey);
        String encodedKey = new String(bEncodedKey);

        return encodedKey;
    }

    public static String generateOtp(String otpkey) throws InvalidKeyException, NoSuchAlgorithmException
    {
        long wave = new Date().getTime() / 30000;
        String str, res;
        res = "";

        try {
            Base32 codec = new Base32();
            byte[] decodedKey = codec.decode(otpkey);
            int window = 3;
            int cnt = 0;
            for (int i = -window; i <= window; ++i) {
                long hash = verify_code(decodedKey, wave + i);
                cnt++;
                str = String.valueOf(hash);
                if (cnt == 4) {
                    if (hash < 10)
                        res = "00000" + str;
                    else if (hash < 100)
                        res = "0000" + str;
                    else if (hash < 1000)
                        res = "000" + str;
                    else if (hash < 10000)
                        res = "00" + str;
                    else if (hash < 100000)
                        res = "0" + str;
                    else
                        res = str;

                }
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return res;
    }

    private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException
    {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }

        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);

        int offset = hash[20 - 1] & 0xF;

        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;

            truncatedHash |= (hash[offset + i] & 0xFF);
        }

        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;

        return (int) truncatedHash;
    }

}
