package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.StartPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;

@Feature("Тестирование покупки тура по по дебетовой карте")
public class PaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {

        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        SQLHelper.deleteTables();
    }

    @DisplayName("Проверяем карту со статусом APPROVED")
    @Test
    void shouldPaymentWithApprovedCard() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(1), getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.successfulPaymentDebitCard();
        String actual = SQLHelper.getStatusPayment();
        assertEquals("APPROVED", actual);
    }

    @DisplayName("Срок окончания действия карты: статус APPROVED")
    @Test
    void shouldPaymentWithApprovedCardExpires() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(0), getYearCard(0), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.successfulPaymentDebitCard();
        String actual = SQLHelper.getStatusPayment();
        assertEquals("APPROVED", actual);
    }

    @DisplayName("Проверяем карту со статусом DECLINED")
    @Test
    void shouldPaymentWithDeclinedCard() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getSecondCardNumber(), getMonthCard(0), getYearCard(1), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.invalidPaymentDebitCard();
        String actual = SQLHelper.getStatusPayment();
        assertEquals("DECLINED", actual);
    }

    @DisplayName("Срок окончания действия карты: статус DECLINED")
    @Test
    void shouldPaymentWithDeclinedCardExpires() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getSecondCardNumber(), getMonthCard(0), getYearCard(0), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.invalidPaymentDebitCard();
        String actual = SQLHelper.getStatusPayment();
        assertEquals("DECLINED", actual);
    }

    @DisplayName("Невалидный номер банковской карты: несуществующая карта")
    @Test
    void shouldPaymentWithInvalidCardNumber() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getInvalidCardNumber(), getMonthCard(2), getYearCard(1), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.invalidPaymentDebitCard();
    }

    @DisplayName("Невалидный номер банковской карты: 13 цифр")
    @Test
    void shouldPaymentWithInvalidCardNumberShort() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getInvalidShortCardNumber(), getMonthCard(2), getYearCard(1), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @DisplayName("Невалидный период действия карты")
    @Test
    void shouldPaymentExpiredCard() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(0), getYearCard(-1), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkCardExpired();
    }

    @DisplayName("Невалидный период действия карты")
    @Test
    void shouldPaymentIncorrectCardExpirationDate() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(-1), getYearCard(0), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @DisplayName("Невалидный период действия карты")
    @Test
    void shouldPaymentCardValidMoreThanFiveYears() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(1), getYearCard(6), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @DisplayName("Невалидный год: ввод менее 2 цифр")
    @Test
    void shouldPaymentCardInvalidYearOneDigit() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(2), getInvalidYearCard(), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @DisplayName("Данные о владельце карты указаны неверно: введено только Имя")
    @Test
    void shouldPaymentInvalidOwnerCard() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(1), getYearCard(3), getInvalidOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidOwner();
    }

    @DisplayName("Данные о владельце карты указаны неверно: имя и фамилия на кириллице")
    @Test
    void shouldPaymentInvalidOwnerCardInCyrillic() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(1), getYearCard(3),
                getInvalidOwnerCardCyrillic(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.incorrectOwner();
    }

    @DisplayName("Данные о владельце карты указаны неверно: цифры в имени")
    @Test
    void shouldPaymentInvalidOwnerCardWithNumbers() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(1), getYearCard(3),
                getInvalidOwnerCardWithNumbers(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.incorrectOwner();
    }

    @DisplayName("Данные о владельце карты указаны неверно: имя, состоящее из 1 буквы")
    @Test
    void shouldPaymentInvalidOwnerCardOneLetterName() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(1), getYearCard(3),
                getInvalidOwnerCardOneLetterName(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.incorrectOwner();
    }


    @DisplayName("Невалидный код CVC: ввод менее 3 цифр")
    @Test
    void shouldPaymentCardInvalidCvc() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(1), getYearCard(2), getOwnerCard(), getInvalidCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @DisplayName("Невалидный месяц: ввод менее 2 цифр")
    @Test
    void shouldPaymentCardInvalidMonthOneDigit() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getInvalidMonthCardOneDigit(), getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @DisplayName("Невалидный месяц: не входит в валидный интервал 01-12")
    @Test
    void shouldPaymentCardInvalidMonthInvalidPeriod() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getInvalidMonthCardInvalidPeriod(), getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @DisplayName("Невалидный месяц: 00")
    @Test
    void shouldPaymentCardInvalidMonth() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getInvalidMonthCard(), getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @DisplayName("Невалидные данные карты: поле Номер карты - не заполнено")
    @Test
    void shouldPaymentEmptyFieldNumberCard() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                null, getMonthCard(1), getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @DisplayName("Невалидные данные карты: поле месяц - не заполнено")
    @Test
    void shouldPaymentEmptyFieldMonth() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), null, getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @DisplayName("Невалидные данные карты: поле год - не заполнено")
    @Test
    void shouldPaymentEmptyFieldYears() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(2), null, getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @DisplayName("Невалидные данные карты: поле владелец - не заполнено")
    @Test
    void shouldPaymentEmptyFieldOwner() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(2), getYearCard(3), null, getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkEmptyField();
    }

    @DisplayName("Невалидные данные карты: поле CVC - не заполнено")
    @Test
    void shouldPaymentEmptyFieldCvc() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                getFirstCardNumber(), getMonthCard(2), getYearCard(3), getOwnerCard(), null);
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkEmptyField();
    }

    @DisplayName("Отправка пустой формы покупки тура")
    @Test
    void shouldPaymentEmptyAllField() {
        var startPage = new StartPage();
        DataHelper card = new DataHelper(
                null, null, null, null, null);
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkAllFieldsAreRequired();
    }

}
