package hibernate.principal;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import hibernate.clasesDAO.DepartamentoDAO;
import hibernate.clasesDAO.EmpleadoDAO;
import hibernate.ejercicioHibernate.Departamento;
import hibernate.ejercicioHibernate.Empleado;



public class principal {
	
	public static int menuPrincipal(Scanner sc) {
		
		int opcion=0;
		
		try
		{
			do
			{
				System.out.println("1. Insercion");
				System.out.println("2. Borrado");
				System.out.println("3. Actualizacion");
				System.out.println("4. Lectura");
				System.out.println("5. Buscar empleados por codigo de departamento");
				System.out.println("6. Buscar empleados mayores que cierta edad");
				System.out.println("7. Salir");
				opcion = sc.nextInt();
				
			}while(opcion<1 && opcion>5);
		}
		catch (Exception e) {
			HibernateUtil.logger.warn("Excepcion al elegir la opcion del menu",e);
			System.exit(-1);
		}
		
		return opcion;
	}
	
	public static int pedirTabla(Scanner sc) {

		int opcion = 0;

		try {
			do {
				System.out.println("1. Departamento");
				System.out.println("2. Empleado");
				System.out.println("3. Cancelar operacion y salir");
				opcion = sc.nextInt();
			} while (opcion < 1 && opcion > 3);
		} catch (Exception e) {
			HibernateUtil.logger.warn("Excepcion al pedir la tabla a usar.",e);
			System.exit(-1);
		}

		return opcion;

	}
	
	public static int pedirCodigo(Scanner sc) {

		int codigo = 0;

		try {
				System.out.println("1. Introduzca el Codigo");
				codigo = sc.nextInt();

		} catch (Exception e) {
			HibernateUtil.logger.warn("Excepcion al pedir el codigo.",e);
			System.exit(-1);
		}

		return codigo;

	}
	
	public static void pedirDepartamento(Session s,Scanner sc, Departamento d) {
		Empleado em;
		int codRes;
		
		HibernateUtil.logger.info("Introduccion del departamento");

		try {
			System.out.println("Introduzca el nombre del departamento");
			d.setNombre(sc.next());
			System.out.println("Introduzca el codigo del responsable");
			codRes = sc.nextInt();
			em = EmpleadoDAO.getEmpleado(s, codRes);
			while(em==null && codRes!=0)
			{
				System.out.println("El responsable con ese codigo no existe, introduzca otro o 0 si no quiere asignar uno.");
				codRes=sc.nextInt();
				em = EmpleadoDAO.getEmpleado(s, codRes);
			}
			d.setCodResponsable(codRes);

		} catch (Exception e) {
			HibernateUtil.logger.warn("Excepcion al introducir el departamento",e);
			System.exit(-1);
		}

	}
	
	public static void pedirEmpleado(Session s,Scanner sc, Empleado em) {
		Departamento d;
		int codDep;
		
		HibernateUtil.logger.info("Introduccion del empleado.");

		try {
			System.out.println("Introduzca el nombre del empleado");
			em.setNombre(sc.next());
			System.out.println("Introduzca el primer apellido");
			em.setApellido1(sc.next());
			System.out.println("Introduzca el segundo apellido");
			em.setApellido2(sc.next());
			em.setDireccion("X");
			em.setFechaNacimiento("X");
			em.setLugarNacimiento("X");
			em.setPuesto("X");
			em.setTelefono("X");
			
			System.out.println("Introduzca codigo del Departamento");
			codDep = sc.nextInt();
			d = DepartamentoDAO.getDepartamento(s, codDep);
			while(d==null && codDep!=0)
			{
				System.out.println("El departamento con ese codigo no existe, introduzca otro o 0 si no quiere asignar uno.");
				codDep=sc.nextInt();
				d = DepartamentoDAO.getDepartamento(s, codDep);
				
			}
			em.setCodDepartamento(codDep);

		} catch (Exception e) {
			HibernateUtil.logger.warn("Excepcion al introducir el empleado",e);
			System.exit(-1);
		}

	}
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Session session;
		Transaction tx = null;
		int opcion;
		int tabla=0;
		int codigo;
		List<Empleado> lista;
		
		HibernateUtil.logger.info("Iniciada Aplicacion.");

		opcion = menuPrincipal(sc);
		if (opcion == 7) {
			HibernateUtil.logger.info("Elegida la opcion de salida.");
			return;
		}

		if(opcion<5)
		{
			tabla = pedirTabla(sc);
			if (tabla == 3) {
				HibernateUtil.logger.info("Cancelada operacion y salida del programa.");
				return;
			}
		}

		session = HibernateUtil.getSessionFactory().openSession();

		switch (opcion) {
		case 1:
			HibernateUtil.logger.info("Elegida la opcion de Insertar");
			try {
				tx = session.beginTransaction();
				if (tabla == 1) {

					Departamento d = new Departamento();
					pedirDepartamento(session, sc, d);
					DepartamentoDAO.insertDepartamento(session, d);

				} else {
					Empleado e = new Empleado();
					pedirEmpleado(session, sc, e);
					EmpleadoDAO.insertEmpleado(session, e);
				}
				tx.commit();

			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
				}
				HibernateUtil.logger.warn("Excepcion al Insertar.",e);
			} finally {
				if (session != null) {
					session.close();
					HibernateUtil.logger.info("Sesion cerrada");
				}
			}
			break;
		case 2:
			HibernateUtil.logger.info("Elegida la opcion de borrar.");
			if (tabla == 1) {

				codigo = pedirCodigo(sc);
				try {

					Departamento d = new Departamento();

					tx = session.beginTransaction();
					d = DepartamentoDAO.getDepartamento(session, codigo);
					DepartamentoDAO.deleteDepartamento(session, d);

					tx.commit();

				} catch (Exception e) {
					if (tx != null) {
						tx.rollback();
					}
					HibernateUtil.logger.warn("Excepcion al borrar",e);
				} finally {
					if (session != null) {
						session.close();
						HibernateUtil.logger.info("Sesion cerrada");
					}
				}

			} else {
				
				codigo = pedirCodigo(sc);
				try {

					Empleado d = new Empleado();

					tx = session.beginTransaction();
					d = EmpleadoDAO.getEmpleado(session, codigo);
					EmpleadoDAO.deleteEmpleado(session, d);

					tx.commit();

				} catch (Exception e) {
					if (tx != null) {
						tx.rollback();
					}
					HibernateUtil.logger.warn("Excepcion al borrar",e);
				} finally {
					if (session != null) {
						session.close();
						HibernateUtil.logger.info("Sesion cerrada");
					}
				}

			}
			break;
		case 3:
			HibernateUtil.logger.info("Elegida la opcion de actualizar.");
			codigo = pedirCodigo(sc);
			try {
				tx = session.beginTransaction();
				if (tabla == 1) {
					Departamento d = DepartamentoDAO.getDepartamento(session, codigo);
					if (d != null) {
						pedirDepartamento(session, sc, d);
						DepartamentoDAO.updateDepartamento(session, d);
					} else {
						System.out.println("El departamento no existe.");
						HibernateUtil.logger.info("El departamento no existe.");
					}

				} else {
					Empleado e = EmpleadoDAO.getEmpleado(session, codigo);
					if (e != null) {
						pedirEmpleado(session, sc, e);
						EmpleadoDAO.updateEmpleado(session, e);
					} else {
						System.out.println("El empleado no existe.");
						HibernateUtil.logger.info("El empleado no existe.");
					}
				}
				tx.commit();

			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
					HibernateUtil.logger.warn("Excepcion al actualizar",e);
				}
			} finally {
				if (session != null) {
					session.close();
					HibernateUtil.logger.info("Sesion cerrada");
				}
			}
			break;
		case 4:
			HibernateUtil.logger.info("Elegida la opcion de mostrar.");
			codigo = pedirCodigo(sc);
			if (tabla == 1) {
				Departamento d = DepartamentoDAO.getDepartamento(session, codigo);
				if (d != null) {
					System.out.println(d.toString());
					HibernateUtil.logger.info("Consulta:" + d.toString());
				}

			} else {
				Empleado e = EmpleadoDAO.getEmpleado(session, codigo);
				if (e != null) {
					System.out.println(e.toString());
					HibernateUtil.logger.info("Consulta:" + e.toString());
				}
			}
			break;
		case 5:
			HibernateUtil.logger.info("Consulta por codigo de departamento.");
			codigo = pedirCodigo(sc);
			lista = EmpleadoDAO.getEmpleadosCodDep(session, codigo);
			if(!lista.isEmpty())
			{
				for (Empleado empleado : lista) {
					System.out.println(empleado.toString());
					HibernateUtil.logger.info("Consulta por codDep="+codigo+":" + empleado.toString());
				}
			}
			else
			{
				System.out.println("No existe ningun resultado para el codigo indicado.");
			}
			break;
		case 6:
			HibernateUtil.logger.info("Consulta por edad.");
			System.out.println("Introduce la edad");
			int edad = sc.nextInt();
			lista = EmpleadoDAO.getEmpleadosEdad(session, edad);
			if(!lista.isEmpty())
			{
				for (Empleado empleado : lista) {
					System.out.println(empleado.toString());
					HibernateUtil.logger.info("Consulta por edad>="+edad+":" + empleado.toString());
				}
			}
			else
			{
				System.out.println("No existe ningun resultado para la edad indicada.");
			}
			break;
		}

		HibernateUtil.logger.info("Finalizada Aplicacion.");
	}
}
