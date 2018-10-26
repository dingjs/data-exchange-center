package data.exchange.center.monitor.interfaces.system.facade.dto;

/**
 * 
 * Description:用户个人信息
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:55:41</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class MyProfile {

    private String username;

    private String email;

    public MyProfile(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
