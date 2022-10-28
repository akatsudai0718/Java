package etc;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

	public static byte[] createHash(String text) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
//		byte[] b = digest.digest(text.getBytes());
		byte[] b = digest.digest(text.getBytes(StandardCharsets.UTF_8));

//		System.out.println("作成したbは"+b);
//
//		String hexString = Hex.encodeHexString(b);
//		System.out.println("16進数文字列変換後は"+hexString);

        return b;
    }
}