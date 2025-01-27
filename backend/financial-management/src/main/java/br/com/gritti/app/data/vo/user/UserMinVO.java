package br.com.gritti.app.data.vo.user;

import br.com.gritti.app.model.User;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class UserMinVO {

    private UUID id;
    private String username;
    private String email;
    private String fullName;
    private Date lastLogin;

    public UserMinVO() {
    }

    public UserMinVO(User entity) {
        id = entity.getId();
        username = entity.getUsername();
        email = entity.getEmail();
        fullName = entity.getFullName();
        lastLogin = entity.getLastLogin();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserMinVO userMinVO = (UserMinVO) o;
        return Objects.equals(id, userMinVO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
