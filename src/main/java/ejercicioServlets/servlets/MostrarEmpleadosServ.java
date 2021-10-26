package ejercicioServlets.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import hibernate.clasesDAO.EmpleadoDAO;
import hibernate.ejercicioHibernate.Empleado;
import hibernate.principal.HibernateUtil;



/**
 * Servlet implementation class MostrarEmpleadosServ
 */
@WebServlet("/MostrarEmpleados")
public class MostrarEmpleadosServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarEmpleadosServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HibernateUtil.logger.info("Accedido al metodo doGet de MostrarEmpleadosServ");
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Session s = HibernateUtil.getSessionFactory().openSession();
		ArrayList<Empleado> lista = EmpleadoDAO.getLista(s);
		if(lista!=null && !lista.isEmpty())
			mostrarTabla(response.getWriter(), lista);
		s.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HibernateUtil.logger.info("Accedido al metodo doPost de MostrarEmpleadosServ");
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private PrintWriter mostrarTabla(PrintWriter out, ArrayList<Empleado> e) {
		HibernateUtil.logger.info("Mostrando tabla de empleados");
		out.println("<html>");
		out.println("<title>Servlet de pruebas :)</title>");
		out.println("<body>");
		out.println("<table style=\"width:100%\">");
		out.println("<tr>");
		out.println("<th>");
		out.println("Codigo");
		out.println("</th>");
		out.println("<th>");
		out.println("Nombre");
		out.println("</th>");
		out.println("<th>");
		out.println("Apellido 1");
		out.println("</th>");
		out.println("<th>");
		out.println("Apellido 2");
		out.println("</th>");
		out.println("<th>");
		out.println("Fecha de Nacimiento");
		out.println("</th>");
		out.println("<th>");
		out.println("Codigo Departamento");
		out.println("</th>");
		out.println("</tr>");
		for (Empleado empleado : e) {
			out.println("<tr>");
			out.println("<th>");
			out.println(empleado.getCodigo());
			out.println("</th>");
			out.println("<th>");
			out.println(empleado.getNombre());
			out.println("</th>");
			out.println("<th>");
			out.println(empleado.getApellido1());
			out.println("</th>");
			out.println("<th>");
			out.println(empleado.getApellido2());
			out.println("</th>");
			out.println("<th>");
			out.println(empleado.getFechaNacimiento());
			out.println("</th>");
			out.println("<th>");
			out.println(empleado.getCodDepartamento());
			out.println("</th>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
		
		
		return out;
	}

}
