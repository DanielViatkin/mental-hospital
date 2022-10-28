package com.epam.hospital.model.user;

import com.epam.hospital.model.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Entity {
    Integer userId;
    Integer userRoleId;
    String email;
    String firstName;
    String lastName;
    String number;
    String hashedPassword;
    Boolean isBanned;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
