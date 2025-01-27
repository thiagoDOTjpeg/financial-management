package br.com.gritti.app.mapper;

import br.com.gritti.app.data.dto.user.UserRequestDTO;
import br.com.gritti.app.data.dto.user.UserResponseDTO;
import br.com.gritti.app.data.vo.user.UserMinVO;
import br.com.gritti.app.data.vo.user.UserVO;
import br.com.gritti.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserVO userToUserVO(User user);
    UserMinVO userToUserMinVO(User user);
    User requestToUser(UserRequestDTO userRequestDTO);
    UserResponseDTO userToResponseDTO(User user);

}
