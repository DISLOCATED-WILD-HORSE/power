package exception;

import common.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import service.impl.MenuServiceImpl;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "controller")
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Map<String, Object> exception(MyException e) {
        Map<String, Object> map = new HashMap<>();
        logger.error(e.getMessage());
        logger.error(e.getCause().toString());
        map.put("message", e.getMessage());
        return map;
    }
}
