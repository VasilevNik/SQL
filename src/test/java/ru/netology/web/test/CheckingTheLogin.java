package ru.netology.web.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DataSQL;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataSQL.cleanDataBase;

public class CheckingTheLogin {

    @AfterAll
    static void clean() {
        cleanDataBase();
    }

    LoginPage loginPage;

    @Test
    void shouldLogInSystem() {
        loginPage = open("http://localhost:9999", LoginPage.class);
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verificationPage = loginPage.validLogin(authInfo);
        verificationPage.VerificationPageVisible();
        DataHelper.VerificationCode verificationCode = DataSQL.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }
}
