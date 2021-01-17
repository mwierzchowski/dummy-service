package com.github.mwierzchowski.dummy.api.v1;

import com.github.mwierzchowski.dummy.core.Dummy;
import com.github.mwierzchowski.dummy.core.DummyChecker;
import com.github.mwierzchowski.dummy.core.DummyRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/v1/dummy")
@Tag(name = "Dummy", description = "Dummy resource")
@Transactional
public class DummyControllerV1 {
    private final DummyMapper mapper = Mappers.getMapper(DummyMapper.class);
    private final DummyRepository repository;
    private final DummyChecker checker;

    @GetMapping
    @Operation(summary = "List of dummies", description = "Provides list of available dummies")
    public List<DummyDto> getDummies() {
        LOG.debug("Finding all dummies");
        return repository.findAll().stream()
                .map(mapper::toDummyDto)
                .collect(toList());
    }

    @GetMapping("/sunset")
    @Operation(summary = "Today's dummy sunset", description = "Provides today's sunset")
    public String getDummySunset() {
        LOG.debug("Finding today sunset");
        return checker.todaySunset().toString();
    }

    @PostMapping
    @Operation(summary = "Add dummy", description = "Adds new dummy to the resource")
    public void addDummy(@Valid @RequestBody DummyDto dto) {
        LOG.debug("Adding {}", dto);
        repository.save(mapper.toDummy(dto));
    }

    @Mapper(unmappedTargetPolicy = IGNORE)
    interface DummyMapper {
        DummyDto toDummyDto(Dummy dummy);
        Dummy toDummy(DummyDto dummyDto);
    }
}
