package epaw.lab4.controller;

import epaw.lab4.service.UserService;
import epaw.lab4.model.User;
import epaw.lab4.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/UpdateUserRole")
public class UpdateUserRole extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        userRepository = UserRepository.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Object u = session.getAttribute("user");
        if (u == null || !(u instanceof User) || !"admin".equals(((User)u).getRole())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String idParam = request.getParameter("id");
        String role = request.getParameter("role");
        if (idParam == null || role == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            Integer id = Integer.parseInt(idParam);
            User user = new User();
            user.setId(id);
            user.setRole(role);
            // fetch other fields to avoid overwriting them: load existing user
            userRepository.findById(id).ifPresent(existing -> {
                user.setName(existing.getName());
                user.setPicture(existing.getPicture());
                user.setBiography(existing.getBiography());
                user.setEmail(existing.getEmail());
                user.setAge(existing.getAge());
                user.setFeaturedTweetId(existing.getFeaturedTweetId());
            });
            userRepository.update(user);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
