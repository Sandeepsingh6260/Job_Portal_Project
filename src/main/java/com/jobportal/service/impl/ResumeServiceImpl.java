package com.jobportal.service.impl;

import java.util.List;

import com.jobportal.dao.IResumeDao;
import com.jobportal.daoimpl.ResumeDaoImpl;
import com.jobportal.model.Resume;
import com.jobportal.service.IResumeService;

public class ResumeServiceImpl implements IResumeService {

    private IResumeDao resumeDao;

    public ResumeServiceImpl() {
        this.resumeDao = new ResumeDaoImpl();
    }

    @Override
    public Boolean saveResume(Resume resume) {
        resume.setStatus(true); // default active
        return resumeDao.saveResume(resume);
    }

    @Override
    public Boolean updateResume(Resume resume) {
        return resumeDao.updateResume(resume);
    }

    @Override
    public Boolean deleteResume(String resumeId) {
        return resumeDao.deleteResume(resumeId);
    }

    @Override
    public Resume getResumeByUserId(String userId) {
        return resumeDao.getResumeByUserId(userId);
    }

    @Override
    public Resume getResumeById(String resumeId) {
        return resumeDao.getResumeById(resumeId);
    }

    @Override
    public List<Resume> getAllResumes() {
        return resumeDao.getAllResumes();
    }

    @Override
    public Boolean hasResume(String userId) {
        Resume resume = resumeDao.getResumeByUserId(userId);
        return resume != null && resume.getStatus();
    }

    @Override
    public List<Resume> getResumesBySkills(String skills) {
        return resumeDao.getResumesBySkills(skills);
    }

    @Override
    public List<Resume> getResumesByExperience(int minYears, int maxYears) {
        return resumeDao.getResumesByExperience(minYears, maxYears);
    }

    @Override
    public Boolean updateResumeStatus(String resumeId, Boolean status) {
        return resumeDao.updateResumeStatus(resumeId, status);
    }

    @Override
    public Integer getResumesCount() {
        return resumeDao.getResumesCount();
    }

    @Override
    public List<Resume> getRecentResumes(int limit) {
        return resumeDao.getRecentResumes(limit);
    }
}
