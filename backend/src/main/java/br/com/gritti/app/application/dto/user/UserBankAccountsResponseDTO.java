package br.com.gritti.app.application.dto.user;

import br.com.gritti.app.application.dto.bankaccount.BankAccountDTO;
import br.com.gritti.app.domain.enums.AccountStatus;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Relation(collectionRelation = "user-bank-accounts")
public class UserBankAccountsResponseDTO extends RepresentationModel<UserBankAccountsResponseDTO> {
  private UUID id;
  private String username;
  private String fullName;
  private String email;
  private Date lastLogin;
  private AccountStatus accountStatus;
  private List<String> roles = new ArrayList<>();
  private List<BankAccountDTO> bankAccounts;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  public UserBankAccountsResponseDTO(UUID id, String username, String fullName, String email, Date lastLogin,
                                     AccountStatus accountStatus, List<String> roles, List<BankAccountDTO> bankAccounts,
                                     LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
    this.id = id;
    this.username = username;
    this.fullName = fullName;
    this.email = email;
    this.lastLogin = lastLogin;
    this.accountStatus = accountStatus;
    this.roles = roles;
    this.bankAccounts = bankAccounts;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.updatedAt = updatedAt;
    this.updatedBy = updatedBy;
  }

  public UserBankAccountsResponseDTO() {
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

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  public AccountStatus getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountStatus accountStatus) {
    this.accountStatus = accountStatus;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public List<BankAccountDTO> getBankAccounts() {
    return bankAccounts;
  }

  public void setBankAccounts(List<BankAccountDTO> bankAccounts) {
    this.bankAccounts = bankAccounts;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
}
