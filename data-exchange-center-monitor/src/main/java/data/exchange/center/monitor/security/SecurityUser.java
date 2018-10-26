package data.exchange.center.monitor.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:57:43</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class SecurityUser extends User {
    /**
	 * 2017年7月17日下午4:48:34
	 * yuguang
	 */
	private static final long serialVersionUID = 1L;
	private String uid;
    private String salt;
    private String email;

    public SecurityUser(String uid, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String salt, String email) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.salt = salt;
        this.uid=uid;
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
