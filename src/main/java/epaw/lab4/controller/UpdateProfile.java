package epaw.lab4.controller;

import epaw.lab4.model.User;
import epaw.lab4.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.util.Map;

@MultipartConfig
@WebServlet("/UpdateProfile")
public class UpdateProfile extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserService userService;

	@Override
	public void init() throws ServletException {
		userService = UserService.getInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		User currentUser = (User) session.getAttribute("user");
		User updatedUser = new User();
		updatedUser.setId(currentUser.getId());
		updatedUser.setPassword(currentUser.getPassword());
		updatedUser.setFeaturedTweetId(currentUser.getFeaturedTweetId());

		try {
			BeanUtils.populate(updatedUser, request.getParameterMap());
			String picturePath = userService.saveProfilePicture(request.getPart("picture"), updatedUser.getName());
			if (picturePath != null) {
				updatedUser.setPicture(picturePath);
			} else {
				updatedUser.setPicture(currentUser.getPicture());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> errors = userService.updateProfile(updatedUser);
		if (errors.isEmpty()) {
			updatedUser.setPassword("");
			session.setAttribute("user", updatedUser);
			request.setAttribute("user", updatedUser);
			request.setAttribute("message", "Profile updated successfully.");
			request.getRequestDispatcher("Profile.jsp").forward(request, response);
		} else {
			request.setAttribute("user", updatedUser);
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
		}
	}
}
