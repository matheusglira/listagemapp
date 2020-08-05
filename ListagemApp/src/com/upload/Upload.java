package com.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet("/")
public class Upload extends HttpServlet {
	//private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {

			Part part = request.getPart("arquivo");
			String nomeArquivo = part.getSubmittedFileName();

			String diretorio = getServletContext().getRealPath("/" + "arquivos" + File.separator + nomeArquivo);
			String diretorioAux = getServletContext().getRealPath("/" + "arquivos" + File.separator);
			
			InputStream in = part.getInputStream();
			boolean cond = uploadArquivo(in, diretorio);
			
			if(cond) {
				out.println("<title>Arquivos</title>");
				out.println("<strong style='background-color: yellow;'>Arquivo inserido no diretório: <br/>" + diretorioAux + "</strong>");
				
				 File file = new File(diretorioAux);
				 File[] arquivos = file.listFiles();
				 
				 out.println("<h3>Diretório Listado: </h3>");
				 
				 out.println("<table border='1'");
				 out.println("<tr>");
				 out.println("<th align='center'>Arquivos</th>");
				 out.println("</tr>");
				 
				 for (File fileTmp : arquivos) {
					 out.println("<tr>");
				     out.println("<td>" + fileTmp.getName() + "</td>");
					 out.println("</tr>");
				   }
				 
				 out.println("</table>");
				 out.println("<a href='index.jsp'>Voltar</a>");
				 
			}else {
				out.println("Erro, tente novamente!");
			}
		}
	}
	
	public boolean uploadArquivo(InputStream in, String diretorio) {
		
		boolean verificador = false;
		
		try {
			byte[] b = new byte[in.available()];
			in.read();
			FileOutputStream out = new FileOutputStream(diretorio);
			out.write(b);
			out.flush();
			out.close();
			
			verificador = true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return verificador;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
