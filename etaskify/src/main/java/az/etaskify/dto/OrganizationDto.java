package az.etaskify.dto;

import lombok.Data;


@Data
public class OrganizationDto {
    private String name;
    private String phoneNumber;
    private String address;
    private UserOwnerDto ownerDto;
}

