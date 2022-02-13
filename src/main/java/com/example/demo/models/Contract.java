package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="America/Sao_Paulo")
    private Date initialDate;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="America/Sao_Paulo")
    private Date endDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hiring_company_id")
    private Company hiringCompany;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hired_company_id")
    private Company hiredCompany;

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", initialDate=" + initialDate +
                ", endDate=" + endDate +
                ", hiringCompany=" + hiringCompany +
                ", hiredCompany=" + hiredCompany +
                '}';
    }
}
