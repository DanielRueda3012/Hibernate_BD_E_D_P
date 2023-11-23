package view;

import java.util.InputMismatchException;
import java.util.logging.Logger;

import controller.GestionController;
import dao.HibernateManager;
import io.IO;
import model.Departamento;
import model.Empleado;
import model.Proyecto;
import repositories.departamento.DepartamanetosRepositoriesImpl;
import repositories.empleado.EmpleadosRepositoriesImpl;
import repositories.proyecto.ProyectosRepositoriesImpl;
import util.ApplicationProperties;

public class Main {
	static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		try {
			System.out.println("Gestion Departamentos, Empleados y Proyectos");
			int opc = 0;
			int opc1 = 0;
			initDataBase();

			var controller = new GestionController(new DepartamanetosRepositoriesImpl(), new EmpleadosRepositoriesImpl(),
					new ProyectosRepositoriesImpl());

			do {
				try {
					menu();
					opc = IO.readInt();
					switch (opc) {
					case 0:
						IO.print("\nSaliendo . . .");
						break;
					case 1:
						do {
							menuEmpleado();
							opc1 = IO.readInt();
							switch (opc1) {
							case 1:
								añadirEmpleado(controller);
								break;
							case 2:
								borrarEmpleado(controller);
								break;
							case 3:
								mostrarEmpleados(controller);
								break;
							case 4:
								mostrarEmpleadoPorId(controller);
								break;
							case 5:
								añadirEmpledoADepartamento(controller);
								break;
							case 6:
								añadirEmpleadoAProyecto(controller);
								break;
							case 7:
								borrarEmpleadoDeDepartamento(controller);
								break;
							case 8:
								borrarEmpleadoDeProyecto(controller);
								break;
							case 0:
								break;
							default:
								IO.println("\nOpcion no valida");
								break;
							}
						} while (opc1 != 0);
						break;
					case 2:
						do {
							menuDepartamento();
							opc1 = IO.readInt();
							switch (opc1) {
							case 1:
								añadirDepartamento(controller);
								break;
							case 2:
								borrarDepartamento(controller);
								break;
							case 3:
								mostrarDepartamentos(controller);
								break;
							case 4:
								mostrarDepartamentoPorId(controller);
								break;
							case 5:
								añadirJefe(controller);
								break;
							case 6:
								borrarJefe(controller);
								break;
							default:
								IO.println("\nOpcion no valida");
								break;
							}
						} while (opc1 != 0);
						break;
					case 3:
						do {
							menuProyecto();
							opc1 = IO.readInt();
							switch (opc1) {
							case 1:
								añadirProyecto(controller);
								break;
							case 2:
								borrarProyecto(controller);
								break;
							case 3:
								mostrarProyectos(controller);
								break;
							case 4:
								mostrarProyectosPorId(controller);
								break;
							default:
								IO.println("\nOpcion no valida");
								break;
							}
						} while (opc1 != 0);
						break;

					default:
						IO.println("\nOpcion no valida");
						break;
					}
				} catch (InputMismatchException e) {
					IO.println("\nNo es un numero entero");
				}
			} while (opc != 0);
		} catch (NullPointerException e) {
		}

	}

	private static void borrarJefe(GestionController controller) {
		if (existenEmpleados(controller)) {
			mostrarEmpleados(controller);
			IO.print("\nID jefe?  ");
			Integer id = IO.readInt();
			Empleado emp = controller.mostrarEmpleadoPorId(id);

			if (emp.getDepartamento() != null) {
				Integer idDep = emp.getDepartamento().getId();
				Departamento dep = controller.mostrarDepartamentoPorId(idDep);

				dep.setJefe(emp);

				dep.setJefe(null);

				controller.actualizarEmpleado(emp);
				controller.actualizarDepartamento(dep);

			} else {
				IO.println("\nEl empleado con ID: " + id + " no pertenece a ningún departamento.");
			}
		} else {
			IO.println("No existen empleados");
		}
	}

	private static void añadirEmpleadoAProyecto(GestionController controller) {
		if (existenEmpleados(controller)) {
			mostrarEmpleados(controller);
			IO.print("\nId empleado?  ");
			Integer id = IO.readInt();
			Empleado emp = controller.mostrarEmpleadoPorId(id);
			if (existenProyectos(controller)) {
				mostrarProyectos(controller);
				IO.print("\nId proyecto?  ");
				Integer idProy = IO.readInt();
				Proyecto proy = controller.mostrarProyectoPorId(idProy);

				try {
					emp.getMisProyectos().add(proy);
					proy.getMisEmpleados().add(emp);

					controller.actualizarEmpleado(emp);

					IO.println(controller.actualizarProyecto(proy) != null ? "Empleado añadido al proyecto con exito"
							: "");

				} catch (NullPointerException e) {
					IO.println("No se ha podido añadir el empleado con ID " + id + " al proyecto con ID " + idProy);
					IO.println("");
				}
			} else {
				IO.println("No existen proyectos");
			}

		} else {
			IO.println("No existen empleados");
		}
	}

	private static void añadirJefe(GestionController controller) {
		if (existenDepartamentos(controller)) {
			IO.println(controller.mostrarDepartamentos());
			IO.print("\nId departamento?  ");
			int idDep = IO.readInt();
			if (existenEmpleados(controller)) {
				IO.println(controller.mostrarEmpleados().toString());
				IO.print("\nId empleado?  ");
				int id = IO.readInt();
				try {

					Departamento dep = controller.mostrarDepartamentoPorId(idDep);

					Empleado emp = controller.mostrarEmpleadoPorId(id);

					dep.setJefe(emp);
					emp.setDepartamento(dep);

					controller.actualizarDepartamento(dep);
					controller.actualizarEmpleado(emp);

				} catch (NullPointerException e) {
					IO.println("No se ha podido añadir el jefe con ID " + id + " al departamento con ID " + idDep);
					IO.println("");
				}
			} else {
				IO.println("No existen empleados");
			}
		} else {
			IO.println("No existen departamentos");
		}

	}

	private static void añadirEmpledoADepartamento(GestionController controller) {
		if (existenDepartamentos(controller)) {
			mostrarEmpleados(controller);
			IO.print("\nId empleado?  ");
			Integer id = IO.readInt();
			Empleado emp = controller.mostrarEmpleadoPorId(id);
			if (existenEmpleados(controller)) {
				mostrarDepartamentos(controller);
				IO.print("\nId departamento  ");
				Integer idDep = IO.readInt();
				Departamento dep = controller.mostrarDepartamentoPorId(idDep);
				try {
					emp.setDepartamento(dep);
					dep.getMisEmpleados().add(emp);

					controller.actualizarDepartamento(dep);
					controller.actualizarEmpleado(emp);

				} catch (NullPointerException e) {
					IO.println("No se ha podido añadir el empleado con ID " + id + " al departamento con ID " + idDep);
					IO.println("");
				}
			} else {
				IO.println("No existen departamentos");
			}
		} else {
			IO.println("No existen empleados");
		}
	}

	private static void mostrarEmpleadoPorId(GestionController controller) {
		if (existenEmpleados(controller)) {
			IO.println("Id empleado?  ");
			Integer id = IO.readInt();
			try {

				IO.println(controller.mostrarEmpleadoPorId(id).toString());
				IO.println("");
			} catch (NullPointerException e) {
				IO.println("No se ha podido mostrar el empleado con ID " + id);
				IO.println("");
			}
		} else {
			IO.println("No existen empleados");
		}

	}

	private static void mostrarDepartamentoPorId(GestionController controller) {
		if (existenDepartamentos(controller)) {
			IO.println("Id departamento?  ");
			Integer id = IO.readInt();
			try {

				IO.println(controller.mostrarDepartamentoPorId(id).toString());
				IO.println("");
			} catch (NullPointerException e) {
				IO.println("No se ha podido mostrar el departamento con ID " + id);
				IO.println("");
			}
		} else {
			IO.println("No existen departamentos");
		}
	}

	private static void mostrarProyectosPorId(GestionController controller) {
		if (existenProyectos(controller)) {
			IO.println("Id proyecto?  ");
			Integer id = IO.readInt();
			try {

				IO.println(controller.mostrarProyectoPorId(id).toString());
				IO.println("");
			} catch (NullPointerException e) {
				IO.println("No se ha podido mostrar el proyecto con ID " + id);
				IO.println("");
			}
		} else {
			IO.println("No existen proyectos");
		}
	}

	private static void mostrarEmpleados(GestionController controller) {
		if (existenEmpleados(controller)) {
			IO.print(controller.mostrarEmpleados().toString());
			IO.println("");
		} else {
			IO.println("No existen empleados");
		}
	}

	private static void mostrarDepartamentos(GestionController controller) {
		if (existenDepartamentos(controller)) {
			IO.print(controller.mostrarDepartamentos().toString());
			IO.println("");
		} else {
			IO.println("No existen departamentos");
		}
	}

	private static void mostrarProyectos(GestionController controller) {
		if (existenProyectos(controller)) {
			IO.print(controller.mostrarProyectos().toString());
			IO.println("");
		} else {
			IO.println("No existen proyectos");
		}
	}

	private static void borrarEmpleado(GestionController controller) {
		if (existenEmpleados(controller)) {
			try {
				mostrarEmpleados(controller);
				IO.println("\nId empleado?  ");
				Integer id = IO.readInt();
				Empleado emp = controller.mostrarEmpleadoPorId(id);

				if (emp.getDepartamento() != null) {
					Departamento d = emp.getDepartamento();
					if (d.getJefe() != null) {
						if (d.getJefe().getId().equals(emp.getId())) {
							d.setJefe(null);
							d.getMisEmpleados().remove(emp);
							controller.actualizarDepartamento(d);
						}
					}
				}
				IO.println(controller.borrarEmpleado(emp) ? "\nEmpleado eliminado con éxito."
						: "\nNo se ha podido eliminar el empleado.");
			} catch (NullPointerException e) {
				IO.println("\nNo existe el empleado con ese ID");
				IO.println("");
			}
		} else {
			IO.println("No existen empleados");
		}
	}

	private static void borrarEmpleadoDeDepartamento(GestionController controller) {
		if (existenEmpleados(controller)) {
			mostrarEmpleados(controller);
			IO.print("\nId empleado?  ");
			Integer id = IO.readInt();
			Empleado emp = controller.mostrarEmpleadoPorId(id);

			if (emp.getDepartamento() != null) {
				Integer idDep = emp.getDepartamento().getId();
				Departamento dep = controller.mostrarDepartamentoPorId(idDep);

				emp.setDepartamento(null);
				if (dep.getJefe() != null) {
					dep.setJefe(null);
				}
				dep.getMisEmpleados().remove(emp);
				controller.actualizarEmpleado(emp);
				controller.actualizarDepartamento(dep);

				IO.println((emp.getDepartamento() == null && !dep.getMisEmpleados().contains(emp))
						? "\nEmpleado retirado de su departamento con éxito."
						: "\nNo se ha podido retirar al empleado de su departamento.");
			} else {
				IO.println("\nEl empleado con ID: " + id + " no tiene ningún departamento.");
			}

		} else {
			IO.println("No existen empleados");
		}
	}

	private static void borrarEmpleadoDeProyecto(GestionController controller) {
		if (existenEmpleados(controller)) {
			mostrarEmpleados(controller);
			IO.print("\nId emplado?  ");
			Integer id = IO.readInt();
			Empleado emp = controller.mostrarEmpleadoPorId(id);

			if (emp.getMisProyectos().size() > 0) {
				IO.println(emp.getMisProyectos());
				IO.print("\nId proyecto?  ");
				Integer idProy = IO.readInt();
				Proyecto proy = controller.mostrarProyectoPorId(idProy);

				emp.getMisProyectos().remove(proy);
				proy.getMisEmpleados().remove(emp);

				controller.actualizarEmpleado(emp);
				controller.actualizarProyecto(proy);

				IO.println((emp.getMisProyectos().contains(proy) && proy.getMisEmpleados().contains(emp))
						? "\nEmpleado retirado del proyecto con éxito."
						: "\nNo se ha podido retirar al empleado del proyecto.");
			} else {
				IO.println("\nEl empleado con ID: " + id + " no tiene ningún proyecto.");
			}
		} else {
			IO.println("No existen empleados");
		}

	}

	private static void borrarProyecto(GestionController controller) {
		if (existenProyectos(controller)) {
			mostrarProyectos(controller);
			IO.print("\nId proyecto?  ");
			Integer id = IO.readInt();
			try {
				IO.println(
						controller.borrarProyecto(Proyecto.builder().id(id).build()) ? "\nProyecto eliminado con éxito."
								: "\nNo se ha podido eliminar el proyecto.");
			} catch (NullPointerException e) {
				IO.println("No existe el proyecto con ID " + id);
				IO.println("");
			}
		} else {
			IO.println("No existen proyectos");
		}
	}

	private static void borrarDepartamento(GestionController controller) {
		if (existenDepartamentos(controller)) {
			Departamento dep = null;
			try {
				if (existenDepartamentos(controller)) {
					mostrarDepartamentos(controller);
					IO.print("\nID departamento: ");
					Integer id = IO.readInt();
					dep = controller.mostrarDepartamentoPorId(id);

					if (dep.getMisEmpleados().size() != 0) {
						for (Empleado empleado : dep.getMisEmpleados()) {
							empleado.setDepartamento(null);
							controller.actualizarEmpleado(empleado);
						}
					}

					IO.println(controller.borrarDepartamento(dep) ? "\nDepartamento eliminado."
							: "\nNo se ha podido eliminar el departamento.");
				} else {
					IO.println("\n¡No existe ningún departamento!");
				}
			} catch (NullPointerException e) {
				if (dep == null) {
					IO.println("\n¡El departamento indicado no existe!");
				}
			}
		} else {
			IO.println("No existen departamentos");
		}
	}

	private static void añadirProyecto(GestionController controller) {
		IO.println("Nombre?  ");
		String nombre = IO.readString();
		IO.println(controller.crearProyecto(Proyecto.builder().nombre(nombre).build()) != null ? "\nAñadido"
				: "\nNo se ha añadido");
	}

	private static void añadirDepartamento(GestionController controller) {
		IO.println("\nNombre?  ");
		String nombre = IO.readString();
		IO.println(controller.crearDepartamento(Departamento.builder().nombre(nombre).jefe(null).build()) != null
				? "\nAñadido"
				: "\nError al añadir");
	}

	private static void añadirEmpleado(GestionController controller) {
		IO.println("\nNombre?  ");
		String nombre = IO.readString();
		IO.println("Introduzca su salario");
		Double salario = IO.readDouble();
		IO.println(controller
				.crearEmpleado(Empleado.builder().nombre(nombre).salario(salario).departamento(null).build()) != null
						? "\nAñadido"
						: "\nNo se ha añadido");
	}

	private static boolean existenProyectos(GestionController controller) {
		return controller.mostrarProyectos().size() != 0;
	}

	private static boolean existenEmpleados(GestionController controller) {
		return controller.mostrarEmpleados().size() != 0;
	}

	private static boolean existenDepartamentos(GestionController controller) {
		return controller.mostrarDepartamentos().size() != 0;
	}

	private static void initDataBase() {
		// Leemos el fichero de configuración

		ApplicationProperties properties = new ApplicationProperties();
		logger.info("Leyendo fichero de configuración..." + properties.readProperty("app.title"));
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.close();
	}

	private static void menu() {
		IO.println("\n1.\tEmpleado");
		IO.println("2.\tDepartamento");
		IO.println("3.\tProyecto");
		IO.println("0.\tSalir\n");
	}

	private static void menuEmpleado() {
		IO.println("\n1.\tCrear empleado");
		IO.println("2.\tEliminar empleado ");
		IO.println("3.\tMostrar empleados");
		IO.println("4.\tMostrar empleado por ID");
		IO.println("5.\tAñadir empleado a departamento");
		IO.println("6.\tAñadir empleado a proyecto");
		IO.println("7.\tEliminar empleado de departamento");
		IO.println("8.\tEliminar empleado de proyecto");
		IO.println("0.\tSalir\n");
	}

	private static void menuDepartamento() {
		IO.println("\n1.\tCrear departamento");
		IO.println("2.\tEliminar departamento");
		IO.println("3.\tMostrar departamentos");
		IO.println("4.\tMostrar departamento por ID");
		IO.println("5.\tAñadir jefe a departamento");
		IO.println("6.\tEliminar jefe de departamento");
		IO.println("0.\tSalir\n");
	}

	private static void menuProyecto() {
		IO.println("\n1. Crear proyectos");
		IO.println("2. Eliminar proyecto");
		IO.println("3. Mostrar proyectos");
		IO.println("4. Mostrar proyecto por ID");
		IO.println("0. Salir\n");
	}

}