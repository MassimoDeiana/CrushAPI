package com.crush.mapper;

import com.crush.domain.entity.User;
import com.crush.dtos.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);


}
