package com.nagarro.restbackApi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.restbackApi.Models.NagpAdmin;
import com.nagarro.restbackApi.repository.LoginRepository;

@Service
public class LoginService {

	@Autowired 
	private  LoginRepository loginInterface;
	
	
	/**
	 * @param Daman Brar
	 * Authentication
	 * @return
	 */
	public NagpAdmin authenticate(NagpAdmin user) {
		 NagpAdmin userob=loginInterface.findByEmail(user.getEmail());//checking if user exists
		 if(userob == null) {
			 return null;
		 }
		 if(userob.getPassword().equals(user.getPassword())) {
			 return userob;//password check
		 }
		 else {
			 return null;
		 }
	}

	
}
