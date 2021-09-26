package main.java.ru.partezan7.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Table(name = "weatherlog", schema = "getweather")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Weatherlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weatherlog_id", nullable = false)
    private int id;

    private String city;

    private double temp;

    private double pressure;

    private Timestamp date;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weatherlog that = (Weatherlog) o;
        return id == that.id && Double.compare(that.temp, temp) == 0 && Double.compare(that.pressure, pressure) == 0 && Objects.equals(city, that.city) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, temp, pressure, date);
    }
}
