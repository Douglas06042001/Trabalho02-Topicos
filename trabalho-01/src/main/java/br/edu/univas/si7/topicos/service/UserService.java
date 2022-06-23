package br.edu.univas.si7.topicos.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.univas.si7.topicos.security.UserPrincipal;

public class UserService {

    /**
     * Used for successful authentication.
     * @param response
     * @param token
     */
    public static void setAuthorizantionInHeader(HttpServletResponse response, String token) {
        response.addHeader("Authorization", "Bearer " + token);
    }

    public static UserPrincipal getAuthenticated() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof UserPrincipal) {
            return (UserPrincipal) user;
        }
        return null;
    }

//    public static void checkCustomer(String id) {
//    	UserPrincipal user = UserService.getAuthenticated();
//        if (user == null || !user.hasRole(UserProfile.ADMIN) && !id.equals(user.getId())) {
//            throw new AuthorizationException("Access denied: customer with different id of user logged in.");
//        }
//    }

}