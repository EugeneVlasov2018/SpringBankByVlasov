package vlasovspringbanksystem.utils.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter extends TagSupport {
    private Timestamp localDateTime;

    public void setLocalDateTime(Timestamp localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public int doStartTag() throws JspException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\nHH:mm:ss");
        try {
            JspWriter writer = pageContext.getOut();
            writer.write(localDateTime.toLocalDateTime().format(formatter));
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
