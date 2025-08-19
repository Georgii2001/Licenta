package travel.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
class GlobalAdvise {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleDBInconsistentException(RuntimeException ex) {
        return buildResponse(ex);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ModelAndView handleDBAlreadyExistsException(RuntimeException ex) {
        return buildResponse(ex);
    }

    private static ModelAndView buildResponse(RuntimeException ex) {
        ModelAndView modelAndView = new ModelAndView("handler");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
}

