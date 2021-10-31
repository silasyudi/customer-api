package silas.yudi.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.isNull;

public class CustomerDto {

    private Long id;

    private String name;

    private LocalDate birty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirty() {
        return birty;
    }

    public void setBirty(LocalDate birty) {
        this.birty = birty;
    }

    public int getAge() {
        return !isNull(birty)
                ? Period.between(birty, LocalDate.now()).getYears()
                : 0;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return isNull(name) && isNull(birty);
    }
}
