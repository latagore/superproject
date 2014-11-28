package listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class Analytics implements ServletRequestAttributeListener, HttpSessionAttributeListener {

    /**
     * Default constructor. 
     */
    public Analytics() {
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0) {
        
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0) {
        //calculateAvgItemTime(arg0);
    }

	/**
     * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
     */
    public void attributeAdded(ServletRequestAttributeEvent evt) {
        if(evt.getName().equals("po_file")){
        	Long now = (new Date()).getTime();
        	HttpServletRequest req= (HttpServletRequest) evt.getServletRequest();
        	HttpSession sess =req.getSession();
        	Map<String, Long> checkout_times= (Map<String, Long>) sess.getServletContext().getAttribute("checkout_times");
      		Long totalTime;
      		Long checkouts;
      		if(checkout_times==null){
      			checkout_times = new HashMap<String, Long>();
      			totalTime = 0L;
      			checkouts = 0L;
      		} else {
      			totalTime = checkout_times.get("time");
      			checkouts = checkout_times.get("checkouts");
      		}
      		
      		Long diff = now -sess.getCreationTime();
      		totalTime = totalTime + diff;
      		checkouts++;
      		checkout_times.put("time", totalTime);
      		checkout_times.put("checkouts", checkouts);
      	   	sess.getServletContext().setAttribute("checkout_times", checkout_times);
      		
        }
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0) {
    	//calculateAvgItemTime(arg0);
    }

	/**
     * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
     */
    public void attributeRemoved(ServletRequestAttributeEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
     */
    public void attributeReplaced(ServletRequestAttributeEvent arg0) {
        // TODO Auto-generated method stub
    }
    
	
}
