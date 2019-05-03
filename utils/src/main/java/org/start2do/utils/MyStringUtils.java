package org.start2do.utils;

public class MyStringUtils {
  /**
   * @title isMatch3
   * @author Lijie HelloBox@outlook.com
   * @params [reg, s1]
   * @returns boolean
   * @updateTime 2019-05-03 16:22
   * @throws
   * @description 基于动态规划 通配符比较 reg 匹配的表达式 s1需要匹配的字符串
   */
  public static boolean isMatch3(String reg, String s1) {
    int countXing = 0;
    for (char c : reg.toCharArray()) {
      countXing++;
    }
    if (reg.length() - countXing > s1.length()) {
      return false;
    }
    boolean[][] dp = new boolean[reg.length() + 1][s1.length() + 1];
    dp[0][0] = true;
    for (int i = 1; i <= reg.length(); i++) {
      char s2_char = reg.charAt(i - 1);
      dp[i][0] = dp[i - 1][0] && s2_char == '*';
      for (int j = 1; j <= s1.length(); j++) {
        char s1_char = s1.charAt(j - 1);
        if (s2_char == '*') {
          dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
        } else {
          dp[i][j] = dp[i - 1][j - 1] && (s2_char == '?' || s1_char == s2_char);
        }
      }
    }
    return dp[reg.length()][s1.length()];
  }
  /**
   * @title isMatch
   * @author Lijie HelloBox@outlook.com
   * @params [s, p]
   * @returns boolean
   * @updateTime 2019-05-03 21:15
   * @throws
   * @description s 源字符串，P表达式
   */
  public static boolean isMatch(String s, String p) {
    int m = s.length(), n = p.length();
    Boolean[] cur = new Boolean[s.length() + 1];
    for (int i = 0; i <= m; i++) {
      cur[i] = false;
    }
    cur[0] = true;
    for (int j = 1; j <= n; j++) {
      boolean pre = cur[0]; // use the value before update
      cur[0] = cur[0] && p.charAt(j - 1) == '*';
      for (int i = 1; i <= m; i++) {
        boolean temp = cur[i]; // record the value before update
        if (p.charAt(j - 1) != '*') {
          cur[i] = pre && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?');
        } else {
          cur[i] = cur[i - 1] || cur[i];
        }
        pre = temp;
      }
    }
    return cur[m];
  }

  // 字符串 编辑路径
  public static int LD(String st1, String st2) {
    int[][] ints = new int[st1.length() + 1][st2.length() + 1];
    for (int i = 0; i <= st1.length(); i++) {
      ints[i][0] = i;
    }
    for (int j = 0; j <= st2.length(); j++) {
      ints[0][j] = j;
    }
    for (int i = 1; i <= st1.length(); i++) {
      for (int j = 1; j <= st2.length(); j++) {
        ints[i][j] =
            st1.charAt(i - 1) == st2.charAt(j - 1)
                ? ints[i - 1][j - 1]
                : Math.min(Math.min(ints[i - 1][j], ints[i][j - 1]), ints[i - 1][j - 1]) + 1;
      }
    }
    return ints[st1.length()][st2.length()];
  }

  // 基于正则的 编辑路径
  public static boolean isMatch2(String reg, String t) {
    String str1 = reg;
    String str2 = t;
    int[][] dp = new int[str1.length() + 1][str2.length() + 1];
    for (int i = 1; i < str1.length() + 1; i++) {
      for (int j = 1; j < str2.length() + 1; j++) {
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }
    if (str1.replaceAll("[?*]", "").length() == dp[str1.length()][str2.length()]) {
      return true;
    }
    return false;
  }

  public static boolean isEmpty(String str) {
    return str == null || "".equals(str);
  }

  public static String replace(String inString, String oldPattern, String newPattern) {
    if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
      int index = inString.indexOf(oldPattern);
      if (index == -1) {
        return inString;
      } else {
        int capacity = inString.length();
        if (newPattern.length() > oldPattern.length()) {
          capacity += 16;
        }

        StringBuilder sb = new StringBuilder(capacity);
        int pos = 0;

        for (int patLen = oldPattern.length();
            index >= 0;
            index = inString.indexOf(oldPattern, pos)) {
          sb.append(inString.substring(pos, index));
          sb.append(newPattern);
          pos = index + patLen;
        }

        sb.append(inString.substring(pos));
        return sb.toString();
      }
    } else {
      return inString;
    }
  }

  public static boolean hasLength(String str) {
    return str != null && !str.isEmpty();
  }
}
