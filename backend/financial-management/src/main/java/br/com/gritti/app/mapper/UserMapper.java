package br.com.gritti.app.mapper;

import br.com.gritti.app.data.dto.user.UserRequestDTO;
import br.com.gritti.app.data.dto.user.UserResponseDTO;
import br.com.gritti.app.data.vo.UserVO;
import br.com.gritti.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "password", ignore = true)
  User requestToUser(UserRequestDTO dto);
  UserResponseDTO userToResponseDTO(User user);
  UserVO userToUserVO(User user);
}
