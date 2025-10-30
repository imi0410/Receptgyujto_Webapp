package hu.unideb.inf.receptgyujto.service.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class HozzavalokDto {
    private Long id;
    private String nev;
    private Double mennyiseg;
    private String mertekegyseg;
    private Long receptId;
}
