package model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "deparatamentos")
@NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d")
public class Departamento {
	@Id
	@GeneratedValue
	@Column(name = "id")
	@Getter
	private Integer id;
	@Setter
	private String nombre;

	@OneToOne
	private Empleado jefe;

	@OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER)
	private List<Empleado> misEmpleados;

	public String toString() {

		if (jefe == null) {
			return "[Departamento(Id-> " + getId() + ", Nombre-> " + getNombre() + ", Jefe-> " + jefe + ", Empleados-> "
					+ mostrarEmpleados(misEmpleados) + "]\n";
		} else {
			return "[Departamento(Id-> " + getId() + ", Nombre-> " + getNombre() + ", Jefe-> " + mostrarJefe(jefe)
					+ ", Empleados-> " + mostrarEmpleados(misEmpleados) + "]\n";
		}
	}

	private String mostrarJefe(Empleado jefe) {
		return "(" + jefe.getId() + ", " + jefe.getNombre() + "),";
	}

	private String mostrarEmpleados(List<Empleado> misEmpleados) {
		String str = "";
		for (Empleado empleado : misEmpleados) {
			str += "(" + empleado.getId() + ", " + empleado.getNombre() + ") ";
		}
		return str;
	}
}