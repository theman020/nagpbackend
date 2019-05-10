package com.nagarro.restbackApi.Service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.restbackApi.Enums.Status;
import com.nagarro.restbackApi.Models.ApplicantActivityRecord;
import com.nagarro.restbackApi.repository.ApplicantActivityRecordRepository;

@Service
public class ApplicantActivityRecordService {
	
	Logger logger=Logger.getLogger(ApplicantActivityRecordService.class.getName());
	

	@Autowired
	private ApplicantActivityRecordRepository applicantActivityRecordRepository;
	
	public void addApplicantActivity(ApplicantActivityRecord applicantActivityRecord) {
		// TODO Auto-generated method stub
		if(applicantActivityRecord.getRecordId() == 0)
		{
			List<ApplicantActivityRecord> list=applicantActivityRecordRepository.findAll();
			int size=list.size();
			applicantActivityRecord.setRecordId(size+1);
			applicantActivityRecordRepository.save(applicantActivityRecord);
		}		
	}

	public List<ApplicantActivityRecord> getAllApplicantActivity() {
		// TODO Auto-generated method stub
		return applicantActivityRecordRepository.findAll();
	}

	public String activityCheck(String activity_id, int applicant_id) {
		// TODO Auto-generated method stub
		List<ApplicantActivityRecord> fetchallRecords=applicantActivityRecordRepository.checkActivity(activity_id, applicant_id);
		if(fetchallRecords.size()==0) {
			logger.info("no record found");
			
			return "true";
		}
		else {
			//check if status is complete,we have to disable button
			if(fetchallRecords.get(0).getStatus().toString().equals("COMPLETED")) {
				logger.info("status is completed");
				return "COMPLETED";
			}
			else if(fetchallRecords.get(0).getStatus().toString().equals("REVIEW_FAILED")) {
				logger.info("status is failed");
				logger.info("count is ="+fetchallRecords.get(0).getCount());
				int count=fetchallRecords.get(0).getCount();
				logger.info("max attempt is "+fetchallRecords.get(0).getActivity().getMaxAttempts());
				int maxAttempts=fetchallRecords.get(0).getActivity().getMaxAttempts();
				if(count<maxAttempts) {
					//we can reAttempt the activity
					nextAttemptForActivity(fetchallRecords.get(0));
					return "ADDMORE";
				}
				
				else {
					return "false";
				}
			}
			else {
				return "PENDING";
			}
			
			
		}
	}
	
	private void nextAttemptForActivity(ApplicantActivityRecord applicantActivityRecord) {
		// TODO Auto-generated method stub
		logger.info("information==>"+applicantActivityRecord);
		logger.info("new count is="+applicantActivityRecord.getCount()+1);
		
		applicantActivityRecord.setCount(applicantActivityRecord.getCount()+1);
		applicantActivityRecord.setStatus(Status.PLANNED);
		
		applicantActivityRecordRepository.save(applicantActivityRecord);		
	}

	public List<ApplicantActivityRecord> getHistoryofActivitiesOfApplicant(int id) {
		// TODO Auto-generated method stub
		return applicantActivityRecordRepository.getHistoryofActivitiesOfApplicant(id);
	}

	public void updateActivityRecord(String status, int recordid) {
		// TODO Auto-generated method stub
		ApplicantActivityRecord fetched=applicantActivityRecordRepository.findById(recordid).get();
		if(status.equals("DONE")){
			status="REVIEW_PENDING";
			applicantActivityRecordRepository.updateActivityRecord(status,recordid);
		}
		applicantActivityRecordRepository.updateActivityRecord(status,recordid);
	}

	public void updateActivityStatus(double percentage_score, String status, int record_id) {
		ApplicantActivityRecord fetched=applicantActivityRecordRepository.findById(record_id).get();
		double points_earned = 0;
		int count=fetched.getCount()+1;
		if(status.equals("COMPLETED")) {
			points_earned = (fetched.getActivity().getQualifyPoints()*percentage_score)/100;
		}
		applicantActivityRecordRepository.updateActivityStatus(percentage_score,points_earned,status, record_id,count);
		
	}

		public double getApplicantScoreById(int applicant_id) {
			if(applicantActivityRecordRepository.getHistoryofActivitiesOfApplicant(applicant_id).size()==0) {
				return 0;
			}
		return applicantActivityRecordRepository.getTotalScore(applicant_id);
	}

	

}
