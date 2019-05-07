package com.nagarro.restbackApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.restbackApi.Models.NagpAdmin;

public interface LoginRepository extends JpaRepository<NagpAdmin, String>{
	
	NagpAdmin findByEmail(String email);
}
