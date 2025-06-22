package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.bankaccount.BankAccountCardsResponseDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountCreateDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountResponseDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountUpdateDTO;
import br.com.gritti.app.application.dto.transaction.TransactionCreateDTO;
import br.com.gritti.app.application.dto.transaction.TransactionResponseDTO;
import br.com.gritti.app.application.mapper.BankAccountMapper;
import br.com.gritti.app.application.mapper.CardMapper;
import br.com.gritti.app.application.mapper.TransactionMapper;
import br.com.gritti.app.domain.enums.PaymentType;
import br.com.gritti.app.domain.model.BankAccount;
import br.com.gritti.app.domain.model.Card;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.service.BankAccountDomainService;
import br.com.gritti.app.domain.service.CardDomainService;
import br.com.gritti.app.domain.service.UserDomainService;
import br.com.gritti.app.domain.valueobject.TransactionProcessingData;
import br.com.gritti.app.interfaces.controller.BankAccountController;
import br.com.gritti.app.shared.util.hateoas.BankAccountHateoasUtil;
import br.com.gritti.app.shared.util.SecurityUtil;
import org.apache.coyote.BadRequestException;
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

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BankAccountApplicationService {
  private final Logger log = org.slf4j.LoggerFactory.getLogger(BankAccountApplicationService.class);
  private final BankAccountDomainService bankAccountDomainService;
  private final TransactionApplicationService transactionApplicationService;
  private final CardDomainService cardDomainService;
  private final TransactionMapper transactionMapper;
  private final BankAccountMapper bankAccountMapper;
  private final CardMapper cardMapper;
  private final PagedResourcesAssembler<BankAccountResponseDTO> assembler;

  @Autowired
  public BankAccountApplicationService(BankAccountDomainService bankAccountDomainService, BankAccountMapper bankAccountMapper,
                                       PagedResourcesAssembler<BankAccountResponseDTO> assembler, CardDomainService cardDomainService,
                                       CardMapper cardMapper, TransactionApplicationService transactionApplicationService, TransactionMapper transactionMapper) {
    this.bankAccountDomainService = bankAccountDomainService;
    this.transactionApplicationService = transactionApplicationService;
    this.bankAccountMapper = bankAccountMapper;
    this.transactionMapper = transactionMapper;
    this.assembler = assembler;
    this.cardDomainService = cardDomainService;
    this.cardMapper = cardMapper;
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
      return accountDto;
    });

    Link findAllLinks = linkTo(methodOn(BankAccountController.class).getBankAccounts(pageable.getPageNumber(), pageable.getPageSize(), "asc", "")).withSelfRel();
    Link createLinks = linkTo(methodOn(BankAccountController.class).createAccount(new BankAccountCreateDTO())).withRel("create-account");
    PagedModel<EntityModel<BankAccountResponseDTO>> pagedModel = assembler.toModel(accountsWithLinks, findAllLinks);
    pagedModel.add(createLinks);
    return pagedModel;
  }

  public BankAccountResponseDTO getAccountById(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to the domain to get a bank account by id: {}", id);
    BankAccount account = bankAccountDomainService.getAccountById(id);
    BankAccountResponseDTO accountDto = bankAccountMapper.accountToAccountResponseDTO(account);
    BankAccountHateoasUtil.addLinks(accountDto);
    return accountDto;
  }

  public BankAccountCardsResponseDTO getAccountCards(UUID id) {
    log.info("APPLICATION: Request received from controller and passing to the domain to get a bank account cards by id: {}", id);
    BankAccount account = bankAccountDomainService.getAccountById(id);
    List<Card> cards = cardDomainService.getCardsByAccount(account.getId());
    BankAccountCardsResponseDTO bankAccountCardsResponseDTO = bankAccountMapper.bankAccountToBankAccountCardsResponseDTO(account);
    bankAccountCardsResponseDTO.setCards(cards.stream().map(cardMapper::cardToCardMinimalVO).toList());;
    BankAccountHateoasUtil.addLinks(bankAccountCardsResponseDTO);
    return bankAccountCardsResponseDTO;
  }

  public BankAccountResponseDTO createAccount(BankAccountCreateDTO accountCreateDTO) {
    log.info("APPLICATION: Request received from controller and passing to the domain to create a new bank account");
    BankAccount account = bankAccountMapper.accountCreateDTOtoAccount(accountCreateDTO);
    User user = SecurityUtil.getCurrentUser();
    if(user == null) throw new AccessDeniedException("Access denied, you don't have permission to access this resource");
    account.setUser(user);
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

  public TransactionResponseDTO createTransfer(TransactionCreateDTO transactionCreateDTO) throws BadRequestException {
    log.info("APPLICATION: Request received from the controller and passing to the domain to create a transfer");
    if(transactionCreateDTO.getPaymentType() == PaymentType.CREDIT || transactionCreateDTO.getPaymentType() == PaymentType.DEBIT) throw new BadRequestException("Wrong endpoint for creating this type of transaction");
    TransactionProcessingData processingData = transactionMapper.transactionCreateDtoToTransactionProcessingData(transactionCreateDTO);
    processingData.setToAccountId(transactionCreateDTO.getToAccountId());
    processingData.setFromAccountId(transactionCreateDTO.getFromAccountId());
    return transactionMapper.transactionToTransactionResponseDTO(transactionApplicationService.createTransfer(processingData));
  }
}
