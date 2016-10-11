package se.liu.ida.tdp024.account.util.logger;

import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;

/*
 * 
 * This is an extremly simple implemenation of logger,
 * one should really consider writing a new one where
 * you implement a much bettern way of persisting logs.
 * An example would be using REST calls to Monlog.
 * 
 */

public class AccountLoggerImpl implements AccountLogger {
    HTTPHelper httpHelper = new HTTPHelperImpl();
            
    @Override
    public void log(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void log(AccountLoggerLevel accountLoggerLevel, String message) {
        if (accountLoggerLevel == AccountLoggerLevel.CRITICAL || accountLoggerLevel == AccountLoggerLevel.ERROR) {
            System.err.println(message);
            httpHelper.get("http://localhost:8090/write-log", "loglevel", accountLoggerLevel + "", "message", message + "");            
        } else {
            System.err.println(message);
            httpHelper.get("http://localhost:8090/write-log", "loglevel", accountLoggerLevel + "", "message", message + "");
        }
    }
}
