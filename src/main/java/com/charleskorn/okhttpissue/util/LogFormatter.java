package com.charleskorn.okhttpissue.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        String message = MessageFormat.format("{0,date,yyyy-MM-dd'T'hh:mm:ss.SSSZ} {1} {2} {3}\n", record.getMillis(), record.getLevel(), record.getLoggerName(), record.getMessage());

        if (record.getThrown() != null) {
            StringWriter writer = new StringWriter();
            record.getThrown().printStackTrace(new PrintWriter(writer));
            message += writer.toString() + "\n";
        }

        return message;
    }
}
