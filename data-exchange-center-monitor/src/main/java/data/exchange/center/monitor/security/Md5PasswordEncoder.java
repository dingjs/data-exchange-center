package data.exchange.center.monitor.security;

import org.springframework.stereotype.Component;

/**
 * 
 * Description:重写此类的目的是改变默认的合并密码和盐的行为， password + "{" + salt.toString() + "}";
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:56:51</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
public class Md5PasswordEncoder extends org.springframework.security.authentication.encoding.Md5PasswordEncoder {

    @Override
    protected String mergePasswordAndSalt(String password, Object salt, boolean strict) {
        if (password == null) {
            password = "";
        }


        if ((salt == null) || "".equals(salt)) {
            return password;
        }
        else {
            return password + salt.toString();
        }
    }
}
