package com.example.orderingmanager.Dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInDto {

    private String signInId;
    private String password;
}
