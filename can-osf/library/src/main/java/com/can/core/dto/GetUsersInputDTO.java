package com.can.core.dto;

import com.can.controller.request.PaginationRequest;

public class GetUsersInputDTO {

    private PaginationRequest paginationRequest;

    private String firstName;

    private String lastName;

    private String city;


    public PaginationRequest getPaginationRequest() {
        return paginationRequest;
    }

    public void setPaginationRequest(PaginationRequest paginationRequest) {
        this.paginationRequest = paginationRequest;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
