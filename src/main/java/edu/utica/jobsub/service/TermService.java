package edu.utica.jobsub.service;

import edu.utica.jobsub.dao.TermDao;
import edu.utica.jobsub.model.Term;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermService {

    private TermDao termDao;

    public TermService(TermDao termDao) {
        this.termDao = termDao;
    }

    public List<Term> getTermsForAidy(String aidyCode) {
        return termDao.getTermsForAidyData(aidyCode);
    }

}
