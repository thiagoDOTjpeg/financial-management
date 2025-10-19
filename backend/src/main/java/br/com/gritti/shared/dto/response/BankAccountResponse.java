package br.com.gritti.shared.dto.response;

import br.com.gritti.domain.enums.AccountType;
import br.com.gritti.shared.dto.response.summary.UserSummaryResponse;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Relation(collectionRelation = "bank-account")
public class BankAccountResponse extends RepresentationModel<BankAccountResponse> {
  private UUID id;
  private UserSummaryResponse user;
  private String bankName;
  private AccountType accountType;
  private String accountNumber;
  private String agency;
  private Boolean isActive;

  public BankAccountResponse() {
  }

  public BankAccountResponse(UUID id, UserSummaryResponse user, String bankName, AccountType accountType, String accountNumber, String agency, Boolean isActive) {
    this.id = id;
    this.user = user;
    this.bankName = bankName;
    this.accountType = accountType;
    this.accountNumber = accountNumber;
    this.agency = agency;
    this.isActive = isActive;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UserSummaryResponse getUser() {
    return user;
  }

  public void setUser(UserSummaryResponse user) {
    this.user = user;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getAgency() {
    return agency;
  }

  public void setAgency(String agency) {
    this.agency = agency;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }
}
