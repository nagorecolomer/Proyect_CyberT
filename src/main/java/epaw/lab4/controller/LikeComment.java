package epaw.lab4.controller;

import epaw.lab4.model.Tweet;
import epaw.lab4.model.User;
import epaw.lab4.service.CommentService;
import epaw.lab4.service.LikeService;
import epaw.lab4.service.TweetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/LikeComment")
public class LikeComment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Tweet> tweets = null;
		User user = null;
		HttpSession session = request.getSession(false);

		if (session != null) {
			user = (User) session.getAttribute("user");
			if (user != null) {
				TweetService tweetService = TweetService.getInstance();
				LikeService likeService = LikeService.getInstance();
				CommentService commentService = CommentService.getInstance();
				tweets = tweetService.getTweetsFromFollowedUsers(user.getId(), 0, 20);
				Map<Integer, Integer> likeCounts = new HashMap<>();
				Map<Integer, Integer> commentCounts = new HashMap<>();
				List<Integer> likedTweetIds = likeService.getLikedTweetIdsByUser(user.getId());
				for (Tweet tweet : tweets) {
					likeCounts.put(tweet.getId(), likeService.getLikeCount(tweet.getId()));
					commentCounts.put(tweet.getId(), commentService.getCommentCount(tweet.getId()));
				}
				request.setAttribute("likeCounts", likeCounts);
				request.setAttribute("commentCounts", commentCounts);
				request.setAttribute("likedTweetIds", likedTweetIds);
			}
		}

		request.setAttribute("tweets", tweets);
		request.setAttribute("user", user);
		request.getRequestDispatcher("LikeComment.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
