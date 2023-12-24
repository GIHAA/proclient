package com.full.stack.demo.core.common;

import com.full.stack.demo.core.model.User;
import com.full.stack.demo.core.payload.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface  MapStructMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    @Mapping(source = "role", target = "userRole")
    UserResponseDto userToUserResponseDto(User user);

}
