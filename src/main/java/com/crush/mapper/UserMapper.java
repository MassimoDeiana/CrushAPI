package com.crush.mapper;

import com.crush.domain.entity.User;
import com.crush.dtos.GetUserByPhoneNumberResponse;
import com.crush.dtos.UserDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    GetUserByPhoneNumberResponse map(User user);

    List<UserDto> map(List<User> users);
}
