package com.github.mwierzchowski.dummy.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DummyTask {
    private String sender;
    private LocalTime sunset;
}
