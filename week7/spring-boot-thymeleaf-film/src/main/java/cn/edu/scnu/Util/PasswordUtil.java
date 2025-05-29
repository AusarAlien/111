package cn.edu.scnu.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 加密密码
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // 匹配密码
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
