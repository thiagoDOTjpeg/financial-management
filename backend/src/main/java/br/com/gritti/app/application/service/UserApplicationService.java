package br.com.gritti.app.application.service;

import br.com.gritti.app.application.dto.bankaccount.BankAccountDTO;
import br.com.gritti.app.application.dto.user.UserBankAccountsResponseDTO;
import br.com.gritti.app.application.dto.user.UserCreateDTO;
import br.com.gritti.app.application.dto.user.UserResponseDTO;
import br.com.gritti.app.application.dto.user.UserUpdateDTO;
import br.com.gritti.app.application.mapper.BankAccountMapper;
import br.com.gritti.app.application.mapper.UserMapper;
import br.com.gritti.app.domain.model.User;
import br.com.gritti.app.domain.service.BankAccountDomainService;
import br.com.gritti.app.domain.service.UserDomainService;
import br.com.gritti.app.interfaces.controller.UserController;
import br.com.gritti.app.shared.util.hateoas.BankAccountHateoasUtil;
import br.com.gritti.app.shared.util.SecurityUtil;
import br.com.gritti.app.shared.util.hateoas.UserHateoasUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;
import java.util.UUID;

@Service
public class UserApplicationService {
  private final Logger log = LoggerFactory.getLogger(UserApplicationService.class.getName());
  private final UserDomainService userDomainService;
  private final BankAccountDomainService bankAccountDomainService;;
  private final UserMapper userMapper;
  private final BankAccountMapper bankAccountMapper;
  private final PasswordEncoder encoder;
  private final PagedResourcesAssembler<UserResponseDTO> assembler;

  @Autowired
  public UserApplicationService(UserDomainService userDomainService, UserMapper userMapper,
                                PasswordEncoder encoder, PagedResourcesAssembler<UserResponseDTO> assembler,
                                BankAccountDomainService bankAccountDomainService, BankAccountMapper bankAccountMapper) {
    this.userDomainService = userDomainService;
    this.userMapper = userMapper;
    this.encoder = encoder;
    this.assembler = assembler;
    this.bankAccountDomainService = bankAccountDomainService;
    this.bankAccountMapper = bankAccountMapper;
  }

  public PagedModel<EntityModel<UserResponseDTO>> getUsers(Pageable pageable) {
    log.info("APPLICATION: Received request in application and passing to domain to get all users");
    boolean isAdmin = SecurityUtil.isAdmin();

    Page<User> users = userDomainService.getUsers(pageable);
    Page<UserResponseDTO> usersWithLinks = users.map(u -> {
      UserResponseDTO userDTO = userMapper.userToUserResponseDTO(u, isAdmin);
      UserHateoasUtil.addLinks(userDTO);
      return userDTO;
    });

    Link findAllLinks = linkTo(methodOn(UserController.class).getUsers(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
    Link createLinks = linkTo(methodOn(UserController.class).createUser(new UserCreateDTO())).withRel("create-user");
    PagedModel<EntityModel<UserResponseDTO>> usersPagedModel = assembler.toModel(usersWithLinks, findAllLinks);
    usersPagedModel.add(createLinks);
    return usersPagedModel;
  }

  public UserResponseDTO getUserById(UUID id) {
    log.info("APPLICATION: Received request in application and passing to domain to find a user by id");
    boolean isAdmin = SecurityUtil.isAdmin();

    User user = userDomainService.getUserById(id);
    UserResponseDTO userDTO = userMapper.userToUserResponseDTO(user, isAdmin);
    UserHateoasUtil.addLinks(userDTO);
    return userDTO;
  }

  public UserBankAccountsResponseDTO getUserBankAccounts(UUID id) {
    log.info("APPLICATION: Received request in application and passing to domain to find a user bank accounts");
    boolean isAdmin = SecurityUtil.isAdmin();
    User user = userDomainService.getUserById(id);
    List<BankAccountDTO> bankAccounts = bankAccountDomainService.getAccounts(Pageable.unpaged(), user.getUsername()).getContent().stream().map(b -> {
      BankAccountDTO dto = bankAccountMapper.accountToAccountDTO(b);
      BankAccountHateoasUtil.addLinkGet(dto);
      return dto;
    }).toList();
    UserBankAccountsResponseDTO userBankAccountsResponseDTO = userMapper.userToUserBankAccountsResponseDTO(user, isAdmin);
    userBankAccountsResponseDTO.setBankAccounts(bankAccounts);
    return userBankAccountsResponseDTO;
  }

  public UserResponseDTO createUser(UserCreateDTO userDTO) {
    log.info("APPLICATION: Received request in application and passing to domain to create a new user");
    boolean isAdmin = SecurityUtil.isAdmin();


    User user = userMapper.userCreateDTOtoUser(userDTO);
    user.setPassword(encoder.encode(userDTO.getPassword()));
    User userCreated = userDomainService.createUser(user);
    return userMapper.userToUserResponseDTO(userCreated, isAdmin);
  }

  public UserResponseDTO updateUser(UUID id, UserUpdateDTO userDTO) {
    log.info("APPLICATION: received request in application and passing to domain to update a user");
    boolean isAdmin = SecurityUtil.isAdmin();

    User user = userMapper.userUpdateDTOtoUser(userDTO);
    User updatedUser = userDomainService.updateUser(id, user);
    return userMapper.userToUserResponseDTO(updatedUser, isAdmin);
  }

  public void deleteUser(UUID id) {
    log.info("APPLICATION: Received request in application and passing to domain to delete a user");

    userDomainService.deleteUser(id);
  }

  public UserResponseDTO inactivateUser(UUID id) {
    log.info("APPLICATION: Received request in application and passing to domain to inactivate user");
    boolean isAdmin = SecurityUtil.isAdmin();

    return userMapper.userToUserResponseDTO(userDomainService.inactivateUser(id), isAdmin);
  }

  public UserResponseDTO assignRoleToUser(UUID userId, String roleName) {
    log.info("APPLICATION: Received request in application and passing to domain to assign role to user");
    boolean isAdmin = SecurityUtil.isAdmin();

    UserResponseDTO dto = userMapper.userToUserResponseDTO(userDomainService.assignRoleToUser(userId, roleName), isAdmin);
    UserHateoasUtil.addLinks(dto);
    return dto;
  }
}
