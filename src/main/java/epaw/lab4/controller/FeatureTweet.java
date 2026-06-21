package epaw.lab4.controller;

import epaw.lab4.model.Tweet;
import epaw.lab4.model.User;
import epaw.lab4.service.TweetService;
import epaw.lab4.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/FeatureTweet")
public class FeatureTweet extends HttpServlet {

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

		if (user.getRole() == null || !"professional".equalsIgnoreCase(user.getRole())) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		String tweetIdStr = request.getParameter("tweetId");
		if (tweetIdStr == null || tweetIdStr.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		try {
			int tweetId = Integer.parseInt(tweetIdStr);
			Tweet tweet = TweetService.getInstance().getTweetById(tweetId);
			if (tweet == null || tweet.getUid() != user.getId()) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			Integer featuredTweetId = user.getFeaturedTweetId();
			if (featuredTweetId != null && featuredTweetId == tweetId) {
				user.setFeaturedTweetId(null);
				UserService.getInstance().setFeaturedTweet(user.getId(), null);
			} else {
				user.setFeaturedTweetId(tweetId);
				UserService.getInstance().setFeaturedTweet(user.getId(), tweetId);
			}

			session.setAttribute("user", user);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
