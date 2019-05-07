package com.nagarro.restbackApi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.restbackApi.Models.ApplicantDetails;
import com.nagarro.restbackApi.repository.ApplicantListRepository;

@Service
public class ApplicantListService {
	
	@Autowired
	private ApplicantListRepository applicantListRepository;
	
	public List<ApplicantDetails> getAllapplicants() {
		// TODO Auto-generated method stub
		return applicantListRepository.findAll();
	}

	

	public void addApplicant(ApplicantDetails applicant) {
		// TODO Auto-generated method stub
		List<ApplicantDetails> list=getAllapplicants();
		applicant.setApplicantId(list.size()+1);
		applicantListRepository.save(applicant);
		
		
	}
	
	
	

}
