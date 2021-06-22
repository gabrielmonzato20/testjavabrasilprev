package com.brasilprev.model;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude="id")
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private Long id;
    @Column(name="NAME")
    @Getter @Setter private String name;
    @Column(name="CPF")
    @Getter @Setter private String cpf;
    @Column(name="ADRESS")
    @Getter @Setter private String adress;

}
