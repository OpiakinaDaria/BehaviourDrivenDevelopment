import data.DataHelper;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFromSecondToFirstCardEnough() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var amount = 100;
        var dashboardPage = new DashboardPage();
        var cardInfoTo = DataHelper.getCardInfoToFirst();
        var startCardBalance = dashboardPage.getStartBalance(cardInfoTo);
        var choiceTo = dashboardPage.choiceTransferTo(cardInfoTo);
        choiceTo.moneyTransfer(cardInfoTo, amount);

        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdTo()), startCardBalance[0] + amount);
        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdFrom()), startCardBalance[1] - amount);
    }


    @Test
    void shouldTransferMoneyFromFirstToSecondCardEnough() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var amount = 100;
        var dashboardPage = new DashboardPage();
        var cardInfoTo = DataHelper.getCardInfoToSecond();
        var startCardBalance = dashboardPage.getStartBalance(cardInfoTo);
        var choiceTo = dashboardPage.choiceTransferTo(cardInfoTo);
        choiceTo.moneyTransfer(cardInfoTo, amount);

        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdTo()), startCardBalance[0] + amount);
        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdFrom()), startCardBalance[1] - amount);
    }


    @Test
    void shouldTransferMoneyFromSecondToFirstCardUnderZero() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var amount = -100;//
        var dashboardPage = new DashboardPage();
        var cardInfoTo = DataHelper.getCardInfoToFirst();
        var startCardBalance = dashboardPage.getStartBalance(cardInfoTo);
        var choiceTo = dashboardPage.choiceTransferTo(cardInfoTo);
        choiceTo.moneyTransfer(cardInfoTo, amount);

        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdTo()), startCardBalance[0] + Math.abs(amount));
        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdFrom()), startCardBalance[1] - Math.abs(amount));
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCardZero() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var amount = 0;
        var dashboardPage = new DashboardPage();
        var cardInfoTo = DataHelper.getCardInfoToSecond();
        var startCardBalance = dashboardPage.getStartBalance(cardInfoTo);
        var choiceTo = dashboardPage.choiceTransferTo(cardInfoTo);
        choiceTo.moneyTransfer(cardInfoTo, amount);

        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdTo()), startCardBalance[0] + amount);
        assertEquals(dashboardPage.getCardBalance(cardInfoTo.getCardIdFrom()), startCardBalance[1] - amount);
    }
}