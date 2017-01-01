package pl.sebcel.librus.accountprovider.api;

import java.util.List;

public class LibrusAccount {

    private String login;
    private String password;
    private List<String> recipients;

    public LibrusAccount(String login, String password, List<String> recipients) {
        this.login = login;
        this.password = password;
        this.recipients = recipients;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }
}