package br.com.gritti.domain.model;

import br.com.gritti.domain.enums.AccountStatus;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User extends AuditableEntity implements UserDetails {
  @Column(nullable = false, unique = true, length = 100)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_status", nullable = false, length = 20)
  private AccountStatus accountStatus = AccountStatus.ACTIVE;

  @Column(name = "account_non_expired", nullable = false)
  private Boolean accountNonExpired = true;

  @Column(name = "account_non_locked", nullable = false)
  private Boolean accountNonLocked = true;

  @Column(name = "credentials_non_expired", nullable = false)
  private Boolean credentialsNonExpired = true;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new HashSet<>();

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private UserSubscription subscription;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<BankAccount> bankAccounts = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Category> categories = new HashSet<>();

  public void addRole(Role role) {
    roles.add(role);
    role.getUsers().add(this);
  }

  public void removeRole(Role role) {
    roles.remove(role);
    role.getUsers().remove(this);
  }

  public List<String> getPermissions() {
    List<String> permissions = new ArrayList<>();
    for(Role role : roles) {
      permissions.add(role.getDescription());
    }
    return permissions;
  }

  public User() {
  }

  private User(Builder builder) {
    this.username = builder.username;
    this.email = builder.email;
    this.password = builder.password;
    this.fullName = builder.fullName;
    this.accountStatus = builder.accountStatus;
    this.accountNonExpired = builder.accountNonExpired;
    this.accountNonLocked = builder.accountNonLocked;
    this.credentialsNonExpired = builder.credentialsNonExpired;
    this.lastLogin = builder.lastLogin;
    this.roles = builder.roles;
    this.bankAccounts = builder.bankAccounts;
    this.categories = builder.categories;
  }

  public static class Builder {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private LocalDateTime lastLogin;
    private Set<Role> roles = new HashSet<>();
    private Set<BankAccount> bankAccounts = new HashSet<>();
    private Set<Category> categories = new HashSet<>();

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder fullName(String fullName) {
      this.fullName = fullName;
      return this;
    }

    public Builder accountStatus(AccountStatus accountStatus) {
      this.accountStatus = accountStatus;
      return this;
    }

    public Builder accountNonExpired(Boolean accountNonExpired) {
      this.accountNonExpired = accountNonExpired;
      return this;
    }

    public Builder accountNonLocked(Boolean accountNonLocked) {
      this.accountNonLocked = accountNonLocked;
      return this;
    }

    public Builder credentialsNonExpired(Boolean credentialsNonExpired) {
      this.credentialsNonExpired = credentialsNonExpired;
      return this;
    }

    public Builder lastLogin(LocalDateTime lastLogin) {
      this.lastLogin = lastLogin;
      return this;
    }

    public Builder roles(Set<Role> roles) {
      this.roles = roles;
      return this;
    }

    public Builder bankAccounts(Set<BankAccount> bankAccounts) {
      this.bankAccounts = bankAccounts;
      return this;
    }

    public Builder categories(Set<Category> categories) {
      this.categories = categories;
      return this;
    }
    public User build() {
      return new User(this);
    }
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.accountStatus == AccountStatus.ACTIVE;
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public AccountStatus getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountStatus accountStatus) {
    this.accountStatus = accountStatus;
  }

  public Boolean getAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(Boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public Boolean getAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(Boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public Boolean getCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public LocalDateTime getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(LocalDateTime lastLogin) {
    this.lastLogin = lastLogin;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public UserSubscription getSubscription() {
    return subscription;
  }

  public void setSubscription(UserSubscription subscription) {
    this.subscription = subscription;
  }

  public Set<BankAccount> getBankAccounts() {
    return bankAccounts;
  }

  public void setBankAccounts(Set<BankAccount> bankAccounts) {
    this.bankAccounts = bankAccounts;
  }

  public Set<Category> getCategories() {
    return categories;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }
}
