package ir.maktab.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LastViewInterceptor implements HandlerInterceptor {

    public static final String LAST_VIEW_ATTRIBUTE = LastViewInterceptor.class.getName() + ".lastView";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute(LAST_VIEW_ATTRIBUTE, modelAndView.getViewName());
        }
    }
}
