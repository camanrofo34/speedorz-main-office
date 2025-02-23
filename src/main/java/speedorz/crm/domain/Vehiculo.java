package speedorz.crm.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nombre;

    private String marca;

    private String modelo;

    private String descripcion;

    private int stock;

    private double precio;
}
