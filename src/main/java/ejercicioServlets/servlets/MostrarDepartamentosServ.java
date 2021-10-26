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

import hibernate.clasesDAO.DepartamentoDAO;
import hibernate.ejercicioHibernate.Departamento;
import hibernate.principal.HibernateUtil;

/**
 * Servlet implementation class MostrarDepartamentosServ
 */
@WebServlet("/MostrarDepartamentos")
public class MostrarDepartamentosServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarDepartamentosServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Session s = HibernateUtil.getSessionFactory().openSession();
		ArrayList<Departamento> lista = DepartamentoDAO.getLista(s);
		if(lista!=null && !lista.isEmpty())
			mostrarTabla(response.getWriter(), lista);
		s.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private PrintWriter mostrarTabla(PrintWriter out, ArrayList<Departamento> d) {
		
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
		out.println("Codigo Responsable");
		out.println("</th>");
		out.println("</tr>");
		for (Departamento departamento : d) {
			out.println("<tr>");
			out.println("<th>");
			out.println(departamento.getCodigo());
			out.println("</th>");
			out.println("<th>");
			out.println(departamento.getNombre());
			out.println("</th>");
			out.println("<th>");
			out.println(departamento.getCodResponsable());
			out.println("</th>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
		
		
		return out;
	}

}
