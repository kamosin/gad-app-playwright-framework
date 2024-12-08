package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record User(String firstname,  String lastname, String email, String birthDate,  String password,  String avatar) {
}