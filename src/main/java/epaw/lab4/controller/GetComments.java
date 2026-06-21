package epaw.lab4.controller;

import epaw.lab4.service.CommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GetComments")
public class GetComments extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tweetIdStr = request.getParameter("tweetId");
		if (tweetIdStr == null || tweetIdStr.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		try {
			int tweetId = Integer.parseInt(tweetIdStr);
			CommentService commentService = CommentService.getInstance();
			request.setAttribute("comments", commentService.getCommentsForTweet(tweetId));
			request.getRequestDispatcher("Comments.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
