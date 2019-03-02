package com.can.controller.request;

public class RequestGetUsersSearchInput {

    private PaginationRequest request;
    private String firstName;
    private String lastName;
    private String city;

    public PaginationRequest getRequest() {
        return request;
    }

    public void setRequest(PaginationRequest request) {
        this.request = request;
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
