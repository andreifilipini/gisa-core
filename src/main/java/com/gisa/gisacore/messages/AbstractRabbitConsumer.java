package com.gisa.gisacore.messages;

import com.gisa.gisacore.exception.InfraException;
import com.gisa.gisacore.util.StringUtil;
import org.slf4j.Logger;

public abstract class AbstractRabbitConsumer {

    protected abstract Logger getLogger();

    protected abstract void receive(String body);

    protected abstract void execute(String body);

    protected void executeLoggin(String body) {
        try {
            execute(body);
        } catch (InfraException ie) {
            logWarn(ie.getMessage(), body);
            throw ie;
        } catch (Exception e) {
            logError(e.getMessage(), body);
            throw e;
        }
    }

    protected void logWarn(String message, String body) {
        getLogger().warn(formatErrorMessage(message, body));
    }

    protected void logError(String message, String body) {
        getLogger().error(formatErrorMessage(message, body));
    }

    private String formatErrorMessage(String message, String body) {
        return String.format("message: %s; body: %s", message, StringUtil.isBlank(body) ? "null" : body);
    }
}
