package com.jobportal.service;

import java.util.List;
import com.jobportal.model.Resume;

public interface IResumeService {
    Boolean saveResume(Resume resume);
    Boolean updateResume(Resume resume);
    Boolean deleteResume(String resumeId);
    Resume getResumeByUserId(String userId);
    Resume getResumeById(String resumeId);
    List<Resume> getAllResumes();
    Boolean hasResume(String userId);
    List<Resume> getResumesBySkills(String skills);
    List<Resume> getResumesByExperience(int minYears, int maxYears);
    Boolean updateResumeStatus(String resumeId, Boolean status);
    Integer getResumesCount();
    List<Resume> getRecentResumes(int limit);
}