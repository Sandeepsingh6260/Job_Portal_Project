package com.jobportal.dao;

import java.util.List;
import com.jobportal.model.Resume;

public interface IResumeDao {
    Boolean saveResume(Resume resume);
    Boolean updateResume(Resume resume);
    Boolean deleteResume(String resumeId);
    Resume getResumeByUserId(String userId);
    Resume getResumeById(String resumeId);
    List<Resume> getAllResumes();
    List<Resume> getResumesBySkills(String skills);
    List<Resume> getResumesByExperience(int minYears, int maxYears);
    Boolean updateResumeStatus(String resumeId, Boolean status);
    Integer getResumesCount();
    List<Resume> getRecentResumes(int limit);
}