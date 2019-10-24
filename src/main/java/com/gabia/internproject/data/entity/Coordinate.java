package com.gabia.internproject.data.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class Coordinate {
  //  @Column(nullable = false, precision = 18, scale=8)
    private BigDecimal lat;
  // @Column(nullable = false, precision = 18, scale=8)
    private BigDecimal lng;
}
