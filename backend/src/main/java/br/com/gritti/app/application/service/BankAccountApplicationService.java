package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.bankaccount.BankAccountCreateDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountResponseDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountUpdateDTO;
import br.com.gritti.app.application.mapper.BankAccountMapper;
import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.domain.service.BankAccountDomainService;
import br.com.gritti.app.domain.service.UserDomainService;
import br.com.gritti.app.interfaces.controller.BankAccountController;
import br.com.gritti.app.shared.util.BankAccountHateoasUtil;
import br.com.gritti.app.shared.util.SecurityUtil;
import br.com.gritti.app.shared.util.UserHateoasUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BankAccountApplicationService {
  private final Logger log = org.slf4j.LoggerFactory.getLogger(BankAccountApplicationService.class);
  private final BankAccountDomainService bankAccountDomainService;
  private final BankAccountMapper bankAccountMapper;
  private final PagedResourcesAssembler<BankAccountResponseDTO> assembler;
  private final UserDomainService userDomainService;

  @Autowired
  public BankAccountApplicationService(BankAccountDomainService bankAccountDomainService, BankAccountMapper bankAccountMapper,
                                       PagedResourcesAssembler<BankAccountResponseDTO> assembler, UserDomainService userDomainService) {
    this.bankAccountDomainService = bankAccountDomainService;
    this.bankAccountMapper = bankAccountMapper;
    this.assembler = assembler;
    this.userDomainService = userDomainService;
  }

  public PagedModel<EntityModel<BankAccountResponseDTO>> getAccounts(Pageable pageable, String username) {
    log.info("APPLICATION: Request received from controller and passing to domain to get all bank accounts");
    String currentUsername = SecurityUtil.getCurrentUsername();
    boolean isAdmin = SecurityUtil.isAdmin();
    Page<BankAccount> accounts;
    if(username != null && !username.isBlank()) {
      if(!isAdmin && !username.equals(currentUsername)){
        throw new AccessDeniedException("Access denied, you don't have permission to access this resource");
      }
      accounts = bankAccountDomainService.getAccounts(pageable, username);
    } else if(!isAdmin){
      username = currentUsername;
      accounts = bankAccountDomainService.getAccounts(pageable, username);
    } else {
      accounts = bankAccountDomainService.getAccounts(pageable);
    }

    Page<BankAccountResponseDTO> accountsWithLinks = accounts.map(a -> {
      BankAccountResponseDTO accountDto = bankAccountMapper.accountToAccountResponseDTO(a);
      BankAccountHateoasUtil.addLinks(accountDto);
      UserHateoasUtil.addLinkGetMinimalVO(accountDto.getUser());
      return accountDto;
    });

    Link findAllLinks = linkTo(methodOn(BankAccountController.class).getBankAccounts(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString(), "")).withSelfRel();
    return assembler.toModel(accountsWithLinks, findAllLinks);
  }

  public BankAccountResponseDTO getAccountById(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to the domain to get a bank account by id: {}", id);
    BankAccount account = bankAccountDomainService.getAccountById(id);
    BankAccountResponseDTO accountDto = bankAccountMapper.accountToAccountResponseDTO(account);
    BankAccountHateoasUtil.addLinks(accountDto);
    UserHateoasUtil.addLinkGetMinimalVO(accountDto.getUser());
    return accountDto;
  }

  public BankAccountResponseDTO createAccount(BankAccountCreateDTO accountCreateDTO) {
    log.info("APPLICATION: Request received from controller and passing to the domain to create a new bank account");
    BankAccount account = bankAccountMapper.accountCreateDTOtoAccount(accountCreateDTO);
    account.setUser(userDomainService.getUserById(accountCreateDTO.getUserId()));
    bankAccountDomainService.createAccount(account);
    return bankAccountMapper.accountToAccountResponseDTO(account);
  }

  public BankAccountResponseDTO updateAccount(UUID id, BankAccountUpdateDTO account) {
    log.info("APPLICATION: Request received from controller and passing to the domain to update a bank account by id: {}", id);
    BankAccount accountUpdate = bankAccountMapper.accountUpdateDTOtoAccount(account);
    BankAccount updatedAccount = bankAccountDomainService.updateAccount(id, accountUpdate);
    return bankAccountMapper.accountToAccountResponseDTO(updatedAccount);
  }

  public void deleteAccount(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to the domain to delete a bank account by id: {}", id);
    bankAccountDomainService.deleteAccount(id);
  }
}
