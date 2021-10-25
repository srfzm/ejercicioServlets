package hibernate.clasesDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.query.Query;

import hibernate.ejercicioHibernate.Empleado;
import hibernate.principal.HibernateUtil;

public class EmpleadoDAO {

	public static Empleado getEmpleado(Session s, int codigo) {
//		  String hQuery = " from Empleado c " +
//		                  " where c.codigo = :codigo";
//		  Empleado empleado = s.createQuery(hQuery, Empleado.class)
//		                   .setParameter("codigo", codigo)
//		                   .setMaxResults(1)
//		                   .uniqueResult();
//	    return empleado;
	    return s.get(Empleado.class, codigo);
		}
	
	public static void insertEmpleado(Session s, Empleado empleado) {
		empleado.setCodigo(getNewCodigo(s));
		s.save(empleado);
		HibernateUtil.logger.info("Realizada inserccion.");
	}
	
	public static void deleteEmpleado(Session s, Empleado empleado) {
		s.delete(empleado);
		HibernateUtil.logger.info("Realizado borrado.");
	}
	
	public static void updateEmpleado(Session s, Empleado empleado) {
		s.update(empleado);
		HibernateUtil.logger.info("Realizada actualizacion.");
	}
	
	public static int getNewCodigo(Session s) {
		String hql= "SELECT max(codigo) from Empleado";
		Query query = s.createQuery(hql);
		return (int)query.getSingleResult()+1;
	}

	public static List<Empleado> getEmpleadosCodDep(Session s, int codDep) {
		DetachedCriteria dcr = DetachedCriteria.forClass(Empleado.class);
		dcr.add(Property.forName("codDepartamento").eq(codDep));
		return dcr.getExecutableCriteria(s).list();
	}

	public static List<Empleado> getEmpleadosEdad(Session s, int edad) {

		List<Empleado> tmp;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaCorte = null;
		Date fechaComp;
		String fecha = LocalDateTime.from(LocalDateTime.now()).minusYears(edad)
				.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		try {
			fechaCorte = formato.parse(fecha);
		} catch (ParseException e) {
			HibernateUtil.logger.warn("Excepcion al parsear la fecha.",e);
			e.printStackTrace();
		}

		// debido al formato de la fecha que no guarda las dos ultimas cifras del año
		// toca filtrarlos manualmente
		String hql = "from Empleado";

		Query<Empleado> q = s.createQuery(hql);
		tmp = q.list();
		ArrayList<Empleado> emp = new ArrayList<Empleado>();
		for (Empleado empleado : tmp) {

			try {
				fechaComp = formato.parse(empleado.getFechaNacimiento() + "00");
				if (fechaComp.before(fechaCorte)) {
					emp.add(empleado);
				}
			} catch (ParseException e) {
				HibernateUtil.logger.warn("Excepcion al parsear la fecha.",e);
				e.printStackTrace();
			}
		}

		return emp;
	}
}
