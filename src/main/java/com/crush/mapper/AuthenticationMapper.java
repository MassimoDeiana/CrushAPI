package com.crush.mapper;

import com.crush.domain.entity.User;
import com.crush.dtos.authentication.RegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    RegisterResponse map(User user);

}
