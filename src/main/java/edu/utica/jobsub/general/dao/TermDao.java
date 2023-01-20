package edu.utica.jobsub.general.dao;

import edu.utica.jobsub.general.model.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TermDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public TermDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Term> getTermsForAidyData(String aidyCode) {
        List<Term> list =
                jdbcTemplate.query("""
                        select stvterm_code term_code
                              ,stvterm_desc term_description
                              ,stvterm_start_date start_date
                              ,stvterm_end_date end_date
                              ,stvterm_fa_proc_yr fa_year
                          from stvterm
                         where stvterm_fa_proc_yr = ?
                        """
                        ,new BeanPropertyRowMapper(Term.class)
                        ,new Object[]{aidyCode});
        return list;
    }

}
