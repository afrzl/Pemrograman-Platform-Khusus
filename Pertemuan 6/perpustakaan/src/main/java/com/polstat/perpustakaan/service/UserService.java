package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.UserDto;

public interface UserService{
    public UserDto createUser(UserDto user);
    public UserDto getUserByEmail(String email);
}
