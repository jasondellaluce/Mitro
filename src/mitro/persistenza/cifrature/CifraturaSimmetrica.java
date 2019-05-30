package mitro.persistenza.cifrature;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import mitro.persistenza.Cifratura;

public class CifraturaSimmetrica implements Cifratura {

	private static String secretKey = "qWFBIUQebfqkjscnaakjsfhqiuefqec";
	private static String salt = "QwfqWLK7NZXUKAHSFNqjasc";

	@Override
	public String cifra(String valore) {
		if(valore == null)
			return valore;
		try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
	        SecretKey tmp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	         
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
	        return Base64.getEncoder().encodeToString(cipher.doFinal(valore.getBytes("UTF-8")));
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	@Override
	public String cifraHash(String valore) {
		if(valore == null)
			return valore;
		try { 
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
            
            byte[] messageDigest = md.digest(valore.getBytes()); 
            BigInteger no = new BigInteger(1, messageDigest); 
            String hashtext = no.toString(16); 
  
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
  
            return hashtext; 
        }
		catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	@Override
	public String decifra(String valore) {
		if(valore == null)
			return valore;
		try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
	        SecretKey tmp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	         
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
	        return new String(cipher.doFinal(Base64.getDecoder().decode(valore)));
	    }
		catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
