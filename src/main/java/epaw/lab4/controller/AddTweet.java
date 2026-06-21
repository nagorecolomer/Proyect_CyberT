package epaw.lab4.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import epaw.lab4.service.TweetService;
import epaw.lab4.model.Tweet;
import epaw.lab4.model.User;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Servlet implementation class AddTweet
 */
@WebServlet("/AddTweet")
public class AddTweet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddTweet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);

		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				try {
					String content = request.getParameter("content");
					if (content != null) {
						content = content.trim();
					}

					if (content == null || content.isEmpty()) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						return;
					}

					Tweet tweet = new Tweet();
					tweet.setUid(user.getId());
					tweet.setUname(user.getName());
					tweet.setPostDateTime(new Timestamp(System.currentTimeMillis()));
					tweet.setContent(content);
					TweetService tweetService = TweetService.getInstance();
					tweetService.add(tweet);
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					return;
				} catch (Exception e) {
					e.printStackTrace();
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
				
			}
		}

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
