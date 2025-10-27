package hu.unideb.inf.receptgyujto.service.dto;

import lombok.*;

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
}
