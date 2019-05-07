package com.nagarro.restbackApi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.restbackApi.Models.Activity;
import com.nagarro.restbackApi.Models.ApplicantDetails;
import com.nagarro.restbackApi.repository.ApplicantRepository;

@Service
public class ApplicantService {
	
	@Autowired
	private ApplicantRepository applicantRepository;

	public ApplicantDetails authenticate(ApplicantDetails user) {
		ApplicantDetails userob=applicantRepository.findByEmail(user.getEmail());
		 if(userob == null) {
			 return null;
		 }
		 if(userob.getPassword().equals(user.getPassword())) {
			 return userob;
		 }
		 else {
			 return null;
		 }
	}

	public ApplicantDetails getApplicantById(int id) {
		// TODO Auto-generated method stub
		return applicantRepository.findById(id).get();
	}

	public void updateProfile(int id, ApplicantDetails newapplicantDetails) {
			// TODO Auto-generated method stub
			ApplicantDetails old=applicantRepository.getOne(id);
			if(old!=null)
			{
				if(newapplicantDetails.getContactNumber()!=0) {
					old.setContactNumber(newapplicantDetails.getContactNumber());
				}
				
				if(newapplicantDetails.getEmail()!=null && !newapplicantDetails.getEmail().equals("")){
					old.setEmail(newapplicantDetails.getEmail());
				}
				
				if(newapplicantDetails.getPassword()!=null && !newapplicantDetails.getPassword().equals("")) {
					old.setPassword(newapplicantDetails.getPassword());
				}
				
				if(newapplicantDetails.getName()!=null && !newapplicantDetails.getName().equals("")) {
					old.setName(newapplicantDetails.getName());
				}
				
				applicantRepository.save(old);
			}
			else
			{
				System.out.println("error");
			}
			
		} 
	 
	public List<Activity> getApplicantActivitiesByBatchAndByLevel(String level_id, String batch_id) {
		// TODO Auto-generated method stub
		return applicantRepository.findByLevelIdAndBatch(level_id,batch_id);
	}

	
	
	
	
	
}
