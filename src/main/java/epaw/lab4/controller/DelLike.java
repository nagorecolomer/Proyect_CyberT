package epaw.lab4.controller;

import epaw.lab4.model.User;
import epaw.lab4.service.LikeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/DelLike")
public class DelLike extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		String tweetIdStr = request.getParameter("tweetId");
		if (tweetIdStr == null || tweetIdStr.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		try {
			int tweetId = Integer.parseInt(tweetIdStr);
			LikeService likeService = LikeService.getInstance();
			boolean removed = likeService.removeLike(tweetId, user.getId());

			if (removed) {
				response.setHeader("X-Like-Count", String.valueOf(likeService.getLikeCount(tweetId)));
				response.setHeader("X-Liked", "false");
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			} else {
				response.setHeader("X-Like-Count", String.valueOf(likeService.getLikeCount(tweetId)));
				response.setHeader("X-Liked", String.valueOf(likeService.hasLiked(tweetId, user.getId())));
				response.setStatus(HttpServletResponse.SC_CONFLICT);
			}
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
