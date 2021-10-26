package ejercicioServlets.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hibernate.principal.HibernateUtil;

/**
 * Servlet implementation class MostrarDatosServ
 */
@WebServlet("/MostrarDatos")
public class MostrarDatosServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarDatosServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HibernateUtil.logger.info("Accedido al metodo doGet de MostrarDatosServ");
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String tabla = request.getParameter("table");
		if(tabla!=null)
		{
			if(tabla.equals("empleado"))
			{	HibernateUtil.logger.info("Redirigiendo a MostrarEmpleadosServ");
				request.getRequestDispatcher("/MostrarEmpleados").forward(request, response);
			}
			else if(tabla.equals("departamento"))
			{
				HibernateUtil.logger.info("Redirigiendo a MostrarDepartamentosServ");
				request.getRequestDispatcher("/MostrarDepartamentos").forward(request, response);
			}
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HibernateUtil.logger.info("Accedido al metodo doPost de MostrarDatosServ");
		// TODO Auto-generated method stub
		//doGet(request, response);
		String tabla = request.getParameter("table");
		if(tabla!=null)
		{
			if(tabla.equals("empleado"))
				request.getRequestDispatcher("/MostrarEmpleados").forward(request, response);
			else if(tabla.equals("departamento"))
				request.getRequestDispatcher("/MostrarDepartamentos").forward(request, response);
		}
	}

}
