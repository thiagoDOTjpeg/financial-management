package br.com.gritti.app.shared.util;

import br.com.gritti.app.application.dto.bankaccount.BankAccountResponseDTO;
import br.com.gritti.app.interfaces.controller.BankAccountController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class BankAccountHateoasUtil {
  public static void addLinks(BankAccountResponseDTO bankAccountResponseDTO) {
    bankAccountResponseDTO.add(linkTo(methodOn(BankAccountController.class).getBankAccounts(0, 12, "asc")).withRel("bank-accounts").withType("GET"));
  }
}
