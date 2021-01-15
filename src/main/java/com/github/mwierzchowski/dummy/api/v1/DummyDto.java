package com.github.mwierzchowski.dummy.api.v1;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DummyDto {
    @NotEmpty
    private String name;

    @NotNull
    @Min(18)
    private Integer age;
}
