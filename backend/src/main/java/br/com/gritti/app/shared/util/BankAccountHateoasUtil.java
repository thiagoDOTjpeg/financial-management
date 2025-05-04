package br.com.gritti.app.shared.util;

import br.com.gritti.app.application.dto.bankaccount.BankAccountCreateDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountResponseDTO;
import br.com.gritti.app.application.dto.bankaccount.BankAccountUpdateDTO;
import br.com.gritti.app.interfaces.controller.BankAccountController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class BankAccountHateoasUtil {
  public static void addLinks(BankAccountResponseDTO bankAccountResponseDTO) {
    bankAccountResponseDTO.add(linkTo(methodOn(BankAccountController.class).getAccountById(bankAccountResponseDTO.getId())).withSelfRel().withType("GET"));
    bankAccountResponseDTO.add(linkTo(methodOn(BankAccountController.class).getBankAccounts(0, 12, "asc")).withRel("bank-accounts").withType("GET"));
    bankAccountResponseDTO.add(linkTo(methodOn(BankAccountController.class).createAccount(new BankAccountCreateDTO())).withRel("create").withType("POST"));
    bankAccountResponseDTO.add(linkTo(methodOn(BankAccountController.class).deleteAccount(bankAccountResponseDTO.getId())).withRel("delete").withType("DELETE"));
    bankAccountResponseDTO.add(linkTo(methodOn(BankAccountController.class).updateAccount(bankAccountResponseDTO.getId(), new BankAccountUpdateDTO())).withRel("update").withType("PUT"));
  }
}
