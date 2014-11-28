package ctrl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import model.Cart;
import model.Order;
import model.bean.ItemBean;

/**
 * Servlet implementation class CheckOutController
 */
public class CheckOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("target", "/checkout.jspx");
		String checkout = request.getParameter("checkout");
		if (checkout != null){
			String f = "order/ethan.xml";
			String filename = this.getServletContext().getRealPath("/" + f);
			try {
				Cart c = (Cart) this.getServletContext().getAttribute("cart");
				export("ethan", "ethan_1", filename, c);
				request.setAttribute("xml_file", f);
				request.getRequestDispatcher("/Done.jspx")
				.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			request.getRequestDispatcher("/index.jspx")
				.forward(request, response);
		}
	}

	public void export(String customername, String ordernumber, 
				String filename, Cart c) throws Exception{

		String path = filename.substring(0, filename.lastIndexOf("/"));
		
		Order order = new Order(c);
	    JAXBContext jc = JAXBContext.newInstance(order.getClass());
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	    
	    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    Schema schema = sf.newSchema(new File(path+"/PO.xsd"));
	    marshaller.setSchema(schema);
	    
	    StringWriter sw = new StringWriter();
	    sw.write("<?xml version='1.0'?>\n");
	    //sw.write("<?xml-stylesheet type=\"text/xsl\" href=\"PO.xsl\"?>");
	    sw.write("\n");
	    marshaller.marshal(order, new StreamResult(sw));
	    
	    System.out.println(sw.toString()); // for debugging
	    FileWriter fw = new FileWriter(filename);
	    fw.write(sw.toString());
	    fw.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
