package org.start2do.utils.ipUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class City {
  private static byte[] data;
  private static long indexSize;
  private static URI datx;
  private static String[] str = {"未知"};

  static {
    try {
      datx = City.class.getClassLoader().getResource("17monipdb.datx").toURI();
      Path path = Paths.get(datx);
      data = Files.readAllBytes(path);
      indexSize = Util.bytesToLong(data[0], data[1], data[2], data[3]);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 返回数组 数组内顺序依次为 国家 - 省份 - 城市 */
  public static String[] find(String ips) {
    if (!Util.isIPv4Address(ips)) {
      return str;
    }

    long val = Util.ip2long(ips);
    int start = 262148;
    int low = 0;
    int mid = 0;
    int high = new Long((indexSize - 262144 - 262148) / 9).intValue() - 1;
    int pos = 0;
    while (low <= high) {
      mid = new Double((low + high) / 2).intValue();
      pos = mid * 9;

      long s = 0;
      if (mid > 0) {
        int pos1 = (mid - 1) * 9;
        s =
            Util.bytesToLong(
                data[start + pos1],
                data[start + pos1 + 1],
                data[start + pos1 + 2],
                data[start + pos1 + 3]);
      }

      long end =
          Util.bytesToLong(
              data[start + pos],
              data[start + pos + 1],
              data[start + pos + 2],
              data[start + pos + 3]);
      if (val > end) {
        low = mid + 1;
      } else if (val < s) {
        high = mid - 1;
      } else {

        byte b = 0;
        long off =
            Util.bytesToLong(
                b, data[start + pos + 6], data[start + pos + 5], data[start + pos + 4]);
        long len = Util.bytesToLong(b, b, data[start + pos + 7], data[start + pos + 8]);

        int offset = new Long(off - 262144 + indexSize).intValue();

        byte[] loc = Arrays.copyOfRange(data, offset, offset + new Long(len).intValue());

        return new String(loc, Charset.forName("UTF-8")).split("\t", -1);
      }
    }

    return null;
  }
}
