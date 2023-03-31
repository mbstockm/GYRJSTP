package edu.utica.jobsub.model;

import com.opencsv.bean.CsvBindByName;

import java.time.LocalDate;
import java.util.Objects;

public class Term {

    @CsvBindByName(column = "Term Code")
    private String termCode;

    @CsvBindByName(column = "Term Description")
    private String termDescription;

    @CsvBindByName(column = "Start Date")
    private LocalDate startDate;

    @CsvBindByName(column = "End Date")
    private LocalDate endDate;

    @CsvBindByName(column = "Aid Year")
    private String faYear;

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getTermDescription() {
        return termDescription;
    }

    public void setTermDescription(String termDescription) {
        this.termDescription = termDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getFaYear() {
        return faYear;
    }

    public void setFaYear(String faYear) {
        this.faYear = faYear;
    }

    @Override
    public String toString() {
        return "Term{" +
                "termCode='" + termCode + '\'' +
                ", termDescription='" + termDescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", faYear='" + faYear + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Term term)) return false;
        return Objects.equals(getTermCode(), term.getTermCode()) && Objects.equals(getTermDescription(), term.getTermDescription()) && Objects.equals(getStartDate(), term.getStartDate()) && Objects.equals(getEndDate(), term.getEndDate()) && Objects.equals(getFaYear(), term.getFaYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTermCode(), getTermDescription(), getStartDate(), getEndDate(), getFaYear());
    }
}
