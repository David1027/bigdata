package org.start2do.utils.tokenUtils;

import org.start2do.utils.classUtils.ClassUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.Md5Crypt;
import org.start2do.utils.tokenUtils.pojo.CheckTokenPojo;
import org.start2do.utils.token.TokenPojo;
import org.start2do.utils.token.TokenBuilder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.StringJoiner;

/** Created by IntelliJ IDEA. Lijie HelloBox@outlook.com Date: 2019/4/23 Time: 14:06 */
public class TokenUtils {
  private static String KEY;
  private static String IV;
  private static String Md5Salt;

  static {
    Properties properties = new Properties();
    try {
      InputStream inputStream = TokenUtils.class.getResourceAsStream("cryptConf1ig.properties");
      if (inputStream != null) {
        properties.load(inputStream);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    KEY = properties.getProperty("Key", "bpb31tS$&2J;F@0S");
    IV = properties.getProperty("Iv", "5FMz2wEo=D52D/~\\");
    Md5Salt = properties.getProperty("Md5Salt", "Yni|#`z@Ms+K7m,Y");
  }
  /**
   * * 转换成指定的DTO
   *
   * @author Lijie HelloBox@outlook.com
   * @date 2019/4/26 10:40
   */
  public static <T> TokenPojo build(TokenBuilder<T> builder, T obj) {
    return builder.build(obj);
  }

  /**
   * * 获取加密后的Token
   *
   * @author Lijie HelloBox@outlook.com
   * @date 2019/4/26 10:40
   */
  public static String getAESString(TokenPojo tokenPojo) {
    return encrypt_decrypt(KEY, IV, getFleString(tokenPojo, false), Cipher.ENCRYPT_MODE);
  }

  /**
   * @Description: 获取校验码
   *
   * @date 2019/4/23 16:29
   * @author Lijie HelloBox@outlook.com
   */
  public static String getSign(TokenPojo tokenPojo) {
    return Md5Crypt.apr1Crypt(getFleString(tokenPojo, true), Md5Salt);
  }

  private static String getFleString(TokenPojo tokenPojo, boolean skin) {
    StringJoiner stringJoiner = new StringJoiner("&");
    for (Field field : TokenPojo.class.getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (skin && field.getName().equals("sign")) continue;
        stringJoiner.add(field.getName() + "=" + String.valueOf(field.get(tokenPojo)));
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return stringJoiner.toString();
  }
  /**
   * * 校验Token是否有效
   *
   * @author Lijie HelloBox@outlook.com
   * @date 2019/4/27 11:27
   */
  public static boolean checkToken(TokenPojo tokenPojo, String sign2) {
    if (tokenPojo == null) return false;
    if (sign2 == null) return false;
    int i2 = sign2.lastIndexOf("sign=");
    i2 = i2 < 0 ? 0 : i2 + 5;
    return getSign(tokenPojo).equals(sign2.substring(i2));
  }
  /**
   * @title checkToken
   * @author Lijie HelloBox@outlook.com
   * @params [tokenPojo, tokenString]
   * @returns boolean
   * @updateTime 2019-04-28 12:09
   * @throws
   * @description 校验Token是否有效
   */
  public static boolean checkToken(CheckTokenPojo tokenPojo, String tokenString) {
    return checkSign(tokenString)
        && isValid(getValueByKey(tokenString, "valid_date"))
        && tokenPojo.getUA().equals(getValueByKey(tokenString, "UA"))
        && tokenPojo.getIMME().equals(getValueByKey(tokenString, "IMME"));
  }

  /**
   * @Description: 加密 key密钥 value 要加密的文本
   *
   * @date 2019/4/23 16:26
   * @author Lijie HelloBox@outlook.com
   */
  private static String encrypt_decrypt(String key, String initVector, String value, int mode) {
    //    定义向量
    IvParameterSpec iv = null;
    try {
      iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
      SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(mode, skeySpec, iv);
      switch (mode) {
        case Cipher.ENCRYPT_MODE:
          synchronized (cipher) {
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
          }
        case Cipher.DECRYPT_MODE:
          synchronized (cipher) {
            byte[] decrypt = cipher.doFinal(Base64.decodeBase64(value));
            return new String(decrypt, "utf8");
          }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  /**
   * @Description: 解密
   *
   * @date 2019/4/26 9:42
   * @author Lijie HelloBox@outlook.com
   */
  public static String decrypt(String aseString) {
    return encrypt_decrypt(KEY, IV, aseString, Cipher.DECRYPT_MODE);
  }

  /**
   * @Description: 校验是否Token是否有效
   *
   * @param initVector 向量
   * @date 2019/4/23 16:26
   * @author Lijie HelloBox@outlook.com
   */
  public static boolean checkSign(String decrypt) {
    if (null == decrypt) return false;
    String s = decrypt.substring(0, decrypt.indexOf("sign") - 1);
    return getSign(s).equals(decrypt.substring(decrypt.indexOf("sign") + 5));
  }

  /**
   * * 获取签名
   *
   * @author Lijie HelloBox@outlook.com
   * @date 2019/4/27 11:26
   */
  private static String getSign(String s) {
    return Md5Crypt.apr1Crypt(s, Md5Salt);
  }

  /**
   * * 将TokenString 注入到DTO中
   *
   * @author Lijie HelloBox@outlook.com
   * @date 2019/4/27 11:26
   */
  public static <T> T build(String tokenString, Class<T> o) {
    if (tokenString == null || tokenString.length() < 0) {
      throw new NullPointerException("tokenString is not Null");
    }
    try {
      T result = o.newInstance();
      for (String s : tokenString.split("&")) {
        String[] fld = s.split("=");
        try {
          Method method =
              o.getDeclaredMethod(ClassUtils.getSetMethodString("set", fld[0]), String.class);
          method.invoke(result, fld[1]);
        } catch (NoSuchMethodException e) {
        }
      }
      return result;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * * 获取Token下面某个key的值
   *
   * @author Lijie HelloBox@outlook.com
   * @date 2019/4/27 11:26
   */
  public static String getValueByKey(String tokenString, String key) {
    if (tokenString == null || tokenString.length() < 1 || key == null || key.length() < 1) {
      throw new NullPointerException();
    }
    int i = tokenString.indexOf(key) + key.length();
    if (tokenString.charAt(i) == '=') {
      int end = tokenString.indexOf("&", i);
      if (end == -1) {
        end = tokenString.length();
      }
      return tokenString.substring(i + 1, end);
    }
    return "";
  }

  /**
   * * 校验Token是否有效期
   *
   * @author Lijie HelloBox@outlook.com
   * @date 2019/4/27 11:27
   */
  public static boolean isValid(String token) {
    LocalDateTime tokenDate =
        LocalDateTime.parse(
            getValueByKey(token, "valid_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    return LocalDateTime.now().isBefore(tokenDate);
  }
}
