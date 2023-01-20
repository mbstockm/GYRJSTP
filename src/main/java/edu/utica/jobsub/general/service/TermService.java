package edu.utica.jobsub.general.service;

import edu.utica.jobsub.general.dao.TermDao;
import edu.utica.jobsub.general.model.Term;
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
