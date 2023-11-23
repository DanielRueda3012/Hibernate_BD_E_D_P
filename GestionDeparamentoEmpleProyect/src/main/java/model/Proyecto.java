package model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "proyectos")
@NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
public class Proyecto {

	@Id
	@GeneratedValue
	@Column(name = "id")
	@Getter
	private Integer id;

	@Setter
	private String nombre;

	@ManyToMany(fetch = FetchType.EAGER)

	@JoinTable(name = "empleado_proyecto", joinColumns = { @JoinColumn(name = "proyecto_id") }, inverseJoinColumns = {
			@JoinColumn(name = "empleado_id") })

	private List<Empleado> misEmpleados;

	public String toString() {
		if (misEmpleados != null) {
			return "[Proyecto (Id-> " + getId() + ", Nombre-> " + getNombre() + ", Empleados-> "
					+ mostrarEmpleados(misEmpleados) + ")]\n";
		}
		return "[Proyecto (Id-> " + getId() + ", Nombre-> " + getNombre() + ", Empleados-> "
				+ mostrarEmpleados(misEmpleados) + "]\n";
	}

	private String mostrarEmpleados(List<Empleado> misEmpleados) {
		String str = "";
		for (Empleado empleado : misEmpleados) {
			str += "(" + empleado.getId() + ", " + empleado.getNombre() + "),";
		}
		return str;
	}
}