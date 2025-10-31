package hu.unideb.inf.receptgyujto.service.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class RegisztracioDto {
    private String nev;
    private Date szuletesiDatum;
    private String nem;
    private String felhasznalonev;
    private String jelszo;
}