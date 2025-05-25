package br.com.gritti.app.shared.util.hateoas;

import br.com.gritti.app.application.dto.bankaccount.*;
import br.com.gritti.app.application.valueobjects.BankAccountMinimalVO;
import br.com.gritti.app.interfaces.controller.BankAccountController;
import br.com.gritti.app.interfaces.controller.UserController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class BankAccountHateoasUtil {
  public static <T> void addLinks(T dto) {
    if(dto instanceof BankAccountResponseDTO bankAccountResponseDTO) {
      bankAccountResponseDTO.add(linkTo(methodOn(BankAccountController.class).getAccountById(bankAccountResponseDTO.getId())).withSelfRel().withType("GET"));
      bankAccountResponseDTO.add(linkTo(methodOn(BankAccountController.class).deleteAccount(bankAccountResponseDTO.getId())).withRel("delete").withType("DELETE"));
      bankAccountResponseDTO.add(linkTo(methodOn(BankAccountController.class).updateAccount(bankAccountResponseDTO.getId(), new BankAccountUpdateDTO())).withRel("update").withType("PUT"));
      bankAccountResponseDTO.add(linkTo(methodOn(UserController.class).getUserById(bankAccountResponseDTO.getId())).withRel("user").withType("GET"));
    } else if(dto instanceof BankAccountCardsResponseDTO bankAccountCardsResponseDTO) {
      bankAccountCardsResponseDTO.add(linkTo(methodOn(BankAccountController.class).getAccountById(bankAccountCardsResponseDTO.getId())).withSelfRel().withType("GET"));
      bankAccountCardsResponseDTO.add(linkTo(methodOn(BankAccountController.class).getBankAccounts(0, 12, "asc", "")).withRel("bankAccounts").withType("GET"));
      bankAccountCardsResponseDTO.add(linkTo(methodOn(BankAccountController.class).createAccount(new BankAccountCreateDTO())).withRel("create").withType("POST"));
      bankAccountCardsResponseDTO.add(linkTo(methodOn(BankAccountController.class).deleteAccount(bankAccountCardsResponseDTO.getId())).withRel("delete").withType("DELETE"));
      bankAccountCardsResponseDTO.add(linkTo(methodOn(BankAccountController.class).updateAccount(bankAccountCardsResponseDTO.getId(), new BankAccountUpdateDTO())).withRel("update").withType("PUT"));
      bankAccountCardsResponseDTO.add(linkTo(methodOn(UserController.class).getUserById(bankAccountCardsResponseDTO.getId())).withRel("user").withType("GET"));
    }
  }

  public static void addLinkGet(BankAccountDTO bankAccountDTO) {
    bankAccountDTO.add(linkTo(methodOn(BankAccountController.class).getAccountById(bankAccountDTO.getId())).withSelfRel().withType("GET"));
  }
  public static void addLinkGetMinimal(BankAccountMinimalVO bankAccountMinimalVO) {
    bankAccountMinimalVO.add(linkTo(methodOn(BankAccountController.class).getAccountById(bankAccountMinimalVO.getId())).withSelfRel().withType("GET"));
  }
}
