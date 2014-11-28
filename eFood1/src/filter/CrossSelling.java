package filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.DispatcherType;

import model.admin;
import model.bean.ItemBean;
/**
 * Servlet Filter implementation class CrossSelling
 */
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST, 
		DispatcherType.FORWARD
}
			, urlPatterns = { "/CrossSelling" }, servletNames = { "Cart" })
public class CrossSelling implements Filter {

    /**
     * Default constructor. 
     */
    public CrossSelling() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		ModifiedResponse wrapper = new ModifiedResponse((HttpServletResponse)response);
		chain.doFilter(request, wrapper);
		String servletResponse = new String(wrapper.toString());
		HttpServletRequest req = (HttpServletRequest) request;
		admin m = (admin) req.getServletContext().getAttribute("model");
		if (servletResponse.contains("1409S413") && !servletResponse.contains("2002H712")){
			ItemBean i;
			String ads = "";
			try {
				i = m.getItem("2002H712");
				
				ads ="<h4>You might also like: (because you added item \"1409S413\")</h4>";//Add!
				ads += "<td class='cart_item_ID'>";
				ads += "<p id='item_ID' value='" + i.getId() + "'>" + i.getId() + "</p>";
				ads += "</td>";
				ads += "<td class='cart_description'>";
				ads += "<h4>" + i.getName() + "</h4>";
				ads += "</td>";
				ads += "<td class='cart_price'>";
				ads += "<f:formatNumber type='currency'>" + i.getPrice() + "</f:formatNumber>";
				ads += "</td>";
				ads += "<td class='item_quantity'>";
				ads += "<div class='item_quantity_button'>";
				ads += "<button class='item_quantity_up' onclick='increaseQuantity(this)' value='" + i.getId() + "'> + </button>";
				ads += "<input class='item_quantity_input' type='text' name='quantity' value='1' autocomplete='off' size='2' id='" + i.getId() + "_item_quantity'/>";
				ads += "<button class='item_quantity_down' onclick='decreaseQuantity(this)' value='"+ i.getId() + "'> - </button>";
				ads += "</div>";
				ads += "</td>";
				ads += "<form name='" + i.getId() + "_submit'>";
				ads += "<td class='cart_add'>";
				ads += "<input type='hidden' name='itemID' value='" + i.getId() + "'/>";
				ads += "<input type='hidden' name='" + i.getId() + "_itemquantity' value='10'/>";
				ads += "<button class='btn btn-default add-to-cart' id='" + i.getId() + "_btnCart' onclick='getquantity(this)' value='" + i.getId() + "'><i class='fa fa-shopping-cart'></i>Add to cart</button >";
				ads += "</td>";
				ads += "</form>";
				ads += "</tr>";
			} catch (Exception e) {
				e.printStackTrace();
			}
			int si = servletResponse.indexOf("suggested_items");
			int fi = servletResponse.indexOf(">", si)+1;
			String f = servletResponse.substring(0,fi) + ads + servletResponse.substring(fi);
			servletResponse = f;
		}
		out.write(servletResponse);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
