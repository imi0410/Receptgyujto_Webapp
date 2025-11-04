package hu.unideb.inf.receptgyujto.service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ReceptDto {
    private Long id;
    private String nev;
    private String leiras;
    private Long felhasznaloId;
    private List<HozzavalokDto> hozzavalok;
}
