package com.github.mwierzchowski.dummy.core;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@SequenceGenerator(name = "dummy_id_generator", sequenceName = "dummy_id_seq", allocationSize = 10)
public class Dummy {
    @Id
    @GeneratedValue(generator = "dummy_id_generator")
    private Integer id;

    @NotNull
    private String name;

    private Integer age;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant updated;

    @Version
    private Integer version;
}
