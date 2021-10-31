package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import jdbc.BaseDao;
import jdbc.Entiteta;
import jdbc.Uporabnik;
import jdbc.UporabnikDaoImpl;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@WebServlet("/servlet")
public class PrviJdbcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        String ime_storitve = ConfigurationUtil.getInstance().get("kumuluzee.name").get();
        String version = ConfigurationUtil.getInstance().get("kumuluzee.version").get();
        String ime_okolja = ConfigurationUtil.getInstance().get("kumuluzee.env.name").get();

        String newline = "<br>";

        pw.println("<html><body>" + newline);
        pw.println("Welcome to the servlet");
        pw.println("ime storitve: " + ime_storitve + newline + "verzija:" + version + newline + "ime okolja: " + ime_okolja);
        pw.println("</body></html>");


        BaseDao uporabnikDao = new UporabnikDaoImpl();
        Uporabnik newuser = new Uporabnik("tajniagent","James", "Bond");

        //uporabnikDao.vstavi(newuser);

        printlist(uporabnikDao, resp);

    }
    private void printlist(BaseDao uporabnikDao, HttpServletResponse resp) throws IOException {
        //resp.getWriter().printf("<html><body>" + "List of users: " + "</body></html>");
        //String newline = "<br>";

        List<Entiteta> allusers = uporabnikDao.vrniVse();
        for(int i = 0; i < allusers.size(); i++){
            Uporabnik temp = (Uporabnik) allusers.get(i);
            resp.getWriter().printf("%s\t%s\t%s\n", temp.getUsername(), temp.getName(), temp.getSurname());

        }
    }
}
