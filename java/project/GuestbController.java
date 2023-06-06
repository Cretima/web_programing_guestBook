package project;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

/* 컨트롤러 경로 */
//@WebServlet("/GuestbController")
@WebServlet(urlPatterns = "/guest.nhn")

public class GuestbController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private GuestbDAO dao;
	private ServletContext ctx;
	
	//웹 페이지 기본 경로 지정 -> guestbMain.jsp
	private final String START_PAGE = "project/guestbMain.jsp";
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new GuestbDAO();
		ctx = getServletContext();
	}
	
	// service() 서블릿이 호출될때마다 실행되는 메소드
	protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		// 메세지 찍어보기
		System.out.println("serviceStart");
		
		Method m; // 자바 리플렉션을 사용해 if, switch 없이 요청에 따라 구현 메서드가 실행되도록 함.
		String view = null;
		
		// action 파라미터 없이 접근한 경우 listGuestb로 보냄
		if(action == null) {
//			System.out.println("Action1");
//			getServletContext().getRequestDispatcher("/guestControl?action=listGuestb").forward(request, response);
			action = "listGuestb";
		}
		try {
			// 현재 클래스에서 action 이름과 HttpServletRequest 를 파라미터로 하는 메서드 찾음
			m = this.getClass().getMethod(action, HttpServletRequest.class);
			
			// 메서드 실행후 리턴값 받아옴
			view = (String)m.invoke(this, request);
		} catch(Exception e) {
			e.printStackTrace();
			ctx.log("요청 action 없음.");
			request.setAttribute("error", "action 파라미터가 잘못 되었다.");
			// 시작 페이지로
			view = START_PAGE;
		}

		
//		System.out.println("View start");
		// 뷰 이동
		if(view.startsWith("redirect:/")) {
			String rview = view.substring("redirect:/".length()); // redirect:/ 이후 경로만 가져옴. 
			response.sendRedirect(rview);
			} else {
//				System.out.println("View 2");
				RequestDispatcher dispatcher = request.getRequestDispatcher(view);
				dispatcher.forward(request, response); // 지정된 뷰로 포워딩, 컨텍스트경로는 필요없음.
		}
	}
	
	/* getAll() 방명록 목록 보여주기 위한 요청 처리 메소드 */
	public String listGuestb(HttpServletRequest request) {
		List<Guestb> list;
		try {
			list = dao.getAll();
			request.setAttribute("guestbList", list);
			
		} catch (Exception e) {
			System.out.println("리스트catch");
			e.printStackTrace();
			ctx.log("방명록 불러오는 과정에서 문제 발생!");
			request.setAttribute("error", "방명록 목록 불러오기가 정상적으로 처리되지 않았습니다.");
		}
		System.out.println("리스트4");
		return "project/guestbMain.jsp";
	}

	/* addList() 요청 처리 메소드 */
	public String addList(HttpServletRequest request) {
		System.out.println("addList start");
		Guestb g = new Guestb();
		try {
			BeanUtils.populate(g, request.getParameterMap());
			dao.addList(g);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("방명록 추가 과정에서 문제 발생!");
			request.setAttribute("error", "방명록이 정상적으로 등록되지 않았습니다.");
			return listGuestb(request);
		}
		return "redirect:/guest.nhn?action=listGuestb";
	}
	
	/* getList() 특정 방명록 클릭했을 때 리스트 호출하기 위한 요청 처리 메소드 */
	public String getList(HttpServletRequest request) {
		System.out.println("getList start");
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			Guestb g = dao.getList(id);
			request.setAttribute("g", g);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("방명록 가져오는 과정에서 문제 발생!");
			request.setAttribute("error", "방명록 수정 목록 불러오기가 정상적으로 처리되지 않았습니다.");
		}
		return "project/guestbUpdate.jsp";
	}
	
	/* UpdateList() 방명록 수정 요청 처리 메소드 */
	public String updateList(HttpServletRequest request) {
		System.out.println("Update start");
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			Guestb g = dao.getList(id);
			
			dao.addList(g);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			ctx.log("방명록 수정 과정에서 문제 발생!");
			request.setAttribute("error", "방명록 수정이 정상적으로 처리되지 않았습니다.");
			//이 아래의 리턴을 이전화면으로 가게끔 해야함
			String undo = request.getHeader("Referer");
			undo = undo.substring(28);
			System.out.println(undo);
			return undo;
		}
		try {
			dao.delList(id);
		} catch (SQLException e) {
			e.printStackTrace();
			ctx.log("방명록 수정 과정에서 문제 발생!");
			request.setAttribute("error", "방명록 수정이 정상적으로 처리되지 않았습니다.");
			return "project/guestbUpdate.jsp";
		}
		return "redirect:/guest.nhn?action=listGuestb";
	}
	
	/* delList() 방명록 삭제 요청을 위한 메소드 */
	public String delList(HttpServletRequest request) {
		System.out.println("삭제1");
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("삭제2");
		try {
			System.out.println("삭제3");
			dao.delList(id);
		} catch(SQLException e) {
			e.printStackTrace();
			ctx.log("방명록 삭제 과정에서 문제 발생!");
			request.setAttribute("error", "방명록이 정상적으로 삭제되지 않음.");
			return listGuestb(request);
		}
		System.out.println("삭제4");
		return "redirect:/guest.nhn?action=listGuestb";
	}
	
}
