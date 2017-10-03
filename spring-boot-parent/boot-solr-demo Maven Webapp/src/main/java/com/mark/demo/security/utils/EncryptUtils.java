package com.mark.demo.security.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mark.demo.security.constant.CharsetConst;

public class EncryptUtils
{
    private static final Logger logger       = LoggerFactory.getLogger(EncryptUtils.class);
    
    private static final String PARSE_KEY    = "Zttx";
    
    private static final String ENCRYPT_NAME = "DES";
    
    private EncryptUtils()
    {
        super();
    }
    
    /**
     * <p>返回经过加密的字符串</p>
     * @param password 要加密码的明文字符串
     * @param algorithm 加密运算法则(可以是MD5、MD2、SHA-256、SHA-1等等)
     * @return String 加密后的字符串
     */
    public static String encrypt(String password, String algorithm)
    {
        String result = null;
        byte[] unencodedPassword = password.getBytes(Charset.forName(CharsetConst.CHARSET_UT));
        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e)
        {
            LoggerUtils.logDebug(logger, e.getMessage());
        }
        if (null != md)
        {
            md.update(unencodedPassword);
            byte[] encodedPassword = md.digest();
            StringBuffer buf = new StringBuffer();
            int iLen = encodedPassword.length;
            for (int i = 0; i < iLen; i++)
            {
                if ((encodedPassword[i] & 0xff) < 0x10) buf.append("0");
                buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
            }
            result = buf.toString();
        }
        return result;
    }
    
    public static String desEncrypt(String strMing)
    {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        // BASE64Encoder base64en = new BASE64Encoder();
        try
        {
            byteMing = strMing.getBytes(CharsetConst.CHARSET_UT);
            byteMi = encryptByte(byteMing);
            // strMi = base64en.encode(byteMi);
            strMi = Base64.encodeBase64String(byteMi);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
        finally
        {
            // base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }
    
    public static String desDecrypt(String strMi)
    {
        // BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try
        {
            // byteMi = base64De.decodeBuffer(strMi);
            byteMi = Base64.decodeBase64(strMi);
            byteMing = decryptByte(byteMi);
            strMing = new String(byteMing, CharsetConst.CHARSET_UT);
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        finally
        {
            // base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }
    
    private static byte[] encryptByte(byte[] byteS)
    {
        byte[] byteFina = null;
        Cipher cipher;
        try
        {
            cipher = Cipher.getInstance(ENCRYPT_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, generatorKey(PARSE_KEY));
            byteFina = cipher.doFinal(byteS);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
        finally
        {
            cipher = null;
        }
        return byteFina;
    }
    
    private static byte[] decryptByte(byte[] byteD)
    {
        Cipher cipher;
        byte[] byteFina = null;
        try
        {
            cipher = Cipher.getInstance(ENCRYPT_NAME);
            cipher.init(Cipher.DECRYPT_MODE, generatorKey(PARSE_KEY));
            byteFina = cipher.doFinal(byteD);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
        finally
        {
            cipher = null;
        }
        return byteFina;
    }
    
    private static Key generatorKey(String parseKey)
    {
        Key key = null;
        KeyGenerator generator = null;
        try
        {
            generator = KeyGenerator.getInstance(ENCRYPT_NAME);
        }
        catch (NoSuchAlgorithmException e)
        {
            LoggerUtils.logError(logger, e.getMessage());
        }
        if (null != generator)
        {
            // generator.init(new
            // SecureRandom(parseKey.getBytes()));//Linux及Solaris下异常
            SecureRandom secureRandom = null;
            try
            {
                secureRandom = SecureRandom.getInstance("SHA1PRNG");
            }
            catch (NoSuchAlgorithmException e)
            {
                LoggerUtils.logError(logger, e.getMessage());
            }
            secureRandom.setSeed(parseKey.getBytes(Charset.forName(CharsetConst.CHARSET_UT)));
            generator.init(secureRandom);
            key = generator.generateKey();
            generator = null;
        }
        return key;
    }
}
