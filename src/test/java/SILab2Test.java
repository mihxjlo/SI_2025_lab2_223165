import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class SILab2Test {

    @Test
    public void testEveryStatement() {
        // Тест случај 1: allItems е null
        RuntimeException exception1 = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(null, "1234567890123456");
        });
        assertEquals("allItems list can't be null!", exception1.getMessage());

        // Тест случај 2: Празна листа со валидна картичка
        List<Item> emptyList = new ArrayList<>();
        double result2 = SILab2.checkCart(emptyList, "1234567890123456");
        assertEquals(0.0, result2, 0.01);

        // Тест случај 3: Предмет со null име
        List<Item> itemsWithNullName = new ArrayList<>();
        itemsWithNullName.add(new Item(null, 1, 100, 0.0));
        RuntimeException exception3 = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(itemsWithNullName, "1234567890123456");
        });
        assertEquals("Invalid item!", exception3.getMessage());

        // Тест случај 4: Предмет со празно име
        List<Item> itemsWithEmptyName = new ArrayList<>();
        itemsWithEmptyName.add(new Item("", 1, 100, 0.0));
        RuntimeException exception4 = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(itemsWithEmptyName, "1234567890123456");
        });
        assertEquals("Invalid item!", exception4.getMessage());

        // Тест случај 5: Предмет кој активира попуст од 30 и има попуст
        List<Item> itemsWithDiscountAndBonus = new ArrayList<>();
        itemsWithDiscountAndBonus.add(new Item("Test", 15, 400, 0.1));
        double result5 = SILab2.checkCart(itemsWithDiscountAndBonus, "1234567890123456");
        assertEquals(5370.0, result5, 0.01); // 15 * 400 * 0.9 - 30 = 5370

        // Тест случај 6: Предмет кој не активира попуст од 30 и нема попуст
        List<Item> itemsNormal = new ArrayList<>();
        itemsNormal.add(new Item("Test", 5, 200, 0.0));
        double result6 = SILab2.checkCart(itemsNormal, "1234567890123456");
        assertEquals(1000.0, result6, 0.01); // 5 * 200 = 1000

        // Тест случај 7: Невалидна картичка (null)
        List<Item> itemsForCard = new ArrayList<>();
        itemsForCard.add(new Item("Test", 1, 100, 0.0));
        RuntimeException exception7 = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(itemsForCard, null);
        });
        assertEquals("Invalid card number!", exception7.getMessage());

        // Тест случај 8: Невалидна картичка (погрешна должина)
        RuntimeException exception8 = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(itemsForCard, "12345");
        });
        assertEquals("Invalid card number!", exception8.getMessage());

        // Тест случај 9: Картичка со невалиден карактер
        RuntimeException exception9 = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(itemsForCard, "123456789012345a");
        });
        assertEquals("Invalid character in card number!", exception9.getMessage());
    }

    @Test
    public void testMultipleCondition() {
        String validCard = "1234567890123456";

        // Тест случај 1: FFF (price ≤ 300, discount ≤ 0, quantity ≤ 10)
        List<Item> items1 = new ArrayList<>();
        items1.add(new Item("Test", 5, 200, 0.0));
        double result1 = SILab2.checkCart(items1, validCard);
        assertEquals(1000.0, result1, 0.01); // 5 * 200 = 1000, no discount of 30

        // Тест случај 2: FFT (price ≤ 300, discount ≤ 0, quantity > 10)
        List<Item> items2 = new ArrayList<>();
        items2.add(new Item("Test", 15, 200, 0.0));
        double result2 = SILab2.checkCart(items2, validCard);
        assertEquals(2970.0, result2, 0.01); // 15 * 200 - 30 = 2970

        // Тест случај 3: FTF (price ≤ 300, discount > 0, quantity ≤ 10)
        List<Item> items3 = new ArrayList<>();
        items3.add(new Item("Test", 5, 200, 0.1));
        double result3 = SILab2.checkCart(items3, validCard);
        assertEquals(870.0, result3, 0.01); // 5 * 200 * 0.9 - 30 = 870

        // Тест случај 4: FTT (price ≤ 300, discount > 0, quantity > 10)
        List<Item> items4 = new ArrayList<>();
        items4.add(new Item("Test", 15, 200, 0.1));
        double result4 = SILab2.checkCart(items4, validCard);
        assertEquals(2670.0, result4, 0.01); // 15 * 200 * 0.9 - 30 = 2670

        // Тест случај 5: TFF (price > 300, discount ≤ 0, quantity ≤ 10)
        List<Item> items5 = new ArrayList<>();
        items5.add(new Item("Test", 5, 400, 0.0));
        double result5 = SILab2.checkCart(items5, validCard);
        assertEquals(1970.0, result5, 0.01); // 5 * 400 - 30 = 1970

        // Тест случај 6: TFT (price > 300, discount ≤ 0, quantity > 10)
        List<Item> items6 = new ArrayList<>();
        items6.add(new Item("Test", 15, 400, 0.0));
        double result6 = SILab2.checkCart(items6, validCard);
        assertEquals(5970.0, result6, 0.01); // 15 * 400 - 30 = 5970

        // Тест случај 7: TTF (price > 300, discount > 0, quantity ≤ 10)
        List<Item> items7 = new ArrayList<>();
        items7.add(new Item("Test", 5, 400, 0.1));
        double result7 = SILab2.checkCart(items7, validCard);
        assertEquals(1770.0, result7, 0.01); // 5 * 400 * 0.9 - 30 = 1770

        // Тест случај 8: TTT (price > 300, discount > 0, quantity > 10)
        List<Item> items8 = new ArrayList<>();
        items8.add(new Item("Test", 15, 400, 0.1));
        double result8 = SILab2.checkCart(items8, validCard);
        assertEquals(5370.0, result8, 0.01); // 15 * 400 * 0.9 - 30 = 5370
    }
}