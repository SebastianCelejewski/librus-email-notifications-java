package pl.sebcel.librus.defaultaccountprovider.service;

import pl.sebcel.librus.accountprovider.api.AccountProvider;
import pl.sebcel.librus.accountprovider.api.LibrusAccount;

import java.util.ArrayList;
import java.util.List;

public class DefaultAccountProvider implements AccountProvider {

    @Override
    public List<LibrusAccount> getLibrusAccounts() {
        List<LibrusAccount> result = new ArrayList<>();

        return result;
    }
}